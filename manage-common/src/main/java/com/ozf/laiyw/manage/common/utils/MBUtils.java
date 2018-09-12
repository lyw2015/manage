package com.ozf.laiyw.manage.common.utils;


import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MBUtils {

    private static final Logger logger = Logger.getLogger(MBUtils.class);

    /**
     * 对象转Map
     *
     * @param obj
     * @return
     */
    public static Map convertObjToMap(Object obj) {
        Map<String, Object> reMap = new HashMap<String, Object>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    reMap.put(fields[i].getName(), o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reMap;
    }

    /**
     * Map转对象
     *
     * @param type
     * @param map
     * @return
     */
    public static Object convertMapToObj(Class type, Map map) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            Object obj = type.newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    descriptor.getWriteMethod().invoke(obj, value);
                }
            }
            return obj;
        } catch (Exception e) {
            logger.error("map转实体类错误," + e);
            return null;
        }
    }
}
