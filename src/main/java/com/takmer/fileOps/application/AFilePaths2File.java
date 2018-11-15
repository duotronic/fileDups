package com.takmer.fileOps.application;

import java.io.*;
import java.time.LocalDateTime;
import com.takmer.fileOps.application.Utilities;

public class AFilePaths2File {
  String checkPath;
  String dupFiles;

  public String getCheckPath() {
    return checkPath;
  }

  public void setCheckPath(String checkPath) {
    this.checkPath = checkPath;
  }

  public String getDupFiles() {
    return dupFiles;
  }

  public void setDupFiles(String dupFiles) {
    this.dupFiles = dupFiles;
  }

  private void fileName2TextFile(PrintWriter printWriter, File dataFile) {
    String fPath = dataFile.getPath().toString();
    try {
      printWriter.println(fPath);
    } catch (Exception e) {
      System.out.println(
          "failed: text write " + printWriter.toString());
      e.printStackTrace();
    }
  }

  private void filesTree(PrintWriter printWriter, String path) {
//    FdDbCrud FDC = new FdDbCrud();

    File f = new File(path);
    if (!f.exists()) {
      System.out.println(path + " does not exist");
      return;
    }
    if (f.isFile()) {
      fileName2TextFile(printWriter, f);
//      getFileMeta(FDC, f);
    } else if (f.isDirectory()) {
//      System.out.println("d " + f.getPath());

      // display each file in the directory
      String objects[] = f.list();
      for (String o : objects) {
        // recursion
        filesTree(printWriter, path + File.separator + o);
      }
    } else
      System.err.println("Unknown type: " + path);
  }

  public PrintWriter createTextFile(String fileName) {
    // thanks to javacodeexamples.com

    // set the path
    String filePathString = System.getProperty("user.home")
        + File.separator + "Documents"
//  + File.separator + "fileDups"
        + File.separator + fileName;

    // nuke an existing file
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

    // create path/text file
    PrintWriter printWriter = null;
    try {
      printWriter = new PrintWriter(
          new BufferedWriter(
              new FileWriter(filePathString)), true);
    } catch (Exception e) {
      System.out.println(
          "failed: text file creation" + filePathString);
      e.printStackTrace();
    }
    return (printWriter);
  }

  public void FilePaths2File() {
    PrintWriter printWriter = createTextFile(getDupFiles());
    filesTree(printWriter, getCheckPath());
  }

  public static void main(String[] args) {
    AFilePaths2File P2F = new AFilePaths2File();
//    P2F.setCheckPath("C:\\Users\\Owner\\IdeaProjects");
//    P2F.setCheckPath("C:\\Users\\Owner\\Documents");
    P2F.setCheckPath("D:\\wdb10\\zvideos");
    P2F.setDupFiles("zvideos.txt");
    P2F.FilePaths2File();

    System.exit(0);
  }

}
