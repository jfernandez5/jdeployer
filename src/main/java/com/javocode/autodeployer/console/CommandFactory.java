package com.javocode.autodeployer.console;

import com.javocode.autodeployer.console.handler.CommandHandler;
import com.javocode.autodeployer.console.handler.ConfigCommandHandler;
import com.javocode.autodeployer.console.handler.DeployCommandHandler;

import javax.naming.OperationNotSupportedException;

public class CommandFactory {

    public static CommandHandler getInstance(Command command) throws OperationNotSupportedException {

        if (CommandOption.DEPLOY.value().equals(command.getOption())) {
            return new DeployCommandHandler(command);
        } else if (CommandOption.SET_CONFIG.value().equals(command.getOption())) {
            return new ConfigCommandHandler();
        } else {
            throw new OperationNotSupportedException("Command not recognized");
        }
    }
}
