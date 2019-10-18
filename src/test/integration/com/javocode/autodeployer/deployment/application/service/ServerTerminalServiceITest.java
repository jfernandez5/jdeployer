package com.javocode.autodeployer.deployment.application.service;

import com.javocode.autodeployer.console.InputData;
import com.javocode.autodeployer.deployment.infrastructure.dao.ServerTerminalDAO;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ServerTerminalServiceITest {

    @Test
    public void saveAServerTerminal() {
        //Arr
        ServerTerminalService serverTerminalService =
                new ServerTerminalService(new ServerTerminalDAO());
        InputData inputData = new InputData();

        inputData.add("stHost", "host213");
        inputData.add("stUsername", "u123123");
        inputData.add("stPassword", "p123123");
        //Act
        long id = serverTerminalService.save(inputData);

        assertThat(id, is(notNullValue()));
        assertThat(id, is(greaterThan(0L)));
    }
}
