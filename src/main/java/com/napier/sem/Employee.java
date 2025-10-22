package com.napier.sem;

public class Employee {
    public int empNo;
    public String firstName;
    public String lastName;

    // extra info we’ll join in
    public String title;        // current title
    public int    salary;       // current salary
    public String dept_name;    // current department name
    public String manager;      // current department manager full name
    public String last_name;
    public String first_name;
    public int emp_no;

    @Override
    public String toString() {
        return empNo + " - " + firstName + " " + lastName;
    }
}
