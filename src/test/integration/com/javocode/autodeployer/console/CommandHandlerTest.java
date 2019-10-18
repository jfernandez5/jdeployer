package com.javocode.autodeployer.console;

import com.javocode.autodeployer.console.handler.CommandHandler;
import com.javocode.autodeployer.console.handler.ConfigCommandHandler;
import org.junit.Ignore;
import org.junit.Test;

public class CommandHandlerTest {

    @Ignore
    @Test
    public void givenACommandThenGetAnAction() {
        CommandHandler commandHandler = new ConfigCommandHandler();
        commandHandler.execute();
    }
}
