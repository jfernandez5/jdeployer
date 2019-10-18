package com.javocode.autodeployer.console;

public enum CommandOption {

    DEPLOY("-d"), SET_CONFIG("-c");

    private String value;

    CommandOption(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }


    enum Type {
        WITH_ARGUMENTS, NOT_ARGUMENTS
    }
}
