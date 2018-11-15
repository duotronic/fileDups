package com.takmer.criteriaQuery.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEES")
public class Employee implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "EMP_ID")
  private Long empId;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  public String lastName;

  @Column(name = "SALARY")
  private int salary;

  public Employee() {
  }

  public Employee(String firstName, String lastName, int salary) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
  }

  @Override
  public String toString() {
    return "Employee{" +
        "empId=" + empId +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", salary=" + salary +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Employee employee = (Employee) o;

    if (getSalary() != employee.getSalary()) return false;
    if (!getEmpId().equals(employee.getEmpId())) return false;
    if (!getFirstName().equals(employee.getFirstName())) return false;
    return getLastName().equals(employee.getLastName());
  }

  @Override
  public int hashCode() {
    int result = getEmpId().hashCode();
    result = 31 * result + getFirstName().hashCode();
    result = 31 * result + getLastName().hashCode();
    result = 31 * result + getSalary();
    return result;
  }

  public Long getEmpId() {
    return empId;
  }

  public void setEmpId(Long empId) {
    this.empId = empId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }
}
