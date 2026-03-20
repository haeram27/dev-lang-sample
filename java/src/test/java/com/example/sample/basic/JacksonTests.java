package com.example.sample.basic;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        Object Mapper Member Methods
        https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-databind/latest/com/fasterxml/jackson/databind/ObjectMapper.html

        // === Deserialize ========================================
        // jsonBin to JsonNode(Tree)
        JsonNode    readTree(byte[] content)
        JsonNode    readTree(File file)
        JsonNode    readTree(InputStream in)
        JsonNode    readTree(Reader r)
        JsonNode    readTree(String content)
        JsonNode    readTree(URL source)

        // jsonBin to Type(JsonNode(Tree), Class Type, Generic Type)
        <T> T    readValue(byte[] src, Class<T> valueType)
        <T> T    readValue(byte[] src, int offset, int len, Class<T> valueType)
        <T> T    readValue(byte[] src, int offset, int len, JavaType valueType)
        <T> T    readValue(byte[] src, int offset, int len, TypeReference valueTypeRef)
        <T> T    readValue(byte[] src, JavaType valueType)
        <T> T    readValue(byte[] src, TypeReference valueTypeRef)
        <T> T    readValue(File src, Class<T> valueType)
        <T> T    readValue(File src, JavaType valueType)
        <T> T    readValue(File src, TypeReference valueTypeRef)
        <T> T    readValue(InputStream src, Class<T> valueType)
        <T> T    readValue(InputStream src, JavaType valueType)
        <T> T    readValue(InputStream src, TypeReference valueTypeRef)
        <T> T    readValue(JsonParser jp, Class<T> valueType)
        <T> T    readValue(JsonParser jp, JavaType valueType)
        <T> T    readValue(JsonParser jp, ResolvedType valueType)
        <T> T    readValue(JsonParser jp, TypeReference<?> valueTypeRef)
        <T> T    readValue(Reader src, Class<T> valueType)
        <T> T    readValue(Reader src, JavaType valueType)
        <T> T    readValue(Reader src, TypeReference valueTypeRef)
        <T> T    readValue(String content, Class<T> valueType)
        <T> T    readValue(String content, JavaType valueType)
        <T> T    readValue(String content, TypeReference valueTypeRef)
        <T> T    readValue(URL src, Class<T> valueType)
        <T> T    readValue(URL src, JavaType valueType)
        <T> T    readValue(URL src, TypeReference valueTypeRef)
        <T> MappingIterator<T>    readValues(JsonParser jp, Class<T> valueType)
        <T> MappingIterator<T>    readValues(JsonParser jp, JavaType valueType)
        <T> MappingIterator<T>    readValues(JsonParser jp, ResolvedType valueType)
        <T> MappingIterator<T>    readValues(JsonParser jp, TypeReference<?> valueTypeRef)

        // JsonObject(JsonNode, ObjectNode etc) to Type(User Defined Class, Generic Type)
        <T> T    convertValue(Object fromValue, Class<T> toValueType)
        <T> T    convertValue(Object fromValue, JavaType toValueType)
        <T> T    convertValue(Object fromValue, TypeReference<?> toValueTypeRef)

        // Important!!!!  exchange Object(Generic) to JsonNode 
        <T extends JsonNode> T valueToTree(Object fromValue)
        <T> T treeToValue(TreeNode n, Class<T> valueType)

        // === Serialize ========================================
        // JsonNode to jsonBin
        void    writeTree(JsonGenerator jgen, JsonNode rootNode)
        void    writeTree(JsonGenerator jgen, TreeNode rootNode)
        // Obejct to jsonBin
        void    writeValue(File resultFile, Object value)
        void    writeValue(JsonGenerator jgen, Object value)
        void    writeValue(OutputStream out, Object value)
        void    writeValue(Writer w, Object value)
        byte[]    writeValueAsBytes(Object value)
        String    writeValueAsString(Object value)
    */

    /*
        // === Read from JsonNode with ptr ========================================
        JsonNode at(String jsonPtrExpr)
    */

    /*
    JsonMapper.builder()
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

    /**
     * read value using json path
     */
    @Test
    public void readWithJsonPtr() {
        String jsonString = """
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
            """;

        try {
            JsonNode rootNode = jsonMapper.readTree(jsonString);

            log.info(rootNode.at("/store/book/0/title").asText());

            // WARNING: path index parameter input type as "int"
            log.info(rootNode.path("store").path("book").path(0).path("title").asText());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseJsonArrayToObjectList() {

        String jsonArrayString = """
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
        """;
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
        String jsonString = """
            {
                "name": "John Doe",
                "age": 30,
                "email": "john.doe@example.com",
                "roles": [
                    "admin",
                    "user"
                ]
            }
            """;
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
        String jsonString = """
            {
                "name": "John Doe",
                "age": 30,
                "email": "john.doe@example.com",
                "role": "admin"
            }
            """;
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
        String jsonString = """
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
            """;

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

    public void valueToTreeTest() {
        String respStr = """
        {"header":{"isSuccessful":true,"resultCode":0,"resultMessage":"SUCCESS"},"body":{"pageNum":0,"pageSize":0,"totalCount":0,"data":null}}
        """;

        var pageNumber = 1;
        var pageSize = 90;
        var totalServerCount = 500;
        var collectedServers = IntStream.rangeClosed(1, 500)
    .boxed()
    .collect(Collectors.toList());

        JsonNode responseBody = jsonMapper.createObjectNode();
        try {
            responseBody = jsonMapper.readTree(respStr);
            ObjectNode body = (ObjectNode) responseBody.path("body");
            body.put("pageNum", pageNumber);
            body.put("pageSize", pageSize);
            body.put("totalCount", totalServerCount);
            body.set("data", jsonMapper.valueToTree(collectedServers)); // 핵심
        } catch (Exception e) {
            log.error("## Error", e);
        }
}
