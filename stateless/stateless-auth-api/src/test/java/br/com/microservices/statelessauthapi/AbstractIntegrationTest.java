package br.com.microservices.statelessauthapi;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = StatelessAuthApiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:tc:postgres:16.2-alpine3.19:///auth-db" })
@ActiveProfiles("test")
abstract class AbstractIntegrationTest {

    static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16.2-alpine3.19")
            .withDatabaseName("auth-db")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        postgresContainer.start();
        registry.add("DB_HOST", postgresContainer::getHost);
        registry.add("DB_PORT", postgresContainer::getFirstMappedPort);
        registry.add("DB_USER", postgresContainer::getUsername);
        registry.add("DB_PASSWORD", postgresContainer::getPassword);
    }
}