package me.fuzzi.breeze.core;

import me.fuzzi.mytoml.TOMLObject;

/**
 * <p>Класс счетчиков. Содержит в себе эддеры (как сеттеры, но не принимают значение, просто добавляют к переменной +1) и геттеры переменных.</p>
 * <p>Переменные этого класса используются для получения и установки информации о загруженных ресурсах.</p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
public class Variables {
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

    private static TOMLObject config;
    static TOMLObject getConfig() {
        return config;
    }
    static void setConfig(TOMLObject config) {
        Variables.config = config;
    }

    private static String conf;
    public static String getConf() {
        return conf;
    }
    public static void setConf(String conf) {
        Variables.conf = conf;
    }

    private static int users;
    public static int getUsers() {
        return users;
    }
    public static void plusUser() {
        users++;
        System.out.println(users);
    }
    public static void minusUser() {
        users--;
        System.out.println(users);
    }

    private static boolean monitor;
    public static void monitor() {
        monitor = !monitor;
    }
    public static boolean getMonitor() {
        return monitor;
    }

    private Variables() {}
}