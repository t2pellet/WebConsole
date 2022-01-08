package es.mesacarlos.webconsole.config;

import java.util.List;

public interface UserData {

    String getUsername();

    String getPassword();

    UserType getUserType();

    boolean isWhitelistEnabled();

    boolean isWhitelistActsAsBlacklist();

    List<String> getWhitelistedCommands();

}
