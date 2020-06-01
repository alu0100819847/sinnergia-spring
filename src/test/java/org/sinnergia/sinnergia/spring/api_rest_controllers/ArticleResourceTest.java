package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.ApiTestConfig;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;

@ApiTestConfig
class ArticleResourceTest {

    @Autowired
    private WebTestClient webTestClient;

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
