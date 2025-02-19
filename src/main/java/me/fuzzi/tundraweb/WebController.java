package me.fuzzi.tundraweb;

import java.util.HashMap;
import java.util.Map;

public abstract class WebController {
    protected static Map<String, String> list = new HashMap<>();
    protected static void add(String id, String replacing) {
        list.put(id, replacing);
    }
    public abstract void init();
    public void load() {
        init();
        Console.out.println("Loading controllers...");
    }
}