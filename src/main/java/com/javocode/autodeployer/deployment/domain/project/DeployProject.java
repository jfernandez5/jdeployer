package com.javocode.autodeployer.deployment.domain.project;

import javax.persistence.*;

@Entity
@Table(name = "project")
public class DeployProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "fs_id")
    private FileServer fileServer;
    @ManyToOne
    @JoinColumn(name = "st_id")
    private ServerTerminal serverTerminal;
    private String deploymentPath;

    public DeployProject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null)
            this.name = name;
    }

    public FileServer getFileServer() {
        return fileServer;
    }

    public void setFileServer(FileServer fileServer) {
        this.fileServer = fileServer;
    }

    public ServerTerminal getServerTerminal() {
        return serverTerminal;
    }

    public void setServerTerminal(ServerTerminal serverTerminal) {
        this.serverTerminal = serverTerminal;
    }

    public String getDeploymentPath() {
        return deploymentPath;
    }

    public void setDeploymentPath(String deploymentPath) {
        if (deploymentPath != null)
            this.deploymentPath = deploymentPath;
    }
}
