package com.dxt.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class MyObjectUtil {

    private static final Logger logger = LoggerFactory.getLogger(MyObjectUtil.class);

    public static String show(Object object) {
        try {
            Class cls = object.getClass();
            StringBuffer buf = new StringBuffer();
            buf.append("[").append(cls.getName()).append("] ");
            Field[] fields = cls.getDeclaredFields();
            for(int i = 0; i < fields.length; i++){
                Field f = fields[i];
                f.setAccessible(true);
                buf.append(f.getName()).append("=").append(f.get(object)).append(", ");
            }
            String ret = buf.toString();
            return ret.substring(0, ret.length() - 2);
        } catch (IllegalAccessException e) {
            logger.error(LogHelper._FUNC_() + e.getMessage());
        }
        return "";
    }


}
