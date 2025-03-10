package me.fuzzi.breeze.core;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import me.fuzzi.breeze.util.Logger;

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

    protected void add(String path, byte[] content) {
        server.createContext(path, new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                try {
                    String contentType = "text/html; charset=UTF-8";

                    if (path.endsWith(".png")) {
                        contentType = "image/png";
                    } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                        contentType = "image/jpeg";
                    } else if (path.endsWith(".gif")) {
                        contentType = "image/gif";
                    } else if (path.endsWith(".css")) {
                        contentType = "text/css; charset=UTF-8";
                    } else if (path.endsWith(".js")) {
                        contentType = "application/javascript; charset=UTF-8";
                    } else if (path.endsWith(".svg")) {
                        contentType = "image/svg+xml";
                    }
                    exchange.getResponseHeaders().add("Content-Type", contentType);

                    exchange.sendResponseHeaders(200, content.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(content);
                    os.close();
                } catch (Throwable e) {
                    Logger.handleException(Thread.currentThread(), e);
                }
            }
        });
    }

    protected void add(String path, String content) {
        add(path, content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return ip + ":" + port;
    }
}