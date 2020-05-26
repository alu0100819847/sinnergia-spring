package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.sinnergia.sinnergia.spring.business_controllers.ArticleController;
import org.sinnergia.sinnergia.spring.business_controllers.UserController;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping(ArticleResource.ARTICLE)
public class ArticleResource {
    public static final String ARTICLE ="/article";
    private ArticleController articleController;

    @Autowired
    public ArticleResource(ArticleController articleController){
        this.articleController = articleController;
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> createArticle(@Valid @RequestParam String name,@RequestParam BigDecimal price,@RequestParam Integer stock, @RequestPart("file") Mono<FilePart> part ) throws IOException {
        return this.articleController.createArticle(new ArticleCreateDto(name, price, stock), part);
    }

    @GetMapping
    public Flux<ArticleBasicDto> getArticles() {
        return this.articleController.readAllArticles();
    }

}
