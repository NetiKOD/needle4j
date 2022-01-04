package org.needle4j.configuration;

import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionProviderInstancesSupplier;

import java.lang.annotation.Annotation;
import java.util.Set;

public final class NeedleConfiguration implements Cloneable {
  private Set<Class<Annotation>> customInjectionAnnotations;
  private Set<Class<InjectionProvider<?>>> customInjectionProviderClasses;
  private Set<Class<InjectionProviderInstancesSupplier>> customInjectionProviderInstancesSupplierClasses;
  private String persistenceunitName;
  private String hibernateCfgFilename;
  private String mockProviderClassName;
  private String dbOperationClassName;
  private String jdbcUrl;
  private String jdbcDriver;
  private String jdbcUser;
  private String jdbcPassword;
  private PostConstructExecuteStrategy postConstructExecuteStrategy;

  /**
   * Returns the configured custom {@link Annotation} classes for default mock
   * injections.
   *
   * @return a {@link Set} of {@link Annotation} classes
   */
  public Set<Class<Annotation>> getCustomInjectionAnnotations() {
    return customInjectionAnnotations;
  }

  public void setCustomInjectionAnnotations(final Set<Class<Annotation>> customInjectionAnnotations) {
    this.customInjectionAnnotations = customInjectionAnnotations;
  }

  /**
   * Returns the configured custom {@link InjectionProvider} classes.
   *
   * @return a {@link Set} of {@link InjectionProvider} classes
   */
  public Set<Class<InjectionProvider<?>>> getCustomInjectionProviderClasses() {
    return customInjectionProviderClasses;
  }

  public void setCustomInjectionProviderClasses(final Set<Class<InjectionProvider<?>>> customInjectionProviderClasses) {
    this.customInjectionProviderClasses = customInjectionProviderClasses;
  }

  /**
   * Returns the configured database operation class name.
   *
   * @return database operation class name or null
   */
  public String getDBOperationClassName() {
    return dbOperationClassName;
  }

  public void setDBOperationClassName(final String dbOperationClassName) {
    this.dbOperationClassName = dbOperationClassName;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public void setJdbcUrl(final String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public String getJdbcDriver() {
    return jdbcDriver;
  }

  public void setJdbcDriver(final String jdbcDriver) {
    this.jdbcDriver = jdbcDriver;
  }

  public String getJdbcUser() {
    return jdbcUser;
  }

  public void setJdbcUser(final String jdbcUser) {
    this.jdbcUser = jdbcUser;
  }

  public String getJdbcPassword() {
    return jdbcPassword;
  }

  public void setJdbcPassword(final String jdbcPassword) {
    this.jdbcPassword = jdbcPassword;
  }

  /**
   * Returns the name of the configured mock provider class
   *
   * @return mock provider class name or null
   */
  public String getMockProviderClassName() {
    return mockProviderClassName;
  }

  public void setMockProviderClassName(final String mockProviderClassName) {
    this.mockProviderClassName = mockProviderClassName;
  }

  /**
   * Returns the configured jpa persistence unit name.
   *
   * @return jpa persistence unit name
   */
  public String getPersistenceunitName() {
    return persistenceunitName;
  }

  public void setPersistenceunitName(final String persistenceunitName) {
    this.persistenceunitName = persistenceunitName;
  }

  /**
   * Returns the name of the configured hibernate.cfg file
   *
   * @return name of hibernate.cfg file
   */
  public String getHibernateCfgFilename() {
    return hibernateCfgFilename;
  }

  public void setHibernateCfgFilename(final String hibernateCfgFilename) {
    this.hibernateCfgFilename = hibernateCfgFilename;
  }

  public Set<Class<InjectionProviderInstancesSupplier>> getCustomInjectionProviderInstancesSupplierClasses() {
    return customInjectionProviderInstancesSupplierClasses;
  }

  public void setCustomInjectionProviderInstancesSupplierClasses(
      final Set<Class<InjectionProviderInstancesSupplier>> supplier) {
    this.customInjectionProviderInstancesSupplierClasses = supplier;
  }

  public PostConstructExecuteStrategy getPostConstructExecuteStrategy() {
    return postConstructExecuteStrategy;
  }

  public void setPostConstructExecuteStrategy(final PostConstructExecuteStrategy postConstructExecuteStrategy) {
    this.postConstructExecuteStrategy = postConstructExecuteStrategy;
  }

  @Override
  public String toString() {
    return "\nPU_NAME=" + getPersistenceunitName() +
        "\nCFG_FILE=" + getHibernateCfgFilename() +
        "\nDB_OPERATION=" + getDBOperationClassName() +
        "\nMOCK_PROVIDER=" + getMockProviderClassName();
  }

  @Override
  public NeedleConfiguration clone() throws CloneNotSupportedException {
    return (NeedleConfiguration) super.clone();
  }
}
