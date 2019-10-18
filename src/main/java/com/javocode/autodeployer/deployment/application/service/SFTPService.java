package com.javocode.autodeployer.deployment.application.service;

import com.javocode.autodeployer.deployment.domain.project.DeployProject;
import com.javocode.autodeployer.deployment.domain.upload.Application;
import com.javocode.autodeployer.deployment.domain.upload.UploadRS;
import com.javocode.autodeployer.deployment.domain.upload.Uploader;
import com.javocode.autodeployer.deployment.infrastructure.connector.SFTPConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class SFTPService implements Uploader {
    private static final Logger LOGGER = LogManager.getLogger(SFTPService.class);
    private SFTPConnector sftpConnector;
    private DeployProject deployProject;

    public SFTPService(DeployProject deployProject) {
        this.deployProject = deployProject;
        this.sftpConnector = new SFTPConnector(deployProject.getFileServer());
    }

    public UploadRS upload(Application application) {
        sftpConnector.connect();
        LOGGER.info("Sending file to {}", deployProject.getDeploymentPath());
        boolean isSent = sftpConnector.sendFile(
                application.getFullPathOfFile(), deployProject.getDeploymentPath());
        sftpConnector.close();
        UploadRS uploadRS = new UploadRS();
        uploadRS.setFilePathInRemoteServer(
                deployProject.getDeploymentPath() + application.getName() + application.getExt());
        uploadRS.setSuccess(isSent);
        uploadRS.setUploadedAt(new Date());
        if (isSent) {
            LOGGER.info("File uploaded at: {}", uploadRS.getFilePathInRemoteServer());
        }
        return uploadRS;
    }
}
