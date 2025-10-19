package com.napier.sem;

public class Employee {
    public int empNo;
    public String firstName;
    public String lastName;

    @Override public String toString() {
        return empNo + " - " + firstName + " " + lastName;
    }
}
