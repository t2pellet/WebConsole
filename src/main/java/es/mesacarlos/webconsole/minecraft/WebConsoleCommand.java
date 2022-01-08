package es.mesacarlos.webconsole.minecraft;

import java.util.ArrayList;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import es.mesacarlos.webconsole.WebConsole;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;
import es.mesacarlos.webconsole.auth.LoginManager;
import es.mesacarlos.webconsole.auth.ConnectedUser;
import es.mesacarlos.webconsole.util.Internationalization;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.literal;


public class WebConsoleCommand {

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean isDedicated) {
		dispatcher.register(literal("WebConsole")
				.executes(context -> {
					String version = FabricLoader.getInstance().getModContainer(WebConsole.MODID).get().getMetadata().getVersion().getFriendlyString();
					StringBuilder msg = new StringBuilder();
					msg.append(Internationalization.getPhrase("webconsole-version", version)).append("\n");
					ArrayList<ConnectedUser> users = LoginManager.getInstance().getLoggedInUsers();
					if (users.isEmpty()) {
						msg.append(Internationalization.getPhrase("webconsole-no-connections"));
					} else {
						msg.append(Internationalization.getPhrase("webconsole-active-connections")).append("\n");
						for (int i = 0; i < users.size(); i++) {
							ConnectedUser user = users.get(i);
							msg.append(user.toString());
							if(i+1 < users.size())
								msg.append("\n");
						}
					}
					context.getSource().sendFeedback(new LiteralText(msg.toString()), false);
					return Command.SINGLE_SUCCESS;
				}));
	}

}