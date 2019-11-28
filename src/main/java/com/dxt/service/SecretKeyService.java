package com.dxt.service;

import com.dxt.boss.model.SecretKeyModel;
import com.dxt.dao.SecertKeyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretKeyService {
        @Autowired
        private SecertKeyDao secertKeyDao;

        public List<String> getAllKeys(){
        return secertKeyDao.getAllSecretKeys();
    }
}
