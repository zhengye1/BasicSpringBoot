package com.itsp.basicspring.integration;

import com.itsp.basicspring.dao.ProMapper;
import com.itsp.basicspring.dao.TeamMapper;
import com.itsp.basicspring.model.Pro;
import com.itsp.basicspring.model.Team;
import com.itsp.basicspring.service.ProService;
import com.itsp.basicspring.service.TeamService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class ProControllerIntegrationTest {
    @LocalServerPort
    private Integer port;

    @Container
    private static MySQLContainer<?> mysqlContainer =
        new MySQLContainer<>("mysql:8.0").withDatabaseName("testdb").withUsername("test").withPassword("test");

    @DynamicPropertySource
    public static void overrideDataSourceProperties(DynamicPropertyRegistry registry) {
        // 动态设置数据库连接属性
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @Autowired
    private ProMapper proMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:" + port;
        List<Pro> pros = proMapper.selectList(null);
        List<Team> teams = teamMapper.selectList(null);
        System.out.println("Pro records loaded from data.sql: " + pros.size());
        System.out.println("Team records loaded from data.sql: " + teams.size());
    }

    @Test
    void testListAllPros() {
        // @formatter:off
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/pro")
        .then()
            .statusCode(200)
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

}
