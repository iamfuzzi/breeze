package me.fuzzi.breeze;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class WebServer {
    private final String ip;
    private final int port;
    private final HttpServer server;
    public HttpServer getServer() {
        return server;
    }
    protected WebServer(String ip, int port) {
        this.port = port;
        this.ip = ip;
        try {
            server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected void start() {
        server.start();
    }

    protected void add(String path, String content) {
        server.createContext(path, new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                byte[] response = content.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, response.length);
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            }
        });
    }

    @Override
    public String toString() {
        return ip + ":" + port;
    }
}