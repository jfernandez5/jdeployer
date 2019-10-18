package com.javocode.autodeployer.deployment.application;

import com.javocode.autodeployer.deployment.domain.deploy.DeployOperations;

public class DeploymentController {
    private DeployOperations deployOperations;

    public DeploymentController(DeployOperations deployOperations) {
        this.deployOperations = deployOperations;
    }

    public void deploy(String... commands) {
        deployOperations.deploy(commands);
    }
}
