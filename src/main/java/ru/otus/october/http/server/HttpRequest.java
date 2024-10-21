package ru.otus.october.http.server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class HttpRequest {
    private static final Logger logger = Logger.getLogger(HttpRequest.class.getName());

    private String rawRequest;
    private HttpMethod method;
    private String uri;
    private Map<String, String> parameters;
    private Map<String, String> headers;
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
        this.parameters = new HashMap<>();
        this.headers = new HashMap<>();
        this.parse();
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public boolean containsParameter(String key) {
        return parameters.containsKey(key);
    }

    private void parse() {
        String[] requestParts = rawRequest.split("\r\n\r\n", 2);
        String requestLineAndHeaders = requestParts[0];
        body = requestParts.length > 1 ? requestParts[1] : "";


        String[] requestLines = requestLineAndHeaders.split("\r\n");
        String requestLine = requestLines[0];
        String[] methodUri = requestLine.split(" ");

        uri = methodUri[1];
        method = HttpMethod.valueOf(methodUri[0]);


        for (int i = 1; i < requestLines.length; i++) {
            String[] header = requestLines[i].split(": ", 2);
            if (header.length == 2) {
                headers.put(header[0], header[1]);
            }
        }

        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            uri = elements[0];
            String[] keysValues = elements[1].split("[&]");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public void info(boolean debug) {
        if (debug) {
            logger.info(rawRequest);
        }
        logger.info("Method: " + method);
        logger.info("URI: " + uri);
        logger.info("Parameters: " + parameters);
        logger.info("Headers: " + headers);
        logger.info("Body: "  + body);
    }
}