package ru.otus.october.http.server.processors;

import ru.otus.october.http.server.BadRequestException;
import ru.otus.october.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalculatorProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (!request.containsParameter("a")) {
            throw new BadRequestException("Parameter 'a' is missing");
        }
        if (!request.containsParameter("b")) {
            throw new BadRequestException("Parameter 'b' is missing");
        }
        int a, b;
        try {
            a = Integer.parseInt(request.getParameter("a"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter 'a' has incorrect type");
        }
        try {
            b = Integer.parseInt(request.getParameter("b"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter 'b' has incorrect type");
        }

        String math = a + " + " + b + " = " + (a + b);
        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body><h1>" + math + "</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
