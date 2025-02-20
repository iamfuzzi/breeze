package me.fuzzi.app;

import me.fuzzi.breeze.WebApplication;
import me.fuzzi.breeze.WebPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application extends WebApplication {
    public static void main(String[] args) {
        launch(new Application(), args);
    }

    @Override
    protected void actions() {
        new Controller().load();
        new WebPage(server.getServer(), "/fileview/") {
            public static String readFile(String filePath) {
                Path path = Paths.get("files/" + filePath);
                try {
                    // Чтение содержимого файла и возврат его как строку
                    return Files.readString(path);
                } catch (IOException e) {
                    System.err.println("Ошибка при чтении файла: " + e.getMessage());
                    e.printStackTrace();
                    return null; // Возвращаем null в случае ошибки
                }
            }

            @Override
            public String page() {
                String name = exchange.getRequestURI().getPath().replace("/fileview/", "");
                String fileContent = readFile(name);

                // Заменяем символы новой строки на <br> для корректного отображения в HTML
                if (fileContent != null) {
                    fileContent = fileContent.replace("\n", "<br>");
                }

                return "<!DOCTYPE html>\n" +
                        "<html lang=\"ru\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>" + name + "</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>" + name + "</h1>\n" +
                        "    <p>" + (fileContent != null ? fileContent : "Ошибка при загрузке файла.") + "</p>\n" +
                        "</body>\n" +
                        "</html>";
            }
        }.start();
    }
}