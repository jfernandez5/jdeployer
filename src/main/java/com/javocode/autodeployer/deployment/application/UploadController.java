package com.javocode.autodeployer.deployment.application;

import com.javocode.autodeployer.deployment.application.service.FileService;
import com.javocode.autodeployer.deployment.domain.upload.Application;
import com.javocode.autodeployer.deployment.domain.upload.UploadRS;
import com.javocode.autodeployer.deployment.domain.upload.Uploader;
import com.javocode.autodeployer.deployment.util.AppUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class UploadController {
    private static final Logger LOGGER = LogManager.getLogger(UploadController.class);
    private Uploader uploader;
    private FileService fileService;

    public UploadController(Uploader uploader, FileService fileService) {
        this.uploader = uploader;
        this.fileService = fileService;
    }

    public UploadRS upload(String filePathToUpload) {
        LOGGER.info(">>> Upload process started.");
        Properties props = AppUtil.getPropertiesFile("extconfig.properties");
        String filePath = fileService.makeBackup(filePathToUpload, props.getProperty("local.backup.path"));
        UploadRS uploadRS = uploader.upload(new Application(filePath));
        LOGGER.info("<<< Upload process finished.");
        return uploadRS;
    }
}
