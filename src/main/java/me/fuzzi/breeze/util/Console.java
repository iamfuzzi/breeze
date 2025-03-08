package me.fuzzi.breeze.util;

import me.fuzzi.breeze.core.Variables;
import me.fuzzi.breeze.resources.ResourceMonitor;
import me.fuzzi.breeze.resources.ResourceMonitor.MonitorStream;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Console {
    public static class ConsoleFormat {
        private final PrintStream stream;
        private final MonitorStream monitor;
        private final boolean format;
        ConsoleFormat(PrintStream stream, MonitorStream monitor, boolean format) {
            this.stream = stream;
            this.monitor = monitor;
            this.format = format;
        }
        public void print(Object o) {
            String out;
            if (format) {
                out = format() + o;
            } else {
                out = o.toString();
            }
            stream.print(out);
            Logger.log(out);
            if (Variables.getMonitor()) monitor.print(out);
        }
        public void println(Object o) {
            String out;
            if (format) {
                out = format() + o;
            } else {
                out = o.toString();
            }
            stream.println(out);
            Logger.logln(out);
            if (Variables.getMonitor()) monitor.println(out);
        }
        private static String format() {
            return "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] ";
        }
    }

    public static final ConsoleFormat out = new ConsoleFormat(System.out, ResourceMonitor.out, true);
    public static final ConsoleFormat err = new ConsoleFormat(System.err, ResourceMonitor.err, true);

    public static final ConsoleFormat st = new ConsoleFormat(System.out, ResourceMonitor.out, false);
    public static final ConsoleFormat sterr = new ConsoleFormat(System.err, ResourceMonitor.err, false);

    private Console() {}
}