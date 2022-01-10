package es.mesacarlos.webconsole.server;

import com.google.gson.JsonObject;
import es.mesacarlos.webconsole.WCConstants;
import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.config.WCConfig;
import fi.iki.elonen.NanoHTTPD;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class WebServer extends NanoHTTPD {

    private static final Map<String, String> TYPES = new HashMap<>() {{
        put("css", "text/css");
        put("html", "text/html");
        put("js", "application/javascript");
    }};

    WebServer(String host, int port) {
        super(host, port);
    }

    @Override
    public void start(int timeout, boolean daemon) throws IOException {
        super.start(timeout, daemon);
        // Create settings file
        File settingsFile = new File(WebConsole.WEB_PATH, "settings.json");
        JsonObject settingsJson = new JsonObject();
        settingsJson.addProperty("port", WCConstants.IMPLICIT_SOCKET_PORT);
        settingsJson.addProperty("host", WCConstants.IMPLICIT_SOCKET_HOST);
        settingsJson.addProperty("ssl", WCConfig.getInstance().isSslEnabled());
        BufferedWriter bw = new BufferedWriter(new FileWriter(settingsFile));
        bw.write(settingsJson.toString());
        bw.close();
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (session.getMethod() == Method.GET) {
            String path = WebConsole.WEB_PATH + session.getUri();
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
