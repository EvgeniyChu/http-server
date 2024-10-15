package ru.otus.october.http.server;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String rawRequest;
    private HttpMethod method;
    private String uri;
    private Map<String, String> parameters;
    private String body;
    private Exception exception;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getUri() {
        return uri;
    }

    public String getRoutingKey() {
        return method + " " + uri;
    }

    public String getBody() {
        return body;
    }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parse();
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public boolean containsParameter(String key) {
        return parameters.containsKey(key);
    }

    private void parse() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        uri = rawRequest.substring(startIndex + 1, endIndex);
        method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        parameters = new HashMap<>();
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            uri = elements[0];
            String[] keysValues = elements[1].split("[&]");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
        if (method == HttpMethod.POST) {
            this.body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4);
        }
    }

    public void info(boolean debug) {
        if (debug) {
            System.out.println(rawRequest);
        }
        System.out.println("Method: " + method);
        System.out.println("URI: " + uri);
        System.out.println("Parameters: " + parameters);
        System.out.println("Body: "  + body);
    }
}
