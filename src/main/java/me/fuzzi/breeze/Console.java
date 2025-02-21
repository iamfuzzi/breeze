package me.fuzzi.breeze;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>Класс работы с консолью.</p>
 * <p>Позволяет применять форматирование к консоли, делая ее красивой</p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
public class Console {

    private static final String format = "yyyy-dd-MM HH:mm:ss"; // Формат даты

    /**
     * <p>Статическая переменная консоли (чтобы сделать Console.out.println похожим на System.out.println)</p>
     * @since 1.0
     */
    public static final ConsoleOut out = new ConsoleOut();

    /**
     * <p>Вложенный класс обработки консоли.</p>
     * <p>Включает в себя методы для вывода в консоль сообщений.</p>
     * @author iamfuzzi
     * @version 1.0
     * @since 1.0
     */
    public static class ConsoleOut {

        /**
         * <p>Аналогично System.out.println выводит в консоль текст, но добавляет к нему форматирование.</p>
         * @param message выводимый текст.
         * @since 1.0
         */
        public void println(Object message) {
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + "] " + message);
        }

        /**
         * <p>Аналогично System.out.print выводит в консоль текст, но добавляет к нему форматирование.</p>
         * @param message выводимый текст.
         * @since 1.0
         */
        public void print(Object message) {
            System.out.print("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + "] " + message);
        }

        private ConsoleOut() {} // Создание экземпляра класса только в классе Console.
    }

    /**
     * <p>Статическая переменная ошибок консоли (чтобы сделать Console.err.println похожим на System.err.println)</p>
     * @since 1.0
     */
    public static final ConsoleErr err = new ConsoleErr();

    /**
     * <p>Вложенный класс обработки ошибок консоли.</p>
     * <p>Включает в себя методы для вывода в консоль сообщений ошибок.</p>
     * @author iamfuzzi
     * @version 1.0
     * @since 1.0
     */
    public static class ConsoleErr {

        /**
         * <p>Аналогично System.err.println выводит в консоль ошибку, но добавляет к нему форматирование.</p>
         * @param message выводимый текст.
         * @since 1.0
         */
        public void println(Object message) {
            System.err.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + "] " + message);
        }

        /**
         * <p>Аналогично System.err.print выводит в консоль ошибку, но добавляет к нему форматирование.</p>
         * @param message выводимый текст.
         * @since 1.0
         */
        public void print(Object message) {
            System.err.print("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + "] " + message);
        }

        private ConsoleErr() {} // Создание экземпляра класса только в классе Console.
    }

    /**
     * <p>Статическая переменная статической консоли (без оформления) (чтобы сделать Console.st.println похожим на System.out.println)</p>
     * @since 1.0
     */
    public static final ConsoleSt st = new ConsoleSt();

    /**
     * <p>Вложенный класс обработки статической консоли.</p>
     * <p>Включает в себя методы для вывода в статическую консоль сообщений.</p>
     * @author iamfuzzi
     * @version 1.0
     * @since 1.0
     */
    public static class ConsoleSt {

        /**
         * <p>Аналогично System.out.println выводит в статическую консоль текст.</p>
         * @param message выводимый текст.
         * @since 1.0
         */
        public void println(Object message) {
            System.out.println(message);
        }

        /**
         * <p>Аналогично System.out.print выводит в статическую консоль текст.</p>
         * @param message выводимый текст.
         * @since 1.0
         */
        public void print(Object message) {
            System.out.print(message);
        }

        private ConsoleSt() {} // Создание экземпляра класса только в классе Console.
    }

    private Console() {} // Без создания экземпляров класса.
}