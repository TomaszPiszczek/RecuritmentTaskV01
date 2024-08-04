package com.example.recuritmenttaskv01.integrationTest;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = com.example.recruitmenttaskv01.RecruitmentTaskV01Application.class)
public class RepositoryControllerResponseMessageTest {

    @Autowired
    private WebTestClient webTestClient;

    private static MockWebServer mockWebServer;

    @DynamicPropertySource
    static void overrideWebClientBaseUrl(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("github_base_url", () -> mockWebServer.url("/").toString());
    }

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void stopMockWebServer() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void repositoryControllerShouldReturnUserNotFoundBodyWithStatus404WhileUserDontExist() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setHeader("Content-Type", "application/json")
                .setBody("{ \"status\": \"404 NOT_FOUND\", \"message\": \"User not found\" }")
        );

        this.webTestClient
                .get()
                .uri("/api/repositories/UserName")
                .header("accept", "application/json")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json("{ \"status\": \"404 NOT_FOUND\", \"message\": \"User not found\" }");
    }

    @Test
    public void repositoryControllerShouldReturn406WhenHeaderIsNotProvided() {
        this.webTestClient
                .get()
                .uri("/api/repositories/UserName")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .json("{ \"status\": \"406 NOT_ACCEPTABLE\", \"message\": \"Unsupported media type. Please specify Accept: application/json in your request header.\" }");
    }
}
