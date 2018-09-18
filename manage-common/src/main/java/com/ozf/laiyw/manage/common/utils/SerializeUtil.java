package com.ozf.laiyw.manage.common.utils;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

/**
 * 序列化
 */
public class SerializeUtil<T> {

    private static Logger log = Logger.getLogger(SerializeUtil.class);


    /**
     * 反序列化
     *
     * @param str 待序列化的字符串
     * @return
     */
    public static <T> T deserialize(String str) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            if (StringUtils.isEmpty(str)) {
                return null;
            }
            bis = new ByteArrayInputStream(Base64.getDecoder().decode(str));
            ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                log.error("反序列化字符串异常", e);
            }

        }
    }

    /**
     * @param stringList
     * @param <T>
     * @return
     */
    public static <T> List<T> deserializeList(Collection<String> stringList) {
        if (null == stringList || stringList.isEmpty()) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (String str : stringList) {
            list.add(deserialize(str));
        }
        return list;
    }

    /**
     * 对象序列化
     *
     * @param obj 待序列化的对象
     * @return
     */
    public static <T> String serialize(T obj) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        } finally {
            try {
                oos.close();
                bos.close();
            } catch (IOException e) {
                log.error("序列化字符串异常", e);
            }

        }
    }
}
