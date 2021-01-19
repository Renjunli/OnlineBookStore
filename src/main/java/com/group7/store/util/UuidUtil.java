package com.group7.store.util;

import java.util.UUID;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/17
 */
public class UuidUtil {
    private UuidUtil() {
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
