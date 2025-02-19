package me.fuzzi.mytoml;

public class TOMLTypeException extends RuntimeException {
    public TOMLTypeException(String value, String type) {
        super("Invalid type: expected " + type + ", but got " + check(value));
    }

    private static String check(String value) {
        if (value.equals("true") || value.equals("false")) {
            return "boolean";
        }
        try {
            Integer.parseInt(value);
            return "int";
        } catch (NumberFormatException ignored) {}
        try {
            Double.parseDouble(value);
            return "double";
        } catch (NumberFormatException ignored) {}

        return "string";
    }
}