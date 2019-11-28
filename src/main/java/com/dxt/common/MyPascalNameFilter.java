package com.dxt.common;

import com.alibaba.fastjson.serializer.PascalNameFilter;

public class MyPascalNameFilter extends PascalNameFilter {

    @Override
    public String process(Object source, String name, Object value) {
        if (name == null || name.length() == 0) {
            return name;
        }

        char[] chars = name.toCharArray();
        chars[0]= Character.toLowerCase(chars[0]);

        String pascalName = new String(chars);
        return pascalName;
    }
}
