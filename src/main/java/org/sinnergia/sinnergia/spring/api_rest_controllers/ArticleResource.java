package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.sinnergia.sinnergia.spring.business_controllers.ArticleController;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping(ArticleResource.ARTICLE)
public class ArticleResource {
    public static final String ARTICLE ="/article";
    private static final String ID = "/{id}";
    private ArticleController articleController;

    @Autowired
    public ArticleResource(ArticleController articleController){
        this.articleController = articleController;
    }

    @GetMapping
    public Flux<ArticleBasicDto> getArticles() {
        return this.articleController.readAllArticles();
    }

    @PutMapping
    public Mono<Void> updateUser(@Valid @RequestBody ArticleBasicDto articleBasicDto){
        return this.articleController.update(articleBasicDto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> createArticle(@Valid @ModelAttribute ArticleCreateImageDto articleCreateImageDto) {
        if(articleCreateImageDto.getFile() != null){
            return this.articleController.createArticleImage(new ArticleCreateDto(articleCreateImageDto.getName(), articleCreateImageDto.getPrice(), articleCreateImageDto.getStock(), articleCreateImageDto.getDescription()), articleCreateImageDto.getFile());
        }
        return this.articleController.createArticle(new ArticleCreateDto(articleCreateImageDto.getName(), articleCreateImageDto.getPrice(), articleCreateImageDto.getStock(), articleCreateImageDto.getDescription()));
    }

    @DeleteMapping(value= ID)
    public Mono<Void> deleteUser(@PathVariable String id){
        return this.articleController.delete(id);
    }


}
