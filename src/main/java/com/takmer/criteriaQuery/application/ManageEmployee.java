package com.takmer.criteriaQuery.application;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
//import org.hibernate.Query;

import com.takmer.criteriaQuery.hibernate.HibernateUtil;
import com.takmer.criteriaQuery.model.Employee;


public class ManageEmployee {
  private static Scanner in = new Scanner(System.in);

  public Long addEmployee() {
    /* Method to CREATE an employee in the database */

    System.out.println("Enter first name: ");
    String fname = in.nextLine();
    System.out.println("Enter last name: ");
    String lname = in.nextLine();
    System.out.println("Enter salary: ");
    int salary = in.nextInt();
    in.nextLine();

    Session session = HibernateUtil.getSession();

    Transaction tx = null;
    Long employeeID = null;
    try {
      tx = session.beginTransaction();
      Employee employee = new Employee(fname, lname, salary);

      employeeID = (Long) session.save(employee);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return employeeID;
  }

  public void updateEmployee() {
//  https://howtodoinjava.com/hibernate/complete-hibernate-query-language-hql-tutorial/#select_operation
    Session session = null;
    Transaction tx = null;

    try {
      session = HibernateUtil.getSession();
      tx = session.beginTransaction();

      Query query = session.createQuery(
          "update Employee set salary=:salary where firstName=:firstName");
      query.setString("firstName", "Zara");
      query.setInteger("salary", 32);
      int modifications = query.executeUpdate();

      System.out.println("xperiment");
      System.out.println(modifications);
      tx.commit();

    } catch (Exception ex) {
      if (tx != null)
        tx.rollback();
      ex.printStackTrace();
      // handle exception here
    } finally {
      session.close();
    }
    return;
  }

  public List<Employee> selectEmployees() {
//    https://www.baeldung.com/hibernate-criteria-queries

    Session session = null;
    List<Employee> employeeList = null;

    try {
      session = HibernateUtil.getSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Employee> cr
          = cb.createQuery(com.takmer.criteriaQuery.model.Employee.class);
      Root<Employee> root = cr.from(Employee.class);

      // employee.salary gt 2000
//      cr.select(root).where(cb.gt(root.get("salary"), 2000));

      // employee.salary lt 5000
//      cr.select(root).where(cb.lt(root.get("salary"), 5000));

      // employee.lastname contains se
//      cr.select(root).where(cb.like(root.get("lastName"), "%se%"));

      // employee.salary between 3000 and 5000
//      cr.select(root).where(cb.between(root.get("salary"), 3000, 5000));

      // cb.isNull, cb.isNotNull
      // Predicate - list of, and, or

      cr.orderBy(
          cb.asc(root.get("salary")),
          cb.desc(root.get("lastName"))
      );

      Query<Employee> query = session.createQuery(cr);
      employeeList = query.getResultList();

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
    return employeeList;
  }

  public List<Employee> listEmployee() {
//    https://www.baeldung.com/hibernate-criteria-queries

    Session session = null;
    List<Employee> employeeList = null;

    try {
      session = HibernateUtil.getSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Employee> cr
          = cb.createQuery(com.takmer.criteriaQuery.model.Employee.class);
      Root<Employee> root = cr.from(Employee.class);
      cr.select(root);

      Query<Employee> query = session.createQuery(cr);
      employeeList = query.getResultList();


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
    return employeeList;
  }

  public void newDeleteEmployee() {
    //    https://www.baeldung.com/hibernate-criteria-queries

    Session session = null;
    List<Employee> employeeList = null;

    try {
      session = HibernateUtil.getSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Employee> cr
          = cb.createQuery(com.takmer.criteriaQuery.model.Employee.class);
      Root<Employee> root = cr.from(Employee.class);

      // employee.salary gt 2000
      cr.select(root).where(cb.equal(root.get("empId"), 4));

      Query<Employee> query = session.createQuery(cr);
      employeeList = query.getResultList();
      int x = employeeList.size();

      if ( x > 0) {
        System.out.println("entries to be deleted");
        Transaction tx = session.beginTransaction();
        for (Employee killMe : employeeList){
          System.out.println(killMe.toString());
          session.delete(killMe);
        }
        tx.commit();
        }
      else {
        System.out.println("no entries to be deleted");
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

  public static void main(String[] args) {
    System.out.println("Hello World");

    ManageEmployee ME = new ManageEmployee();
    List<Employee> employeeList = null;

    employeeList = ME.listEmployee();
    System.out.println(
        "employee count: " + employeeList.size());
    employeeList.stream().forEach(System.out::println);

    ME.newDeleteEmployee();

    employeeList = ME.selectEmployees();
    System.out.println(
        "employee count: " + employeeList.size());
    employeeList.stream().forEach(System.out::println);
    System.out.println(
        "---------------------------");

    System.exit(0);

  }

}
