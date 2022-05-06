package com.yfh.myssm.util;

public class StringUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if(str != null && !"".equals(str))
            return false;
        return true;
    }

}
