package com.example;

import me.fuzzi.breeze.WebController;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Controller extends WebController {
    @Override
    public void init() {
        File folder = new File("articles");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        StringBuilder jsonFilesList = new StringBuilder();

        if (files != null) {
            for (File file : files) {
                try {
                    String content = new String(Files.readAllBytes(file.toPath()));
                    JSONObject jsonObject = new JSONObject(content);
                    String title = jsonObject.getString("title");
                    String fileName = file.getName().replace(".json", "");
                    jsonFilesList.append("<li><a href=\"/article/").append(fileName).append("\">").append(title).append("</a></li>");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            jsonFilesList.append("<li>Нет доступных файлов</li>");
        }

        add("jsonFiles", jsonFilesList.toString());
    }
}