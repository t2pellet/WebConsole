package es.mesacarlos.webconsole.websocket;

import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.auth.LoginManager;
import es.mesacarlos.webconsole.util.DateTimeUtils;
import es.mesacarlos.webconsole.util.Internationalization;
import es.mesacarlos.webconsole.util.JsonUtils;
import es.mesacarlos.webconsole.websocket.command.WSCommand;
import es.mesacarlos.webconsole.websocket.command.WSCommandFactory;
import es.mesacarlos.webconsole.websocket.response.*;
import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;

public class WSServer extends WebSocketServer {
	private HashMap<String, WSCommand> commands = WSCommandFactory.getCommandsHashMap();

	public WSServer(InetSocketAddress address) {
		super(address);
		setReuseAddr(true);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		if (LoginManager.getInstance().isSocketConnected(conn.getRemoteSocketAddress())) {
			sendToClient(conn, new LoggedIn(Internationalization.getPhrase("connection-resumed-message")));
			WebConsole.LOGGER.info(Internationalization.getPhrase("connection-resumed-console", conn.getRemoteSocketAddress()));
		} else {
			sendToClient(conn, new LoginRequired(Internationalization.getPhrase("connection-login-message")));
			WebConsole.LOGGER.info(Internationalization.getPhrase("connection-login-console", conn.getRemoteSocketAddress()));
		}
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		if(!JsonUtils.containsStringProperty(message, "command") //Contains a command
				|| ( !JsonUtils.containsStringProperty(message, "token") && !JsonUtils.getStringProperty(message, JsonUtils.COMMAND_PROPERTY).equals("LOGIN")) //Contains a token or it is a login command
			)
			return;
		
		// Get command and params
		String wsCommand = JsonUtils.getStringProperty(message, JsonUtils.COMMAND_PROPERTY);
		String wsToken = JsonUtils.getStringProperty(message, JsonUtils.TOKEN_PROPERTY);
		String wsCommandParams = JsonUtils.getStringProperty(message, JsonUtils.PARAMS_PROPERTY);

		// Run command
		WSCommand cmd = commands.get(wsCommand);

		if (cmd == null) {
			// Command does not exist
			sendToClient(conn, new UnknownCommand(Internationalization.getPhrase("unknown-command-message"), message));
			WebConsole.LOGGER.info(Internationalization.getPhrase("unknown-command-console", message));
		} else if (!wsCommand.equals("LOGIN")
				&& !LoginManager.getInstance().isLoggedIn(conn.getRemoteSocketAddress(), wsToken)) {
			// User is not authorised. DO NOTHING, IMPORTANT!
			sendToClient(conn, new LoginRequired(Internationalization.getPhrase("forbidden-message")));
			WebConsole.LOGGER.warn(Internationalization.getPhrase("forbidden-console", conn.getRemoteSocketAddress(), message));
		} else {
			cmd.execute(this, conn, wsCommandParams);
		}
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		LoginManager.getInstance().logOut(conn.getRemoteSocketAddress());
		WebConsole.LOGGER.info(Internationalization.getPhrase("closed-connection", conn.getRemoteSocketAddress()));
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		WebConsole.LOGGER.warn(Internationalization.getPhrase("error-on-connection", conn.getRemoteSocketAddress(), ex));
	}

	@Override
	public void onStart() {
		WebConsole.LOGGER.info(Internationalization.getPhrase("started-websocket"));
	}

	/**
	 * Sends the message to all connected AND logged-in users
	 */
	public void onNewConsoleLinePrinted(String line) {
		Collection<WebSocket> connections = getConnections();
		for (WebSocket connection : connections) {
			if (LoginManager.getInstance().isSocketConnected(connection.getRemoteSocketAddress()))
				sendToClient(connection, new ConsoleOutput(line, DateTimeUtils.getTimeAsString()));
		}
	}

	/**
	 * Sends this JSONOutput to client
	 * @param conn    Connection to client
	 * @param content JSONOutput object
	 */
	public void sendToClient(WebSocket conn, JSONOutput content) {
		try {
			conn.send(content.toJSON());
		}catch(WebsocketNotConnectedException e) {
			LoginManager.getInstance().logOut(conn.getRemoteSocketAddress());
			WebConsole.LOGGER.warn(Internationalization.getPhrase("error-disconnected-client"));
		}
		
	}

}