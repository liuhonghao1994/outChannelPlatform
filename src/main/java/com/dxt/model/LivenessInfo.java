package com.dxt.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LivenessInfo {
    private String id;
    private String resCode;
    private String resMsg;
    private String bestFrameBase64;
    private String sim;
}
