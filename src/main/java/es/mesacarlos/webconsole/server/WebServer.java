package es.mesacarlos.webconsole.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import es.mesacarlos.webconsole.WCConstants;
import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.config.WCConfig;
import es.mesacarlos.webconsole.util.Internationalization;
import fi.iki.elonen.NanoHTTPD;
import net.lingala.zip4j.ZipFile;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static es.mesacarlos.webconsole.WebConsole.WEB_PATH;

class WebServer extends NanoHTTPD {

    private static final Map<String, String> TYPES = new HashMap<>() {{
        put("css", "text/css");
        put("html", "text/html");
        put("js", "application/javascript");
        put("json", "application/json");
    }};

    WebServer(String host, int port) {
        super(host, port);
    }

    @Override
    public void start(int timeout, boolean daemon) throws IOException {
        super.start(timeout, daemon);
        File settingsFile = new File(WEB_PATH, "settings.json");
        // Update client server if necessary
        int revision = 0;
        if (settingsFile.exists()) {
            FileReader reader  = new FileReader(settingsFile);
            JsonElement revisionElement = JsonParser.parseReader(reader).getAsJsonObject().get("revision");
            if (revisionElement != null) revision = revisionElement.getAsInt();
            reader.close();
        }
        if (revision < WCConstants.CLIENT_REVISION) {
            updateServer();
        }

        // Create settings file
        Files.deleteIfExists(settingsFile.toPath());
        JsonObject settingsJson = new JsonObject();
        settingsJson.addProperty("revision", WCConstants.CLIENT_REVISION);
        settingsJson.addProperty("ssl", WCConfig.getInstance().isSslEnabled());
        settingsJson.addProperty("port", WCConfig.getInstance().socketPort);
        settingsJson.addProperty("host", "local");
        BufferedWriter bw = new BufferedWriter(new FileWriter(settingsFile));
        bw.write(settingsJson.toString());
        bw.close();
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (session.getMethod() == Method.GET) {
            String path = WEB_PATH + session.getUri();
            if (session.getUri().equals("/")) {
                path += "index.html";
            }
            String suffix = path.substring(path.lastIndexOf(".") + 1);
            String type = TYPES.get(suffix);
            return newFixedLengthResponse(Response.Status.OK, type, readFile(path));
        } else {
            return null;
        }
    }

    private static void updateServer() {
        // Delete old server if applicable
        File wcFolder = new File(WEB_PATH);
        if (wcFolder.exists()) {
            var files = wcFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        } else {
            wcFolder.mkdirs();
        }
        // Extract server
        WebConsole.LOGGER.info(Internationalization.getPhrase("unzip"));
        try {
            // Copy zip
            InputStream is = WebConsole.class.getResourceAsStream("/client.zip");
            File wcZipFile = new File(wcFolder, "client.zip");
            Files.copy(is, wcZipFile.toPath());
            // Unzip
            ZipFile zipFile = new ZipFile(wcZipFile);
            zipFile.extractAll(wcFolder.getAbsolutePath());
            Files.deleteIfExists(wcZipFile.toPath());
            WebConsole.LOGGER.info(Internationalization.getPhrase("unzip-success"));
        } catch (IOException ex) {
            WebConsole.LOGGER.error(Internationalization.getPhrase("unzip-error") + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String readFile(String path) {
        // Read index html
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
}
