package com.example.sample.exam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * General interface to the event-schema REST services
 */
public class RestApiClientExam {
    public static final String URL_LOCAL_HOST = "https://localhost:8080/get";

    private final String url;

    public RestApiClientExam(final String url) {
        this.url = (url == null || url.isBlank()) ? URL_LOCAL_HOST : url;
    }

    /**
     * HTTP GET request.
     *
     * @return The jsonObject of the return results. An empty Map signified success. Otherwise, the
     * Json map has the detailed info on why validation failed.
     * @throws IOException          on http connection failures.
     * @throws InterruptedException on http connection failures
     */
    public Map<String, Object> get() throws IOException, InterruptedException {
        final String url = this.url;
        final HttpClient client = HttpClient.newHttpClient();
        //final HttpClient client = HttpClient.newBuilder().connectTimeout(?).proxy(?).build();

        final HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
                        .header("Accept", "application/json").version(HttpClient.Version.HTTP_1_1).build();

        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        final Map<String, Object> jsonObj;
        if (response.statusCode() != 200) {
            jsonObj = new HashMap<>();
            jsonObj.put("service", "failed");
            jsonObj.put("code", response.statusCode());
            jsonObj.put("message", response.body());
        } else {
            // Parse response body
            // jsonObj = JsonParser.to(response.body());

            //temp
            System.out.println(response.body());
            jsonObj = new HashMap<>();
            jsonObj.put("message", response.body());
        }
        return jsonObj;
    }

    /**
     * HTTP POST Request.
     *
     * @param data - json content to be http body.
     * @return The jsonObject of the return results. An empty Map signified success. Otherwise, the
     * Json map has the detailed info on why validation failed.
     * @throws IOException          on http connection failures.
     * @throws InterruptedException on http connection failures
     */
    public Map<String, Object> post(final String data) throws IOException, InterruptedException {
        final String url = this.url;
        final HttpClient client = HttpClient.newHttpClient();
        //final HttpClient client = HttpClient.newBuilder().connectTimeout(?).proxy(?).build();
        final HttpRequest.BodyPublisher bp = HttpRequest.BodyPublishers.ofString(data);

        final HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
                        .header("Accept", "application/json").version(HttpClient.Version.HTTP_1_1).POST(bp).build();

        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        final Map<String, Object> jsonObj;
        if (response.statusCode() != 200) {
            jsonObj = new HashMap<>();
            jsonObj.put("service", "failed");
            jsonObj.put("code", response.statusCode());
            jsonObj.put("message", response.body());
        } else {
            // Parse response body
            // jsonObj = JsonParser.to(response.body());

            //temp
            System.out.println(response.body());
            jsonObj = new HashMap<>();
            jsonObj.put("message", response.body());
        }
        return jsonObj;
    }
}
