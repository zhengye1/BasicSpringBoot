package com.itsp.basicspring.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class ProControllerIntegrationTest {
    @LocalServerPort
    private Integer port;

    @Container
    private static final MySQLContainer<?> mysqlContainer =
        new MySQLContainer<>("mysql:8.0").withDatabaseName("testdb").withUsername("test").withPassword("test");

    @DynamicPropertySource
    public static void overrideDataSourceProperties(DynamicPropertyRegistry registry) {
        // 动态设置数据库连接属性
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }


    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    // 1. 测试 listAllPros() 在没有提供 name 参数时
    @Test
    void testListAllPros() {
        // @formatter:off
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/pro")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("data.size()", equalTo(8))  // 验证数据总数
            .body("code", equalTo("0"))
            .body("msg", equalTo("ok"))
            // 验证第一个记录的关键字段
            .body("data[0].id", equalTo(1))
            .body("data[0].proName", equalTo("Test Pro 1"))
            .body("data[0].teamName", equalTo("Test Team A"))
            .body("data[0].org", equalTo("Test Org 1"))
            // 验证最后一个记录的关键字段
            .body("data[7].id", equalTo(8))
            .body("data[7].proName", equalTo("Test Pro 8"))
            .body("data[7].teamName", equalTo("Test Team B"))
            .body("data[7].org", equalTo("Test Org 3"));
        // @formatter:on
    }

    // 2. 测试 listAllPros() 在提供 name 参数时
    @Test
    void testGetProByName() {
        // @formatter:off
        given()
            .queryParam("name", "Test Pro 1")
            .contentType(ContentType.JSON)
        .when()
            .get("/pro")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo("0"))
            .body("msg", equalTo("ok"))
            .body("data[0].proName", equalTo("Test Pro 1"))
            .body("data[0].teamName", equalTo("Test Team A"))
            .body("data[0].org", equalTo("Test Org 1"))
            .body("data[0].id", equalTo(1))
            .body("data[0].birth",equalTo("1990-01-01"))
            .body("data[0].birthPlace",equalTo("City A"))
            .body("data[0].proYear", equalTo(2023));
        // @formatter:ofn
    }

    // 2. 测试 listAllPros() 在提供 name 参数时
    @Test
    void testGetProByNameNotExists() {
        // @formatter:off
        given()
            .queryParam("name", "Test Pro")
            .contentType(ContentType.JSON)
            .when()
            .get("/pro")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo("1404"))
            .body("msg", equalTo("找不到对象"))
            .body("data", anEmptyMap());
        // @formatter:on
    }

    // 3. 测试 getProById() 返回单个 ProDTO
    @Test
    void testGetProById() {
        // @formatter:off
        given()
            .pathParam("id", 1)
            .contentType(ContentType.JSON)
        .when()
            .get("/pro/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo("0"))
            .body("msg", equalTo("ok"))
            .body("data.proName", equalTo("Test Pro 1"))
            .body("data.teamName", equalTo("Test Team A"))
            .body("data.org", equalTo("Test Org 1"))
            .body("data.id", equalTo(1))
            .body("data.birth",equalTo("1990-01-01"))
            .body("data.birthPlace",equalTo("City A"))
            .body("data.proYear", equalTo(2023));
    }

}
