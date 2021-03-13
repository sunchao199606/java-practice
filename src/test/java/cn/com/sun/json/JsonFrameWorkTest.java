package cn.com.sun.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonFrameWorkTest {
    private ObjectMapper objectMapper;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
        objectMapper = new ObjectMapper();
        //在序列化时忽略值为 null 的属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //忽略值为默认值的属性
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        //object中没有属性时失败关闭
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void objectToString() {
        Message message = new Message("request", "10010", new Object());
        // fastJson
        System.out.println("FastJson : " + JSON.toJSONString(message));
        // jackson
        try {
            System.out.println("Jackson : " + objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // gson
        System.out.println("Gson : " + gson.toJson(message));
    }

    @Test
    public void stringToObject() {
        String message = "{\"data\":{},\"type\":\"request\",\"uid\":\"10010\"}";
        Message m1 = JSON.parseObject(message, Message.class);
        Message m3 = gson.fromJson(message, Message.class);
        Message m2 = null;
        try {
            m2 = objectMapper.readValue(message, Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(m2.toString());
    }
}
