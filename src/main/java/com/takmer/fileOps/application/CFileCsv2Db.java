package com.takmer.fileOps.application;

import java.io.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Iterator;

import org.apache.commons.codec.binary.Base64;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import com.takmer.fileOps.model.Fd;
import com.takmer.fileOps.hibernate.FdDbCrud;

public class CFileCsv2Db {
  String csvPathName;
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

  public void MetaCsv2Db() throws IOException {
    FdDbCrud FDC = new FdDbCrud();

    // nuke existing db records of the same set
    FDC.deleteSet(getSetName());

    try (
        Reader reader = Files.newBufferedReader(
            Paths.get(getCsvPathName()));
    ) {
      // crc reader
      CsvToBean<Fd> csvToBean
          = new CsvToBeanBuilder(reader)
          .withType(Fd.class)
          .withIgnoreLeadingWhiteSpace(true)
          .build();

      // for every csv entry -> filter -> db
      Iterator<Fd> fdMetaDataIterator = csvToBean.iterator();
      int count = 0;
      try {
        while (fdMetaDataIterator.hasNext()) {
          System.out.println(count);
          Fd fdCsvMetaData = fdMetaDataIterator.next();
          Fd fdDbMetaData = fdCsvMetaData;
          fdDbMetaData.setFdName(decode64(fdCsvMetaData.getFdName()));
          fdDbMetaData.setFdParent(decode64(fdCsvMetaData.getFdParent()));
          System.out.println(
              "FdName : " + fdDbMetaData.getFdName());
          System.out.println(
              "FdExt : " + fdDbMetaData.getFdExt());
          System.out.println(
              "FdParent : " + fdDbMetaData.getFdParent());
          System.out.println(
              "FdCrc : " + fdDbMetaData.getFdCrc());
          FDC.addFd(fdDbMetaData);
          count++;
          System.out.println(
              "==========================");

        }
      } catch (Exception e) {
        System.out.println("Error: " + count);
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws IOException,
      CsvDataTypeMismatchException,
      CsvRequiredFieldEmptyException {
    CFileCsv2Db FCW = new CFileCsv2Db();
    FCW.setSetName("uvideos");
    FCW.setCsvPathName("uvideos.csv");

    FCW.MetaCsv2Db();


    System.exit(0);
  }

}


