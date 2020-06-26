package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.AdminTestService;
import org.sinnergia.sinnergia.spring.config.ApiTestConfig;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@ApiTestConfig
class ArticleResourceTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private AdminTestService adminTestService;

    @Test
    void testCreateArticle() {

        this.adminTestService.login(webTestClient)
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromMultipartData(this.buildFormDataforCreate("TestName", 17, 14, null)))
                .exchange()
                .expectStatus().isOk();
        this.deleteAll();
    }

    @Test
    void testCreateArticleWithBadRequestException(){
        webTestClient
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromMultipartData(this.buildFormDataforCreate(null, 14, 17, null)))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testReadAllArticles() {
        this.adminTestService.login(webTestClient)
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromMultipartData(this.buildFormDataforCreate("testingCreateArticle", 14, 17, null) ))
                .exchange()
                .expectStatus().isOk();
        this.adminTestService.login(webTestClient)
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromMultipartData(this.buildFormDataforCreate("testingCreateArticle", 14, 17, null) ))
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
        this.adminTestService.login(webTestClient)
                .post().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromMultipartData(this.buildFormDataforCreate("testingUpdatingArticle", 14, 17, null) ))
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
        this.adminTestService.login(webTestClient)
                .put().uri(ArticleResource.ARTICLE)
                .body(BodyInserters.fromMultipartData(buildFormDataforUpdate(articleBasicDto, null)))
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
            this.adminTestService.login(webTestClient)
                    .delete().uri(ArticleResource.ARTICLE + "/" + article.getId())
                    .exchange()
                    .expectStatus().isOk();
        }
    }

    private MultiValueMap<String, HttpEntity<?>> buildFormDataforCreate(String name, double price, int stock, MultipartFile file){
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        if(name != null){
            builder.part("name", name);
        }
        builder.part("price", new BigDecimal(price));
        builder.part("stock", stock);
        if(file != null){
            builder.part("file", file);
        }
        return builder.build();
    }

    private MultiValueMap<String, HttpEntity<?>> buildFormDataforUpdate(ArticleBasicDto articleBasicDto, MultipartFile file){
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        if(articleBasicDto.getName() != null){
            builder.part("name", articleBasicDto.getName());
        }
        builder.part("id", articleBasicDto.getId());
        builder.part("price", articleBasicDto.getPrice());
        builder.part("stock", articleBasicDto.getStock());
        builder.part("description", articleBasicDto.getDescription());
        if(file != null){
            builder.part("file", file);
        }
        return builder.build();
    }


}
