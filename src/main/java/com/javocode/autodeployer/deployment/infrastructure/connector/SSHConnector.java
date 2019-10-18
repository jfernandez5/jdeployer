package com.javocode.autodeployer.deployment.infrastructure.connector;

import com.javocode.autodeployer.deployment.domain.project.ServerTerminal;
import com.javocode.autodeployer.deployment.util.AppUtil;
import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class SSHConnector {
    private static final Logger LOGGER = LogManager.getLogger(SSHConnector.class);
    private static Session session;
    private ServerTerminal serverTerminal;
    private static final int BUFFER_SIZE = 100;

    public SSHConnector(ServerTerminal serverTerminal) {
        this.serverTerminal = serverTerminal;
    }

    public void open() {
        JSch jsch = new JSch();

        try {
            if (serverTerminal.getKeyPath() != null
                    && !serverTerminal.getKeyPath().isEmpty()) {
                jsch.addIdentity(serverTerminal.getKeyPath());
            }
            session = jsch.getSession(
                    serverTerminal.getUsername(), serverTerminal.getHost());
            session.setPassword(serverTerminal.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(30000);
            LOGGER.info(">> SSH connection established.");

        } catch (JSchException e) {
            LOGGER.error("Connect by SSH", e);
        }
    }

    public void sendCommands(String... commands) {
        byte[] bufferContent = new byte[BUFFER_SIZE];
        Properties props = AppUtil.getPropertiesFile("extconfig.properties");
        boolean isPassive = Boolean.valueOf(props.getProperty("commands.passive"));
        String allCommands = joinCommands(commands, isPassive);
        allCommands = addCommandToStoreInVariable(allCommands);
        LOGGER.info(">> Commands input:\n{}", allCommands);
        StringBuilder outputBuilder = new StringBuilder();
        Channel channel = null;
        String formattedOutput;
        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(allCommands);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int bytesRead;
            while ((bytesRead = commandOutput.read(bufferContent)) != -1) {
                String data = new String(bufferContent, 0, bytesRead);
                outputBuilder.append(data);
            }
            formattedOutput = formatOutput(outputBuilder, commands.length);
            LOGGER.info("<< Commands output: \n {}", formattedOutput);
        } catch (Exception e) {
            LOGGER.error("sendCommands", e);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    String formatOutput(StringBuilder outputBuilder, int size) {
        if (outputBuilder.length() == 0) {
            throw new RuntimeException("Response from commands sent is empty");
        }
        int index = outputBuilder.indexOf("#");
        int endIndex = outputBuilder.indexOf(">");
        for (int i = 0; i < size; i++) {
            outputBuilder.insert(index, "\n");
            outputBuilder.insert(endIndex + 2, "\n");
            index = outputBuilder.indexOf("#", index + 2);
            endIndex = outputBuilder.indexOf(">", index + 3);
        }
        return outputBuilder.toString().substring(0, outputBuilder.length() - 1);
    }

    private String addCommandToStoreInVariable(String commands) {
        return "output=`" + commands + "` &&\necho $output";
    }

    String joinCommands(String[] commands, boolean shouldIgnoreErrors) {
        StringBuilder stringBuilder = new StringBuilder();
        String concatSymbol = "&&";
        if (shouldIgnoreErrors) {
            concatSymbol = "||";
        }
        String commandToLogDate = "echo \"# Command completed at: $(date) >\"";
        for (String command : commands) {
            stringBuilder.append(command).append(" ").append(concatSymbol).append("\n")
                    .append(commandToLogDate).append(" ").append(concatSymbol).append("\n");
        }
        return stringBuilder.toString().substring(0, stringBuilder.length() - 4);
    }

    String joinCommands(String[] commands) {
        return joinCommands(commands, false);
    }

    boolean isSessionOpen() {
        if (session != null)
            return session.isConnected();
        return false;
    }

    public void close() {
        session.disconnect();
    }
}
