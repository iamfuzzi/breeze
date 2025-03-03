package me.fuzzi.breeze.core;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>Класс создания динамических страниц внутри Java-кода.</p>
 * <p>Поддерживают переменную exchange (HttpExchange).</p>
 * <p>Важно, чтобы все переменные или значения, использующие exchange, были установлены внутри метода page.</p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
public abstract class WebPage implements WebPageInterface {

    public WebPage(HttpServer server, String web) {
        this.server = server;
        this.web = web;
    }
    private final String web;
    private final HttpServer server;
    protected HttpExchange exchange;

    /**
     * <p>Метод регистрации страницы на сервере.</p>
     * @since 1.0
     */
    public void load() {
        server.createContext(web, new HttpHandler() {
            @Override
            public void handle(HttpExchange ex) throws IOException {
                exchange = ex;
                String content = page();
                if (content.contains("%br:")) {
                    String[] values = WebController.list.values().toArray(new String[0]);
                    String[] keys = WebController.list.keySet().toArray(new String[0]);

                    for (int i = 0; i < values.length; i++) {
                        content = content.replace("%br:" + keys[i] + "%", values[i]);
                    }
                }
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, content.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(content.getBytes());
                os.close();
            }
        });
        Variables.page();
    }
}