package com.javocode.autodeployer.deployment.infrastructure.dao;

import com.javocode.autodeployer.deployment.domain.project.FileServer;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileServerDAOITest {

    @Test
    public void getAllFileServers() {

        FileServerDAO fileServerDAO = new FileServerDAO();

        List<FileServer> fileServers = fileServerDAO.findAll();

        assertThat(fileServers.size(), is(2));
    }
}
