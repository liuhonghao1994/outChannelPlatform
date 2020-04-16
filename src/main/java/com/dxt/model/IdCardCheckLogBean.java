package com.dxt.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class IdCardCheckLogBean {
    private String name;
    private String idCard;
    private String resultCode;
    private String resultMsg;
    private String resultRequestId;
    private String plat;
}
