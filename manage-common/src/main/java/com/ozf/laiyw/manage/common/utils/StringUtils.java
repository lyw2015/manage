package com.ozf.laiyw.manage.common.utils;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 连接
     *
     * @param objs
     * @param sper
     * @return
     */
    public static String join(Object[] objs, String sper) {
        if (objs == null || objs.length == 0 || isEmpty(sper))
            return null;
        StringBuffer sb = new StringBuffer();
        for (Object obj : objs) {
            sb.append(obj).append(sper);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 校验字符串是否满足yyyy-MM-dd HH:mm / yyyy.MM.dd HH:mm格式
     *
     * @param dateTimeStr
     * @return
     */
    public static boolean checkDateTime(String dateTimeStr) {
        try {
            if (dateTimeStr.contains("-")) {
            } else if (dateTimeStr.contains(".")) {
                dateTimeStr = dateTimeStr.replace(".", "-");
            }
            new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM).parse(dateTimeStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * UUID
     *
     * @return
     */
    public static String randUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 获取新的文件名
     *
     * @param orginFileName
     * @return
     */
    public static String createNewFileName(String orginFileName) {
        StringBuffer sb = new StringBuffer(randUUID());
        sb.append(orginFileName.substring(orginFileName.indexOf(".")));
        return sb.toString();
    }

}
