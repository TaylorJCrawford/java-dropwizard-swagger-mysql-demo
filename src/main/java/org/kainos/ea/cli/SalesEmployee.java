package org.kainos.ea.cli;

public class SalesEmployee extends Employee {

    private double monthlySales;
    private float commissionRate;

    private int employeeId;
    private String name;
    private double salary;

    public SalesEmployee(double monthlySales, float commissionRate, int employeeId, String name, double salary) {
        this.monthlySales = monthlySales;
        this.commissionRate = commissionRate;
        this.employeeId = employeeId;
        this.name = name;
        this.salary = salary;
    }

    public double getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(double monthlySales) {
        this.monthlySales = monthlySales;
    }

    public float getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(float commissionRate) {
        this.commissionRate = commissionRate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double calcPay() {
        return this.getSalary()/12;
    }


    public double calcSalesEmployeePay () {
        return getSalary()/12 + (monthlySales*commissionRate);
    }
}
