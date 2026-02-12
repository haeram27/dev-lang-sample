package com.example.sample.basic;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonTests {

    /* Json Mapper */
    JsonMapper jsonMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    /*
    JsonMapper jsonMapper = JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
        .enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)
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
    public void mapperReadTreeTest() {

        String nullStr = null;
        var emptyStr = "";
        var anyStr  = "asdf";
        var jsonStr  = """
                        {
                            "key":"value"
                        }
                        """;

        JsonNode jsonNode;

        log.info("=== null string test =========================");
        try {
            jsonNode = jsonMapper.readTree(nullStr);   // Exception
            if (jsonNode == null) {
                log.error("Error: node is null");
            }else if (jsonNode.isEmpty()) {
                log.error("Error: node is empty");
            }
            log.info(jsonNode.toPrettyString());
        } catch(Exception e) {
            log.error(e.getMessage(), e.getClass());
        }

        log.info("=== any string test =========================");
        try {
            jsonNode = jsonMapper.readTree(anyStr);     // Exception
            if (jsonNode == null) {
                log.error("Error: node is null");
            }else if (jsonNode.isEmpty()) {
                log.error("Error: node is empty");
            }
            log.info(jsonNode.toPrettyString());
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }

        log.info("=== empty string test =========================");
        try {
            jsonNode = jsonMapper.readTree(emptyStr);    // return empty JsonNode
            if (jsonNode == null) {
                log.error("Error: node is null");
            }else if (jsonNode.isEmpty()) {
                log.error("Error: node is empty");  // here
            }
            log.info(jsonNode.toPrettyString());
        } catch(Exception e) {
            log.error(e.getMessage());
        }


        log.info("=== json string test =========================");
        try {
            jsonNode = jsonMapper.readTree(jsonStr); // OK
            if (jsonNode == null) {
                log.error("Error: node is null");
            }else if (jsonNode.isEmpty()) {
                log.error("Error: node is empty");
            }
            log.info(jsonNode.toPrettyString());
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    public void parseJsonArrayToObjectList() {

        // @formatter:off
        /*
        [
            {
                "name": "John Doe",
                "age": 30,
                "email": "john.doe@example.com"
            },
            {
                "name": "Jane Smith",
                "age": 25,
                "email": "jane.smith@example.com"
            }
        ]
        */
        // @formatter:on

        String jsonArrayString = "[{\"name\":\"John Doe\",\"age\":30,\"email\":\"john.doe@example.com\"},{\"name\":\"Jane Smith\",\"age\":25,\"email\":\"jane.smith@example.com\"}]";

        try {
            // JSON 배열 문자열을 List로 변환
            // @formatter:off
            List<Map<String, Object>> list = jsonMapper.readValue(jsonArrayString, new TypeReference<List<Map<String, Object>>>(){});
            // @formatter:on

            // List에서 데이터 접근
            for (Map<String, Object> map : list) {
                String name = (String) map.get("name");
                int age = (Integer) map.get("age");
                String email = (String) map.get("email");

                // 출력
                System.out.println("Name: " + name);
                System.out.println("Age: " + age);
                System.out.println("Email: " + email);
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // gradle test --rerun-tasks --tests 'JacksonTests.parseJsonObjectToMap'
    @Test
    public void parseJsonObjectToMap() {

        // @formatter:off
        /*
        {
            "name": "John Doe",
            "age": 30,
            "email": "john.doe@example.com",
            "roles": [
                "admin",
                "user"
            ]
        }
        */
        // @formatter:on

        String jsonString = "{\"name\":\"John Doe\",\"age\":30,\"email\":\"john.doe@example.com\",\"roles\":[\"admin\",\"user\"]}";

        try {
            // JSON 문자열을 Map으로 변환
            // @formatter:off
            Map<String, Object> map = jsonMapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
            // @formatter:on

            // Map에서 데이터 접근
            String name = (String) map.get("name");
            int age = (Integer) map.get("age");
            String email = (String) map.get("email");
            List<String> roles = (List<String>) map.get("roles");

            // 출력
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Email: " + email);
            System.out.println("Roles: " + roles);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // gradle test --rerun-tasks --tests 'JacksonTests.upateValueUsingMap'
    @Test
    public void upateValueUsingMap() {

        // @formatter:off
        /*
        {
            "name": "John Doe",
            "age": 30,
            "email": "john.doe@example.com",
            "role": "admin"
        }
        */
        // @formatter:on

        String jsonString = "{\"name\":\"John Doe\",\"age\":30,\"email\":\"john.doe@example.com\",\"role\":\"admin\"}";

        try {
            // deserialize: json string -> java object(Map)
            // @formatter:off
            Map<String, Object> map = jsonMapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
            // @formatter:on

            // read map object
            String name = (String) map.get("name");
            int age = (Integer) map.get("age");
            String email = (String) map.get("email");
            String role = (String) map.get("role");

            // print
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Email: " + email);
            System.out.println("Role: " + role);

            // update map
            map.put("name", "Meh Jong");
            map.put("age", 20);
            map.put("email", "meh.jong@example.com");
            map.put("role", "user");

            // serialize: java object(map) -> json string
            System.out.println(jsonMapper.writeValueAsString(map));

            map.remove("role");
            System.out.println(map.toString());
            System.out.println(jsonMapper.writeValueAsString(map));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // gradle test --rerun-tasks --tests 'JacksonTests.upateValueUsingJsonNode'
    @Test
    public void upateValueUsingJsonNode() {
        // @formatter:off
        /*
        {
            "store": {
                "book": [
                    {
                        "category": "reference",
                        "author": "Nigel Rees",
                        "title": "Sayings of the Century",
                        "price": 8.95
                    },
                    {
                        "category": "fiction",
                        "author": "Evelyn Waugh",
                        "title": "Sword of Honour",
                        "price": 12.99
                    }
                ],
                "bicycle": {
                    "color": "red",
                    "price": 19.95
                }
            }
        }
        */
        // @formatter:on
        String jsonString = "{"
                + "\"store\": {"
                + "\"book\": ["
                + "{ \"category\": \"reference\", \"author\": \"Nigel Rees\", \"title\": \"Sayings of the Century\", \"price\": 8.95 },"
                + "{ \"category\": \"fiction\", \"author\": \"Evelyn Waugh\", \"title\": \"Sword of Honour\", \"price\": 12.99 }"
                + "],"
                + "\"bicycle\": { \"color\": \"red\", \"price\": 19.95 }"
                + "}"
                + "}";

        try {
            JsonNode rootNode = jsonMapper.readTree(jsonString);

            /*
             * read first category of book
             */
            String firstBookCategory;

            // use json point expression
            firstBookCategory = rootNode.at("/store/book/0/category").asText();
            System.out.println("First book category: " + firstBookCategory); // reference
            // use field(key) name
            firstBookCategory = rootNode.path("store").path("book").get(1).path("category").asText();
            System.out.println("First book category: " + firstBookCategory); // fiction
            System.out.println("----------------------------------------------------------");

            /*
             * read bicycle price
             */
            double bicyclePrice;

            // use json point expression
            bicyclePrice = rootNode.at("/store/bicycle/price").asDouble();
            System.out.println("Bicycle price: " + bicyclePrice); // 19.95

            // use field name (key)
            bicyclePrice = rootNode.path("store").path("bicycle").path("price").asDouble();
            System.out.println("Bicycle price: " + bicyclePrice); // 19.95
            System.out.println("----------------------------------------------------------");

            /*
             * update value using json point expression
             */
            String modifiedJsonString;
            ((ObjectNode) rootNode.at("/store/book/0")).put("price", 10.99);
            ((ObjectNode) rootNode.at("/store/bicycle")).put("color", "blue");
            modifiedJsonString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            System.out.println("Modified JSON: " + modifiedJsonString);
            System.out.println("----------------------------------------------------------");

            JsonNode bicycleNode = rootNode.at("/store/bicycle");
            if (bicycleNode.isObject()) {
                ((ObjectNode) bicycleNode).put("color", "green");
            }
            modifiedJsonString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            System.out.println("Modified JSON: " + modifiedJsonString);
            System.out.println("----------------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
