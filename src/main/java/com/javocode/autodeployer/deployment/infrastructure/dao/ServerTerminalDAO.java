package com.javocode.autodeployer.deployment.infrastructure.dao;

import com.javocode.autodeployer.deployment.domain.project.ServerTerminal;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ServerTerminalDAO {

    private EntityManager entityManager = JPAManager.getEntityManagerFactory().createEntityManager();

    public long save(ServerTerminal serverTerminal) {
        beginTransaction();
        entityManager.persist(serverTerminal);
        long serverTerminalId = serverTerminal.getId();
        commitTransaction();
        return serverTerminalId;
    }

    private void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    private void commitTransaction() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<ServerTerminal> findAll() {
        try {
            CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ServerTerminal.class));
            Query q = entityManager.createQuery(cq);
            return q.getResultList();
        } finally {
            entityManager.close();
        }
    }
}
