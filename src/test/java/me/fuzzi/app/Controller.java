package me.fuzzi.app;

import me.fuzzi.breeze.WebController;

import java.io.File;

public class Controller extends WebController {
    @Override
    public void init() {
        add("fileList", getFileList());
    }
    private String getFileList() {
        StringBuilder fileListHtml = new StringBuilder("<ul>");
        File folder = new File("files");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileListHtml.append("<li><a href=\"/fileview/")
                            .append(file.getName())
                            .append("\">")
                            .append(file.getName())
                            .append("</a></li>");
                }
            }
        }
        fileListHtml.append("</ul>");
        return fileListHtml.toString();
    }
}