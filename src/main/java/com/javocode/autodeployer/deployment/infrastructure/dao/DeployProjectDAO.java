package com.javocode.autodeployer.deployment.infrastructure.dao;

import com.javocode.autodeployer.deployment.domain.project.DeployProject;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collections;
import java.util.List;

public class DeployProjectDAO {

    private EntityManager entityManager = JPAManager.getEntityManagerFactory().createEntityManager();

    public void save(DeployProject deployProject) {
        beginTransaction();
        entityManager.persist(deployProject);
        commitTransaction();
    }

    public DeployProject findById(Long id) {
        return entityManager.find(DeployProject.class, id);
    }

    private void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    private void commitTransaction() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void update(DeployProject deployProject) {
        beginTransaction();
        DeployProject deployProjectFound = findById(deployProject.getId());
        deployProjectFound.setName(deployProject.getName());
        deployProjectFound.setDeploymentPath(deployProject.getDeploymentPath());
        commitTransaction();
    }

    public void delete(long id) {
        beginTransaction();
        DeployProject deployProject = findById(id);
        entityManager.remove(deployProject);
        commitTransaction();
    }

    public List<DeployProject> findAll() {

        try {
            CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DeployProject.class));
            Query q = entityManager.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
