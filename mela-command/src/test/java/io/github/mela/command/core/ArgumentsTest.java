package io.github.mela.command.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {

  private Arguments arguments;

  @BeforeEach
  public void setUp() {
    arguments = Arguments.of(
        "\"\\\"Lorem ipsum dolor sit amet\\\" consetetur \\\"sadipscing\\\" elitr.\" ( sed (d))iam");
  }

  @Test
  public void testNext() {
    char next = arguments.next();
    assertEquals('"', next, "next() did not return the next character");
    char previous = arguments.previous();
    assertEquals(next, previous,
        "previous() did not return the previous character/next() did not consume said character");
  }

  @Test
  public void testNextString() {
    String word = arguments.nextString();
    assertEquals("\\\"Lorem ipsum dolor sit amet\\\" consetetur \\\"sadipscing\\\" elitr.", word,
        "nextWord() did not return the following word");
  }

  @Test
  public void testPeek() {
    char peeked = arguments.peek();
    char next = arguments.next();
    assertEquals(next, peeked, "peek() did not return the next character");
  }

  @Test
  public void testIndexOf() {
    int index = arguments.indexOf("sed");
    arguments.jumpTo(index);
    String sed = arguments.nextString();
    assertEquals("sed", sed, "indexOf() did not return the correct index of the given substring");
  }

  @Test
  public void testNextScope() {
    while (arguments.hasNext() && arguments.peek() != '(') {
      arguments.next();
    }
    String scope = arguments.nextScope('(', ')');
    assertEquals("sed (d)", scope, "nextScope() did not return the next scope correctly");
  }

}