package com.atomicjar.todos;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Start Spring Boot backend
@Testcontainers
public class TodoIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withDatabaseName("postgres");

    @BeforeAll
    static void setUp() {
        postgreSQLContainer.start();
        seedDatabase();
        System.setProperty("DB_URL", postgreSQLContainer.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgreSQLContainer.getUsername());
        System.setProperty("DB_PASSWORD", postgreSQLContainer.getPassword());
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    private static void seedDatabase() {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS todos (id SERIAL PRIMARY KEY, title VARCHAR(255), completed BOOLEAN)";
            try (PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL)) {
                createTableStatement.execute();
            }

            String seedDataSQL = "INSERT INTO todos (title, completed) VALUES (?, ?)";
            try (PreparedStatement seedDataStatement = connection.prepareStatement(seedDataSQL)) {
                seedDataStatement.setString(1, "Learn Testcontainers");
                seedDataStatement.setBoolean(2, false);
                seedDataStatement.execute();

                seedDataStatement.setString(1, "Write a blog post");
                seedDataStatement.setBoolean(2, false);
                seedDataStatement.execute();

                seedDataStatement.setString(1, "Publish on Dev.to");
                seedDataStatement.setBoolean(2, true);
                seedDataStatement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error seeding the database", e);
        }
    }

    @Test
    void testGetTodos() {
        // Example test for your Spring Boot application
        // Use a test HTTP client to send a GET request to the endpoint and verify response
        String url = "/todos"; // Replace with your endpoint
        // Use RestTemplate, WebTestClient, or another client to make HTTP requests
    }
}

