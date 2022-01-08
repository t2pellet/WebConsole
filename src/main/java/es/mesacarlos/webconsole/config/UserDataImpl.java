package es.mesacarlos.webconsole.config;

import java.util.List;

class UserDataImpl implements UserData {
	public String username;
	public String password;
	public UserType userType;
	public boolean isWhitelistEnabled;
	public boolean isWhitelistActsAsBlacklist;
	public List<String> whitelistedCommands;
	
	UserDataImpl(String username, String password, UserType userType,
						boolean isWhitelistEnabled, boolean isWhitelistActsAsBlacklist, List<String> whitelistedCommands) {
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.isWhitelistEnabled = isWhitelistEnabled;
		this.isWhitelistActsAsBlacklist = isWhitelistActsAsBlacklist;
		this.whitelistedCommands = whitelistedCommands;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public boolean isWhitelistEnabled() {
		return isWhitelistEnabled;
	}

	public boolean isWhitelistActsAsBlacklist() {
		return isWhitelistActsAsBlacklist;
	}

	public List<String> getWhitelistedCommands() {
		return whitelistedCommands;
	}
}