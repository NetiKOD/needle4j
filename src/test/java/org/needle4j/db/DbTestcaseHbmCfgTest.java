package org.needle4j.db;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.junit.DatabaseRule;

import javax.persistence.EntityManager;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class DbTestcaseHbmCfgTest {

  private final static Class<?>[] entityClasses = {Person.class, Address.class};

  @Rule
  public DatabaseRule db = new DatabaseRule(entityClasses);

  @Test
  public void testPersist() throws Exception {
    final Person person = new Person();
    final EntityManager entityManager = db.getEntityManager();

    person.setMyName("My Name");

    assertNotNull(db);
    assertNotNull(entityManager);

    entityManager.getTransaction().begin();
    entityManager.persist(person);

    final Person fromDB = entityManager.find(Person.class, person.getId());

    assertThat(fromDB.getMyName(), equalTo(person.getMyName()));
    entityManager.getTransaction().commit();
  }

}
