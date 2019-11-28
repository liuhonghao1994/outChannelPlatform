package com.dxt.dao;

import com.dxt.model.MapInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Liuhh
 * @creat 2019-08-16 13:49
 */
@Repository
public interface InsertMesDao {

    @Insert("insert into baidupio values(#{name,jdbcType=VARCHAR},#{address,jdbcType=VARCHAR},#{province,jdbcType=VARCHAR},#{city,jdbcType=VARCHAR},#{area,jdbcType=VARCHAR},#{telephone,jdbcType=VARCHAR})")
    void insertMapInfo(@Param("name") String name,
                       @Param("address") String address,
                       @Param("province") String province,
                       @Param("city") String city,
                       @Param("area") String area,
                       @Param("telephone") String telephone);
}
