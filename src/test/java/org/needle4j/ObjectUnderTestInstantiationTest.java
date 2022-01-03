package org.needle4j;

import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.reflection.ReflectionUtil;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class ObjectUnderTestInstantiationTest {

  @ObjectUnderTest
  private MyEjbComponent ejbComponent;

  @ObjectUnderTest
  private PrivateConstructorClass privateConstructorClass;

  @ObjectUnderTest
  private NoArgsConstructorClass noArgsConstructorClass;

  @Test(expected = ObjectUnderTestInstantiationException.class)
  public void testInterfaceInstantiation() throws Exception {

    setInstanceIfNotNull("ejbComponent");
  }

  @Test(expected = ObjectUnderTestInstantiationException.class)
  public void testNoArgConstuctorInstantiation() throws Exception {
    setInstanceIfNotNull("noArgsConstructorClass");
  }

  @Test(expected = ObjectUnderTestInstantiationException.class)
  public void testNoPublicConstuctorInstantiation() throws Exception {
    setInstanceIfNotNull("privateConstructorClass");
  }

  private void setInstanceIfNotNull(final String fieldName) throws Exception {
    final NeedleTestcase needleTestcase = new NeedleTestcase() {
    };

    final Field field = ObjectUnderTestInstantiationTest.class.getDeclaredField(fieldName);
    final ObjectUnderTest objectUnderTestAnnotation = field.getAnnotation(ObjectUnderTest.class);

    ReflectionUtil.invokeMethod(needleTestcase, "setInstanceIfNotNull", field, objectUnderTestAnnotation, this);

  }
}
