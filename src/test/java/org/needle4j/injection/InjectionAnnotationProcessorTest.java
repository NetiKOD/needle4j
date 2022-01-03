package org.needle4j.injection;

import org.junit.Rule;
import org.junit.Test;
import org.needle4j.MyEjbComponentBean;
import org.needle4j.annotation.InjectInto;
import org.needle4j.annotation.InjectIntoMany;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.db.User;
import org.needle4j.junit.NeedleRule;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class InjectionAnnotationProcessorTest {
  @Rule
  public NeedleRule _needleRule = new NeedleRule();

  @ObjectUnderTest
  private final UserDao _userDao1 = new UserDao();

  @ObjectUnderTest
  private final UserDao _userDao2 = new UserDao();

  @ObjectUnderTest(id = "testInjectionId")
  private MyEjbComponentBean bean;

  @InjectInto(targetComponentId = "testInjectionId")
  private String test = "Hello";

  @InjectIntoMany
  private final User _user = new User();

  @InjectIntoMany(value = {@InjectInto(targetComponentId = "testInjectionId", fieldName = "queue"),
      @InjectInto(targetComponentId = "_userDao2")})
  private Queue<?> queue = new LinkedBlockingDeque<Object>();

  @Test
  public void testInjectMany() throws Exception {
    assertSame(_user, _userDao1.getCurrentUser());
    assertSame(_user, _userDao2.getCurrentUser());
  }

  @Test
  public void testInjectManyWithInjectInto() throws Exception {
    assertSame(queue, _userDao2.getQueue());
    assertSame(queue, bean.getQueue());

    // these are not the same since userDao1 gets a default mock injected
    assertNotSame(queue, _userDao1.getQueue());
  }

  @Test
  public void testInjectIntoById() throws Exception {
    assertSame(test, bean.getTestInjection());
  }
}
