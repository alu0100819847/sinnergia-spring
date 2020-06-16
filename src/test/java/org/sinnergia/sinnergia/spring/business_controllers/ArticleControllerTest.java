package org.sinnergia.sinnergia.spring.business_controllers;

import org.junit.jupiter.api.Test;
import org.sinnergia.sinnergia.spring.config.TestConfig;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@TestConfig
public class ArticleControllerTest {

    @Autowired
    private ArticleController articleController;


    @Test
    void createArticle(){
        MultipartFile image = new MockMultipartFile("image","estoEsUnTest.png", null, "some xml".getBytes());
        StepVerifier
                .create(this.articleController.createArticleImage(new ArticleCreateDto("articulo", new BigDecimal(0), 0, "desc"), image))
                .expectComplete()
                .verify();
    }

    @Test
    void createArticleWithUnsupportedExtension(){
        MultipartFile image = new MockMultipartFile("image","estoEsUnTest.txt", null, "some xml".getBytes());
        StepVerifier
                .create(this.articleController.createArticleImage(new ArticleCreateDto("articulo", new BigDecimal(0), 0, "desc"), image))
                .expectErrorMatches( err -> {
                    assertEquals("Unsupported Extension (415). txt extension is not supported.", err.getMessage());
                    return true;
                })
                .verify();
    }
}
