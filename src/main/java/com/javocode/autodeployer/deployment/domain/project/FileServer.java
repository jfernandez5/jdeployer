package com.javocode.autodeployer.deployment.domain.project;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "file_server")
public class FileServer extends PrivateKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fs_id")
    private Long id;
    private String host;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Protocol protocol = Protocol.SFTP;
    @OneToMany(mappedBy = "fileServer")
    private Set<DeployProject> deployProjects;

    public FileServer() {
    }

    public FileServer(String host, String username, String password, Protocol protocol) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.protocol = protocol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        if (host != null) {
            this.host = host;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null) {
            this.username = username;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocolToUploadFiles) {
        this.protocol = protocolToUploadFiles;
    }

    public Set<DeployProject> getDeployProjects() {
        return deployProjects;
    }

    public void setDeployProjects(Set<DeployProject> deployProjects) {
        this.deployProjects = deployProjects;
    }
}
