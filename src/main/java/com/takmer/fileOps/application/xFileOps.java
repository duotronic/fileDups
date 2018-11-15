package com.takmer.fileOps.application;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class xFileOps {

  static String timeStamp() {
    LocalDateTime ldt = LocalDateTime.now();
    DateTimeFormatter DATE_TME_FORMATTER
        = DateTimeFormatter.ofPattern("YYMMdd-HHmm");
    return ldt.format(DATE_TME_FORMATTER).toString();
  }

  static PrintWriter createFile(String fileName) {
    // thanks to javacodeexamples.com

    String filePathString = System.getProperty("user.home")
        + File.separator + "Documents"
//  + File.separator + "fileDups"
        + File.separator + fileName;

    File f = new File(filePathString);
    if (f.exists() && !f.isDirectory()) {
      try {
        f.delete();
      } catch (Exception e) {
        System.out.print(
            "failed: deletion of " + filePathString);
        e.printStackTrace();
      }
    }

    // create  file
    File file = null;
    boolean isCreatedFlg = false;
    try {
      file = new File(filePathString);
      file.getParentFile().mkdirs();
      isCreatedFlg = file.createNewFile();
    } catch (Exception e) {
      System.out.println(
          "failed: creation of " + filePathString);
      e.printStackTrace();
    }

    // create text file
    PrintWriter out = null;
    try {
      out = new PrintWriter(
          new BufferedWriter(
              new FileWriter(filePathString)), true);
    } catch (Exception e) {
      System.out.println(
          "failed: text file creation" + filePathString);
      e.printStackTrace();
    }

    String testData = timeStamp() + "a\n";
    try {
      out.println(testData);
    } catch (Exception e) {
      System.out.println(
          "failed: text write " + filePathString);
      e.printStackTrace();
    }


//    out.close();

    return (out);
  }

  public static void main(String[] args) {
    String testfilePath = "fileList.txt";
    PrintWriter out = createFile(testfilePath);
    out.close();
  }
}
