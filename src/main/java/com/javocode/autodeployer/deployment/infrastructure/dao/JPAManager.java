package com.javocode.autodeployer.deployment.infrastructure.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JPAManager {

    private static EntityManagerFactory entityManagerFactory = null;
    private static final String NAME = "Autodeployer"; //Match with the name in persistence.xml

    private JPAManager() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(NAME);
        }
        return entityManagerFactory;
    }

    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
