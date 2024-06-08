package com.example.btl_dbclpm.repository;


import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area,Long> {
    List<Area> findByEmployee(Employee employee);
    @Query(value = "SELECT a.id FROM Area a WHERE a.wardCommune= :wardCommune")
    Long findAreaIdByWardCommune(@Param("wardCommune") String wardCommune);
    @Query("SELECT a FROM Area a LEFT JOIN FETCH a.employee WHERE a.id = :id")
    Optional<Area> findByIdWithEmployee(@Param("id") Long id);
}
