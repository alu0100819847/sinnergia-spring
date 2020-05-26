package org.sinnergia.sinnergia.spring.business_controllers;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.sinnergia.sinnergia.spring.documents.Article;
import org.sinnergia.sinnergia.spring.documents.User;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.sinnergia.sinnergia.spring.repositories.ArticleRepository;
import org.sinnergia.sinnergia.spring.services.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.function.Function;

@Controller
public class ArticleController {

    private ArticleRepository articleRepository;


    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Mono<Void> createArticle(ArticleCreateDto articleCreateDto, Mono<FilePart> part) {

        return part.map(file ->{

                    Mono<Article> articleMono =
                            file.content().map(dataBuffer -> {
                                Article article = new Article();
                                article.setName(articleCreateDto.getName());
                                article.setPrice(articleCreateDto.getPrice());
                                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(bytes);
                                article.setImage(new Binary(BsonBinarySubType.BINARY, bytes));

                                return article;
                            }).next();
                            return this.articleRepository.saveAll(articleMono);
                        }).then();
    }

    public Flux<ArticleBasicDto> readAllArticles() {
        return this.articleRepository.findAll().map(article -> new ArticleBasicDto(article.getName(), article.getPrice(), article.getStock()));
    }
}
