package me.fuzzi.tundraweb;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Console {
    private Console() {}
    public static final ConsoleHandler out = new ConsoleHandler();
    public static class ConsoleHandler {
        protected ConsoleHandler() {}
        private final String format = "yyyy-dd-MM HH:mm:ss";
        public void println(Object o) {
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + "] " + o);
        }
        public void print(Object o) {
            System.out.print("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + "] " + o);
        }
    }
}