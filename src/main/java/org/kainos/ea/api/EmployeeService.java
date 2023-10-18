package org.kainos.ea.api;

import org.kainos.ea.cli.Contractor;
import org.kainos.ea.cli.Employee;
import org.kainos.ea.cli.IPayable;
import org.kainos.ea.cli.SalesEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public List<IPayable> getEmployee() {
        Employee employee = new Employee(1, "Taylor", 20000);
        SalesEmployee salesEmployee = new SalesEmployee(1000, 0.01f,
                1, "Taylor", 20000);
        Contractor contractor = new Contractor("Taylor", 1000, 10);

        List<IPayable> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(salesEmployee);
        employees.add(contractor);

        for (IPayable e : employees) {
            System.out.println(e.calcPay());
        }

        return employees;


    }
}
