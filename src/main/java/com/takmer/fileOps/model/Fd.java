package com.takmer.fileOps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "FDS")
public class Fd implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "FD_ID")
  private Long fdId;

  @Column(name = "FD_NAME")
  private String fdName;

  @Column(name = "FD_EXT")
  private String fdExt;

  @Column(name = "FD_PARENT")
  private String fdParent;

  @Column(name = "FD_LEN")
  private long fdLen;

  @Column(name = "FD_DATE")
  private long fdDate;

  @Column(name = "FD_HASH")
  private int fdHash;

    @Column(name = "FD_MD5")
  private String fdMd5;

  @Column(name = "FD_CRC")
  private long fdCrc;

  @Column(name = "FD_SET")
  private String fdSet;

  @Column(name = "FD_STATE")
  private int fdState;

  // constructor
  public Fd() {

  }

  // constructor 10
  public Fd(String fdName, String fdExt, String fdParent,
            long fdLen, long fdDate, int fdHash,
            String fdMd5, long fdCrc,
            String fdSet, int fdState) {
    this.fdName = fdName;
    this.fdExt = fdExt;
    this.fdParent = fdParent;
    this.fdLen = fdLen;
    this.fdDate = fdDate;
    this.fdHash = fdHash;
    this.fdMd5 = fdMd5;
    this.fdCrc = fdCrc;
    this.fdSet = fdSet;
    this.fdState = fdState;
  }



  @Override
  public String toString() {
    return "Fd{" +
        "fdId=" + fdId +
        ", fdName='" + fdName + '\'' +
        ", fdExt='" + fdExt + '\'' +
        ", fdParent='" + fdParent + '\'' +
        ", fdLen=" + fdLen +
        ", fdDate=" + fdDate +
        ", fdHash=" + fdHash +
        ", fdMd5='" + fdMd5 + '\'' +
        ", fdCrc=" + fdCrc +
        ", fdSet='" + fdSet + '\'' +
        ", fdState=" + fdState +
        '}';
  }

  public Long getFdId() {
    return fdId;
  }

  public void setFdId(Long fdId) {
    this.fdId = fdId;
  }

  public String getFdName() {
    return fdName;
  }

  public void setFdName(String fdName) {
    this.fdName = fdName;
  }

  public String getFdExt() {
    return fdExt;
  }

  public void setFdExt(String fdExt) {
    this.fdExt = fdExt;
  }

  public String getFdParent() {
    return fdParent;
  }

  public void setFdParent(String fdParent) {
    this.fdParent = fdParent;
  }

  public long getFdLen() {
    return fdLen;
  }

  public void setFdLen(long fdLen) {
    this.fdLen = fdLen;
  }

  public long getFdDate() {
    return fdDate;
  }

  public void setFdDate(long fdDate) {
    this.fdDate = fdDate;
  }

  public int getFdHash() {
    return fdHash;
  }

  public void setFdHash(int fdHash) {
    this.fdHash = fdHash;
  }

  public String getFdMd5() {
    return fdMd5;
  }

  public void setFdMd5(String fdMd5) {
    this.fdMd5 = fdMd5;
  }

  public long getFdCrc() {
    return fdCrc;
  }

  public void setFdCrc(long fdCrc) {
    this.fdCrc = fdCrc;
  }

  public String getFdSet() {
    return fdSet;
  }

  public void setFdSet(String fdSet) {
    this.fdSet = fdSet;
  }

  public int getFdState() {
    return fdState;
  }

  public void setFdState(int fdState) {
    this.fdState = fdState;
  }
}