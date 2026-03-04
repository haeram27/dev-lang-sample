package com.example.sample.basic;

import java.nio.file.Files;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.example.sample.model.YamlTestDto;
import com.example.sample.util.PathUtil;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonYamlTests {

    /* Json Mapper */
    YAMLMapper yamlMapper = YAMLMapper.builder().addModule(new JavaTimeModule()).build();

    /*
    YAMLMapper.builder()
        .addModule(new JavaTimeModule())
        .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
        .enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // prevent UnknownPropertyException
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .build();
     */

    /**
     * To map json string to object
     * use
     *     T = JsonMapper.readValue(jsonString, new TypeReference<T>(){})
     *     JsonNode = JsonMapper.readTree(jsonString)
     *
     * jsonString is only allowed empty("") or json string
     */


    @Test
    public void yamlFileReadTest() {
        var path = PathUtil.processWorkingDirectory().resolve(
            "test.yaml"
        ).normalize();

        log.info(path.toString());

        if (!(Files.exists(path) && Files.isRegularFile(path))) {
            log.error("can NOT find configuration file");
            return;
        }

        YamlTestDto yaml = new YamlTestDto();
        try {
            yaml = yamlMapper.readValue(path.toFile(), YamlTestDto.class);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }

// read to JsonNode
/*
        try {
            JsonNode yaml = yamlMapper.readTree(path.toFile());
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
*/
        log.info(Optional.ofNullable(yaml.getKey1()).orElse("key1 is null"));
        log.info(Optional.ofNullable(yaml.getKey1()).orElse("key2 is null"));
    }
}
