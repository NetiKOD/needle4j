package org.needle4j;

import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.reflection.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class NeedleContext {

  private final Object test;
  private final Map<String, Object> objectUnderTestMap = new HashMap<String, Object>();
  private final Map<String, ObjectUnderTest> objectUnderTestAnnotationMap = new HashMap<String, ObjectUnderTest>();
  private final Map<Object, Object> injectedObjectMap = new HashMap<Object, Object>();

  private final Map<Class<? extends Annotation>, List<Field>> annotatedTestcaseFieldMap;

  public NeedleContext(final Object test) {
    this.test = test;
    annotatedTestcaseFieldMap = ReflectionUtil.getAllAnnotatedFields(test.getClass());
  }

  public Object getTest() {
    return test;
  }

  @SuppressWarnings("unchecked")
  public <X> X getInjectedObject(final Object key) {
    return (X) injectedObjectMap.get(key);
  }

  public Collection<Object> getInjectedObjects() {
    return injectedObjectMap.values();
  }

  public void addInjectedObject(final Object key, final Object instance) {
    injectedObjectMap.put(key, instance);
  }

  public Object getObjectUnderTest(final String id) {
    return objectUnderTestMap.get(id);
  }

  public ObjectUnderTest getObjectUnderTestAnnotation(final String id) {
    return objectUnderTestAnnotationMap.get(id);
  }

  public void addObjectUnderTest(final String id, final Object instance,
                                 final ObjectUnderTest objectUnderTestAnnotation) {
    objectUnderTestMap.put(id, instance);
    objectUnderTestAnnotationMap.put(id, objectUnderTestAnnotation);
  }

  public Collection<Object> getObjectsUnderTest() {
    return objectUnderTestMap.values();
  }

  public Set<String> getObjectsUnderTestIds() {
    return objectUnderTestMap.keySet();
  }

  public List<Field> getAnnotatedTestcaseFields(final Class<? extends Annotation> annotationClass) {
    final List<Field> value = annotatedTestcaseFieldMap.get(annotationClass);
    return value != null ? value : new ArrayList<Field>();
  }

}
