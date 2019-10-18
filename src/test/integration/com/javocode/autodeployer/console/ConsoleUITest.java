package com.javocode.autodeployer.console;

import org.junit.Ignore;
import org.junit.Test;

public class ConsoleUITest {

    @Ignore
    @Test
    public void printErrorLog_whenPassAWrongCommand() {

        ConsoleUI consoleUI = new ConsoleUI();

        String[] parameters = new String[2];
        parameters[0] = "-c";
        parameters[1] = "-f";
        consoleUI.handleParameters(parameters);
    }

    @Ignore
    @Test
    public void startConfigProcess_whenPassConfigCommand() {

        ConsoleUI consoleUI = new ConsoleUI();
        String[] parameters = {"-c"};
        consoleUI.handleParameters(parameters);
    }
}