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

    /**
     * <p>Статическая переменная консоли (чтобы сделать Console.out.println похожим на System.out.println)</p>
     * @since 1.0
     */
    public static final ConsoleMethods out = new ConsoleMethods();

    /**
     * <p>Вложенный класс обработки консоли.</p>
     * <p>Включает в себя методы для вывода в консоль сообщений.</p>
     * @author iamfuzzi
     * @version 1.0
     * @since 1.0
     */
    public static class ConsoleMethods {
        private final String format = "yyyy-dd-MM HH:mm:ss"; // Формат даты

        /**
         * Аналогично System.out.println выводит в консоль текст, но добавляет к нему форматирование
         * @param message выводимый текст
         * @since 1.0
         */
        public void println(Object message) {
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + "] " + message);
        }

        /**
         * Аналогично System.out.print выводит в консоль текст, но добавляет к нему форматирование
         * @param message выводимый текст
         * @since 1.0
         */
        public void print(Object message) {
            System.out.print("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(format)) + "] " + message);
        }

        private ConsoleMethods() {} // Создание экземпляров класса только через Console.out
    }

    private Console() {} // Отключение экземпляров класса
}