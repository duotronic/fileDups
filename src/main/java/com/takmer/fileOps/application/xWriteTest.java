package com.takmer.fileOps.application;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.security.MessageDigest;
import java.util.zip.CRC32;

import org.apache.commons.io.FilenameUtils;

public class xWriteTest {

  // create directory & datafile.txt
  static void createFile() {
    // thanks to javacodeexamples.com
    String dupsDataFile = System.getProperty("user.home")
        + File.separator + "Documents"
        + File.separator + "fileDups" + timeStamp()
        + File.separator + "dupsdata.txt";

    boolean isCreatedFlg = false;
    try {
      File file = new File(dupsDataFile);
      file.getParentFile().mkdirs();
      isCreatedFlg = file.createNewFile();
    } catch (Exception e) {
      System.out.println(
          "failed: creation of " + dupsDataFile);
      e.printStackTrace();
    }
  }

  static String timeStamp() {
    LocalDateTime ldt = LocalDateTime.now();
    DateTimeFormatter DATE_TME_FORMATTER
        = DateTimeFormatter.ofPattern("YYMMdd-HHmm");
    String str = ldt.format(DATE_TME_FORMATTER);

    return ldt.format(DATE_TME_FORMATTER).toString();
  }


  public static void main(String[] args) {

    createFile();
  }
}
