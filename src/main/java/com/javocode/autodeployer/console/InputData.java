package com.javocode.autodeployer.console;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputData {

    private static Scanner scanner = new Scanner(System.in);
    private Map<String, String> data;

    public InputData() {
        data = new HashMap<>();
    }

    public String getData(String key) {
        return data.get(key);
    }

    public InputData prompt(String msg) {
        System.out.println(msg);
        return this;
    }

    public void saveValueAs(String name) {
        String value = scanner.nextLine();
        data.put(name, value);
    }

    public void add(String key, String value) {
        data.put(key, value);
    }

    public void clear() {
        data.clear();
        scanner.close();
    }
}
