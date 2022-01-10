package es.mesacarlos.webconsole;

import es.mesacarlos.webconsole.config.WCConfig;
import es.mesacarlos.webconsole.minecraft.WebConsoleCommand;
import es.mesacarlos.webconsole.server.WCServer;
import es.mesacarlos.webconsole.util.Internationalization;
import es.mesacarlos.webconsole.util.LogFilter;
import fi.iki.elonen.NanoHTTPD;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.lingala.zip4j.ZipFile;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;

import javax.net.ssl.KeyManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyStore;

public class WebConsole implements ModInitializer {

	public static final String MODID = "webconsole";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final String WEB_PATH = FabricLoader.getInstance().getGameDir().toString() + "/webconsole";
	// MC Server
	private static MinecraftServer mcServer;

	// Websocket server and thread
	private WCServer server;
	private Thread wsThread;

	public static MinecraftServer getMCServer() {
		return mcServer;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing WebConsole...");

		// Register Config
		AutoConfig.register(WCConfig.class, JanksonConfigSerializer::new);

		//Change language to user-specified language.
		Internationalization.setCurrentLocale(WCConfig.getInstance().getLanguage());

		// Register command
		CommandRegistrationCallback.EVENT.register(WebConsoleCommand::register);

		// Unzip server
		File wcFolder = new File(WEB_PATH);
		// Extract server if it isn't there
		File htmlFile = new File(wcFolder, "index.html");
		if (!htmlFile.exists()) {
			LOGGER.info(Internationalization.getPhrase("unzip"));
			wcFolder.mkdirs();
			try {
				// Copy zip
				InputStream is = WebConsole.class.getResourceAsStream("/client.zip");
				File wcZipFile = new File(wcFolder, "client.zip");
				Files.copy(is, wcZipFile.toPath());
				// Unzip
				ZipFile zipFile = new ZipFile(wcZipFile);
				zipFile.extractAll(wcFolder.getAbsolutePath());
				LOGGER.info(Internationalization.getPhrase("unzip-success"));
			} catch (IOException ex) {
				LOGGER.error(Internationalization.getPhrase("unzip-error") + ex.getMessage());
				ex.printStackTrace();
			}
		}

		// On Server Start
		ServerLifecycleEvents.SERVER_STARTING.register(mcServer -> {
			WebConsole.mcServer = mcServer;
			//Start WebSocket Server
			try {
				startWS();
			} catch (Exception e) {
				LOGGER.warn(Internationalization.getPhrase("boot-error"));
				e.printStackTrace();
			}
			//This filter is used to read the whole console.
			Filter f = new LogFilter(server);
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
		server = new WCServer(WCConfig.getInstance().host, 80);

		if(WCConfig.getInstance().isSslEnabled()) {
			// Configure SSL
			String STORETYPE = WCConfig.getInstance().getStoreType();
			String KEYSTORE = WCConfig.getInstance().getKeyStore();
			String STOREPASSWORD = WCConfig.getInstance().getStorePassword();
			String KEYPASSWORD = WCConfig.getInstance().getKeyPassword();
			
			KeyStore ks = KeyStore.getInstance(STORETYPE);
			File kf = new File(KEYSTORE);
			ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, KEYPASSWORD.toCharArray());
			server.setServerSocketFactory(new NanoHTTPD.SecureServerSocketFactory(NanoHTTPD.makeSSLSocketFactory(ks, kmf), null));
		}

		// Start Server
		wsThread = new Thread(() -> {
			try {
				server.start(60000, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		wsThread.start();
	}
}