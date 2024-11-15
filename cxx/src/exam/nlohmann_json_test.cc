#include <iostream>
#include <fstream>

#include "gtest/gtest.h"
#include "nlohmann/json.hpp"
#include "util/path.h"

using json = nlohmann::json;


class Nlohmanntest: public ::testing::Test {
protected:
    // You can remove any or all of the following functions if its body
    // is empty.
    Nlohmanntest() {
        // You can do set-up work for each test here.
        // No exception handling allowed here.
    }

    virtual ~Nlohmanntest() {
        // You can do clean-up work that doesn't throw exceptions here.
    }

    virtual void SetUp() override {
        // Code here will be called immediately after each test
        // (right before each test).
    }

    virtual void TearDown() override {
        // Code here will be called immediately after each test
        // (right before the destructor).
        // No exception handling allowed here.
    }
};

/*
https://json.nlohmann.me/api/json/
https://json.nlohmann.me/doxygen/index.html
*/

TEST_F(Nlohmanntest, object) {
    // create a JSON object
    json j =
    {
        {"pi", 3.141}, // object 1st item should be string as object key
        {"happy", true}, // object
        {"name", "Niels"}, // object
        {"nothing", nullptr}, // object
        {
            "answer", { // object 1st item should be string as object key
                {"everything", 42} // object
            }
        },
        {"list", {1, 0, 2}}, // array
        {
            "object", { // object 1st item should be string as object key
                {"currency", "USD"}, // object
                {"value", 42.99} // object
            }
        }
    };

    // create a JSON object with litteral string with ""_json operator
    json j_litteral = R"({
        "node_id": 9876,
        "file_type": "b",
        "file_list_info": [
            {
                "file_name": "c",
                "file_path": "d",
                "file_size": 567,
                "hash": 1234
            }
        ]
    })"_json;

    // print pretty format with indent of 4 spaces
    // std::cout << std::setw(4) << j << '\n'; // pretty print: OK
    std::cout << j.dump(4) << '\n';  // pretty print: BEST


    // initialize json object
    json j_obj = json::object(); // empty json value is basically treated by array
    //or json j_obj; j_obj2.emplace("key1", "value1");
    //or json j_obj = {{"key1", "value1"}};
    j_obj += {"key3", "valu3"};
    //j_obj += {{"key3", "value3"}, {"key4", "value4"}}; // exception: json.exception.type_error.308] cannot use push_back() with object"
    j_obj.emplace("key4", "value4"); // .emplace() used for only object
    std::cout << "j_obj: " << j_obj << '\n';


    // initialize json array
    json j_arr = json::array(); 
    j_arr[0] = "value1";
    j_arr += "value2";
    j_arr.emplace_back("value3"); // emplace_back() used for only array
    j_arr.push_back("value4"); // push_back() is not recommend to use. but it is prefered to use for array
    std::cout << "j_arr: " << j_arr << '\n';

    json null; // empty json value is basically treated by array
    std::cout << "null: " << null << '\n';
    null +=  {"key1", "value1"}; // ["key1", "value1"]
    null +=  {{"key2", "value2"}}; // [{"key2:"value2"}]
    std::cout << "null: " << null << '\n';
    json j_arr2 = {"key1", "value1"}; // !!!! json array
    std::cout << "j_arr2: " << j_arr2 << '\n';


    // add new array
    j["new"]["key"]["array1"] = {"a1", "b1"};
    j["/new/key/array2"_json_pointer] = {"a2", "b2"};
    j["/new/key/array3"_json_pointer].emplace_back("a3");
    j["/new/key/array3"_json_pointer].emplace_back("b3");
    std::cout << "j: " << j << '\n';


    // set null
    j["nothing2"] = nullptr;
    j["nothing3"] = {};
    j["nothing4"] = json{};
    j["nothing5"]; // "nothing5" should NOT be existed before
    std::cout << std::boolalpha << (j["nothing"] == nullptr) << '\n'; // true
    std::cout << std::boolalpha << (j["nothing"] == json::value_t::null) << '\n'; // true
    std::cout << std::boolalpha << (j["NOT_EXIST"] == nullptr) << '\n'; // true
    std::cout << std::boolalpha << (j["NOT_EXIST"] == json::value_t::null) << '\n'; // true


    // count elements
    auto s = j.size();
    j["size"] = s;


    // how to access element
