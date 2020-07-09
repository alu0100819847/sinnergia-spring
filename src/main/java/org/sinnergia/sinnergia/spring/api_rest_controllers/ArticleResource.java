package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.sinnergia.sinnergia.spring.business_controllers.ArticleController;
import org.sinnergia.sinnergia.spring.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequestMapping(ArticleResource.ARTICLE)

public class ArticleResource {
    public static final String ARTICLE ="/article";
    private static final String ID = "/{id}";
    private static final String CATEGORY = "/{category}";
    private ArticleController articleController;

    @Autowired
    public ArticleResource(ArticleController articleController){
        this.articleController = articleController;
    }

    @GetMapping
    public Flux<ArticleBasicDto> getArticles() {
        return this.articleController.readAllArticles();
    }

    @GetMapping(value= CATEGORY)
    public Flux<ArticleBasicDto> getArticlesByCategory(@PathVariable String category) {
        return this.articleController.readByCategory(category);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> updateArticleImage(@Valid @ModelAttribute ArticleUpdateImageDto articleUpdateImageDto){
        if(articleUpdateImageDto.getFile() != null){
            return this.articleController.updateWithImage(new ArticleUpdateDto(articleUpdateImageDto.getId(), articleUpdateImageDto.getName(), articleUpdateImageDto.getPrice(), articleUpdateImageDto.getStock(), articleUpdateImageDto.getDescription(), articleUpdateImageDto.getCategoryId()), articleUpdateImageDto.getFile());
        }
        return this.articleController.update(new ArticleUpdateDto(articleUpdateImageDto.getId(), articleUpdateImageDto.getName(), articleUpdateImageDto.getPrice(), articleUpdateImageDto.getStock(), articleUpdateImageDto.getDescription(), articleUpdateImageDto.getCategoryId()));

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> createArticle(@Valid @ModelAttribute ArticleCreateImageDto articleCreateImageDto) {
        if(articleCreateImageDto.getFile() != null){
            return this.articleController.createArticleImage(new ArticleCreateDto(articleCreateImageDto.getName(), articleCreateImageDto.getPrice(), articleCreateImageDto.getStock(), articleCreateImageDto.getDescription(), articleCreateImageDto.getCategoryId()), articleCreateImageDto.getFile());
        }
        return this.articleController.createArticle(new ArticleCreateDto(articleCreateImageDto.getName(), articleCreateImageDto.getPrice(), articleCreateImageDto.getStock(), articleCreateImageDto.getDescription(), articleCreateImageDto.getCategoryId()));

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value= ID)
    public Mono<Void> deleteArticle(@PathVariable String id){
        return this.articleController.delete(id);
    }


}
