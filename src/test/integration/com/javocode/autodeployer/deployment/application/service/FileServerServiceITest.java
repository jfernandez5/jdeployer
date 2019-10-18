package com.javocode.autodeployer.deployment.application.service;

import com.javocode.autodeployer.console.InputData;
import com.javocode.autodeployer.deployment.infrastructure.dao.FileServerDAO;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class FileServerServiceITest {

    @Test
    public void saveAFileServer() {
        //Arr
        FileServerService fileServerService = new FileServerService(new FileServerDAO());
        InputData inputData = new InputData();
        inputData.add("fsHost", "host123");
        inputData.add("fsUser", "utest");
        inputData.add("fsPassword", "ptest");
        //Act
        long id = fileServerService.save(inputData);

        assertThat(id, is(notNullValue()));
        assertThat(id, is(greaterThan(0L)));
    }
}
