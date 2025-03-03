package me.fuzzi.breeze.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>Класс логгера - инструмента для добавления логов в файл лога текущего процесса.</p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
class Logger {

    /**
     * <p>Файл конфига, нужен для добавления в него текста.</p>
     * @since 1.0
     */
    private static final File file;

    // Указание файла конфига
    static {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            logDir.mkdirs();
        }
        file = new File("logs/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH-mm-ss")) + ".log");
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            Console.err.println(e);
            System.exit(5051);
        }
    }

    /**
     * <p>Добавляет текст в лог.</p>
     * @param message добавляемый текст.
     * @since 1.0
     */
    public static void log(Object message) {
        logPrint(message, false);
    }

    /**
     * <p>Добавляет текст в лог вместе с новой строкой.</p>
     * @param message добавляемый текст.
     * @since 1.0
     */
    public static void logln(Object message) {
        logPrint(message, true);
    }

    /**
     * <p>Метод для добавления информации в файл лога.</p>
     * @param message добавляемый текст.
     * @param newLine если нужен перенос на новую строку.
     * @since 1.0
     */
    private static void logPrint(Object message, boolean newLine) {
        try (FileWriter fileWriter = new FileWriter(file, true);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            if (newLine) {
                writer.println(message);
            } else {
                writer.print(message);
            }
        } catch (IOException e) {
            Console.err.println(e);
            System.exit(5051);
        }
    }

    private Logger() {} // Без создания экземпляров класса.
}