package com.dxt.dao;


import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecertKeyDao {
        @Select("select open_secretkey from Secret_Key")
        List<String> getAllSecretKeys();
}
