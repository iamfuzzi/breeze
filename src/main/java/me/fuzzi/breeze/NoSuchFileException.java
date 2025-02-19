package me.fuzzi.breeze;

public class NoSuchFileException extends RuntimeException {
    protected NoSuchFileException(String path) {
        super("Cannot find file " + path);
    }
}