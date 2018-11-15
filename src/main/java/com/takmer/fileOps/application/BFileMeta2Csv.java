package com.takmer.fileOps.application;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.codec.binary.Base64;

import com.takmer.fileOps.model.Fd;

import java.util.zip.CRC32;

public class BFileMeta2Csv {
  String csvPathName;
  String dupFiles;
  String setName;

  public String getCsvPathName() {
    String filePathString = System.getProperty("user.home")
        + File.separator + "Documents"
        + File.separator + this.csvPathName;
    return filePathString;
  }

  public void setCsvPathName(String csvPathName) {
    this.csvPathName = csvPathName;
  }

  public String getDupFiles() {
    return dupFiles;
  }

  public void setDupFiles(String dupFiles) {
    this.dupFiles = dupFiles;
  }

  public String getSetName() {
    return setName;
  }

  public void setSetName(String setName) {
    this.setName = setName;
  }

  public String encode64(String unencodedData) {

    byte[] bytesEncoded
        = Base64.encodeBase64(unencodedData.getBytes());
//    String encodedString = new String(unencodedData);

    return (new String(bytesEncoded));
  }

  public String decode64(String encodedString) {

    byte[] byesDecoded
        = Base64.decodeBase64(encodedString);
//    String decodedString = new String(byesDecoded);

    return (new String(byesDecoded));
  }

  public byte[] createChecksum(String filename) throws Exception {
    InputStream fis = new FileInputStream(filename);

    byte[] buffer = new byte[1024];
    MessageDigest complete = MessageDigest.getInstance("MD5");
    int numRead;

    do {
      numRead = fis.read(buffer);
      if (numRead > 0) {
        complete.update(buffer, 0, numRead);
      }
    } while (numRead != -1);

    fis.close();
    return complete.digest();
  }

  public String getMD5Checksum(String filename) throws Exception {
    byte[] b = createChecksum(filename);
    String result = "";

    for (int i = 0; i < b.length; i++) {
      result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
    }
    return result;
  }

  public long getFileCRC(File file) throws IOException {
//https://www.programcreek.com/java-api-examples/java.util.zip.CRC32
    BufferedInputStream bsrc = null;
    CRC32 crc = new CRC32();
    try {
      bsrc = new BufferedInputStream(new FileInputStream(file));
      byte[] bytes = new byte[1024];
      int i;
      while ((i = bsrc.read(bytes)) != -1) {
        crc.update(bytes, 0, i);
      }
    } finally {
      if (bsrc != null) {
        bsrc.close();
      }
    }
    return crc.getValue();
  }

  private Fd getFileMeta(File file) {

    // collect file's data
    String fPath = file.getPath().toString();
    String fDirectory = file.getParent();
    String fName = FilenameUtils.getBaseName(fPath);
    String fExt = FilenameUtils.getExtension(fPath);
    long fLen = file.length();
    long fLastModified = file.lastModified();
    int fHashCode = file.hashCode();

    String fEDirectory = encode64(fDirectory);
    String fEName = encode64(fName);

    // md5 - slow & trusted
    String fMd5 = "00";
//    try {
//      fMd5 = fileMD5Checksum(fPath);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }

    // crc
    long fCrc = 0;
    try {
      fCrc = getFileCRC(file);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Fd fileMeta = new Fd(fEName, fExt, fEDirectory, fLen,
        fLastModified, fHashCode,
        fMd5, fCrc,
        this.getSetName(), 1);
//    fdc.addFd(fd);

//    System.out.println("p " + fPath);
//    System.out.println("d " + fDirectory);
//    System.out.println("n " + fName);
//    System.out.println("e " + fExt);
//    System.out.println("l " + fLen);
//    System.out.println("m " + fLastModified);
//    System.out.println("h " + fHashCode);

//    String fileNameWithOutExt = FilenameUtils.removeExtension(fileNameWithExt);
    return (fileMeta);
  }

  public BufferedReader openFileList(String fileName) {
    ;
    // base the path from user.home/documents
    String filePathString = System.getProperty("user.home")
        + File.separator + "Documents"
//  + File.separator + "fileDups"
        + File.separator + fileName;

    BufferedReader in = null;
    try {
      in = new BufferedReader(
          new FileReader(filePathString));
    } catch (Exception e) {
      System.out.println("file list read failure: " + fileName);
      e.printStackTrace();
    }
    return (in);
  }

  public void writeMetaCsv() throws IOException {
    try (
        Writer writer = Files.newBufferedWriter(
            Paths.get(getCsvPathName()));

        CSVWriter csvWriter = new CSVWriter(writer,
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END);
    ) {

      String[] headerRecord = {"fdId", "fdName", "fdExt", "fdParent",
          "fdLen", "fdDate", "fdHash",
          "fdMd5", "fdCrc",
          "fdSet", "fdState"};
      csvWriter.writeNext(headerRecord);

      BufferedReader in = openFileList(getDupFiles());
      // read filenames from dupsData
      int count = 0;
      try {
        String line = in.readLine();
        while (line != null) {
          System.out.println(line);
          count++;
          File f = new File(line);
          Fd fd = getFileMeta(f);

          // create csv entry here
          String[] metaRecord = {
              Long.toString(-01L),
              fd.getFdName(),
              fd.getFdExt(),
              fd.getFdParent(),
              Long.toString(fd.getFdLen()),
              Long.toString(fd.getFdDate()),
              String.valueOf(fd.getFdHash()),
              fd.getFdMd5(),
              Long.toString(fd.getFdCrc()),
              fd.getFdSet(),
              String.valueOf(fd.getFdState())
          };
          csvWriter.writeNext(metaRecord);

          line = in.readLine();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  public static void main(String[] args) throws IOException,
      CsvDataTypeMismatchException,
      CsvRequiredFieldEmptyException {
    BFileMeta2Csv FCW = new BFileMeta2Csv();
    FCW.setDupFiles("zvideos.txt");
    FCW.setCsvPathName("zvideos.csv");
    FCW.setSetName("zvideos");

    FCW.writeMetaCsv();

    System.exit(0);
  }

}

