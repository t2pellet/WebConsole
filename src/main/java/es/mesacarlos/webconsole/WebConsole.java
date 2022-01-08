package es.mesacarlos.webconsole;

import es.mesacarlos.webconsole.config.WebConsoleConfig;
import es.mesacarlos.webconsole.minecraft.WebConsoleCommand;
import es.mesacarlos.webconsole.util.Internationalization;
import es.mesacarlos.webconsole.util.LogFilter;
import es.mesacarlos.webconsole.websocket.WSServer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

public class WebConsole implements ModInitializer {

	public static final String MODID = "webconsole";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	// MC Server
	private static MinecraftServer mcServer;

	// Websocket server and thread
	private WSServer server;
	private Thread wsThread;

	public static MinecraftServer getMCServer() {
		return mcServer;
	}

	@Override
	public void onInitialize() {
		// Register Config
		AutoConfig.register(WebConsoleConfig.class, JanksonConfigSerializer::new);

		//Change language to user-specified language.
		Internationalization.setCurrentLocale(WebConsoleConfig.getInstance().getLanguage());

		// Register command
		CommandRegistrationCallback.EVENT.register(WebConsoleCommand::register);

		// On Server Start
		ServerLifecycleEvents.SERVER_STARTING.register(mcServer -> {
			this.mcServer = mcServer;
			//Start WebSocket Server
			try {
				startWS();
			} catch (Exception e) {
				LOGGER.warn(Internationalization.getPhrase("boot-error"));
				e.printStackTrace();
			}
			//This filter is used to read the whole console.
			Filter f = new LogFilter(getWSServer());
			((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(f);
		});
		// On Server Stop
		ServerLifecycleEvents.SERVER_STOPPING.register(mcServer -> {
			try {
				server.stop();
				wsThread = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Start WebSocket server
	 */
	private void startWS() throws Exception {
		// Create WebSocket server
		server = new WSServer(WebConsoleConfig.getInstance().getSocketAdress());
		
		if(WebConsoleConfig.getInstance().isSslEnabled()) {
			// Configure SSL
			String STORETYPE = WebConsoleConfig.getInstance().getStoreType();
			String KEYSTORE = WebConsoleConfig.getInstance().getKeyStore();
			String STOREPASSWORD = WebConsoleConfig.getInstance().getStorePassword();
			String KEYPASSWORD = WebConsoleConfig.getInstance().getKeyPassword();
			
			KeyStore ks = KeyStore.getInstance(STORETYPE);
			File kf = new File(KEYSTORE);
			ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, KEYPASSWORD.toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ks);
			
			SSLContext sslContext = null;
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			
			server.setWebSocketFactory(new DefaultSSLWebSocketServerFactory(sslContext));
		}

		// Start Server
		wsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				server.run();
			}
		});
		wsThread.start();
	}

	public WSServer getWSServer() {
		return server;
	}
}