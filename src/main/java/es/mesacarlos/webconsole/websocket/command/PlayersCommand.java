package es.mesacarlos.webconsole.websocket.command;

import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.util.Internationalization;
import es.mesacarlos.webconsole.websocket.WSServer;
import es.mesacarlos.webconsole.websocket.response.Players;
import org.java_websocket.WebSocket;

import java.util.List;

public class PlayersCommand implements WSCommand{

	@Override
	public void execute(WSServer wsServer, WebSocket conn, String params) {
		List<String> connectedPlayersList = List.of(WebConsole.getMCServer().getPlayerNames());

		int connectedPlayers = connectedPlayersList.size();
		int maxPlayers = WebConsole.getMCServer().getMaxPlayerCount();
		
		wsServer.sendToClient(conn, 
			new Players(
				Internationalization.getPhrase("players-message", connectedPlayers, maxPlayers),
				connectedPlayers,
				maxPlayers,
				connectedPlayersList
			));
	}
	
}