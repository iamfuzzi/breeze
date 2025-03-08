package me.fuzzi.breeze.util;

public class Prog {
    public static void exit(Object o) {
        Console.err.println(o);
        Console.out.println("Shutting down with status 1!");
        System.exit(1);
    }
    public static void exit() {
        Console.out.println("Shutting down with status 1!");
        System.exit(1);
    }
}