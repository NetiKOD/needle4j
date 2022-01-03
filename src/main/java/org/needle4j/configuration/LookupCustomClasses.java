package org.needle4j.configuration;

import org.needle4j.reflection.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import static org.needle4j.common.Preconditions.checkArgument;

/**
 * Function to lookup classes. Expects FQN classnames separated by colon.
 */
class LookupCustomClasses {

  private final Logger logger = LoggerFactory.getLogger(LookupCustomClasses.class);
  private final Map<String, String> configurationProperties;

  public LookupCustomClasses(final Map<String, String> configurationProperties) {
    checkArgument(configurationProperties != null, "configurationProperties must not be null!");
    this.configurationProperties = configurationProperties;
  }

  public <T> Set<Class<T>> lookup(final String key) {
    final String classesList = configurationProperties.containsKey(key) ? configurationProperties.get(key) : "";

    final Set<Class<T>> result = new HashSet<Class<T>>();
    final StringTokenizer tokenizer = new StringTokenizer(classesList, ",");

    String token = null;
    while (tokenizer.hasMoreElements()) {
      try {
        token = tokenizer.nextToken().trim();
        if (token == null || "".equals(token)) {
          continue;
        }

        @SuppressWarnings("unchecked") final Class<T> clazz = (Class<T>) ReflectionUtil.forName(token);

        if (clazz != null) {
          result.add(clazz);
        } else {
          logger.warn("could not load class '{}'", token);
        }
      } catch (final Exception e) {
        logger.warn("could not load class '" + token + "'", e);
      }
    }

    return result;
  }
}
