package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.ApiTestConfig;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.sinnergia.sinnergia.spring.dto.UserAdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

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
        this.deleteAll();
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
        this.deleteAll();
    }

    @Test
    void testUpdateArticle(){
        webTestClient
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromValue(new ArticleCreateDto("testingUpdatingArticle", new BigDecimal(14), 17) ))
                .exchange()
                .expectStatus().isOk();

        ArticleBasicDto articleBasicDto =
                webTestClient
                        .get().uri(ArticleResource.ARTICLE)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(ArticleBasicDto.class)
                        .returnResult()
                        .getResponseBody().get(0);
        assertNotNull(articleBasicDto);

        articleBasicDto.setDescription("Esto es un test.");
        webTestClient
                .put().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromValue(articleBasicDto))
                .exchange()
                .expectStatus().isOk();

        articleBasicDto =
                webTestClient
                        .get().uri(ArticleResource.ARTICLE)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(ArticleBasicDto.class)
                        .returnResult()
                        .getResponseBody().get(0);

        assertNotNull(articleBasicDto);
        assertEquals("Esto es un test.", articleBasicDto.getDescription());
        this.deleteAll();
    }

    void deleteAll(){
        List<ArticleBasicDto> articleBasicDto =
                webTestClient
                        .get().uri(ArticleResource.ARTICLE)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(ArticleBasicDto.class)
                        .returnResult()
                        .getResponseBody();
        assertNotNull(articleBasicDto);
        for(ArticleBasicDto article : articleBasicDto){
            webTestClient
                    .delete().uri(ArticleResource.ARTICLE + "/" + article.getId())
                    .exchange()
                    .expectStatus().isOk();
        }
    }
}
