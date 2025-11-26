package com.example.sample.basic;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionTests {
    @Test
    void reflectTypeTest() {
        JsonMapper jsonMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        TypeFactory typeFactory = jsonMapper.getTypeFactory();

        TypeReference<List<Map<String, Integer>>> tr = new TypeReference<>() {};

        JavaType jtype = typeFactory.constructType(tr);

        log.info("{}", jtype.getTypeName());

        // printClassInfo(tr.getClass());
        printTypeInfo(tr.getType());
    }

    private void printClassInfo(Class<?> c) {
        log.info("toString: {}", c.toString());
        log.info("toGenericString: {}", c.toGenericString());
        log.info("getCanonicalName: {}", c.getCanonicalName());
        log.info("getTypeName: {}", c.getTypeName());
        log.info("getTypeParameters: {}", c.getTypeParameters());
    }

    private void printTypeInfo(Type t) {
        log.info("getClass(): {}", t.getClass());
        log.info("getClass().getGenericSuperclass(): {}", t.getClass().getGenericSuperclass());

        if (t instanceof Class<?>) {
            log.info("=== Class");
        } else if (t instanceof ParameterizedType) {
            log.info("=== ParameterizedType");
            log.info("Types: {}", ((ParameterizedType) t).getActualTypeArguments());
        } else if (t instanceof JavaType) {
            log.info("=== JavaType");
        } else if (t instanceof GenericArrayType) {
            log.info("=== GenericArrayType");
        } else if (t instanceof TypeVariable<?>) {
            log.info("=== TypeVariable");
        } else if (t instanceof WildcardType) {
            log.info("=== WildcardType");
        } else {
            log.info("UnknownType");
        }
    }
}
