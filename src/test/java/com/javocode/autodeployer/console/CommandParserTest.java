package com.javocode.autodeployer.console;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CommandParserTest {

    @Test(expected = CommandParserException.class)
    public void returnFalseWhenPassMoreThanTwoArguments() {

        String[] args = {"-d", "/home/user/tsds.txt", "1", "-c"};

        CommandParser commandParser = new CommandParser();
        commandParser.parse(args);
    }

    @Test
    public void returnTrueWhenPassCorrectSizeOfArguments() {

        String[] args = {"-d", "/home/user/tsds.txt", "1"};

        CommandParser commandParser = new CommandParser();
        List<Command> commands = commandParser.parse(args);

        assertThat(commands.size(), is(1));
    }

    @Test(expected = CommandParserException.class)
    public void returnFalseWhenPassWrongArguments() {

        String[] args = {"-f", "/home/user/tsds.txt"};

        CommandParser commandParser = new CommandParser();
        commandParser.parse(args);
    }

    @Test
    public void givenACommandArray_convertToParametersArray() {

        String[] parameters = new String[3];
        parameters[0] = "-d";
        parameters[1] = "/home/user/ty.txt";
        parameters[2] = "1";

        CommandParser commandParser = new CommandParser();
        List<Command> commands = commandParser.convertToCommands(parameters);

        assertThat(commands.size(), is(1));
        assertThat(commands.get(0).getOption(), is("-d"));
        assertThat(commands.get(0).getArgs().get("sourceFilePath"), is("/home/user/ty.txt"));
    }

    @Test
    public void givenACommandArrayWithTwoArgs_convertToParametersArray() {

        String[] parameters = new String[3];
        parameters[0] = "-d";
        parameters[1] = "/home/user/ty.txt";
        parameters[2] = "1";

        CommandParser commandParser = new CommandParser();
        List<Command> commands = commandParser.convertToCommands(parameters);

        assertThat(commands.size(), is(1));
        assertThat(commands.get(0).getOption(), is("-d"));
        assertThat(commands.get(0).getArgs().get("sourceFilePath"), is("/home/user/ty.txt"));
        assertThat(commands.get(0).getArgs().get("id"), is("1"));
    }

    @Test
    public void givenMoreThanTwoCommands_thenReturnTwoParameters() {

        String[] parameters = new String[4];
        parameters[0] = "-d";
        parameters[1] = "/home/user/ty.txt";
        parameters[2] = "1";
        parameters[3] = "-c";

        CommandParser commandParser = new CommandParser();
        List<Command> commands = commandParser.convertToCommands(parameters);

        assertThat(commands.size(), is(2));
        assertThat(commands.get(0).getOption(), is("-d"));
        assertThat(commands.get(0).getArgs().get("sourceFilePath"), is("/home/user/ty.txt"));
        assertThat(commands.get(0).getArgs().get("id"), is("1"));
        assertThat(commands.get(1).getOption(), is("-c"));
    }
}
