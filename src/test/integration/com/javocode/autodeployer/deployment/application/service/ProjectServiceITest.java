package com.javocode.autodeployer.deployment.application.service;

import com.javocode.autodeployer.console.InputData;
import com.javocode.autodeployer.deployment.infrastructure.dao.DeployProjectDAO;
import com.javocode.autodeployer.deployment.domain.project.DeployProject;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ProjectServiceITest {

    @Test
    public void createADeployProject() {
        //Arr
        ProjectService projectService = getInstance();
        InputData inputData = new InputData();
        inputData.add("projectName", "primary_proy 2");
        inputData.add("deploymentPath", "/test/db");
        inputData.add("file_server_id", "1");
        inputData.add("server_terminal_id", "1");

        //Act
        projectService.save(inputData);

        DeployProject deployProjectFound = getInstance().findById(4L);
        assertThat(deployProjectFound.getId(), is(4L));
        assertThat(deployProjectFound.getName(), is("primary_proy 2"));
    }

    @Test
    public void updateNameOfDeployProjectById() {
        //Arr
        ProjectService projectService = getInstance();

        InputData inputData = new InputData();
        //DeployProject
        inputData.add("id", "1");
        inputData.add("projectName", "modified");
        //FileServer, ServerTerminal
        inputData.add("fsId", "1");
        inputData.add("stId", "1");
        

        //Act
        projectService.update(inputData);

        DeployProject deployProjectModified = getInstance().findById(
                Long.valueOf(inputData.getData("id")));
        assertThat(deployProjectModified.getId(), is(1L));
        assertThat(deployProjectModified.getName(), is("modified"));
    }

    @Test
    public void findDeployProjectById() {
        //Arr
        ProjectService projectService = getInstance();
        //Act
        DeployProject deployProject = projectService.findById(1L);

        assertThat(deployProject.getId(), is(1L));
        assertThat(deployProject.getName(), is(notNullValue()));
    }

    @Test
    public void deleteDeployProjectById() {
        //Arr
        ProjectService projectService = getInstance();

        //Act
        InputData inputData = new InputData();
        Map<String, String> data = new HashMap<>();
        
        projectService.delete(inputData);

        DeployProject deployProjectModified = getInstance().findById(1);
        assertThat(deployProjectModified, is(nullValue()));
    }

    @Test
    public void getAllDeployProjects() {
        //Arr
        ProjectService projectService = getInstance();
        //Act
        List<DeployProject> deployProjects = projectService.findAll();

        assertThat(deployProjects.size(), is(1));
        assertThat(deployProjects.get(0).getName(), is("modified"));
    }

    private ProjectService getInstance() {
        return new ProjectService(new DeployProjectDAO());
    }
}
