package org.needle4j.injection;

import org.needle4j.NeedleContext;
import org.needle4j.processor.AbstractNeedleProcessor;
import org.needle4j.reflection.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class TestcaseInjectionProcessor extends AbstractNeedleProcessor {

  private final Logger logger = LoggerFactory.getLogger(TestcaseInjectionProcessor.class);

  public TestcaseInjectionProcessor(final InjectionConfiguration configuration) {
    super(configuration);
  }

  @Override
  public void process(final NeedleContext context) {
    final Set<Class<? extends Annotation>> supportedAnnotations = configuration.getSupportedAnnotations();

    for (Class<? extends Annotation> supportedAnnotation : supportedAnnotations) {
      processAnnotation(context, configuration, supportedAnnotation);
    }
  }

  private void processAnnotation(final NeedleContext context, final InjectionConfiguration configuration,
                                 final Class<? extends Annotation> annotation) {
    final List<Field> fields = context.getAnnotatedTestcaseFields(annotation);

    for (Field field : fields) {
      processField(context, configuration, field);
    }
  }

  private void processField(final NeedleContext context, final InjectionConfiguration configuration, final Field field) {
    final List<List<InjectionProvider<?>>> injectionProviderList = configuration.getInjectionProvider();
    final InjectionTargetInformation injectionTargetInformation = new InjectionTargetInformation(field.getType(),
        field);

    for (Collection<InjectionProvider<?>> injectionProvider : injectionProviderList) {
      Entry<Object, Object> injection = configuration.handleInjectionProvider(injectionProvider,
          injectionTargetInformation);

      if (injection != null) {
        Object contextObject = context.getInjectedObject(injection.getKey());
        Object injectedObject = contextObject != null ? contextObject : injection.getValue();

        try {
          ReflectionUtil.setField(field, context.getTest(), injectedObject);
          return;

        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      }
    }
  }

}
