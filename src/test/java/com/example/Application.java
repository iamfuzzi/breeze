package com.example;

import me.fuzzi.breeze.Console;
import me.fuzzi.breeze.WebApplication;
import me.fuzzi.breeze.WebPage;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Application extends WebApplication {
    public static void main(String[] args) {
        launch(new Application(), args);
    }

    @Override
    protected void actions() {
        new Controller().load();
        new WebPage(server.getServer(), "/docs/") {
            @Override
            public String page() {
                JSONObject object;
                try {
                    object = new JSONObject(new String(Files.readAllBytes(Paths.get("docs" + exchange.getRequestURI().getPath().replace("/docs", "") + ".json"))));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                Console.out.println(exchange.getRemoteAddress().getAddress().getHostAddress() + " подключился. Получает " + exchange.getRequestURI().getPath().replace("/docs/", ""));

                String[] array;
                if (object.has("text") && object.get("text") instanceof org.json.JSONArray) {
                    array = object.getJSONArray("text").toList().toArray(new String[0]);
                } else {
                    array = new String[]{"Нет доступного текста."};
                }

                StringBuilder textContent = new StringBuilder();
                for (String line : array) {
                    textContent.append(line).append("<br>");
                }

                String content = textContent.toString();
                String author = object.optString("author", "Неизвестен");
                String title = object.optString("title", "Без названия");

                // Загружаем файлы, сортируем по num
                File folder = new File("docs");
                File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

                List<JSONObject> jsonObjects = new ArrayList<>();
                if (files != null) {
                    for (File file : files) {
                        try {
                            JSONObject jsonFile = new JSONObject(new String(Files.readAllBytes(file.toPath())));
                            jsonFile.put("filename", file.getName().replace(".json", "")); // Добавляем имя файла
                            jsonObjects.add(jsonFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Сортируем по num (по возрастанию)
                jsonObjects.sort(Comparator.comparingInt(o -> o.optInt("num", Integer.MAX_VALUE)));

                // Формируем список статей
                StringBuilder jsonFilesList = new StringBuilder();
                for (JSONObject jsonFile : jsonObjects) {
                    jsonFilesList.append("<li><a href=\"/docs/")
                            .append(jsonFile.getString("filename"))
                            .append("\">")
                            .append(jsonFile.optString("title", "Без названия"))
                            .append("</a></li>");
                }

                return "<!DOCTYPE html>\n" +
                        "<html lang=\"ru\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Сайт | Документация</title>\n" +
                        "    <link rel=\"stylesheet\" href=\"/static/main.css\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<nav>\n" +
                        "    <a href=\"/\">Главная</a>\n" +
                        "    <a href=\"/docs/main\">Документация</a>\n" +
                        "    <button id=\"theme-toggle\">Сменить тему</button>\n" +
                        "    <script src=\"/static/theme.js\"></script>\n" +
                        "</nav>" +
                        "<div class=\"container\">\n" +
                        "    <div class=\"sidebar\">\n" +
                        "        <h2>Список статей</h2>\n" +
                        "        <ul>\n" +
                        jsonFilesList +
                        "        </ul>\n" +
                        "    </div>\n" +
                        "    <div class=\"content\">\n" +
                        "        <h1>" + title + "</h1>\n" +
                        "        <h3>" + author + "</h3>\n" +
                        "        <div>" + content + "</div>\n" +
                        "    </div>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>";
            }
        }.load();
    }
}