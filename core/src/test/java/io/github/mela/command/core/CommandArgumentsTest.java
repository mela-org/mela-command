package io.github.mela.command.core;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandArgumentsTest {

  private CommandArguments arguments;

  @BeforeEach
  void setUp() {
    arguments = CommandArguments.of(
        "\"\\\"Lorem ipsum dolor sit amet\\\" consetetur \\\"sadipscing\\\" elitr.\" ( sed (d))iam");
  }

  @Test
  void testCursor() {
    assertEquals(0, arguments.getRawCursor(),
        "Cursor was not in the correct start position");
    arguments.nextString();
    assertEquals(
        "\"\\\"Lorem ipsum dolor sit amet\\\" consetetur \\\"sadipscing\\\" elitr.\" ".length(),
        arguments.getRawCursor(), "Cursor was not moved along correctly"
    );
    arguments.setPosition("( sed (".length());
    assertEquals(
        "\"\\\"Lorem ipsum dolor sit amet\\\" consetetur \\\"sadipscing\\\" elitr.\" ( sed ("
            .length(), arguments.getRawCursor(), "Cursor was not moved forward correctly");
    arguments.resetPosition();
    assertEquals("\"\\\"Lorem ipsum dolor sit amet\\\" consetetur \\\"sadipscing\\\" elitr.\" "
        .length(), arguments.getRawCursor(), "Cursor was not moved back correctly");
  }

  @Test
  void testSingleStringArguments() {
    CommandArguments single = CommandArguments.singleString(arguments.nextString());
    assertEquals(
        "\"\\\"Lorem ipsum dolor sit amet\\\" consetetur \\\"sadipscing\\\" elitr.\"",
        single.getRaw(), "Single string arguments were not created correctly"
    );
  }

  @Test
  void testNext() {
    char next = arguments.next();
    assertEquals('"', next, "next() did not return the next character");
    char previous = arguments.previous();
    assertEquals(next, previous,
        "previous() did not return the previous character/next() did not consume said character");
  }

  @Test
  void testNextString() {
    String word = arguments.nextString();
    assertEquals("\"Lorem ipsum dolor sit amet\" consetetur \"sadipscing\" elitr.", word,
        "nextWord() did not return the following word");
  }

  @Test
  void testPeek() {
    char peeked = arguments.peek();
    char next = arguments.next();
    assertEquals(next, peeked, "peek() did not return the next character");
  }

  @Test
  void testIndexOf() {
    int index = arguments.indexOf("sed");
    arguments.setPosition(index);
    String sed = arguments.nextString();
    assertEquals("sed", sed, "indexOf() did not return the correct index of the given substring");
  }

  @Test
  void testNextScope() {
    while (arguments.hasNext() && arguments.peek() != '(') {
      arguments.next();
    }
    String scope = arguments.nextScope('(', ')');
    assertEquals("sed (d)", scope, "nextScope() did not return the next scope correctly");
  }

}