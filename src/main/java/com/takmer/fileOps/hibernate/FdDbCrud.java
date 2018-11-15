package com.takmer.fileOps.hibernate;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
//import org.hibernate.Query; old

import com.takmer.fileOps.model.Fd;

public class FdDbCrud {

  private void testData() {

    String[][] arrayStringLists = {
        {"name11", "ext12", "parent13", "14", "15", "md1", "10", "setTest", "17"},
        {"name21", "ext22", "parent23", "24", "25", "md2", "20", "setTest", "27"},
        {"name31", "ext32", "parent33", "34", "35", "md3", "30", "x", "37"},
        {"name41", "ext42", "parent43", "44", "45", "md4", "40", "setTest", "47"},
        {"name51", "ext52", "parent53", "54", "55", "md5", "50", "setTest", "57"}};
    for (int r = 0; r < arrayStringLists.length; r++) {
      String[] arrayRow = arrayStringLists[r];
      Fd fdData = new Fd();
      fdData.setFdName(arrayRow[0]);
      fdData.setFdExt(arrayRow[1]);
      fdData.setFdParent(arrayRow[2]);
      fdData.setFdLen(Integer.parseInt(arrayRow[3]));
      fdData.setFdHash(Integer.parseInt(arrayRow[4]));
      fdData.setFdMd5(arrayRow[5]);
      fdData.setFdCrc(Long.parseLong(arrayRow[6]));

      fdData.setFdSet(arrayRow[7]);
//      fdData.setFdSet(LocalDateTime.now().toString());

      fdData.setFdState(Integer.parseInt(arrayRow[8]));
      long newId = addFd(fdData);
    }
  }

