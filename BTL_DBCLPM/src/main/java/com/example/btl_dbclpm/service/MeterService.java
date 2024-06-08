package com.example.btl_dbclpm.service;


import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.model.Meter;
import com.example.btl_dbclpm.repository.MeterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeterService {
    @PersistenceContext
    private EntityManager entityManager;

    private final MeterRepository meterRepository;

    public List<Meter> filterByArea(Area area) {
        return meterRepository.findByArea(area);
    }
    @Transactional
    public Meter save(Meter meter) {
        // Kiểm tra meter có null không
        if (meter == null) {
            return null;
        }

        Customer customer = meter.getCustomer();
        // Kiểm tra customer có null không
        if (customer == null) {
            return null;
        }

        Long customerId = customer.getId();
        // Kiểm tra customerId có null không
        if (customerId == null) {
            return null;
        }

        // Nếu không, nạp lại customer từ cơ sở dữ liệu trước khi lưu trữ
        Customer managedCustomer = entityManager.find(Customer.class, customerId);
        meter.setCustomer(managedCustomer);

        return meterRepository.save(meter);
    }

}

