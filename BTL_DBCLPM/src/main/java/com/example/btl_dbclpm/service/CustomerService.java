package com.example.btl_dbclpm.service;

import com.example.btl_dbclpm.model.Area;
import com.example.btl_dbclpm.model.Customer;
import com.example.btl_dbclpm.repository.AreaRepository;
import com.example.btl_dbclpm.repository.CustomerRepository;
import com.example.btl_dbclpm.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private JavaMailSender emailSender;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    public String registerUser(Customer customer) {
        Area area = new Area();
        System.out.println(customer.getUsername());
        if (userRepository.existsByEmail(customer.getEmail())) {
            return "EMAIl";
        }
        if (userRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            return "PHONE_NUMBER";
        }
        if (userRepository.existsByUsername(customer.getUsername())) {
            return "USERNAME";
        }
        String otp = generateOTP();
        return otp;
    }

    public Customer saveUser(Customer customer){
        return customerRepository.save(customer);
    }
    public void sendOTP(String to, String otp) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject("OTP Verification Code");
            helper.setText("Your OTP code is: " + otp);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        emailSender.send(message);
    }
    public Long findCustomerIdByFullName(String fullName) {
        return customerRepository.findCustomerIdByFullName(fullName);
    }

    private String generateOTP() {
        // Tạo mã OTP ngẫu nhiên gồm 6 chữ số
        return RandomStringUtils.randomNumeric(6);
    }
    public boolean checkPass(String pass){
        if(pass.length() < 10){
            return false;
        }
        else{
            boolean checkChuThuong = false;
            boolean checkChuHoa = false;
            boolean checkSo = false;
            boolean checkKyTu = false;
            for(char i : pass.toCharArray()){
                if(Character.isDigit(i)){
                    checkSo = true;
                }
                else if(Character.isUpperCase(i)){
                    checkChuThuong = true;
                }
                else if (Character.isLowerCase(i)) {
                    checkChuHoa = true;
                }
                else{
                    checkKyTu = true;
                }
            }
            if(checkSo == true && checkKyTu == true && checkChuHoa == true && checkChuThuong == true){
                return true;
            }
            else {
                return false;
            }
        }
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
}
