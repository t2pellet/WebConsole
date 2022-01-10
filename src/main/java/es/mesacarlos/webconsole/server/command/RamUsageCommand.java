package es.mesacarlos.webconsole.server.command;

import es.mesacarlos.webconsole.server.WCServer;
import es.mesacarlos.webconsole.util.Internationalization;
import es.mesacarlos.webconsole.server.response.JSONOutput;
import es.mesacarlos.webconsole.server.response.RamUsage;

public class RamUsageCommand implements WSCommand {

	@Override
	public void execute(WCServer.WCSocket socket, String address, String command) {
		Runtime r = Runtime.getRuntime();
		
		long free = r.freeMemory() / 1024 / 1024;
		long max = r.maxMemory() / 1024 / 1024;
		long used = r.totalMemory() / 1024 / 1024 - free;

		socket.sendToClient(new RamUsage(
				Internationalization.getPhrase("ram-usage-message", free, used, max),
				free,
				used,
				max
		));
	}

}