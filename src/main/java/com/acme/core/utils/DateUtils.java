package com.acme.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

  public static LocalDateTime getCurrentDate() {
    return LocalDateTime.now();
  }

  public static String format(LocalDateTime localDateTime) {
    Assert.notNull(localDateTime, "DateTime must not be null");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    return localDateTime.format(formatter);
  }
}
