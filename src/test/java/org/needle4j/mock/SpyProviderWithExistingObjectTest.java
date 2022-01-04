package org.needle4j.mock;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Spy;
import org.needle4j.annotation.InjectInto;
import org.needle4j.annotation.ObjectUnderTest;
import org.needle4j.junit.NeedleRule;
import org.needle4j.mock.SpyProviderTest.A;
import org.needle4j.mock.SpyProviderTest.B;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.needle4j.junit.NeedleBuilders.needleRule;

public class SpyProviderWithExistingObjectTest {
  @Rule
  public final NeedleRule needle = needleRule().withMockProvider(MockitoProvider.class).build();

  @ObjectUnderTest
  @Spy
  private A a;

  // b becomes a spy, although it is already instantiated
  @SuppressWarnings("Convert2Lambda")
  @ObjectUnderTest
  @InjectInto(targetComponentId = "a")
  @Spy
  private final B b = new B() {
    @Override
    public String getName() {
      return "world";
    }
  };

  @Test
  public void shouldInjectSpyForA() {
    when(b.getName()).thenReturn("world");

    assertEquals(a.hello(), "hello world");
    verify(a).hello();
    verify(b).getName();
  }
}
