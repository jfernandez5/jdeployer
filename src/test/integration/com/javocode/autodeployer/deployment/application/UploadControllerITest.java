package com.javocode.autodeployer.deployment.application;

import com.javocode.autodeployer.deployment.TestUtil;
import com.javocode.autodeployer.deployment.application.service.FileService;
import com.javocode.autodeployer.deployment.application.service.SFTPService;
import com.javocode.autodeployer.deployment.domain.project.DeployProject;
import com.javocode.autodeployer.deployment.domain.project.FileServer;
import com.javocode.autodeployer.deployment.domain.upload.UploadRS;
import com.javocode.autodeployer.deployment.infrastructure.connector.SFTPConnector;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UploadControllerITest {

    private UploadController uploadController;
    private FileServer fileServer;

    @Before
    public void setUp() {
        DeployProject deployProject = new DeployProject();
        fileServer = TestUtil.getFileServerInfo();
        deployProject.setFileServer(fileServer);
        deployProject.setDeploymentPath("/home/javocode/apps/");
        SFTPService SFTPService = new SFTPService(deployProject);
        uploadController = new UploadController(SFTPService, new FileService());
    }

    @Ignore("Server must be available")
    @Test
    public void uploadSomeFileToServer() {
        String path = "/Users/javo/jmeter.log";

        UploadRS uploadRS = uploadController.upload(path);

        assertThatFileWasUploaded(uploadRS);
    }

    private void assertThatFileWasUploaded(UploadRS uploadRS) {
        assertThat(uploadRS.isSuccess(), is(true));

        SFTPConnector sftpConnector = new SFTPConnector(fileServer);
        sftpConnector.connect();
        assertThat(sftpConnector.existsFile(
                uploadRS.getFilePathInRemoteServer()), is(true));
        sftpConnector.close();
    }
}
