package org.needle4j.junit;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.needle4j.NeedleTestcase;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.injection.InjectionConfiguration;
import org.needle4j.injection.InjectionProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit {@link MethodRule} for the initialization of the test. The Rule
 * processes and initializes all fields annotated with {@link ObjectUnderTest}.
 *
 * <pre>
 * Example:
 *
 * public class UserDaoBeanTest {
 *
 * 	&#064;Rule
 * 	public NeedleRule needle = new NeedleRule();
 *
 * 	&#064;ObjectUnderTest
 * 	private UserDaoBean userDao;
 *
 * 	&#064;Test
 * 	public void test() {
 * 	 ...
 * 	 userDao.someAction();
 * 	 ...
 *  }
 * }
 *
 * </pre>
 *
 * @see NeedleTestcase
 */
public class NeedleRule extends NeedleTestcase implements MethodRule {
  private final List<MethodRule> methodRuleChain = new ArrayList<>();

  public NeedleRule() {
    super();
  }

  /**
   * @see NeedleTestcase#NeedleTestcase(InjectionProvider...)
   */
  public NeedleRule(final InjectionProvider<?>... injectionProviders) {
    super(injectionProviders);
  }

  NeedleRule(final InjectionConfiguration configuration, final InjectionProvider<?>... injectionProviders) {
    super(configuration, injectionProviders);
  }

  /**
   * {@inheritDoc} Before evaluation of the base statement, the test instance
   * will initialized.
   */
  @Override
  public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
    Statement appliedStatement = base;
    for (final MethodRule rule : methodRuleChain) {
      appliedStatement = rule.apply(appliedStatement, method, target);
    }

    return statement(appliedStatement, target);

  }

  private Statement statement(final Statement base, final Object target) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        initTestcase(target);
        base.evaluate();

      }
    };
  }

  /**
   * Encloses the added rule.
   *
   * @param rule - outer method rule
   * @return {@link NeedleRule}
   */
  public NeedleRule withOuter(final MethodRule rule) {
    if (rule instanceof InjectionProvider) {
      addInjectionProvider((InjectionProvider<?>) rule);
    }
    methodRuleChain.add(0, rule);
    return this;
  }
}
