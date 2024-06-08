package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.*;
import com.example.btl_dbclpm.repository.MeterRepository;
import com.example.btl_dbclpm.request.MeterRequest;
import com.example.btl_dbclpm.service.AreaService;
import com.example.btl_dbclpm.service.CustomerService;
import com.example.btl_dbclpm.service.MeterService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/meters")
@CrossOrigin
@RequiredArgsConstructor
public class MeterController {
    private final MeterService meterService;
    private final MeterRepository meterRepository;
    private final CustomerService customerService;
    private final AreaService areaService;

    @PostMapping("/filter")
    public ResponseEntity<List<Meter>> filterByArea(@RequestBody Area area) {
        List<Meter> meter = meterService.filterByArea(area);
        return meter != null ? ResponseEntity.ok(meter) : ResponseEntity.badRequest().build();
    }
    @PostMapping("/save")
    public ResponseEntity<?> createMeter(@RequestBody MeterRequest meterRequest) throws ParseException {
        String meterCode = meterRequest.getMeterCode();
        String installmentAddress = meterRequest.getInstallmentAddress();
        Date installmentDate = meterRequest.getInstallmentDate();
        String meterType = meterRequest.getMeterType();
        String wardCommune = meterRequest.getWardCommune();
        String customerName = meterRequest.getCustomerName();


        // Tìm ID của khách hàng dựa trên tên nhập từ người dùng
        Long customerId = customerService.findCustomerIdByFullName(customerName);

        // Kiểm tra xem có tồn tại khách hàng có ID này không
        if (customerId == null) {
            // Nếu không tìm thấy khách hàng, bạn có thể xử lý lỗi hoặc trả về thông báo không tìm thấy khách hàng
            return ResponseEntity.badRequest().body("Không tìm thấy khách hàng.");
        }

        // Tìm ID của tỉnh/thành phố dựa trên tên nhập từ người dùng
        Long areaId = areaService.findAreaIdByWardCommune(wardCommune);

        // Kiểm tra xem có tồn tại tỉnh/thành phố có ID này không
        if (areaId == null) {
            // Nếu không tìm thấy tỉnh/thành phố, bạn có thể xử lý lỗi hoặc trả về thông báo không tìm thấy
            return ResponseEntity.badRequest().body("Không tìm thấy tỉnh/thành phố.");
        }

        // Tạo một đối tượng Meter
        Meter meter = new Meter();
        meter.setMeterCode(meterCode);
        meter.setInstallmentAddress(installmentAddress);
        meter.setInstallmentDate(java.sql.Date.valueOf(installmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        meter.setMeterType(meterType);
        meter.setTimeUpdate(java.sql.Date.valueOf(installmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

        // Tạo một đối tượng Customer
//        Customer customer = new Customer();
//        customer.setId(customerId);
        Customer customer1 = customerService.findCustomerById(customerId);
        if (customer1 == null) {
            // Nếu không tìm thấy, trả về lỗi hoặc tạo mới đối tượng Area
            // Xử lý lỗi hoặc thông báo không tìm thấy
            return ResponseEntity.badRequest().body("Không tìm thấy khu vực.");
        }// Gán ID của User cho Customer;
        // Tạo đối tượng Area hoặc lấy từ cơ sở dữ liệu nếu đã tồn tại
        Area area = areaService.findAreaById(areaId);
        if (area == null) {
            // Nếu không tìm thấy, trả về lỗi hoặc tạo mới đối tượng Area
            // Xử lý lỗi hoặc thông báo không tìm thấy
            return ResponseEntity.badRequest().body("Không tìm thấy khu vực.");
        }
        meter.setCustomer(customer1);
        meter.setArea(area);
        // Lưu trữ Meter
        meterService.save(meter);
        return ResponseEntity.ok("Meter created successfully.");
    }
}
