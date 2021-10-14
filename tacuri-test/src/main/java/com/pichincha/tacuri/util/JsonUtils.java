package com.pichincha.tacuri.util;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 *
 * @author fmtacuri
 */
public class JsonUtils {

    private JsonUtils() {

    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> T mapToObject(Map<?, ?> map, Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    public static <T> List<T> jsonToList(Object object, Class<T> clazz) {
        return JSON.parseArray(jsonToObject(JSON.toJSONString(object), String.class), clazz);
    }
}
