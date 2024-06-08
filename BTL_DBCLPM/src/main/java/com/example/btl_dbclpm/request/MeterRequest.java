package com.example.btl_dbclpm.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MeterRequest {
        private String meterCode;
        private String installmentAddress;
        private Date installmentDate;
        private String meterType;
        private String customerName;
        private String wardCommune;// Các getter và setter
        private String customerFullName;
        private String customerEmail;
        private String customerPhoneNumber;
        private String customerPassword;
        private String timeUpdate;


}
