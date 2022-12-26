package es.mesacarlos.webconsole.server.command;

import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.auth.ConnectedUser;
import es.mesacarlos.webconsole.auth.LoginManager;
import es.mesacarlos.webconsole.config.UserData;
import es.mesacarlos.webconsole.config.UserType;
import es.mesacarlos.webconsole.config.WCConfig;
import es.mesacarlos.webconsole.server.WCServer;
import es.mesacarlos.webconsole.util.Internationalization;
import net.minecraft.command.CommandException;
import net.minecraft.server.command.ServerCommandSource;

public class ExecCommand implements WSCommand {
	LoginManager loginManager = LoginManager.getInstance();

	@Override
	public void execute(WCServer.WCSocket socket, String address, String command) {
		ConnectedUser u = LoginManager.getInstance().getUser(address);
		if(u == null || u.getUserType() != UserType.ADMIN) {
			if(u != null)
				WebConsole.LOGGER.warn(Internationalization.getPhrase("no-send-permission-console", u, command));
			return;
		}

		boolean allowCommand = checkWhitelist(address, command);
		if (!allowCommand) {
			WebConsole.LOGGER.warn(Internationalization.getPhrase("no-send-permission-console", u, command));
			return;
		}
		
		WebConsole.LOGGER.info(Internationalization.getPhrase("cmd-executed-console", address, Internationalization.utf8ToIso(command)));
		try {
			ServerCommandSource source = WebConsole.getMCServer().getCommandSource();
			WebConsole.getMCServer().getCommandManager().executeWithPrefix(source, command);
		} catch (CommandException e) {
			WebConsole.LOGGER.error(e.getMessage());
		}
	}
	
	private boolean checkWhitelist(String address, String command) {
		for(UserData ud : WCConfig.getInstance().getAllUsers()) {
			if (ud.getUsername().equals(loginManager.getUser(address).getUsername())) {

				if (!ud.isWhitelistEnabled()) { //Skip whitelist check.
					return true;
				}

				String[] splitCommand = command.split(" ");

				for (String whitelistedCommand : ud.getWhitelistedCommands()) {
					String[] splitWhitelistedCommand = whitelistedCommand.split(" ");

					if(equalsArray(splitCommand, splitWhitelistedCommand)) {
						//Command matches the whitelist
						if(ud.isWhitelistActsAsBlacklist())
							return false; //If acts as blacklist, do not allow command
						else
							return true; //If acts as Whitelist, allow command
					}
				}
				
				//If execution reached this point, then the command is not in the blacklist.
				if(ud.isWhitelistActsAsBlacklist())
					return true; //If acts as blacklist, allow command
				else
					return false; //If acts as Whitelist, do not allow command
			}
		}
		throw new RuntimeException("No user matched the whitelist check.");
	}
	
	/**
	 * Check if the user command matches the whitelisted command
	 * 
	 * @param splitCommand Command sent by user
	 * @param splitWhitelistedCommand Command in the whitelist
	 * @return true if the user command matches the whitelist command
	 */
	private boolean equalsArray(String[] splitCommand, String[] splitWhitelistedCommand) {
		for (int i = 0; i < splitWhitelistedCommand.length; i++)
			if (!splitCommand[i].equalsIgnoreCase(splitWhitelistedCommand[i])) 
				return false; //Does not match so far
		return true; //Matches the command
	}

}