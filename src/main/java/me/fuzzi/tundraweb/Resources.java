package me.fuzzi.tundraweb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Resources {

    protected static InputStream getResourceAsStream(String path) {
        ClassLoader classLoader = WebApplication.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);

        if (inputStream == null) {
            throw new NoSuchFileException(path);
        }

        return inputStream;
    }

    protected static String getResourceAsContent(String path) {
        try (InputStream inputStream = getResourceAsStream(path)) {
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A")) {
                return scanner.hasNext() ? scanner.next() : "";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static String[] getSubdirectories(String path) {
        List<String> directories = new ArrayList<>();

        URL resourceUrl = WebApplication.class.getClassLoader().getResource(path);
        if (resourceUrl != null) {
            try {
                File folder = new File(resourceUrl.toURI());
                if (folder.exists() && folder.isDirectory()) {
                    File[] files = folder.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isDirectory()) {
                                directories.add(file.getName());
                            }
                        }
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            throw new NoSuchFileException(path);
        }
        return directories.toArray(new String[0]);
    }
    protected static String[] getFilesInDirectory(String path) {
        List<String> files = new ArrayList<>();

        URL resourceUrl = WebApplication.class.getClassLoader().getResource(path);
        if (resourceUrl != null) {
            try {
                File folder = new File(resourceUrl.toURI());
                if (folder.exists() && folder.isDirectory()) {
                    File[] fileArray = folder.listFiles();
                    if (fileArray != null) {
                        for (File file : fileArray) {
                            if (file.isFile()) {
                                files.add(file.getName());
                            }
                        }
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            throw new NoSuchFileException(path);
        }
        return files.toArray(new String[0]);
    }
}