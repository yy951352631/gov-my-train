package com.my.train.util;

import java.util.UUID;

/**
 * @author Wtq
 * @date 2019/10/23 - 15:29
 */
public class IdUtil {
    public static String generateId() {
        //使用UUID策略生成主键
        return UUID.randomUUID().toString();
    }
}