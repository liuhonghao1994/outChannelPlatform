package com.dxt.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TxyOcrAnalyseCardLogBean {
    private String txyOrderId;
    private String responseStr;
    private String plat;
    private String cardType;
}
