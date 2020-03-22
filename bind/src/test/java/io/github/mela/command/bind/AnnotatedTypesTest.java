package io.github.mela.command.bind;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.google.common.reflect.TypeToken;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.List;
import org.junit.jupiter.api.Test;

class AnnotatedTypesTest {

  @Test
  void testRawType() {
    AnnotatedType type = AnnotatedTypes.fromType(Object.class);
    assertEquals(Object.class, type.getType(), "AnnotatedType did not return correct type");
    assertFalse(type instanceof AnnotatedParameterizedType
            || type instanceof AnnotatedArrayType,
        "Raw type was assigned an incorrect subtype of AnnotatedType");
  }

  @SuppressWarnings("UnstableApiUsage")
  @Test
  void testParameterizedType() {
    TypeToken<List<Object>> token = new TypeToken<List<Object>>() {
    };
    AnnotatedType type = AnnotatedTypes.fromType(token.getType());
    assertEquals(token.getType(), type.getType(),
        "AnnotatedType did not return correct type");
    assertTrue(type instanceof AnnotatedParameterizedType,
        "Parameterized type was assigned an incorrect subtype of AnnotatedType");
    assertEquals(Object.class, ((AnnotatedParameterizedType) type)
            .getAnnotatedActualTypeArguments()[0].getType(),
        "Actual type argument of AnnotatedType and provided type differ");
  }

  @Test
  void testRawArrayType() {
    AnnotatedType type = AnnotatedTypes.fromType(Object[].class);
    assertEquals(Object[].class, type.getType(), "AnnotatedType did not return correct type");
    assertTrue(type instanceof AnnotatedArrayType,
        "Array type was assigned an incorrect subtype of AnnotatedType");
    assertEquals(Object.class, ((AnnotatedArrayType) type).getAnnotatedGenericComponentType().getType(),
        "Component type of AnnotatedType and provided type differ");
  }

  @SuppressWarnings("UnstableApiUsage")
  @Test
  void testGenericArrayType() {
    TypeToken<List<Object>[]> token = new TypeToken<List<Object>[]>() {
    };
    AnnotatedType type = AnnotatedTypes.fromType(token.getType());
    assertEquals(token.getType(), type.getType(), "AnnotatedType did not return correct type");
    assertTrue(type instanceof AnnotatedArrayType,
        "Array type was assigned an incorrect subtype of AnnotatedType");
    TypeToken<List<Object>> componentToken = new TypeToken<List<Object>>() {
    };
    assertEquals(componentToken.getType(), ((AnnotatedArrayType) type).getAnnotatedGenericComponentType().getType(),
        "Component type of AnnotatedType and provided type differ");
  }
}