/*
    [] operator :
        can only be used with object and array type
        return null when access on non-existing key(object) or index(array)
    value() :
        can only be used with object.
        return default value when access on non-existing key(object) or index(array)
    at() :
        can only be used with objects (with a string argument) or with arrays (with a numeric argument)
        throw exception when access on non-existing key(object) or index(array)
    get<type>():
        can only be used with objects (with a string argument) or with arrays (with a numeric argument)
        throw exception when access on non-existing key(object) or index(array)
*/
    std::cout << j["NOT_EXIST"] << '\n'; // null, j=object
    std::cout << std::boolalpha << (j["NOT_EXIST"] == nullptr) << '\n'; // true
    std::cout << std::boolalpha << (j["NOT_EXIST"] == json::value_t::null) << '\n'; // true
    std::cout << j.at("NOT_EXIST") << '\n'; // null, j=object
    std::cout << j["NOT_EXIST"][0] << '\n'; // null, j=object
    std::cout << j["list"][3] << '\n'; // null, j=object
    std::cout << j["name"].get<std::string>() << '\n'; // null, j=object
    std::cout << j["happy"].get<bool>() << '\n'; // null, j=object
    // std::cout << j.at(0) << '\n'; // j=object, exception: [json.exception.type_error.304] cannot use at() with object
    // std::cout << j[0]<< '\n'; // j=object, exception: [json.exception.type_error.305] cannot use operator[] with a numeric argument with object
    //std::cout << j.value("NOT_EXIST","NOTFOUND") << '\n';
}

TEST_F(Nlohmanntest, array) {
    // create a JSON array
    json j1 = R"({
        "error_code": "EPP-00000",
        "error_msg": "success",
        "response": [],
        "revision": 1
    })"_json;

    json j2 = R"({
        "error_code": "EPP-00000",
        "error_msg": "success",
        "response": [
            {
            "hash_value": "978921233557383108",
            "node_hash_type": "HARDWARE"
            }
        ],
        "revision": 1
    })"_json;

    json j3 = R"({
        "error_code": "EPP-00000",
        "error_msg": "success",
        "response": [
            {
                "hash_value": "10988636878217696476",
                "node_hash_type": "NODE_STATUS"
            },
            {
                "hash_value": "",
                "node_hash_type": "NODE_INFO"
            },
            {
                "hash_value": "978921233557383108",
                "node_hash_type": "HARDWARE"
            },
            {
                "hash_value": "2654435769",
                "node_hash_type": "SHARE_FOLDER"
            },
            {
                "hash_value": "5962700740829845848",
                "node_hash_type": "SOFTWARE"
            },
            {
                "hash_value": "740847744684406860",
                "node_hash_type": "APM_STATUS"
            },
            {
                "hash_value": "9e7f1bcfc7394924ea6cf60013b2830ee9415d8360206c27c5bb28cc584fcf04",
                "node_hash_type": "APM_PATCH_STATUS"
            }
        ],
        "revision": 1
    })"_json;

    // print pretty format with indent of 4 spaces
    // std::cout << std::setw(4) << j << '\n'; // pretty print: OK
    std::cout << j1.dump(4) << '\n';  // pretty print: BEST
    std::cout << j2.dump(4) << '\n';  // pretty print: BEST

    auto &hashes1 = j1["/response"_json_pointer];
    if (hashes1.is_array()) {
        for (auto hash : hashes1) {
            std::cout << "type: " << hash["node_hash_type"] << "\n";
            std::cout << "value: " << hash["hash_value"] << "\n";
        }
    }

    auto &hashes2 = j2["response"];
    if (hashes2.is_array()) {
        for (auto hash : hashes2) {
            std::cout << "type: " << hash["node_hash_type"] << "\n";
            std::cout << "value: " << hash["hash_value"] << "\n";
        }
    }
}