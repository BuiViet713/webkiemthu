package com.example.btl_dbclpm.repository;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AreaRepositoryTest {
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveArea_StandardCase_SaveArea() {
        Employee employee = new Employee();
        employee.setFullName("Quốc Việt");
        employee.setAuthorization("employee");
        employee.setEmail("quocviet@gmail.com");
        employee.setPassword("Viet@12345");
        employee.setPhoneNumber("0869062889");
        employee.setUsername("viet123");
        employee.setEmployeeCode("NV002");
        employee.setPosition("manager");

        Employee savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee);
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindByEmployee_StandardCase1_ReturnListArea() {
        Employee employee = new Employee();
        employee.setFullName("Quốc Việt");
        employee.setAuthorization("employee");
        employee.setEmail("quocviet@gmail.com");
        employee.setPassword("Viet@12345");
        employee.setPhoneNumber("0869062889");
        employee.setUsername("viet123");
        employee.setEmployeeCode("NV002");
        employee.setPosition("manager");
        employeeRepository.save(employee);

        Area area1 = Area.builder()
                .id(1L)
                .city("Quảng Ninh")
                .district("Hạ Long")
                .wardCommune("Bãi Cháy")
                .employee(employee)
                .build();
        areaRepository.save(area1);

        Area area2 = Area.builder()
                .id(2L)
                .city("Quảng Ninh")
                .district("Hạ Long")
                .wardCommune("Cao Xanh")
                .employee(employee)
                .build();
        areaRepository.save(area2);

        List<Area> actualAreas = areaRepository.findByEmployee(employee);

        assertNotNull(actualAreas);
        assertEquals(2, actualAreas.size());
    }
}
