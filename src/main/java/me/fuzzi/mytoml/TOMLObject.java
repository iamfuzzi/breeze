package me.fuzzi.mytoml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TOMLObject {
    private final Map<String, String> data = new HashMap<>();

    public TOMLObject(String text) {
        parse(text);
    }

    // Конструктор для создания TOMLObject из файла
    public TOMLObject(File file) throws IOException {
        StringBuilder textBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            textBuilder.append(line).append("\n");
        }
        reader.close();
        parse(textBuilder.toString());
    }

    // Метод для парсинга текста и заполнения значений
    private void parse(String text) {
        String[] lines = text.split("\n");
        String currentSection = "";

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue; // Игнорируем пустые строки и комментарии
            }

            if (line.startsWith("[")) {
                // Это секция
                int endIndex = line.indexOf(']');
                if (endIndex == -1) {
                    throw new TOMLSyntaxException("Missing closing bracket in section: " + line);
                }
                currentSection = line.substring(1, endIndex).trim();
                if (currentSection.isEmpty()) {
                    throw new TOMLSyntaxException("Empty section name: " + line);
                }
            } else {
                // Это ключ-значение
                int equalsIndex = line.indexOf('=');
                if (equalsIndex == -1) {
                    throw new TOMLSyntaxException("Missing '=' in key-value pair: " + line);
                }

                String key = line.substring(0, equalsIndex).trim();
                String value = line.substring(equalsIndex + 1).trim();

                if (key.isEmpty()) {
                    throw new TOMLSyntaxException("Empty key in key-value pair: " + line);
                }
                if (value.isEmpty()) {
                    throw new TOMLSyntaxException("Empty value in key-value pair: " + line);
                }

                value = value.replaceAll("^\"|\"$", ""); // Убираем кавычки вокруг значения

                if (!currentSection.isEmpty()) {
                    key = currentSection + "." + key; // Добавляем секцию к ключу
                }

                data.put(key, value);
            }
        }
    }

    public String getString(String key) {
        return data.get(key);
    }

    public int getInt(String key) {
        String value = getString(key);
        if (value == null) {
            throw new TOMLSyntaxException("Missing key: " + key);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new TOMLTypeException(value, "int");
        }
    }
    public double getDouble(String key) {
        String value = getString(key);
        if (value == null) {
            throw new TOMLSyntaxException("Missing key: " + key);
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new TOMLTypeException(value, "double");
        }
    }
    public boolean getBoolean(String key) {
        String value = getString(key);
        if (value == null) {
            throw new TOMLSyntaxException("Missing key: " + key);
        }

        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new TOMLTypeException(value, "boolean");
        }
    }

    public boolean hasKey(String key) {
        if (getString(key) != null) return true;
        return false;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}