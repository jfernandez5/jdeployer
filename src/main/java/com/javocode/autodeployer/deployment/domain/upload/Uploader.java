package com.javocode.autodeployer.deployment.domain.upload;

public interface Uploader {
    UploadRS upload(Application applicationFile);
}
