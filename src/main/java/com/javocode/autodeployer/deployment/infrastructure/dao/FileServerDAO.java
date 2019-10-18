package com.javocode.autodeployer.deployment.infrastructure.dao;

import com.javocode.autodeployer.deployment.domain.project.FileServer;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class FileServerDAO {

    private EntityManager entityManager = JPAManager.getEntityManagerFactory().createEntityManager();

    public long save(FileServer fileServer) {
        beginTransaction();
        entityManager.persist(fileServer);
        long fileServerId = fileServer.getId();
        commitTransaction();
        return fileServerId;
    }

    private void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    private void commitTransaction() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<FileServer> findAll() {
        try {
            CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FileServer.class));
            Query q = entityManager.createQuery(cq);
            return q.getResultList();
        } finally {
            entityManager.close();
        }
    }
}
