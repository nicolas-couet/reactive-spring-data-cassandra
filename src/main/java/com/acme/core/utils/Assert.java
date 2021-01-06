package com.acme.core.utils;


import java.util.Collection;

public abstract class Assert extends org.springframework.util.Assert {

  public static void betweenSize(int limitRequested, int limitMin, int limitMax) {
    isTrue(limitRequested >= limitMin && limitRequested <= limitMax,
            String.format("Invalid limit (must be between %d and %d).", limitMin, limitMax));
  }

  public static void notEmpty(String string) {
    notNull(string, "The String argument must not be null");
    isTrue(string.length() > 0, "The string must not be empty");
  }

  public static void isNull(Object object, Class<?> clazz) {
    isNull(object, String.format("The %s argument must be null", clazz.getSimpleName()));
  }

  public static void notNull(Object object, Class<?> clazz) {
    notNull(object,
            String.format("The %s argument is required, it must not be null", clazz.getSimpleName()));
  }

  public static void isRequired(Object object) {
    notNull(object, "This argument is required; it must not be null");
  }

  public static void minSize(Collection<?> collection, int size) {
    notNull(collection, Collection.class);
    isTrue(collection.size() >= size,
            String.format("This collection must contain a minimum of %d elements", size));
  }

  public static void maxSize(Collection<?> collection, int size) {
    notNull(collection, Collection.class);
    isTrue(collection.size() <= size,
            String.format("This collection must contain a maximum of %d elements", size));
  }

  public static void betweenSize(Collection<?> collection, int minSize, int maxSize) {
    minSize(collection, minSize);
    maxSize(collection, maxSize);
  }
}
