package com.javocode.autodeployer.console.handler;

import com.javocode.autodeployer.console.Command;
import com.javocode.autodeployer.deployment.application.DeploymentController;
import com.javocode.autodeployer.deployment.application.UploadController;
import com.javocode.autodeployer.deployment.application.service.FileService;
import com.javocode.autodeployer.deployment.application.service.SFTPService;
import com.javocode.autodeployer.deployment.application.service.SSHCommandService;
import com.javocode.autodeployer.deployment.domain.project.DeployProject;
import com.javocode.autodeployer.deployment.domain.project.ProjectNotFoundException;
import com.javocode.autodeployer.deployment.domain.upload.UploadRS;
import com.javocode.autodeployer.deployment.infrastructure.dao.DeployProjectDAO;
import com.javocode.autodeployer.deployment.util.AppUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Properties;

public class DeployCommandHandler implements CommandHandler {
    private static final Logger LOGGER = LogManager.getLogger(DeployCommandHandler.class);
    private Command command;

    public DeployCommandHandler(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        LOGGER.info("::::: DEPLOYMENT PROCESS STARTED :::::");
        DeployProjectDAO deployProjectDAO = new DeployProjectDAO();
        DeployProject deployProject = deployProjectDAO.findById(
                Long.valueOf(command.getArgs().get("id")));
        if (deployProject == null) {
            throw new ProjectNotFoundException(command.getArgs().get("id"));
        }

        UploadController uploadController = new UploadController(
                new SFTPService(deployProject), new FileService());
        UploadRS uploadRS = uploadController.upload(command.getArgs().get("sourceFilePath"));

        DeploymentController deploymentController = new DeploymentController(
                new SSHCommandService(deployProject.getServerTerminal()));

        Properties props = AppUtil.getPropertiesFile("extconfig.properties");
        String appName = props.getProperty("remote.app-name");
        String serviceName = props.getProperty("remote.service-name");

        Map<String, String> map = FileService.splitToMap(appName);

        String goIntoAppsFolder = "cd " + deployProject.getDeploymentPath();
        String makeBackup = "mv " + appName + " "
                + map.get("name") + "-BACK-" + System.currentTimeMillis() + map.get("ext");
        String changeNameToOriginalFile = "mv "
                + uploadRS.getFilePathInRemoteServer()
                + " "
                + appName;
        String stopService = "sudo /etc/init.d/" + serviceName + " stop";
        String startService = "sudo /etc/init.d/" + serviceName + " start";

        String[] commands = {stopService, goIntoAppsFolder,
                makeBackup, changeNameToOriginalFile, startService};
        deploymentController.deploy(commands);
        LOGGER.info("::::: DEPLOYMENT PROCESS COMPLETED :::::");
    }
}
