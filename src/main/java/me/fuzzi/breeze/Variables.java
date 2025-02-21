package me.fuzzi.breeze;

public class Variables {
    private static int page = 0;
    public static void page() {
        page++;
    }
    public static int getPage() {
        return page;
    }
}