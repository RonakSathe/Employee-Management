package com.ronak.Employee.services;


import com.ronak.Employee.entity.EmployeeEntity;
import com.ronak.Employee.model.Employee;
import com.ronak.Employee.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{


    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        //Copy All the values from employee to employeeEntity
        BeanUtils.copyProperties(employee,employeeEntity);
        //Save the data into the table that's why we have used employee entity
        employeeRepository.save(employeeEntity);
        return employee;

    }

    @Override
    public List<Employee> getEmployees() {
        //From the database, where the employee data is stored in the format of employeeentitiy, so we are fetching it out.
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        //we want to display on the UI page in the format of Employee, not EmployeeEntitiy, so we are getting from Lis of EE(Employee Entity) and putting it into the List ofEmployee.
        List<Employee> employees = employeeEntities
                //stream is used to perform particular function on each elements present in the list.
                .stream()
                //map is the denotion to perform the function on the elements
                .map(employeeEntity -> new Employee(
                        employeeEntity.getId(),
                        employeeEntity.getFirstName(),
                        employeeEntity.getLastName(),
                        employeeEntity.getEmailId()))
                .toList();
        return employees;
    }

    @Override
    public boolean deleteEmployee(Long id) {

        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        employeeRepository.delete(employeeEntity);

        return true;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        EmployeeEntity employeeEntity= employeeRepository.findById(id).get();

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeEntity,employee);
        return employee;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
        employeeEntity.setEmailId(employee.getEmailId());
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());

        employeeRepository.save(employeeEntity);
        return employee;
    }
}
