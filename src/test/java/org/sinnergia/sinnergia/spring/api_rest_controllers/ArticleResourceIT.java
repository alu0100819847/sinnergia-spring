package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.ApiTestConfig;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;

import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;

@ApiTestConfig
public class ArticleResourceIT {

    @Autowired
    private WebTestClient webTestClient;


/*
    @Test
    void testCreateArticle() throws IOException {
        MultipartFile image = new MockMultipartFile("image", "some xml".getBytes());
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("name", "TestName");
        builder.part("price", new BigDecimal(17));
        builder.part("stock", 14);
        builder.part("image", image);

        webTestClient
                .post().uri(ArticleResource.ARTICLE)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isOk();

    }

    */
    @Test
    void testCreateArticle() {
        webTestClient
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromValue(new ArticleCreateDto("testingCreateArticle", new BigDecimal(14), 17) ))
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void testCreateArticleWithBadRequestException(){
        webTestClient
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromValue(new ArticleCreateDto(null, new BigDecimal(14), 17) ))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testReadAllArticles() {
        webTestClient
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromValue(new ArticleCreateDto("testingCreateArticle", new BigDecimal(14), 17) ))
                .exchange()
                .expectStatus().isOk();
        webTestClient
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromValue(new ArticleCreateDto("testingCreateArticle", new BigDecimal(14), 17) ))
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .get().uri(ArticleResource.ARTICLE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ArticleBasicDto.class)
                .hasSize(2);

    }
}