  public Long addFd(Fd newFd) {
    /* Method to CREATE a fd in the database */

    Session session = HibernateUtil.getSession();
    Transaction tx = null;
    Long fdID = 0l;
    LocalDateTime currentDateTime = LocalDateTime.now();

    try {
      tx = session.beginTransaction();

      fdID = (Long) session.save(newFd);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) {
        tx.rollback();
        e.printStackTrace();
      }
    } finally {
      session.close();
    }
    return fdID;
  }

  public List<Fd> listAllFds() {
//    https://www.baeldung.com/hibernate-criteria-queries

    Session session = null;
    List<Fd> fdList = null;

    try {
      session = HibernateUtil.getSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Fd> cr
          = cb.createQuery(com.takmer.fileOps.model.Fd.class);
      Root<Fd> root = cr.from(Fd.class);
      cr.select(root);

      cr.orderBy(
//          cb.asc(root.get("salary")),
          cb.desc(root.get("fdCrc"))
      );

      Query<Fd> query = session.createQuery(cr);
      fdList = query.getResultList();

    } catch (Exception ex) {
      // handle exception here
      ex.printStackTrace();
    } finally {
      try {
        if (session != null)
          session.close();
      } catch (Exception ex) {
        // handle exception here
      }
    }
    return fdList;
  }

  public List<Fd> testSetDb(String testSet) {
//    https://www.baeldung.com/hibernate-criteria-queries

    Session session = null;
    List<Fd> fdList = null;

    try {
      session = HibernateUtil.getSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Fd> cr
          = cb.createQuery(com.takmer.fileOps.model.Fd.class);
      Root<Fd> root = cr.from(Fd.class);

      cr.select(root).where(cb.equal(root.get("fdSet"), testSet));

      // fd.salary lt 5000
//      cr.select(root).where(cb.lt(root.get("salary"), 5000));

      // fd.lastname contains se
//      cr.select(root).where(cb.like(root.get("lastName"), "%se%"));

      // fd.salary between 3000 and 5000
//      cr.select(root).where(cb.between(root.get("salary"), 3000, 5000));

      // cb.isNull, cb.isNotNull
      // Predicate - list of, and, or

      cr.orderBy(
//          cb.asc(root.get("salary")),
          cb.desc(root.get("fdState"))
      );

      Query<Fd> query = session.createQuery(cr);
      fdList = query.getResultList();

    } catch (Exception ex) {
      // handle exception here
      ex.printStackTrace();
    } finally {
      try {
        if (session != null)
          session.close();
      } catch (Exception ex) {
        // handle exception here
      }
    }
    return fdList;
  }

  public List<Fd> selectFds() {
//    https://www.baeldung.com/hibernate-criteria-queries

    Session session = null;
    List<Fd> fdList = null;

    try {
      session = HibernateUtil.getSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Fd> cr
          = cb.createQuery(com.takmer.fileOps.model.Fd.class);
      Root<Fd> root = cr.from(Fd.class);

      cr.select(root)
          .where(cb.gt(root.get("fdLen"), 34));

      // fd.salary lt 5000
//      cr.select(root).where(cb.lt(root.get("salary"), 5000));

      // fd.lastname contains se
//      cr.select(root).where(cb.like(root.get("lastName"), "%se%"));

      // fd.salary between 3000 and 5000
//      cr.select(root).where(cb.between(root.get("salary"), 3000, 5000));

      // cb.isNull, cb.isNotNull
      // Predicate - list of, and, or

      cr.orderBy(
//          cb.asc(root.get("salary")),
          cb.desc(root.get("fdState"))
      );

      Query<Fd> query = session.createQuery(cr);
      fdList = query.getResultList();

    } catch (Exception ex) {
      // handle exception here
      ex.printStackTrace();
    } finally {
      try {
        if (session != null)
          session.close();
      } catch (Exception ex) {
        // handle exception here
      }
    }
    return fdList;
  }

  public void deleteFd(Fd fd) {
    //    https://www.baeldung.com/hibernate-criteria-queries

    Session session = null;
    List<Fd> fdList = null;

    try {
      session = HibernateUtil.getSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Fd> cr
          = cb.createQuery(com.takmer.fileOps.model.Fd.class);
      Root<Fd> root = cr.from(Fd.class);

      cr.select(root).where(cb.equal(root.get("fdId"), fd.getFdId()));

      Query<Fd> query = session.createQuery(cr);
      fdList = query.getResultList();

      int x = fdList.size();
      if (x > 0) {
        System.out.println(
            "entries to be deleted");
        Transaction tx = session.beginTransaction();
        for (Fd killMe : fdList) {
          System.out.println(killMe.toString());
          session.delete(killMe);
        }
        tx.commit();
      } else {
        System.out.println(
            "no entries to be deleted");
      }

    } catch (Exception ex) {
      // handle exception here
      ex.printStackTrace();
    } finally {
      try {
        if (session != null)
          session.close();
      } catch (Exception ex) {
        // handle exception here
      }
    }

    return;
  }

  public void deleteFdList(List<Fd> fdList) {
    //    https://www.baeldung.com/hibernate-criteria-queries

    for (Fd fd : fdList) {
      deleteFd(fd);
    }
    return;
  }

  public void deleteSet(String setName) {
    List<Fd> fdList = testSetDb(setName);
    deleteFdList(fdList);
  }

  public static void main(String[] args) {
    // will-not create database, will create tables
    // create database criteriaquery;

    List<Fd> fdList = null;

    System.out.println("FdDbCrud starts");
    FdDbCrud FC = new FdDbCrud();

    // create test set
    FC.testData();

    // doesn't seem to do anything
//    FC.listAllFds();

    // display all
    fdList = FC.listAllFds();
    System.out.println(
        "fd count: " + fdList.size());
    fdList.stream().forEach(System.out::println);
    System.out.println(
        "----------");


    // nuke one set
    FC.deleteSet("x");
    System.out.println(
        "fd count: " + fdList.size());
    fdList.stream().forEach(System.out::println);

    // done something?
    System.out.println("done");
    System.exit(0);
  }
}
