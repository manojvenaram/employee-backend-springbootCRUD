package com.saveetha.employee_backend.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/employee")

public class EmployeeController {
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public List<Employee> getEmployee(){
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/{id}")
    public Employee getEmployeeById(@PathVariable("id") Long employeeID){
        return employeeService.getEmployeeDataById(employeeID);
    }

    @PostMapping("/post")
    public void postEmployee(@RequestBody Employee employeeToBeAdded){
        employeeService.addNewEmployee(employeeToBeAdded);
    }

    @PutMapping(path = "/put/{id}")
    public void putEmployee(@PathVariable("id") Long employeeId,
                            @RequestBody Employee employeeToBeUpdated){

        employeeService.updateExistingEmployee(employeeId, employeeToBeUpdated);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteEmployee(@PathVariable("id") Long employeeId){
        employeeService.deleteExistingEmployee(employeeId);
    }
}
