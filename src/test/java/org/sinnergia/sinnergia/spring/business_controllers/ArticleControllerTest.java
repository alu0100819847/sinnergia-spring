package org.sinnergia.sinnergia.spring.business_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.TestConfig;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.sinnergia.sinnergia.spring.dto.ArticleUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@TestConfig
class ArticleControllerTest {

    @Autowired
    private ArticleController articleController;


    @Test
    void createArticle(){
        MultipartFile image = new MockMultipartFile("image","estoEsUnTest.png", null, "some xml".getBytes());
        StepVerifier
                .create(this.articleController.createArticleImage(new ArticleCreateDto("articulo", new BigDecimal(0), 0, "desc", ""), image))
                .expectComplete()
                .verify();
        this.deleteAll();
    }

    @Test
    void createArticleWithUnsupportedExtension(){
        MultipartFile image = new MockMultipartFile("image","estoEsUnTest.txt", null, "some xml".getBytes());
        StepVerifier
                .create(this.articleController.createArticleImage(new ArticleCreateDto("articulo", new BigDecimal(0), 0, "desc", ""), image))
                .expectErrorMatches( err -> {
                    assertEquals("Unsupported Extension (415). txt extension is not supported.", err.getMessage());
                    return true;
                })
                .verify();
    }

    @Test
    void updateArticle(){
        MultipartFile image = new MockMultipartFile("image","estoEsUnTest.png", null, "some xml".getBytes());
        StepVerifier
                .create(this.articleController.createArticleImage(new ArticleCreateDto("articulo", new BigDecimal(0), 0, "desc", ""), image))
                .expectComplete()
                .verify();

        ArticleBasicDto articleBasicDto = this.articleController.readAllArticles().blockFirst();
        articleBasicDto.setName("prueba de update");

        StepVerifier
                .create(this.articleController.update(new ArticleUpdateDto(articleBasicDto.getId(), articleBasicDto.getName(), articleBasicDto.getPrice(), articleBasicDto.getStock(), articleBasicDto.getDescription(), "category")))
                .expectComplete()
                .verify();
        ArticleBasicDto articleBasicDto2 = this.articleController.readAllArticles().blockFirst();
        assertNotNull(articleBasicDto2);
        assertEquals("prueba de update", articleBasicDto2.getName());
        this.deleteAll();
    }

    @Test
    void updateArticleWithImage(){
        MultipartFile image = new MockMultipartFile("image","estoEsUnTest.png", null, "some xml".getBytes());
        StepVerifier
                .create(this.articleController.createArticleImage(new ArticleCreateDto("articulo", new BigDecimal(0), 0, "desc", ""), image))
                .expectComplete()
                .verify();

        ArticleBasicDto articleBasicDto = this.articleController.readAllArticles().blockFirst();
        articleBasicDto.setName("prueba de update");
        MultipartFile image2 = new MockMultipartFile("otherImage","estoEsUnTest.png", null, "some xml".getBytes());

        StepVerifier
                .create(this.articleController.updateWithImage(new ArticleUpdateDto(articleBasicDto.getId(), articleBasicDto.getName(), articleBasicDto.getPrice(), articleBasicDto.getStock(), articleBasicDto.getDescription(), ""), image2))
                .expectComplete()
                .verify();
        ArticleBasicDto articleBasicDto2 = this.articleController.readAllArticles().blockFirst();
        assertNotNull(articleBasicDto2);
        assertEquals("prueba de update", articleBasicDto2.getName());
        this.deleteAll();
    }

    private void deleteAll(){
        this.articleController.readAllArticles().subscribe( articleBasicDto -> {
            this.articleController.delete(articleBasicDto.getId());
        });
    }

}
