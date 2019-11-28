package com.dxt.dao;

import com.dxt.boss.model.ChoiceNumberNewBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceNumberDao {
        @Select({"<script>",
                "select b2.district_name provice, b2.region_id pro_code,b1.district_name city,substr(t.res_id,1,7) hrl,t.res_id,t.region_id,\n"
                + "t.manage_status,t.reserve_fee,t.deposit_month,t.deposit_amount,t.res_level,d.pattern_def_name,t.res_spec_id  net,t.rsrv_num1\n"
                + "from so.res_phone_num_origin t ,res.res_pattern_define d ,base.bs_district b1,base.bs_district b2\n",
                "<where>",
                "  t.SELECT_PRICE_MODE=d.pattern_def_id(+)",
                "  and t.region_id=b1.region_id",
                "  and b1.parent_district_id=b2.district_id",
                "<when test='net !=null '>",
                "  and t.res_spec_id=#{net}",
                "</when>",
                "<when test='region_id !=null '>",
                "  and t.region_id=#{region_id}",
                "</when>",
                "<when test='res_level !=null '>",
                "  and t.res_level=#{res_level}",
                "</when>",
                " <when test='pattern_def_id !=null '>",
                "  and d.pattern_def_id=#{pattern_def_id}",
                "</when>",
                "<when test='res_store_id !=null '>",
                "  and t.res_store_id in (#{res_store_id})",
                "</when>",
                "<when test='hrl !=null '>",
                "  and  t.res_id like (#{hrl} || '%')",
                "</when>",

                "<when test='match_pattern==1 and match_count !=null '>",
                "  and  t.res_id like ( '%' || #{match_count} )",
                "</when>",

                "<when test='match_pattern==2 and match_count !=null '>",
                "  and  t.res_id like (#{match_count}  ||  '%')",
                "</when>",

                "  and t.manage_status='2'",
                "</where>",
                "</script>"})
        List<ChoiceNumberNewBean> getChoiceNumber(
                @Param("net") String net,
                @Param("region_id") String region_id,
                @Param("res_level") String res_level,
                @Param("pattern_def_id") String pattern_def_id,
                @Param("res_store_id") String res_store_id,
                @Param("hrl") String hrl,
                @Param("match_pattern") int match_pattern,
                @Param("match_count") String match_count
        );





}
