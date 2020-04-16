package com.dxt.service;

import com.dxt.dao.ScanMesParseDao;
import com.dxt.message.ReponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScanMes {
    @Autowired
    private ScanMesParseDao scanMesParseDao;
    public void insertMes(Map map){
        scanMesParseDao.insetMesInfoIntoParseMes(map);
    }
}
