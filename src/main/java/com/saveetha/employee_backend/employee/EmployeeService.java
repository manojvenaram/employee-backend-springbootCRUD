package com.saveetha.employee_backend.employee;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeDataById(Long employeeID) {
        Employee employeeWithExpectedId = employeeRepository.findById(employeeID)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatusCode.valueOf(405),"No Employee Exist with employee id "+ employeeID)

                );
        return employeeWithExpectedId;
    }

    public void addNewEmployee(Employee employeeToBeAdded){
        Optional<Employee> existingEmployee = employeeRepository.findEmployeeByEmail(employeeToBeAdded.getEmployeeEmail());
        if (existingEmployee.isPresent()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(419),"Email id already exists");
        }
        employeeRepository.save(employeeToBeAdded);
    }

    @Transactional
    public void updateExistingEmployee(Long employeeId, Employee employeeToBeUpdated) {
        Employee matchingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatusCode.valueOf(405), "No employee exist with employee id" + employeeId)
                );
        String updatedEmployeeName = employeeToBeUpdated.getEmployeeName();
        String updatedEmployeeEmail = employeeToBeUpdated.getEmployeeEmail();
        LocalDate updatedEmployeeDOB = employeeToBeUpdated.getEmployeeDOB();

        if (updatedEmployeeName != null && updatedEmployeeName.length() > 0 && !Objects.equals(matchingEmployee.getEmployeeName(), employeeToBeUpdated.getEmployeeName())) {
            matchingEmployee.setEmployeeName(updatedEmployeeName);
        }
        if (updatedEmployeeEmail != null && updatedEmployeeEmail.length() > 0 && !Objects.equals(matchingEmployee.getEmployeeEmail(), employeeToBeUpdated.getEmployeeEmail())) {
            Optional<Employee> duplicateEmployeeEmail = employeeRepository.findEmployeeByEmail(updatedEmployeeEmail);

            if (duplicateEmployeeEmail.isPresent()) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(419), "Email Id is already in use");
            }
            matchingEmployee.setEmployeeEmail(updatedEmployeeEmail);
        }

        if (!Objects.equals(matchingEmployee.getEmployeeDOB(), updatedEmployeeDOB)) {
            matchingEmployee.setEmployeeDOB(updatedEmployeeDOB);
        }
    }
    public void deleteExistingEmployee(Long employeeId){
        boolean employeeExists = employeeRepository.existsById(employeeId);

        if(!employeeExists){
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(405), "No employee exist with  employee id "+ employeeId);

        }
        employeeRepository.deleteById(employeeId);
    }
}
