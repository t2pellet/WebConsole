package es.mesacarlos.webconsole.auth;

import es.mesacarlos.webconsole.config.UserType;
import es.mesacarlos.webconsole.util.Internationalization;

public class ConnectedUser {
	private String username;
	private String socketAddress;
	private String token;
	private UserType userType;
	
	public ConnectedUser(String address, String username, String token, UserType userType) {
		this.socketAddress = address;
		this.username = username;
		this.token = token;
		this.userType = userType;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getAddress() {
		return socketAddress;
	}
	
	public String getToken() {
		return token;
	}
	
	public UserType getUserType() {
		return userType;
	}
	
	public String toString() {
		return Internationalization.getPhrase("user-tostring", username, socketAddress, userType);
	}
}