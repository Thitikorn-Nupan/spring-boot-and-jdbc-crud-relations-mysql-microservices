package com.ttknp.restapicontroller.entities;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Employee {
    private String eid;
    private String firstname;
    private String lastname;
    private String position;
    private boolean active; // Using boolean for tinyint(1)
    private BigDecimal salary; // Using BigDecimal for decimal(9,2)
    private List<Address> addresses; // *** for join table

    // in jdbc you have to create default constructor
    public Employee() {
    }

    public Employee(String eid, String firstname, String lastname, String position, boolean active, BigDecimal salary) {
        this.eid = eid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.position = position;
        this.active = active;
        this.salary = salary;
    }


    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    /**
        Here's how to remove duplicate POJOs from a Java List Override equals() and hashCode() in your POJO
        If you don't override these methods, objects will be compared by reference, not by their content.
    **/
    // The equals() method defines how two objects of your POJO class are considered equal.
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return active == employee.active && Objects.equals(eid, employee.eid) && Objects.equals(firstname, employee.firstname) && Objects.equals(lastname, employee.lastname) && Objects.equals(position, employee.position) && Objects.equals(salary, employee.salary) && Objects.equals(addresses, employee.addresses);
    }

    // The hashCode() method should return the same hash code for equal objects.
    @Override
    public int hashCode() {
        return Objects.hash(eid, firstname, lastname, position, active, salary, addresses);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eid='" + eid + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", position='" + position + '\'' +
                ", active=" + active +
                ", salary=" + salary +
                ", addresses=" + addresses +
                '}';
    }
}
