package com.group7.store.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: YangZhaoYan
 * @Date: 2021/1/17
 */
public class ResultUtil {

    /**
     * 成功后的返回结果
     *
     * @param resultMap
     * @return
     */
    public static Map<String, Object> resultSuccess(Map<String, Object> resultMap) {
        resultMap.put("message", "操作成功");
        resultMap.put("code", 200);
        return resultMap;
    }

    /**
     * 失败后的返回结果
     *
     * @param resultMap
     * @return
     */
    public static Map<String, Object> resultError(Map<String, Object> resultMap) {
        resultMap.put("message", "操作失败");
        resultMap.put("code", 500);
        return resultMap;
    }

    /**
     * 通用的返回结果
     *
     * @param code
     * @param msg
     * @return
     */
    public static Map<String, Object> resultCode(Integer code, String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", msg);
        resultMap.put("code", code);
        return resultMap;
    }
}
