package com.javocode.autodeployer.deployment.application.service;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FileServiceITest {

    @Test
    public void makeABackupOfFile() {

        //Arr
        FileService fileService = new FileService();
        String sourcePath = "/Users/javo/jmeter.log";
        String backupTargetPath = "/Users/javo/DeployBackups/";

        //Act
        String pathResponse = fileService.makeBackup(sourcePath, backupTargetPath);

        assertThat(pathResponse, is(notNullValue()));
        assertThat(new File(pathResponse).exists(), is(true));
    }
}
