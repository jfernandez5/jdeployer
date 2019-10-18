package com.javocode.autodeployer.deployment;

import com.javocode.autodeployer.deployment.domain.project.FileServer;
import com.javocode.autodeployer.deployment.util.AppUtil;

import java.util.Properties;

public class TestUtil {

    private TestUtil() {
    }

    public static FileServer getFileServerInfo() {
        Properties props = AppUtil.getPropertiesFile("env-dev.properties");
        FileServer fileServer = new FileServer();
        fileServer.setHost(String.valueOf(props.get("ftp.host")));
        fileServer.setUsername(String.valueOf(props.get("ftp.username")));
        fileServer.setPassword(String.valueOf(props.get("ftp.password")));
        return fileServer;
    }
}
