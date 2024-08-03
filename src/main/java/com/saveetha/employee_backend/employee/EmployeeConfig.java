package com.saveetha.employee_backend.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class EmployeeConfig {
    @Bean
    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository){
        return args -> {
            Employee jai = new Employee(
                    "Manoj Choudhary V",
                    LocalDate.of(2003, Month.DECEMBER,21),
                    "manojvibranium@example.com"
            );

            Employee sai = new Employee(
                    "Ranjith D",
                    LocalDate.of(2004,Month.JANUARY,1),
                    "tt@example.com"
            );
            employeeRepository.saveAll(List.of(jai,sai));
        };
    }
}
