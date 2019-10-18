package com.javocode.autodeployer.deployment.application.service;

import com.javocode.autodeployer.deployment.TestUtil;
import com.javocode.autodeployer.deployment.domain.project.DeployProject;
import com.javocode.autodeployer.deployment.domain.project.FileServer;
import com.javocode.autodeployer.deployment.domain.upload.Application;
import com.javocode.autodeployer.deployment.domain.upload.UploadRS;
import com.javocode.autodeployer.deployment.domain.upload.Uploader;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SFTPServiceITest {

    @Test
    public void shouldUploadAFileToServer() {
        //Arr
        FileServer fileServer = TestUtil.getFileServerInfo();
        DeployProject deployProject = new DeployProject();
        deployProject.setFileServer(fileServer);
        Uploader sftpUploader = new SFTPService(deployProject);
        Application application = new Application("/Users/javo/jmeter.log");

        //Act
        UploadRS uploadRS = sftpUploader.upload(application);

        assertThat(uploadRS.isSuccess(), is(true));
    }
}
