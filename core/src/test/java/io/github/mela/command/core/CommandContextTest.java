package io.github.mela.command.core;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class CommandContextTest {

  @SuppressWarnings("UnstableApiUsage")
  @Test
  void testContextAddition() {
    CommandContext context = CommandContext.create();
    context.put(int.class, "test", 42);
    assertEquals(Integer.valueOf(42), context.get(int.class, "test")
        .orElseThrow(AssertionError::new));

    TypeToken<List<String>> type = new TypeToken<List<String>>() {};
    List<String> list = ImmutableList.of("one", "two", "three");
    context.put(type, "test", list);
    assertEquals(list, context.get(type, "test").orElseThrow(AssertionError::new));

    Object key = new Object();
    Object value = new Object();
    context.put(key, value);
    assertEquals(value, context.get(key).orElseThrow(AssertionError::new));
  }

}
