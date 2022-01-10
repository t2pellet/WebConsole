package es.mesacarlos.webconsole.server.command;

import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.auth.ConnectedUser;
import es.mesacarlos.webconsole.auth.LoginManager;
import es.mesacarlos.webconsole.config.UserData;
import es.mesacarlos.webconsole.config.WCConfig;
import es.mesacarlos.webconsole.server.WCServer;
import es.mesacarlos.webconsole.util.Internationalization;
import es.mesacarlos.webconsole.server.response.JSONOutput;
import es.mesacarlos.webconsole.server.response.LoggedIn;
import es.mesacarlos.webconsole.server.response.LoginRequired;

import java.util.UUID;

public class LogInCommand implements WSCommand {
	
	@Override
	public void execute(WCServer.WCSocket socket, String address, String command) {
		// If user is logged in, then return.
		if (LoginManager.getInstance().isAddressConnected(address))
			return;
		
		//Check if user exists
		String password = command;
		for(UserData ud : WCConfig.getInstance().getAllUsers()) {
			if(ud.getPassword().equals(password)) {
				ConnectedUser user = new ConnectedUser(address, ud.getUsername(), UUID.randomUUID().toString(), ud.getUserType());
				LoginManager.getInstance().logIn(user);

				socket.sendToClient(new LoggedIn(Internationalization.getPhrase("login-sucessful-message"), "LOGIN ********", user.getUsername(), user.getUserType(), user.getToken()));
				WebConsole.LOGGER.info(Internationalization.getPhrase("login-sucessful-console", user.toString()));
				return;
			}
		}
		WebConsole.LOGGER.info(Internationalization.getPhrase("login-failed-console", address));
		socket.sendToClient(new LoginRequired(Internationalization.getPhrase("login-failed-message")));
	}

}