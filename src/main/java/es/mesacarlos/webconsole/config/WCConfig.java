package es.mesacarlos.webconsole.config;

import java.net.InetSocketAddress;
import java.util.*;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import es.mesacarlos.webconsole.WebConsole;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = WebConsole.MODID)
public class WCConfig implements ConfigData {
	private static WCConfig instance;

	public static WCConfig getInstance() {
		if(instance == null)
			instance = AutoConfig.getConfigHolder(WCConfig.class).getConfig();
		return instance;
	}

	@ConfigEntry.Category("SSL")
	public boolean useSSL = false;
	@ConfigEntry.Category("SSL")
	public String StoreType = "JKS";
	@ConfigEntry.Category("SSL")
	public String KeyStore = "plugins/WebConsole/keystore.jks";
	@ConfigEntry.Category("SSL")
	public String StorePassword = "storepassword";
	@ConfigEntry.Category("SSL")
	public String KeyPassword = "keypassword";

	@ConfigEntry.Category("Connection")
	public String host = "127.0.0.1";
	@ConfigEntry.Category("Connection")
	public boolean useIntegratedWebServer = true;
	@ConfigEntry.Category("Connection")
	public int port = 8080;

	@ConfigEntry.Category("Language")
	public String language = "en";

	@ConfigEntry.Category("User Data")
	@ConfigEntry.Gui.CollapsibleObject
	public List<UserData> users = new ArrayList<>(List.of(new UserDataImpl("admin", "admin", UserType.ADMIN, false, false, null)));

	public boolean isSslEnabled() {
		return useSSL;
	}

	public String getStoreType() {
		return StoreType;
	}

	public String getKeyStore() {
		return KeyStore;
	}

	public String getStorePassword() {
		return StorePassword;
	}

	public String getKeyPassword() {
		return KeyPassword;
	}

	/**
	 * Get host and port fields already as InetSocketAddress
	 * @return InetSocketAddress created from the fields host and port of config.yml
	 */
	public InetSocketAddress getSocketAdress() {
		return new InetSocketAddress(host, port);
	}

	/**
	 * Language code from config.yml
	 * @return language code
	 */
	public String getLanguage() {
		return language;
	}


	/**
	 * Get all admins as a UserData list
	 * @return list of admin users
	 */
	private List<UserData> getAdmins() {
		return users.stream().filter(u -> u.getUserType() == UserType.ADMIN).toList();
	}

	/**
	 * Get all viewers as a UserData list
	 * @return list of viewer users
	 */
	private List<UserData> getViewers() {
		return users.stream().filter(u -> u.getUserType() == UserType.VIEWER).toList();
	}

	/**
	 * Get all registered users
	 * @return All Admin and Viewer users inside config.yml
	 */
	public List<UserData> getAllUsers(){
		return users;
	}

}