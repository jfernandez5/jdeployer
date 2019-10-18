package com.javocode.autodeployer.deployment.infrastructure.connector;

import com.javocode.autodeployer.deployment.domain.project.ServerTerminal;
import com.javocode.autodeployer.deployment.util.AppUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SSHConnectorITest {
    private SSHConnector sshConnector;

    @Before
    public void setUp() {
        String filePath = "env-dev.properties";
        ServerTerminal serverTerminal = getServerTerminalData(filePath);
        sshConnector = new SSHConnector(serverTerminal);
    }

    @Ignore("You must change the values in env-*.properties")
    @Test
    public void openSessionInServerBySSH() {
        //Act
        sshConnector.open();

        assertThat(sshConnector.isSessionOpen(), is(true));
    }

    @Ignore("You must change the values in env-*.properties")
    @Test
    public void enterInsideAFolderAndShowItsContentByCommands() {
        // "ll" command doesn't work in this SSH session for some reason.
        //Arr
        sshConnector.open();
        String[] commands = {"ls -l", "ls -l", "ls -l"};
        //Act
        sshConnector.sendCommands(commands);
    }

    @Ignore("You must change the values in env-*.properties and be careful with active processes")
    @Test
    public void stopAndStartSomeService() {
        //Arr
        sshConnector.open();
        String[] commands = new String[2];
        commands[0] = "sudo /etc/init.d/senju-process stop";
        commands[1] = "sudo /etc/init.d/senju-process start";
        //Act
        sshConnector.sendCommands(commands);
    }

    @After
    public void tearDown() {
        if (sshConnector.isSessionOpen())
            sshConnector.close();
    }

    private ServerTerminal getServerTerminalData(String filePath) {
        ServerTerminal serverTerminal = new ServerTerminal();
        Properties props = AppUtil.getPropertiesFile(filePath);
        serverTerminal.setHost(props.getProperty("ssh.host"));
        serverTerminal.setUsername(props.getProperty("ssh.username"));
        serverTerminal.setPassword(props.getProperty("ssh.password"));
        serverTerminal.setKeyPath(props.getProperty("key.path"));
        return serverTerminal;
    }
}
