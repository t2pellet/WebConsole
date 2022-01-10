package es.mesacarlos.webconsole.server.command;

import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.server.WCServer;
import es.mesacarlos.webconsole.util.Internationalization;
import es.mesacarlos.webconsole.server.response.JSONOutput;
import es.mesacarlos.webconsole.server.response.Players;

import java.util.List;

public class PlayersCommand implements WSCommand{

	@Override
	public void execute(WCServer.WCSocket socket, String address, String command) {
		List<String> connectedPlayersList = List.of(WebConsole.getMCServer().getPlayerNames());

		int connectedPlayers = connectedPlayersList.size();
		int maxPlayers = WebConsole.getMCServer().getMaxPlayerCount();

		socket.sendToClient(new Players(
				Internationalization.getPhrase("players-message", connectedPlayers, maxPlayers),
				connectedPlayers,
				maxPlayers,
				connectedPlayersList
		));
	}
	
}