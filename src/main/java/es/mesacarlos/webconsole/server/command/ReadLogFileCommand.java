package es.mesacarlos.webconsole.server.command;

import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.server.WCServer;
import es.mesacarlos.webconsole.server.response.ConsoleOutput;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadLogFileCommand implements WSCommand{

	@Override
	public void execute(WCServer.WCSocket socket, String address, String command) {
		List<String> lines = null;
		try {
			 lines = Files.readAllLines(Paths.get("logs/latest.log"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			try {
				lines = Files.readAllLines(Paths.get("logs/latest.log"), StandardCharsets.ISO_8859_1);
			} catch (IOException e1) {
				WebConsole.LOGGER.error(e.getMessage());
			}
		} finally {
			if (lines != null) {
				for (String line : lines) {
					socket.sendToClient(new ConsoleOutput(line, null));
				}
			}
		}
	}

}