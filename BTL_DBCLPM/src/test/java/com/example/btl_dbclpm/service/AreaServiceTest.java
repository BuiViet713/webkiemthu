package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import com.example.btl_dbclpm.repository.AreaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AreaServiceTest {

    @InjectMocks
    private AreaService areaService;

    @Mock
    private AreaRepository areaRepository;

    @Test
    public void testFindByEmployee_StandardCase1_ReturnListArea() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFullName("Quốc Việt");
        employee.setAuthorization("employee");
        employee.setEmail("QuocViet@gmail.com");
        employee.setPassword("Quocviet@12345");
        employee.setPhoneNumber("0869062889");
        employee.setUsername("quocviet");
        employee.setEmployeeCode("NV002");
        employee.setPosition("manager");
        Area area1 = Area.builder()
                .id(1L)
                .city("Quảng Ninh")
                .district("Hạ Long")
                .wardCommune("Bãi Cháy")
                .employee(employee)
                .build();
        Area area2 = Area.builder()
                .id(50L)
                .city("Quảng Ninh")
                .district("Hạ Long")
                .wardCommune("Cao Xanh")
                .employee(employee)
                .build();

        List<Area> expectedAreas = Arrays.asList(area1, area2);
        when(areaRepository.findByEmployee(employee)).thenReturn(Arrays.asList(area1, area2));
        List<Area> actualAreas = areaService.filterAreaByEmployee(employee);

        assertEquals(2, actualAreas.size());
        assertEquals(expectedAreas, actualAreas);
    }
}
