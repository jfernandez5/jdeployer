package com.javocode.autodeployer.deployment.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class AppUtil {

    private static final Logger LOGGER = LogManager.getLogger(AppUtil.class);

    private AppUtil() {
    }

    public static Properties getPropertiesFile(String fileName) {
        Properties props = new Properties();
        Path currentPath = Paths.get("");
        String parent = currentPath.toAbsolutePath().getParent().toString();
        try {
            InputStream is = new FileInputStream(parent + "/" + fileName);
            props.load(is);
        } catch (IOException e) {
            LOGGER.error("getPropertiesFile", e);
            throw new PropertiesFileException("Error on read properties file", e);
        }
        return props;
    }
}
