package es.mesacarlos.webconsole.server;

import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.auth.ConnectedUser;
import es.mesacarlos.webconsole.auth.LoginManager;
import es.mesacarlos.webconsole.config.WCConfig;
import es.mesacarlos.webconsole.util.DateTimeUtils;
import es.mesacarlos.webconsole.util.Internationalization;
import es.mesacarlos.webconsole.util.JsonUtils;
import es.mesacarlos.webconsole.server.command.WSCommand;
import es.mesacarlos.webconsole.server.command.WSCommandFactory;
import es.mesacarlos.webconsole.server.response.*;
import fi.iki.elonen.NanoWSD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WCServer extends NanoWSD {
	private final HashMap<String, WSCommand> commands = WSCommandFactory.getCommandsHashMap();
	private final List<WCSocket> connections = new ArrayList<>();
	private final WebServer clientServer;

	public WCServer(String host, int port) {
		super(host, port);
		if (WCConfig.getInstance().useIntegratedWebServer) {
			clientServer = new WebServer(host, WCConfig.getInstance().clientPort);
		} else clientServer = null;
	}

	@Override
	protected WebSocket openWebSocket(IHTTPSession handshake) {
		return new WCSocket(handshake);
	}

	@Override
	public void start(int timeout, boolean daemon) throws IOException {
		super.start(timeout, daemon);
		if (clientServer != null) clientServer.start(timeout, daemon);
		WebConsole.LOGGER.info(Internationalization.getPhrase("started-websocket"));
	}

	/**
	 * Sends the message to all connected AND logged-in users
	 */
	public void onNewConsoleLinePrinted(String line) {
		for (WCSocket connection : connections) {
			if (LoginManager.getInstance().isAddressConnected(connection.getHandshakeRequest().getRemoteIpAddress()))
				connection.sendToClient(new ConsoleOutput(line, DateTimeUtils.getTimeAsString()));
		}
	}

	public class WCSocket extends WebSocket {

		public WCSocket(IHTTPSession handshakeRequest) {
			super(handshakeRequest);
		}

		@Override
		protected void onOpen() {
			var address = getHandshakeRequest().getRemoteIpAddress();
			ConnectedUser user = LoginManager.getInstance().getUser(address);
			if (LoginManager.getInstance().isAddressConnected(address)) {
				sendToClient(new LoggedIn(Internationalization.getPhrase("connection-resumed-message"), null, user.getUsername(), user.getUserType(), user.getToken()));
				WebConsole.LOGGER.info(Internationalization.getPhrase("connection-resumed-console", address));
			} else {
				sendToClient(new LoginRequired(Internationalization.getPhrase("connection-login-message")));
				WebConsole.LOGGER.info(Internationalization.getPhrase("connection-login-console", address));
			}
			connections.add(this);
		}

		@Override
		protected void onClose(WebSocketFrame.CloseCode code, String reason, boolean initiatedByRemote) {
			String address = getHandshakeRequest().getRemoteIpAddress();
			LoginManager.getInstance().logOut(address);
			WebConsole.LOGGER.info(Internationalization.getPhrase("closed-connection", address));
			connections.remove(this);
		}

		@Override
		protected void onMessage(WebSocketFrame message) {
			String messageStr = message.getTextPayload();
			if(!JsonUtils.containsStringProperty(messageStr, "command") //Contains a command
					|| ( !JsonUtils.containsStringProperty(messageStr, "token") && !JsonUtils.getStringProperty(messageStr, JsonUtils.COMMAND_PROPERTY).equals("LOGIN")) //Contains a token or it is a login command
			)
				return;

			// Get command and params
			String wsCommand = JsonUtils.getStringProperty(messageStr, JsonUtils.COMMAND_PROPERTY);
			String wsToken = JsonUtils.getStringProperty(messageStr, JsonUtils.TOKEN_PROPERTY);
			String wsCommandParams = JsonUtils.getStringProperty(messageStr, JsonUtils.PARAMS_PROPERTY);

			// Run command
			WSCommand cmd = commands.get(wsCommand);
			String address = getHandshakeRequest().getRemoteIpAddress();
			if (cmd == null) {
				// Command does not exist
				sendToClient(new UnknownCommand(Internationalization.getPhrase("unknown-command-message"), messageStr));
				WebConsole.LOGGER.info(Internationalization.getPhrase("unknown-command-console", messageStr));
			} else if (!wsCommand.equals("LOGIN")
					&& !LoginManager.getInstance().isLoggedIn(address, wsToken)) {
				// User is not authorised. DO NOTHING, IMPORTANT!
				sendToClient(new LoginRequired(Internationalization.getPhrase("forbidden-message")));
				WebConsole.LOGGER.warn(Internationalization.getPhrase("forbidden-console", address, messageStr));
			} else {
				cmd.execute(this, address, wsCommandParams);
			}
		}

		@Override
		protected void onPong(WebSocketFrame pong) {
		}

		@Override
		protected void onException(IOException exception) {
			WebConsole.LOGGER.warn(Internationalization.getPhrase("error-on-connection", getHandshakeRequest().getRemoteIpAddress(), exception.getMessage()));
		}

		/**
		 * Sends this JSONOutput to client
		 * @param content JSONOutput object
		 */
		public void sendToClient(JSONOutput content) {
			try {
				send(content.toJSON());
			}catch(IOException e) {
				LoginManager.getInstance().logOut(getHandshakeRequest().getRemoteIpAddress());
				WebConsole.LOGGER.warn(Internationalization.getPhrase("error-disconnected-client"));
			}
		}
	}

}