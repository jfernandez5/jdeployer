package com.javocode.autodeployer.console;

import java.util.Map;

public class Command {

    private String option;
    private Map<String, String> args;
    private CommandOption.Type type;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    public CommandOption.Type getType() {
        return type;
    }

    public void setType(CommandOption.Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Command{" +
                "option='" + option + '\'' +
                ", args=" + args +
                ", type=" + type +
                '}';
    }
}
