package com.ozf.laiyw.manage.common.utils;

import java.text.SimpleDateFormat;
import java.util.Random;
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

    /**
     * 随机验证码
     *
     * @param number 生成位数
     * @return
     */
    public static String randomCode(int number) {
        StringBuffer codeNum = new StringBuffer();
        int[] code = new int[3];
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int num = random.nextInt(10) + 48;
            int uppercase = random.nextInt(26) + 65;
            int lowercase = random.nextInt(26) + 97;
            code[0] = num;
            code[1] = uppercase;
            code[2] = lowercase;
            codeNum.append((char) code[random.nextInt(3)]);
        }
        return codeNum.toString();
    }
}
