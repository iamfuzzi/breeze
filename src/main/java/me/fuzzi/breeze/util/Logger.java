package me.fuzzi.breeze.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final File file;

    static {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            logDir.mkdirs();
        }
        file = new File("logs/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")) + ".log");
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            Prog.exit(e);
        }

        Thread.setDefaultUncaughtExceptionHandler(Logger::handleException);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> Console.out.println("Shutting down...")));
    }

    public static void handleException(Thread thread, Throwable e) {
        Console.err.println("Exception in thread \"" + thread.getName() + "\" " + e);
        Console.sterr.println("\tat " + e.getStackTrace()[0]);
        for (int i = 1; i < e.getStackTrace().length; i++) {
            Console.sterr.println("\tat " + e.getStackTrace()[i]);
        }
    }

    public static void log(Object message) {
        logPrint(message, false);
    }

    public static void logln(Object message) {
        logPrint(message, true);
    }

    private static void logPrint(Object message, boolean newLine) {
        try (FileWriter fileWriter = new FileWriter(file, true);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            if (newLine) {
                writer.println(message);
            } else {
                writer.print(message);
            }
        } catch (IOException e) {
            Prog.exit(e);
        }
    }

    private Logger() {}
}