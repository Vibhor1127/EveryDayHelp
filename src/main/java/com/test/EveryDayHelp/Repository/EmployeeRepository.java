package com.test.EveryDayHelp.Repository;

import com.test.EveryDayHelp.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    void deleteById(String employee_id);


   // Employee findByEmployee_id(String employee_id);
}
