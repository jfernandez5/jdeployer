package com.javocode.autodeployer.console;

public final class ConsoleUtil {

    private ConsoleUtil() {
    }

    public static void print(String message) {
        System.out.println(message);
    }
    public static void printBlankLine() {
        System.out.println();
    }

    public static void printf(String text, Object... args) {
        System.out.printf(text, args);
    }
}
