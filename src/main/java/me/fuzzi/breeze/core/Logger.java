package me.fuzzi.breeze.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p></p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
public class Logger {
    private Logger() {}
    private static final File file;
    static {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        file = new File("logs/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM HH-mm-ss")) + ".log");
        try {
            file.createNewFile();
        } catch (IOException e) {
            Console.err.println(e);
            System.exit(5051);
        }
    }

    public static void logln(Object message) {
        add(message, true);
    }
    public static void log(Object message) {
        add(message, false);
    }

    private static void add(Object message, boolean newLine) {
        try (FileWriter fw = new FileWriter(file, true);
             PrintWriter pw = new PrintWriter(fw)) {
            if (newLine) {
                pw.println(message);
            } else {
                pw.print(message);
            }
        } catch (IOException e) {
            Console.err.println(e);
            System.exit(5051);
        }
    }
}