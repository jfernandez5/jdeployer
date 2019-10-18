package com.javocode.autodeployer.deployment.application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class FileService {

    private static final Logger LOGGER = LogManager.getLogger(FileService.class);

    public String makeBackup(String sourcePath, String backupTargetPath) {
        if (backupTargetPath.isEmpty()) {
            throw new IllegalArgumentException("backup.path in pathconfig.properties is empty");
        }
        String[] list = sourcePath.split("/");
        String fileNameWithExt = list[list.length - 1];
        Map<String, String> file = splitToMap(fileNameWithExt);
        try {
            new File(backupTargetPath).mkdirs();
            long unixtime = System.currentTimeMillis();
            Path fullBackupPath = Paths.get(
                    backupTargetPath + file.get("name")
                            + "-BK-" + String.valueOf(unixtime) + file.get("ext"));
            Files.copy(new File(sourcePath).toPath(),
                    fullBackupPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Backup file generated locally at: {}", fullBackupPath.toString());
            return fullBackupPath.toString();
        } catch (IOException e) {
            LOGGER.error("makeBackup", e);
        }
        return "";
    }

    public static Map<String, String> splitToMap(String fileName) {
        String name = fileName.substring(0, fileName.indexOf("."));
        String extension = fileName.substring(fileName.indexOf("."));
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("ext", extension);
        return map;
    }
}
