package me.fuzzi.breeze.core;

/**
 * <p>Класс счетчиков. Содержит в себе эддеры (как сеттеры, но не принимают значение, просто добавляют к переменной +1) и геттеры переменных.</p>
 * <p>Переменные этого класса используются для получения и установки информации о загруженных ресурсах.</p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
class Variables {

    private static int page = 0;
    public static void page() {
        page++;
    }
    public static int getPage() {
        return page;
    }

    private static int statics = 0;
    public static int getStatics() {
        return statics;
    }
    public static void statics() {
        statics++;
    }

    private static int controller = 0;
    public static int getController() {
        return controller;
    }
    public static void controller() {
        controller++;
    }

    private static int placeholder = 0;
    public static int getPlaceholder() {
        return placeholder;
    }
    public static void placeholder() {
        placeholder++;
    }
}