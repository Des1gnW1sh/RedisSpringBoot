package com.scnjwh.intellectreport.modules.report.utils;

public class StringUtil {
    /**
     * 判断字符串是否为数字:包含整数、小数
     *
     * @param str 被检测字符
     * @return boolean 是否为数字
     */
    public static boolean isNumeric(String str) {
        if(str.startsWith(".")){
            str = "0" + str;
        }
        return str.matches("^-?\\d+$|^(-?\\d+)(\\.\\d+)?$");
    }
}
