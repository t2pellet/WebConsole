package es.mesacarlos.webconsole.server.command;

import es.mesacarlos.webconsole.server.WCServer;

public interface WSCommand {
	void execute(WCServer.WCSocket socket, String address, String command);
}