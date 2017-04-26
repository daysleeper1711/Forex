package daysleeper.project.forex.tradelog.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class AbstractService<T> {

    //-------------------------------------------------
    // Inject entity manager
    //-------------------------------------------------
    protected EntityManager em = Persistence.createEntityManagerFactory("TradeLogPU")
            .createEntityManager();

    //-------------------------------------------------
    // Entity class declaration
    //-------------------------------------------------
    private Class<T> entityClass;

    //-------------------------------------------------
    // Constructor
    //-------------------------------------------------
    public AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //-------------------------------------------------
    // Persist
    //-------------------------------------------------
    public void persist(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    //-------------------------------------------------
    // Merge
    //-------------------------------------------------
    public T merge(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        return entity;
    }

    //-------------------------------------------------
    // Remove
    //-------------------------------------------------
    public void remove(T entity) {
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.getTransaction().commit();
    }

    //-------------------------------------------------
    // Find entity by Id
    //-------------------------------------------------
    public T findById(Object id) {
        return em.find(entityClass, id);
    }

    //-------------------------------------------------
    // Find the list of entities by the criteria
    //-------------------------------------------------
    public List<T> findCollection(T example, int from, int size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> root = cq.from(entityClass);
        cq.select(root).orderBy(cb.desc(root.get("created"))).where(getPredicates(root, example));
        return em.createQuery(cq)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }

    //-------------------------------------------------
    // Count how many entities return
    //-------------------------------------------------
    public Long count(T example) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);
        cq.select(cb.count(root)).where(getPredicates(root, example));
        return em.createQuery(cq)
                .getSingleResult();
    }

    //-------------------------------------------------------------
    // Abtract method to get the predicates for find the collection
    //-------------------------------------------------------------
    public abstract Predicate[] getPredicates(Root<T> root, T example);
}
