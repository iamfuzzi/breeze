package me.fuzzi.breeze.core;

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
}