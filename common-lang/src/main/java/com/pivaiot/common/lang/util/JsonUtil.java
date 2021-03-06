package com.pivaiot.common.lang.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * A json util based on jackson.
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();
    static {
        SimpleModule customModule = new SimpleModule("jsonUtilsModule", new Version(1, 0, 0, "", "", ""));
        customModule.addSerializer(Long.class, new ToStringSerializer());
        mapper.registerModule(customModule)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
                .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            LOGGER.error("toJson error", e);
            return null;
        }
    }

    /**
     * 将一个 object 转成键值字典.
     */
    public static Map<String, String> toMap(Object value) {
        return mapper.convertValue(value, new TypeReference<Map<String, String>>() {});
    }

    /**
     * 讲一个json字符串转成键值字典
     * @param json 字符串
     * @return 字典Map
     */
    public static Map<String, Object> toMap(String json) {
        TypeReference<Map<String, Object>> typeRef
            = new TypeReference<Map<String, Object>>() {};

        try {
            return mapper.readValue(json, typeRef);
        } catch (IOException e) {
            LOGGER.error("Json String toMap error: {}", json, e);
            return null;
        }
    }

    /**
     * 将一个 object 转成键值数组, 一对键值放在相邻的位置.
     * [name1, value1, name2, value2, ..., nameN, valueN]
     */
    public static List<String> toNamesAndValues(Object value) {
        Map<String, String> map = toMap(value);
        List<String> namesAndValues = new ArrayList<>();
        map.entrySet().stream().forEach(entry -> {
            namesAndValues.add(entry.getKey());
            namesAndValues.add(entry.getValue());
        });

        return namesAndValues;
    }

    /**
     * 处理任意 JSON 结构.
     * {T}
     */
    public static <T> T fromPlainJson(String json, Class<T> clazz) {
        try {
            T entity = mapper.readValue(json, clazz);
            return entity;
        } catch (IOException e) {
            LOGGER.error("json to object error", e);
            return null;
        }
    }

    public static <T> T fromPlainJson(String json, TypeReference typeReference) {
        try {
            T entity = mapper.readValue(json, typeReference);
            return entity;
        } catch (IOException e) {
            LOGGER.error("json to object error", e);
            return null;
        }
    }

    /**
     * Create a new ObjectMapper.
     * @return a new ObjectMapper object
     */
    public static ObjectMapper build() {
        return mapper.copy();
    }
}
