package com.javocode.autodeployer.console;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommandParser {

    private static final Logger LOGGER = LogManager.getLogger(CommandParser.class);

    List<Command> parse(String[] parameters) {
        LOGGER.info(">> Parsing parameters..");
        try {
            if (parameters.length == 0) {
                throw new IllegalArgumentException("No arguments passed.");
            }
            if (parameters.length > 3) {
                throw new IllegalArgumentException("Too much arguments.");
            }

            return convertToCommands(parameters);

        } catch (Exception e) {
            if (e instanceof IndexOutOfBoundsException) {
                throw new CommandParserException("Incorrect number of args.", e);
            }
            throw new CommandParserException(e.getMessage(), e);
        }
    }

    List<Command> convertToCommands(String[] parameters) {
        List<Command> commands = new ArrayList<>();

        for (int i = 0; i < parameters.length; i++) {
            Command command;
            if (CommandOption.DEPLOY.value().equals(parameters[i])) {

                if (!isValidArgument(parameters[i + 1])) {
                    throw new IllegalArgumentException("Invalid argument.");
                }
                command = new Command();
                command.setOption(parameters[i]);
                Map<String, String> args = new HashMap<>();
                args.put("sourceFilePath", parameters[i + 1]);
                args.put("id", parameters[i + 2]);
                command.setArgs(args);
                command.setType(CommandOption.Type.WITH_ARGUMENTS);
                i += 2;

            } else if (CommandOption.SET_CONFIG.value().equals(parameters[i])) {

                command = new Command();
                command.setOption(parameters[i]);
                command.setType(CommandOption.Type.NOT_ARGUMENTS);
            } else {
                throw new CommandParserException("Command was not reconized");
            }
//            if (CommandOption.Type.WITH_ARGUMENTS.equals(command.getType())) {
//                i++;
//            }
            commands.add(command);
        }
        return commands;
    }

    private boolean isValidArgument(String command) {
        Matcher matcher = Pattern.compile("^/.*\\.[a-z]{3}$").matcher(command);
        return matcher.find();
    }
}
