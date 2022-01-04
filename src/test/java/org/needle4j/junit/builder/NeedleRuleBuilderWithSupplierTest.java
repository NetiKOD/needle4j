package org.needle4j.junit.builder;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionProviderInstancesSupplier;
import org.needle4j.injection.InjectionProviders;
import org.needle4j.junit.NeedleRule;

import java.util.HashSet;
import java.util.Set;

import static org.needle4j.junit.NeedleBuilders.needleRule;

public class NeedleRuleBuilderWithSupplierTest {
  private final Runnable runnable = new RunnableImpl();

  @SuppressWarnings("unchecked")
  @Rule
  public final NeedleRule needleRule = needleRule().addAnnotation(TestBuilderQualifier.class).addSupplier(new SupplierImpl()).build();

  @ObjectUnderTest
  private ClassToTest objectUnderTest = new ClassToTest();

  @Test
  public void testInjection() {
    Assert.assertNotNull(objectUnderTest.runnable);
    Assert.assertSame(runnable, runnable);
  }

  static class ClassToTest {
    @TestBuilderQualifier
    Runnable runnable;
  }

  static class RunnableImpl implements Runnable {
    @Override
    public void run() {
    }
  }

  class SupplierImpl implements InjectionProviderInstancesSupplier {
    private final Set<InjectionProvider<?>> provider = new HashSet<>();

    public SupplierImpl() {
      provider.add(InjectionProviders.providerForQualifiedInstance(TestBuilderQualifier.class, runnable));
    }

    @Override
    public Set<InjectionProvider<?>> get() {
      return provider;
    }
  }
}
