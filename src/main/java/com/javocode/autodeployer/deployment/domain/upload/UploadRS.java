package com.javocode.autodeployer.deployment.domain.upload;

import java.util.Date;

public class UploadRS {
    private boolean success;
    private String filePathInRemoteServer;
    private Date uploadedAt;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFilePathInRemoteServer() {
        return filePathInRemoteServer;
    }

    public void setFilePathInRemoteServer(String filePathInRemoteServer) {
        this.filePathInRemoteServer = filePathInRemoteServer;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
