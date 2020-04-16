package com.dxt.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CustCertInfo {
    private String id;
    private String name;
    private String idCode;
    private String birthday;
    private String gender;
    private String sex;
    private String nation;
    private String address;
    private String authority;
    private String validDate;
}
