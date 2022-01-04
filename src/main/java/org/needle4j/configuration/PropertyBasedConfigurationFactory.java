package org.needle4j.configuration;

import org.needle4j.injection.InjectionProvider;
import org.needle4j.injection.InjectionProviderInstancesSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

public class PropertyBasedConfigurationFactory {
  private static final Logger LOG = LoggerFactory.getLogger(NeedleConfiguration.class);

  private static NeedleConfiguration CONFIGURATION = null;

  public static NeedleConfiguration get() {
    if (CONFIGURATION == null) {

      CONFIGURATION = new PropertyBasedConfigurationFactory().init();
    }
    return CONFIGURATION;
  }

  public static NeedleConfiguration get(final String resourceName) {
    return new PropertyBasedConfigurationFactory(resourceName).init();
  }

  private final Map<String, String> configurationProperties;

  private final LookupCustomClasses lookupCustomClasses;

  private PropertyBasedConfigurationFactory() {
    this(new ConfigurationLoader());
  }

  private PropertyBasedConfigurationFactory(final String resourceName) {
    this(new ConfigurationLoader(resourceName));
  }

  private PropertyBasedConfigurationFactory(final ConfigurationLoader configurationLoader) {
    configurationProperties = configurationLoader.getConfigProperties();
    lookupCustomClasses = new LookupCustomClasses(configurationProperties);
  }

  private NeedleConfiguration init() {
    final NeedleConfiguration configuration = new NeedleConfiguration();
    final Set<Class<Annotation>> customInjectionAnnotations = lookupCustomClasses
        .lookup(ConfigurationProperties.CUSTOM_INJECTION_ANNOTATIONS_KEY);
    configuration.setCustomInjectionAnnotations(customInjectionAnnotations);

    final Set<Class<InjectionProvider<?>>> customInjectionProviderClasses = lookupCustomClasses
        .lookup(ConfigurationProperties.CUSTOM_INJECTION_PROVIDER_CLASSES_KEY);
    configuration.setCustomInjectionProviderClasses(customInjectionProviderClasses);

    final Set<Class<InjectionProviderInstancesSupplier>> supplier = lookupCustomClasses
        .lookup(ConfigurationProperties.CUSTOM_INSTANCES_SUPPLIER_CLASSES_KEY);
    configuration.setCustomInjectionProviderInstancesSupplierClasses(supplier);

    configuration.setHibernateCfgFilename(configurationProperties
        .get(ConfigurationProperties.HIBERNATE_CFG_FILENAME_KEY));
    configuration.setPersistenceunitName(configurationProperties
        .get(ConfigurationProperties.PERSISTENCEUNIT_NAME_KEY));

    configuration.setMockProviderClassName(configurationProperties.get(ConfigurationProperties.MOCK_PROVIDER_KEY));

    configuration.setDBOperationClassName(configurationProperties.get(ConfigurationProperties.DB_OPERATION_KEY));
    configuration.setJdbcUrl(configurationProperties.get(ConfigurationProperties.JDBC_URL_KEY));
    configuration.setJdbcDriver(configurationProperties.get(ConfigurationProperties.JDBC_DRIVER_KEY));
    configuration.setJdbcUser(configurationProperties.get(ConfigurationProperties.JDBC_USER_KEY));
    configuration.setJdbcPassword(configurationProperties.get(ConfigurationProperties.JDBC_PASSWORD_KEY));

    configuration.setPostConstructExecuteStrategy(
        PostConstructExecuteStrategy.fromString(configurationProperties.get(ConfigurationProperties.POSTCONSTRUCT_EXECUTESTRATEGY)));

    LOG.info("Needle Configuration: {}", configuration);

    return configuration;
  }
}
