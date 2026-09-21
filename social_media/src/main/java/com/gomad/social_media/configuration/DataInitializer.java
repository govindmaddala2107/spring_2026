package com.gomad.social_media.configuration;

import com.gomad.social_media.models.Employee;
import com.gomad.social_media.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final EmployeeRepository employeeRepository;

    public DataInitializer(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Bean
    public CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
        return args -> {
            Employee employee1 = new Employee();
            employee1.setFirstName("Amar");
            employee1.setLastName("Deepak");
            employeeRepository.save(employee1);
        };
    }
}
