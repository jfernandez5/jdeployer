package com.javocode.autodeployer.deployment.application.service;

import com.javocode.autodeployer.console.InputData;
import com.javocode.autodeployer.deployment.infrastructure.dao.FileServerDAO;
import com.javocode.autodeployer.deployment.domain.project.FileServer;

public class FileServerService {

    private FileServerDAO fileServerDAO;
    public FileServerService(FileServerDAO fileServerDAO) {
        this.fileServerDAO = fileServerDAO;
    }

    public long save(InputData inputDataProvider) {
        FileServer fileServer = new FileServer();
        fileServer.setUsername(inputDataProvider.getData("fsUsername"));
        fileServer.setPassword(inputDataProvider.getData("fsPassword"));
        fileServer.setHost(inputDataProvider.getData("fsHost"));
        return fileServerDAO.save(fileServer);
    }
}
