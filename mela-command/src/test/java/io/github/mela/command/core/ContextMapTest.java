package io.github.mela.command.core;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ContextMapTest {

  @SuppressWarnings("UnstableApiUsage")
  @Test
  public void testContextAddition() {
    ContextMap context = ContextMap.create();
    context.put(int.class, "test", 42);
    assertEquals(Integer.valueOf(42), context.get(int.class, "test").orElseThrow());

    TypeToken<List<String>> type = new TypeToken<>() {
    };
    List<String> list = List.of("one", "two", "three");
    context.put(type, "test", list);
    assertEquals(list, context.get(type, "test").orElseThrow());

    Object key = new Object();
    Object value = new Object();
    context.put(key, value);
    assertEquals(value, context.get(key).orElseThrow());
  }

}
