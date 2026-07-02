package api;

import constants.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private static final Logger LOGGER = LogManager.getLogger(ApiClient.class);
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public static JSONObject post(String url, String body, boolean includeLocaleHeader)
            throws IOException, InterruptedException, ParseException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json, text/plain, */*")
                .header("content-type", "application/json")
                .header("Version", AppConstants.API_VERSION)
                .header("channel", "APP")
                .header("source", "WEB_USER")
                .header("token-id", AppConstants.API_TOKEN)
                .POST(HttpRequest.BodyPublishers.ofString(body));

        if (includeLocaleHeader) {
            builder.header("x-locale", "en");
        }

        HttpResponse<String> response = HTTP_CLIENT.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        LOGGER.info("POST {} -> HTTP {}", url, response.statusCode());
        LOGGER.info("Response body: {}", response.body());

        return (JSONObject) new JSONParser().parse(response.body());
    }
}