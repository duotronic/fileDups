package com.takmer.fileOps.application;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class xFileInfo {

  private static void findFilePaths(String s) {
    File f = new File(s);
    if (!f.exists()) {
      System.out.println(s + " does not exist");
      return;
    }
    if (f.isFile()) {
      processPaths(f);
    }
    else if (f.isDirectory()) {
//      System.out.println("d " + f.getPath());

      // display each file in the directory
      String objects[] = f.list();
      for (String o : objects) {
        findFilePaths(s + File.separator + o);
      }
    } else
      System.err.println("Unknown type: " + s);
  }

  /** processPaths - process one regular file. */
  private static void processPaths(File f) {
    String fPath = f.getPath();
    String fDirectory = f.getParent();
    String fName = FilenameUtils.getBaseName(fPath);
    String fExt = FilenameUtils.getExtension(fPath);
    long fLen = f.length();
    long fLastModified = f.lastModified();
    int fHashCode = f.hashCode();
    System.out.println("p " + fPath);
    System.out.println("d " + fDirectory);
    System.out.println("n " + fName);
    System.out.println("e " + fExt);
    System.out.println("l " + fLen);
    System.out.println("m " + fLastModified);
    System.out.println("h " + fHashCode);
//    String fileNameWithOutExt = FilenameUtils.removeExtension(fileNameWithExt);
  }

  /**
   * Main program
   */
  public static void main(String[] args) {
    String testPath = "C:\\mektemp";
    findFilePaths(testPath);
  }
}
// END main
