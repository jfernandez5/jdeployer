package com.javocode.autodeployer.deployment.infrastructure.dao;

import com.javocode.autodeployer.deployment.domain.project.DeployProject;
import com.javocode.autodeployer.deployment.domain.project.FileServer;
import com.javocode.autodeployer.deployment.domain.project.Protocol;
import com.javocode.autodeployer.deployment.domain.project.ServerTerminal;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DeployProjectDAOITest {

    @Test
    public void saveDeployProject() {
        //Arr
        DeployProject deployProject = new DeployProject();
        FileServer fileServer = new FileServer();
        fileServer.setUsername("sftpjavocode");
        fileServer.setPassword("yxyxyxyx");
        fileServer.setHost("10.5.12.10");
        fileServer.setProtocol(Protocol.SFTP);
        FileServerDAO fileServerDAO = new FileServerDAO();
        fileServerDAO.save(fileServer);
        deployProject.setFileServer(fileServer);
        ServerTerminal serverTerminal = new ServerTerminal();
        serverTerminal.setUsername("sshjavocode");
        serverTerminal.setPassword("xxxx");
        serverTerminal.setHost("10.5.2.130");
        serverTerminal.setProtocol(Protocol.SSH);
        ServerTerminalDAO serverTerminalDAO = new ServerTerminalDAO();
        serverTerminalDAO.save(serverTerminal);
        deployProject.setServerTerminal(serverTerminal);
        deployProject.setDeploymentPath("/home/ubuntu/");

        //Act
        DeployProjectDAO deployProjectDAO = new DeployProjectDAO();
        deployProjectDAO.save(deployProject);

        assertThat(deployProject.getId(), is(1L));
    }

    @Test
    public void findAllDeployProjects() {
        DeployProjectDAO deployProjectDAO = new DeployProjectDAO();

        List<DeployProject> deployProjects = deployProjectDAO.findAll();

        assertThat(deployProjects.size(), is(greaterThan(0)));
    }

    @After
    public void tearDown() {
        JPAManager.shutdown();
    }
}
