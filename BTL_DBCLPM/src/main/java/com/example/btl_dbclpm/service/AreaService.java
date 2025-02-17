package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import com.example.btl_dbclpm.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;

    public List<Area> filterAreaByEmployee(Employee employee) {
        return areaRepository.findByEmployee(employee);
    }
    public Long findAreaIdByWardCommune(String wardCommune) {
        return areaRepository.findAreaIdByWardCommune(wardCommune);
    }
    public Area findAreaById(Long id) {
        return areaRepository.findById(id).orElse(null);
    }
//    public Area findAreaById(Long id) {
//        return areaRepository.findByIdWithEmployee(id).orElse(null);
//    }

}
