package org.needle4j.junit;

import org.needle4j.configuration.NeedleConfiguration;
import org.needle4j.db.DatabaseTestcase;
import org.needle4j.db.operation.AbstractDBOperation;

@SuppressWarnings("unchecked")
public abstract class AbstractDatabaseRuleBuilder<B, R extends DatabaseTestcase> extends AbstractRuleBuilder<B, R> {
  private Class<? extends AbstractDBOperation> dbOperationClass;

  private String persistenceUnitName;
  private String jdbcUrl;
  private String jdbcDriver;
  private String jdbcUser;
  private String jdbcPassword;
  private String hibernateCfgFilename;

  @Override
  public B fromResource(final String configurationResourceName) {
    this.configurationResourceName = configurationResourceName;
    return (B) this;
  }

  public B withDBOperation(final Class<? extends AbstractDBOperation> dbOperationClass) {
    this.dbOperationClass = dbOperationClass;
    return (B) this;
  }

  public B withJdbcUser(final String jdbcUser) {
    this.jdbcUser = jdbcUser;
    return (B) this;
  }

  public B withJdbcPassword(final String jdbcPassword) {
    this.jdbcPassword = jdbcPassword;
    return (B) this;
  }

  public B withJdbcUrl(final String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
    return (B) this;
  }

  public B withPersistenceUnit(final String name) {
    this.persistenceUnitName = name;
    return (B) this;
  }

  public B withHibernateCfgFilename(final String name) {
    this.hibernateCfgFilename = name;
    return (B) this;
  }

  public B withJdbcDriver(final String jdbcDriver) {
    this.jdbcDriver = jdbcDriver;
    return (B) this;
  }

  @Override
  protected final R build(final NeedleConfiguration needleConfiguration) {
    needleConfiguration.setDBOperationClassName(dbOperationClass != null ? dbOperationClass.getName()
        : needleConfiguration.getDBOperationClassName());
    needleConfiguration.setJdbcDriver(jdbcDriver != null ? jdbcDriver : needleConfiguration.getJdbcDriver());
    needleConfiguration.setJdbcUser(jdbcUser != null ? jdbcUser : needleConfiguration.getJdbcUser());
    needleConfiguration
        .setJdbcPassword(jdbcPassword != null ? jdbcPassword : needleConfiguration.getJdbcPassword());
    needleConfiguration.setJdbcUrl(jdbcUrl != null ? jdbcUrl : needleConfiguration.getJdbcUrl());
    needleConfiguration.setPersistenceunitName(persistenceUnitName != null ? persistenceUnitName
        : needleConfiguration.getPersistenceunitName());
    needleConfiguration.setHibernateCfgFilename(hibernateCfgFilename != null ? hibernateCfgFilename
        : needleConfiguration.getHibernateCfgFilename());

    return createRule(needleConfiguration);
  }

  protected abstract R createRule(final NeedleConfiguration needleConfiguration);

}
