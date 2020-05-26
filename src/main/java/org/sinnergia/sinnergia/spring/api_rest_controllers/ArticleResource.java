package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.sinnergia.sinnergia.spring.business_controllers.ArticleController;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequestMapping(ArticleResource.ARTICLE)
public class ArticleResource {
    public static final String ARTICLE ="/article";
    private ArticleController articleController;

    @Autowired
    public ArticleResource(ArticleController articleController){
        this.articleController = articleController;
    }

    @PostMapping
    public Mono<Void> createArticle(@Valid @RequestBody ArticleCreateDto articleCreateDto) {
        return this.articleController.createArticle(articleCreateDto);
    }

    @GetMapping
    public Flux<ArticleBasicDto> getArticles() {
        return this.articleController.readAllArticles();
    }

}
