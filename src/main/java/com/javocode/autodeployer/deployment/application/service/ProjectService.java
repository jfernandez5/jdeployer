package com.javocode.autodeployer.deployment.application.service;

import com.javocode.autodeployer.console.InputData;
import com.javocode.autodeployer.deployment.infrastructure.dao.DeployProjectDAO;
import com.javocode.autodeployer.deployment.domain.project.DeployProject;
import com.javocode.autodeployer.deployment.domain.project.FileServer;
import com.javocode.autodeployer.deployment.domain.project.ServerTerminal;

import java.util.List;

public class ProjectService {

    private DeployProjectDAO deployProjectDAO;

    public ProjectService(DeployProjectDAO deployProjectDAO) {
        this.deployProjectDAO = deployProjectDAO;
    }

    public DeployProject findById(long id) {
        return deployProjectDAO.findById(id);
    }

    public void save(InputData inputData) {
        
        FileServer fileServer = new FileServer();
        fileServer.setId(Long.valueOf(inputData.getData("file_server_id")));

        ServerTerminal serverTerminal = new ServerTerminal();
        serverTerminal.setId(Long.valueOf(inputData.getData("server_terminal_id")));

        DeployProject deployProject = new DeployProject();
        deployProject.setName(inputData.getData("projectName"));
        deployProject.setFileServer(fileServer);
        deployProject.setServerTerminal(serverTerminal);
        deployProject.setDeploymentPath(inputData.getData("deploymentPath"));
        deployProjectDAO.save(deployProject);
    }

    public void update(InputData inputData) {
        
        FileServer fileServer = new FileServer();
        fileServer.setId(Long.valueOf(inputData.getData("file_server_id")));

        ServerTerminal serverTerminal = new ServerTerminal();
        serverTerminal.setId(Long.valueOf(inputData.getData("server_terminal_id")));

        DeployProject deployProject = new DeployProject();
        deployProject.setId(Long.valueOf(inputData.getData("id")));
        deployProject.setName(inputData.getData("projectName"));
        deployProject.setFileServer(fileServer);
        deployProject.setServerTerminal(serverTerminal);
        deployProject.setDeploymentPath(inputData.getData("deploymentPath"));

        deployProjectDAO.update(deployProject);
    }

    public void delete(InputData inputData) {

        deployProjectDAO.delete(Long.valueOf(inputData.getData("id")));
    }

    public List<DeployProject> findAll() {
        return deployProjectDAO.findAll();
    }
}
