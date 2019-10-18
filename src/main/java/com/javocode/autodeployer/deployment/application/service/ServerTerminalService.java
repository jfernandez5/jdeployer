package com.javocode.autodeployer.deployment.application.service;

import com.javocode.autodeployer.console.InputData;
import com.javocode.autodeployer.deployment.infrastructure.dao.ServerTerminalDAO;
import com.javocode.autodeployer.deployment.domain.project.ServerTerminal;

public class ServerTerminalService {

    private ServerTerminalDAO serverTerminalDAO;

    public ServerTerminalService(ServerTerminalDAO serverTerminalDAO) {
        this.serverTerminalDAO = serverTerminalDAO;
    }

    public long save(InputData inputDataProvider) {
        ServerTerminal serverTerminal = new ServerTerminal();
        serverTerminal.setUsername(inputDataProvider.getData("stUsername"));
        serverTerminal.setPassword(inputDataProvider.getData("stPassword"));
        serverTerminal.setHost(inputDataProvider.getData("stHost"));
        return serverTerminalDAO.save(serverTerminal);
    }
}
