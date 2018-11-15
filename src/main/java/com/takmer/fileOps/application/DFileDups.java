package com.takmer.fileOps.application;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.takmer.fileOps.hibernate.FdDbCrud;
import com.takmer.fileOps.model.Fd;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DFileDups {
  String delSetName;
  String delBatFile;

  public String getDelSetName() {
    return delSetName;
  }

  public void setDelSetName(String delName) {
    this.delSetName = delName;
  }

  public String getDelBatFile() {
    return delBatFile;
  }

  public void setDelBatFile(String delBatFile) {
    this.delBatFile = delBatFile;
  }

  public PrintWriter createTextFile(String fileName) {
    // thanks to javacodeexamples.com

    // set the path
    String filePathString = System.getProperty("user.home")
        + File.separator + "Documents"
//  + File.separator + "fileDups"
        + File.separator + getDelBatFile();

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

  private void dupFileName2delBatFile(PrintWriter printWriter, String fPath) {
    try {
      printWriter.println(fPath);
    } catch (Exception e) {
      System.out.println(
          "failed: text write " + printWriter.toString());
      e.printStackTrace();
    }
  }

  public void MultiSetDupsReporter() throws IOException {
    FdDbCrud FDC = new FdDbCrud();
    PrintWriter delBatFile = createTextFile(getDelBatFile());
    String dQ = "\"";

    List<Fd> fdList = FDC.listAllFds();
    System.out.println(
        "fd count: " + fdList.size());

    Fd previousFd = new Fd();
    List<Fd> fdDupList = new ArrayList<Fd>();
    for (Fd f : fdList) {
      if (previousFd.getFdCrc() == f.getFdCrc()) {
        fdDupList.add(previousFd);
        fdDupList.add(f);
      }
      previousFd = f;
    }

    System.out.println(
        "fdDupList count: " + fdDupList.size());

    // sort by fDuplist by set & path
    fdDupList.sort(Comparator.comparing(Fd::getFdSet)
        .thenComparing(Comparator.comparing(Fd::getFdParent)));
    for (Fd f : fdDupList) {
      String set = f.getFdSet().toString();
      String parent = f.getFdParent();
      String name = f.getFdName();
      String ext = f.getFdExt();
      String fullPath =
          dQ + parent + "\\" + name + dQ + "." + ext;

      if (set.equals(getDelSetName())) {
        String batFile = "del " + fullPath;
        dupFileName2delBatFile(delBatFile, batFile);
//          System.out.println(batFile);
      }

      String display = "Set: " + set +
          ", Path: " + fullPath;
      System.out.println(display);
    }
    delBatFile.close();
    return;
  }

  public void SingleSetDupsReporter() throws IOException {
    FdDbCrud FDC = new FdDbCrud();
    PrintWriter delBatFile = createTextFile(getDelBatFile());
    String dQ = "\"";

    List<Fd> fdList = FDC.listAllFds();
    System.out.println(
        "fd count: " + fdList.size());

    Fd previousFd = new Fd();
    List<Fd> fdDupList = new ArrayList<Fd>();
    for (Fd f : fdList) {
      if (previousFd.getFdCrc() == f.getFdCrc()) {
//
        fdDupList.add(previousFd);
        String fullPath =
            dQ + previousFd.getFdParent()
                + "\\" + previousFd.getFdName() + dQ
                + "." + previousFd.getFdExt();
        String dupFilePath = "::del " + fullPath;
        dupFileName2delBatFile(delBatFile, dupFilePath);
        //
        fdDupList.add(f);
        fullPath =
            dQ + f.getFdParent()
                + "\\" + f.getFdName() + dQ
                + "." + f.getFdExt();
        dupFilePath = "::del " + fullPath;
        dupFileName2delBatFile(delBatFile, dupFilePath);
        dupFileName2delBatFile(delBatFile, "::");
      }
      previousFd = f;
    }

    System.out.println(
        "fdDupList count: " + fdDupList.size());
    delBatFile.close();
    return;
  }

  public static void main(String[] args) throws IOException,
      CsvDataTypeMismatchException,
      CsvRequiredFieldEmptyException {

    DFileDups FCW = new DFileDups();
    FCW.setDelSetName("uvideos");
    FCW.setDelBatFile("uvideosDEL.bat");

    FCW.SingleSetDupsReporter();
//    FCW.MultiSetDupsReporter();

    System.exit(0);
  }
}



