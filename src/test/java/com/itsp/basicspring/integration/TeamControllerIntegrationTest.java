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
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class TeamControllerIntegrationTest {
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

    // 1. 测试 listAllTeams() 正常返回的情况
    @Test
    void testListAllTeams() {
        // @formatter:off
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/team")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code",equalTo("0"))
            .body("msg", equalTo("ok"))
            .body("data.size()", equalTo(9))
            .body("data[0].id", equalTo(1))
            .body("data[0].name", equalTo("Test Team A"))
            .body("data[0].pros.size()", equalTo(4))
            .body("data[0].pros[0].proName", equalTo("Test Pro 1"))
            .body("data[0].pros[1].proName", equalTo("Test Pro 2"))
            .body("data[0].pros[2].proName", equalTo("Test Pro 3"))
            .body("data[0].pros[3].proName", equalTo("Test Pro 4"))
            .body("data[1].id", equalTo(2))
            .body("data[1].name", equalTo("Test Team B"))
            .body("data[1].pros.size()", equalTo(4))
            .body("data[1].pros[0].proName", equalTo("Test Pro 5"))
            .body("data[1].pros[1].proName", equalTo("Test Pro 6"))
            .body("data[1].pros[2].proName", equalTo("Test Pro 7"))
            .body("data[1].pros[3].proName", equalTo("Test Pro 8"));
        // @formatter:on
    }
}
