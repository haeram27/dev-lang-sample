package com.example.sample.sniffet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ParseProperties {

    @ParameterizedTest
    @ValueSource(strings = {"./test.properties"})
    public static void run(String path) {
        if (Optional.ofNullable(path).isEmpty() || path.isBlank()) {
            System.out.println("invalid file path");
            return;
        }

        var map = new HashMap<String, List<String>>();
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            lines.filter(line -> line.contains("=")).forEach(line -> {
                var pair = line.split("=", 2);
                if (pair.length < 2)
                    return;

                var key = pair[0];
                var value = pair[1];
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
        map.entrySet().stream().filter(e -> e.getValue().size() >= 2)
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                //.sorted((o1, o2) -> Integer.valueOf(o1.getKey()).compareTo(Integer.valueOf(o2.getKey())))
                .forEach(e -> System.out.println(e));
    }
}
