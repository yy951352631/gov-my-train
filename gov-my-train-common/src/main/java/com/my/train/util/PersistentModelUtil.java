/**
 * 
 */
package com.my.train.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */

public class PersistentModelUtil {
    public static void copyNotNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullProperties(src));
    }

    private static String[] getNullProperties(Object src) {
        Class<?> objClass = src.getClass();
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        List<String> nullProperties = new ArrayList<String>();
        for (PropertyDescriptor p : pds) {
            Object srcValue = srcBean.getPropertyValue(p.getName());
            if (srcValue == null) {
//                if (ReflectionUtils.findField(objClass, p.getName()).getAnnotation(WebData.class) == null) {
//                    // value为null并且没有webdata的注解
//                    nullProperties.add(p.getName());
//                }
            }
        }
        String[] result = new String[nullProperties.size()];
        return nullProperties.toArray(result);
    }
}
