package com.javocode.autodeployer.deployment.application.service;

import com.javocode.autodeployer.deployment.domain.deploy.DeployOperations;
import com.javocode.autodeployer.deployment.domain.project.ServerTerminal;
import com.javocode.autodeployer.deployment.infrastructure.connector.SSHConnector;

public class SSHCommandService implements DeployOperations {
    private SSHConnector sshConnector;

    public SSHCommandService(ServerTerminal serverTerminal) {
        this.sshConnector = new SSHConnector(serverTerminal);
    }

    @Override
    public void deploy(String... comands) {
        sshConnector.open();
        sshConnector.sendCommands(comands);
        sshConnector.close();
    }
}
