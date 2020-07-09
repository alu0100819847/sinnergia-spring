package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.AdminTestService;
import org.sinnergia.sinnergia.spring.config.ApiTestConfig;
import org.sinnergia.sinnergia.spring.dto.CategoryCreateDto;
import org.sinnergia.sinnergia.spring.dto.CategoryDto;
import org.sinnergia.sinnergia.spring.dto.CategoryUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import static org.junit.Assert.assertEquals;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@ApiTestConfig
public class CategoryResourceTest {

    @Autowired
    private AdminTestService adminTestService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateCategory() {
        this.adminTestService.login(webTestClient)
                .post().uri(CategoryResource.CATEGORY)
                .body(BodyInserters.fromValue(new CategoryCreateDto(null, "testCategory")))
                .exchange()
                .expectStatus().isOk();
        deleteAll();
    }

    @Test
    void testReadAllCategories(){
        this.adminTestService.login(webTestClient)
                .post().uri(CategoryResource.CATEGORY)
                .body(BodyInserters.fromValue(new CategoryCreateDto(null, "testReadAll")))
                .exchange()
                .expectStatus().isOk();

        this.adminTestService.login(webTestClient)
                .post().uri(CategoryResource.CATEGORY)
                .body(BodyInserters.fromValue(new CategoryCreateDto(null, "testReadAllCategories")))
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .get().uri(CategoryResource.CATEGORY)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CategoryDto.class)
                .hasSize(2);
        deleteAll();
    }

    @Test
    void testEditCategory(){
        this.adminTestService.login(webTestClient)
                .post().uri(CategoryResource.CATEGORY)
                .body(BodyInserters.fromValue(new CategoryCreateDto(null, "testEditCategory")))
                .exchange()
                .expectStatus().isOk();

        CategoryDto categoryDto =
                webTestClient
                        .get().uri(CategoryResource.CATEGORY)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(CategoryDto.class)
                        .returnResult()
                        .getResponseBody().get(0);

        assertNotNull(categoryDto);
        categoryDto.setName("Name changed");

        this.adminTestService.login(webTestClient)
                .put().uri(CategoryResource.CATEGORY)
                .body(BodyInserters.fromValue(new CategoryUpdateDto(null, categoryDto.getName(), categoryDto.getId())))
                .exchange()
                .expectStatus().isOk();

        categoryDto =
                webTestClient
                        .get().uri(CategoryResource.CATEGORY)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(CategoryDto.class)
                        .returnResult()
                        .getResponseBody().get(0);

        assertEquals("Name changed", categoryDto.getName());
        deleteAll();
    }

    @Test
    void testDeleteArticle(){
        this.adminTestService.login(webTestClient)
                .post().uri(CategoryResource.CATEGORY)
                .body(BodyInserters.fromValue(new CategoryCreateDto(null, "testDeleteCategory")))
                .exchange()
                .expectStatus().isOk();

        CategoryDto categoryDto =
                webTestClient
                        .get().uri(CategoryResource.CATEGORY)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(CategoryDto.class)
                        .returnResult()
                        .getResponseBody().get(0);

        this.adminTestService.login(webTestClient)
                .delete().uri(CategoryResource.CATEGORY + "/" + categoryDto.getId())
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .get().uri(CategoryResource.CATEGORY)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CategoryDto.class)
                .hasSize(0);
    }

    void deleteAll(){
        List<CategoryDto> categoryDto =
                webTestClient
                        .get().uri(CategoryResource.CATEGORY)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(CategoryDto.class)
                        .returnResult()
                        .getResponseBody();
        assertNotNull(categoryDto);
        for(CategoryDto category : categoryDto){
            this.adminTestService.login(webTestClient)
                    .delete().uri(CategoryResource.CATEGORY + "/" + category.getId())
                    .exchange()
                    .expectStatus().isOk();
        }
    }
}
