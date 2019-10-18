package com.javocode.autodeployer.deployment.domain.project;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(String.format("Project not found with id: %s", message));
    }
}
