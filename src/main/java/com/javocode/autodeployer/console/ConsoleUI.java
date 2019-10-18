package com.javocode.autodeployer.console;

import com.javocode.autodeployer.console.handler.CommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;
import java.util.List;

public class ConsoleUI {
    private static final Logger LOGGER = LogManager.getLogger(ConsoleUI.class);
    private static CommandParser commandParser = new CommandParser();

    public void handleParameters(String[] parameters) {
        try {
            handle(parameters);
        } catch (Exception e) {
            LOGGER.error("handleParameters: " + Arrays.toString(parameters), e);
        }
    }

    private void handle(String[] parameters) throws OperationNotSupportedException {
        List<Command> commands = commandParser.parse(parameters);
        for (Command command : commands) {
            CommandHandler commandHandler = CommandFactory.getInstance(command);
            commandHandler.execute();
        }
    }
}
