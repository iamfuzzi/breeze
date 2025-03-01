package me.fuzzi.breeze.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <p>Класс работы с ресурсами приложения.</p>
 * <p>Его методы позволяют получать доступ к файлам из ресурсов и возвращать их как поток или содержимое.</p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
public class Resources {

    /**
     * <p>Возвращает файл и ресурсов как поток.</p>
     * @param path определяет какой файл будет прочитан.
     */
    protected static InputStream getResourceAsStream(String path) {
        ClassLoader classLoader = WebApplication.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        return inputStream;
    }

    /**
     * <p>Возвращает файл и ресурсов как его содержимое.</p>
     * @param path определяет какой файл будет прочитан.
     */
    public static String getResourceAsContent(String path) {
        try (InputStream inputStream = getResourceAsStream(path)) {
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A")) {
                return scanner.hasNext() ? scanner.next() : "";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Возвращает список всех папок, которые находятся в нужной папке.</p>
     * @param path определяет родительскую папку.
     */
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
        }

        return directories.toArray(new String[0]);
    }

    /**
     * <p>Возвращает список всех файлов, которые находятся в нужной папке.</p>
     * @param path определяет родительскую папку.
     */
    public static String[] getFilesInDirectory(String path) {
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
        }

        return files.toArray(new String[0]);
    }
}