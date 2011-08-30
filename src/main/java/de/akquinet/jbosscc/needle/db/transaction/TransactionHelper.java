package de.akquinet.jbosscc.needle.db.transaction;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * Utility class to manage transactions conveniently.
 */
public class TransactionHelper {
  private final EntityManager entityManager;

  public TransactionHelper(final EntityManager manager) {
    this.entityManager = manager;
  }

  /**
   * Saves the given object in the database.
   * 
   * @param <T>
   *          -- type of given object obj
   * @param obj
   *          -- object to save
   * @return -- saved object
   * @throws Exception
   *           -- save objects failed
   */
  public final <T> T saveObject(final T obj) throws Exception {
    return executeInTransaction(new Runnable<T>() {
      @Override
      public T run(final EntityManager entityManager) {
        entityManager.persist(obj);
        return obj;
      }
    });
  }

  /**
   * Finds and returns object of the given id in the persistence context.
   * 
   * @param <T>
   *          -- type of searched object
   * @param clazz
   *          -- type of searched object
   * @param id
   *          -- technical id of searched object
   * @return -- found object
   * @throws Exception
   *           -- finding object failed
   */
  public final <T> T loadObject(final Class<T> clazz, final Object id) throws Exception {
    return executeInTransaction(new Runnable<T>() {
      @Override
      public T run(final EntityManager entityManager) {
        return entityManager.find(clazz, id);
      }
    });
  }

  /**
   * Returns all objects of the given class in persistence context.
   * 
   * @param <T>
   *          -- type of searched objects
   * @param clazz
   *          -- type of searched objects
   * @return -- list of found objects
   * @throws Exception
   *           -- finding objects failed
   */
  public final <T> List<T> loadAllObjects(final Class<T> clazz) throws Exception {
    return executeInTransaction(new Runnable<List<T>>() {
      @Override
      @SuppressWarnings("unchecked")
      public List<T> run(final EntityManager entityManager) {
        return entityManager.createQuery("FROM " + clazz.getSimpleName()).getResultList();
      }
    });
  }

  /**
   * Encapsulates execution of runnable.run() in transactions.
   * 
   * @param <T>
   *          -- result type of runnable.run()
   * @param runnable
   *          -- algorithm to execute
   * @param clearAfterCommit
   *          -- <tt>true</tt> triggers entityManager.clear() after transaction commit
   * @return -- return value of runnable.run()
   * @throws Exception
   *           -- execution failed
   */
  public final <T> T executeInTransaction(final Runnable<T> runnable, final boolean clearAfterCommit) throws Exception {
    T result;

    try {
      entityManager.getTransaction().begin();
      result = runnable.run(entityManager);
      entityManager.flush();
      entityManager.getTransaction().commit();

      if (clearAfterCommit) {
        entityManager.clear();
      }
    } finally {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
    }

    return result;
  }

  /**
   * see executeInTransaction(runnable, clearAfterCommit) .
   * 
   * @param <T>
   *          -- result type of runnable.run()
   * @param runnable
   *          -- algorithm to execute
   * @return -- return value of runnable.run()
   * @throws Exception
   *           -- execution failed
   */
  public final <T> T executeInTransaction(final Runnable<T> runnable) throws Exception {
    return executeInTransaction(runnable, true);
  }

  public final EntityManager getEntityManager() {
    return entityManager;
  }
}
