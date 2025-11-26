package com.example.sample.sniffet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.sample.util.StringUtil;

/**
 * parse properties file and load to map
 */
public class Properties2Map {

    public static Map<String, List<String>> parse(String properties) {
        var map = new HashMap<String, List<String>>();

        if (properties == null || StringUtil.isEmpty(properties)) {
            System.out.println("invalid file path");
            return map;
        }

        try (Stream<String> lines = properties.lines()) {
            lines.filter(line -> line.contains("=")).forEach(line -> {
                var pair = line.split("=", 2);
                if (pair.length < 2)
                    return;

                var key = pair[0].strip().split("\\s+")[0];
                var value = pair[1].strip().split("\\s+")[0];
                if (map.containsKey(key)) {
                    map.get(key).add(value);
                } else {
                    map.put(key, Stream.of(value).collect(Collectors.toList()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(map);
        map.entrySet().stream().filter(e -> !e.getValue().isEmpty())
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                //.sorted((o1, o2) -> Integer.valueOf(o1.getKey()).compareTo(Integer.valueOf(o2.getKey())))
                .forEach(e -> System.out.println(e));

        return map;
    }

    public static Map<String, List<String>> parse(Path path) {
        var map = new HashMap<String, List<String>>();

        if (path == null || !Files.exists(path)) {
            System.out.println("invalid file path");
            return map;
        }

        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(line -> line.contains("=")).forEach(line -> {
                var pair = line.split("=", 2);
                if (pair.length < 2)
                    return;

                var key = pair[0].strip().split("\\s+")[0];
                var value = pair[1].strip().split("\\s+")[0];

                if (map.containsKey(key)) {
                    map.get(key).add(value);
                } else {
                    map.put(key, Stream.of(value).collect(Collectors.toList()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(map);
        map.entrySet().stream().filter(e -> !e.getValue().isEmpty())
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                //.sorted((o1, o2) -> Integer.valueOf(o1.getKey()).compareTo(Integer.valueOf(o2.getKey())))
                .forEach(e -> System.out.println(e));

        return map;
    }


    @ParameterizedTest
    @ValueSource(strings = {"""
            a=apple
            b d =banana
            c=carrot cream
            """})
    public void parseTest(String properties) {
        parse(properties);
    }

    @ParameterizedTest
    @ValueSource(strings = {"./test.properties"})
    public void parseTest(Path path) {
        parse(path);
    }

}
