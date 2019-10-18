package com.javocode.autodeployer.deployment.infrastructure.connector;

import com.javocode.autodeployer.deployment.domain.project.FileServer;
import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SFTPConnector {
    private static final Logger LOGGER = LogManager.getLogger(SFTPConnector.class);
    private FileServer fileServer;
    private int port = 22;
    private static Session session;

    public SFTPConnector(FileServer fileServer) {
        this.fileServer = fileServer;
    }

    public SFTPConnector(int port) {
        this.port = port;
    }

    public void connect() {
        if (session == null || !session.isConnected()) {
            JSch jSch = new JSch();
            try {
                session = jSch.getSession(fileServer.getUsername(), fileServer.getHost(), port);
                session.setPassword(fileServer.getPassword());
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
            } catch (JSchException e) {
                LOGGER.error("Error in connection", e);
            }
        }
    }

    public boolean sendFile(String srcPath, String destPath) {
        boolean status = false;
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            //server path
            channelSftp.connect();

            //local paths
            channelSftp.put(srcPath, destPath);
            status = true;
        } catch (JSchException | SftpException e) {
            LOGGER.error("Error adding a file", e);
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
            }
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
        }
        return status;
    }

    public boolean existsFile(String srcFilePath) {

        boolean status = false;
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            //server path
            channelSftp.connect();

            //local paths
            channelSftp.ls(srcFilePath);
            status = true;
        } catch (JSchException | SftpException e) {
            LOGGER.error("Error adding a file", e);
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
            }
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
        }
        return status;
    }

    public void close() {
        session.disconnect();
    }
}
