package com.javocode.autodeployer.deployment.infrastructure.connector;

import com.javocode.autodeployer.deployment.domain.project.ServerTerminal;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SSHConnectorTest {

    @Test
    public void joinCommandsWithDateBetweenEachCommand() {
        //Arr
        SSHConnector sshConnector = new SSHConnector(new ServerTerminal());
        String[] commands = {"cd bots/", "ls", "pwd"};
        //Act
        String commandsJoined = sshConnector.joinCommands(commands);

        assertThat(commandsJoined,
                is("cd bots/ " +
                        "&&\necho \"# Command completed at: $(date) >\" " +
                        "&&\nls &&\necho \"# Command completed at: $(date) >\" " +
                        "&&\npwd &&\necho \"# Command completed at: $(date) >\""));
    }

    @Test
    public void joinCommandsWithDateBetweenEachCommandIgnoringErrors() {
        //Arr
        SSHConnector sshConnector = new SSHConnector(new ServerTerminal());
        String[] commands = {"cd bots/", "ls", "pwd"};
        //Act
        String commandsJoined = sshConnector.joinCommands(commands, true);

        assertThat(commandsJoined,
                is("cd bots/ " +
                        "||\necho \"# Command completed at: $(date) >\" " +
                        "||\nls ||\necho \"# Command completed at: $(date) >\" " +
                        "||\npwd ||\necho \"# Command completed at: $(date) >\""));
    }

    @Test
    public void formatOutput() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" +
                "total sometext " +
                "# Command completed at: Fri Aug 23 00:01:40 -05 2019 > " +
                "total sometext2 # Command completed at: Fri Aug 23 00:01:40 -05 2019 > \n");
        SSHConnector sshConnector = new SSHConnector(new ServerTerminal());

        String result = sshConnector.formatOutput(stringBuilder, 2);

        assertThat(result, is("\n" +
                "total sometext \n" +
                "# Command completed at: Fri Aug 23 00:01:40 -05 2019 >\n" +
                " total sometext2 \n" +
                "# Command completed at: Fri Aug 23 00:01:40 -05 2019 >\n "));
    }
}
