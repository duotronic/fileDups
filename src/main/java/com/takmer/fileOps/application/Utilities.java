package com.takmer.fileOps.application;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utilities {

  static String timeStamp() {
    DateTimeFormatter DATE_TME_FORMATTER
        = DateTimeFormatter.ofPattern("YYMMdd-HHmm");
    LocalDateTime ldt = LocalDateTime.now();
    return ldt.format(DATE_TME_FORMATTER).toString();
  }

  public static void main(String[] args) throws IOException {
    System.out.println(timeStamp());
  }
}
