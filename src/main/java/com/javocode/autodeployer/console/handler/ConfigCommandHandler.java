package com.javocode.autodeployer.console.handler;

import com.javocode.autodeployer.console.ConsoleUtil;
import com.javocode.autodeployer.console.InputData;
import com.javocode.autodeployer.deployment.application.service.FileServerService;
import com.javocode.autodeployer.deployment.application.service.ProjectService;
import com.javocode.autodeployer.deployment.application.service.ServerTerminalService;
import com.javocode.autodeployer.deployment.domain.project.DeployProject;
import com.javocode.autodeployer.deployment.domain.project.FileServer;
import com.javocode.autodeployer.deployment.domain.project.ServerTerminal;
import com.javocode.autodeployer.deployment.infrastructure.dao.DeployProjectDAO;
import com.javocode.autodeployer.deployment.infrastructure.dao.FileServerDAO;
import com.javocode.autodeployer.deployment.infrastructure.dao.JPAManager;
import com.javocode.autodeployer.deployment.infrastructure.dao.ServerTerminalDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConfigCommandHandler implements CommandHandler {
    private static final String CREATE_NEW_SERVER = "2";
    private static final String CHOOSE_SERVER = "1";
    private ProjectService projectService;

    @Override
    public void execute() {
        printMainOptions();
        handleUserInputData();
    }

    private void handleUserInputData() {
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        performOperationsByOption(option);
        closeAll(scanner);
    }

    private void performOperationsByOption(String option) {
        projectService = new ProjectService(new DeployProjectDAO());

        if (Option.CREATE.value.equals(option)) {

            InputData inputData = new InputData();
            inputData.prompt("Enter a name for the project: ")
                    .saveValueAs("projectName");

            ConsoleUtil.print("::: FTP server :::");
            final String chooseText = "Choose a server from list (1) " +
                    "/ Create new server (2), enter an option:";
            inputData.prompt(chooseText).saveValueAs("option");
            if (CREATE_NEW_SERVER.equals(inputData.getData("option"))) {
                //Create
                inputData.prompt("Enter host:").saveValueAs("fsHost");
                inputData.prompt("Enter username:").saveValueAs("fsUsername");
                inputData.prompt("Enter password:").saveValueAs("fsPassword");
                FileServerService fileServerService = new FileServerService(new FileServerDAO());
                long id = fileServerService.save(inputData);
                inputData.add("file_server_id", String.valueOf(id));

            } else if (CHOOSE_SERVER.equals(inputData.getData("option"))) {
                //Choose
                chooseFileServer(inputData);
            }

            ConsoleUtil.print("::: SSH credentials :::");
            inputData.prompt(chooseText).saveValueAs("option");
            if (CREATE_NEW_SERVER.equals(inputData.getData("option"))) {
                //Create
                inputData.prompt("Enter host:").saveValueAs("stHost");
                inputData.prompt("Enter username:").saveValueAs("stUsername");
                inputData.prompt("Enter password:").saveValueAs("stPassword");
                ServerTerminalService serverTerminalService =
                        new ServerTerminalService(new ServerTerminalDAO());
                long id = serverTerminalService.save(inputData);
                inputData.add("server_terminal_id", String.valueOf(id));

            } else if (CHOOSE_SERVER.equals(inputData.getData("option"))) {
                //Choose
                chooseTerminalServer(inputData);
            }
            inputData.prompt("Enter the parent path to deploy (e.g., /Somepath/Myapps/):")
                    .saveValueAs("deploymentPath");

            projectService.save(inputData);
            ConsoleUtil.print("Project created!");
            inputData.clear();
        } else if (Option.UPDATE.value.equals(option)) {

            InputData inputData = new InputData();
            listAllProjects();
            ConsoleUtil.printBlankLine();
            inputData.prompt("Enter the project Id to edit from the list above:").saveValueAs("id");
            inputData.prompt("Edit the name of the project:").saveValueAs("projectName");
            chooseFileServer(inputData);
            chooseTerminalServer(inputData);
            inputData.prompt("Edit the path of the file (optional):").saveValueAs("deploymentPath");
            projectService.update(inputData);
            ConsoleUtil.print("Updated successfully!");
            inputData.clear();

        } else if (Option.DELETE.value.equals(option)) {

            InputData inputData = new InputData();
            listAllProjects();
            ConsoleUtil.printBlankLine();
            inputData.prompt("Enter the project Id to delete from the list above:").saveValueAs("id");
            projectService.delete(inputData);
            ConsoleUtil.print("Deleted successfully!");
            inputData.clear();
        } else if (Option.LIST.value.equals(option)) {

            listAllProjects();
            showSecondaryOptions();
        } else if (Option.GO_TO_START.value.equals(option)) {
            execute();
        } else if (Option.EXIT.value.equals(option)) {
            ConsoleUtil.print("Good bye! :)");
        }
    }

    private void closeAll(Scanner scanner) {
        scanner.close();
        JPAManager.shutdown();
    }

    private void chooseTerminalServer(InputData inputData) {
        ServerTerminalDAO serverTerminalDAO  = new ServerTerminalDAO();
        List<ServerTerminal> serverTerminals = serverTerminalDAO.findAll();
        serverTerminals.forEach(s ->
                ConsoleUtil.printf("ID: %s | HOST: %s%n", s.getId(), s.getHost()));
        inputData.prompt("Choose a SSH Server, enter the ID:")
                .saveValueAs("server_terminal_id");
    }

    private void chooseFileServer(InputData inputData) {
        FileServerDAO fileServerDAO = new FileServerDAO();
        List<FileServer> fileServers = fileServerDAO.findAll();
        fileServers.forEach(f ->
                ConsoleUtil.printf("ID: %s | HOST: %s%n", f.getId(), f.getHost()));
        inputData.prompt("Choose a FTP Server, enter the ID:").saveValueAs("file_server_id");
    }

    private void listAllProjects() {
        List<DeployProject> deployProjects = projectService.findAll();
        ConsoleUtil.print("PROJECTS:");
        StringBuilder stb = new StringBuilder(".");
        for (int i = 0; i < 110; i++) {
            stb.append("-");
        }
        deployProjects.forEach(d -> {
            ConsoleUtil.print(stb.toString());
            ConsoleUtil.printf("|Id: %s | Name: %s | FileServer [host]: %s " +
                            "| SSH Server [host]: %s%n",
                    d.getId(), d.getName(),
                    d.getFileServer().getHost(),
                    d.getServerTerminal().getHost());
        });
    }

    private void showSecondaryOptions() {
        ConsoleUtil.printBlankLine();
        printSecondaryOptions();
        handleUserInputData();
    }

    private void printSecondaryOptions() {
        Map<String, String> options = new HashMap<>();
        ConsoleUtil.print("OPTIONS:");
        options.put("6", "Go to initial config.");
        options.put("5", "Exit.");

        for (Map.Entry<String, String> entry : options.entrySet()) {
            ConsoleUtil.printf("%s. %s %n", entry.getKey(), entry.getValue());
        }
        ConsoleUtil.printBlankLine();
    }

    private void printMainOptions() {
        ConsoleUtil.printBlankLine();
        ConsoleUtil.print("-------------------------");
        ConsoleUtil.print("|     CONFIGURATION     |");
        ConsoleUtil.print("-------------------------");
        ConsoleUtil.printBlankLine();
        Map<String, String> options = new HashMap<>();
        options.put("1", "CREATE a deployment project.");
        options.put("2", "EDIT a deployment project.");
        options.put("3", "DELETE a deployment project.");
        options.put("4", "LIST all projects.");
        options.put("5", "Exit.");

        for (Map.Entry<String, String> entry : options.entrySet()) {
            ConsoleUtil.printf("%s. %s %n", entry.getKey(), entry.getValue());
        }
        ConsoleUtil.printBlankLine();
        ConsoleUtil.print("Enter a number:");
    }

    enum Option {
        CREATE("1"), UPDATE("2"), DELETE("3"), LIST("4"), EXIT("5"), GO_TO_START("6");

        private String value;

        Option(String value) {
            this.value = value;
        }
    }
}
