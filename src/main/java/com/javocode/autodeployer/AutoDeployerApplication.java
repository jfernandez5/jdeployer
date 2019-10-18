package com.javocode.autodeployer;

import com.javocode.autodeployer.console.ConsoleUI;
import com.javocode.autodeployer.deployment.infrastructure.dao.JPAManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoDeployerApplication {
    // TODO: Mostrar log solo en debug e imprimir en consola al momento del deploy

    private static final Logger LOGGER = LogManager.getLogger(AutoDeployerApplication.class);

    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.handleParameters(args);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Closing program...");
            JPAManager.shutdown();
        }));
    }
}
