package com.javocode.autodeployer.deployment.domain.project;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "server_terminal")
public class ServerTerminal extends PrivateKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "st_id")
    private Long id;
    private String host;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Protocol protocol = Protocol.SSH;
    @OneToMany(mappedBy = "serverTerminal")
    private Set<DeployProject> deployProjects;

    public ServerTerminal() {
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

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Set<DeployProject> getDeployProjects() {
        return deployProjects;
    }

    public void setDeployProjects(Set<DeployProject> deployProjects) {
        this.deployProjects = deployProjects;
    }
}
