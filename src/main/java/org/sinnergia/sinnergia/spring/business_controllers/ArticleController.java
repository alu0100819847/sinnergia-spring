package org.sinnergia.sinnergia.spring.business_controllers;

import org.sinnergia.sinnergia.spring.documents.Article;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.sinnergia.sinnergia.spring.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Controller
public class ArticleController {

    private ArticleRepository articleRepository;


    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

/*
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

*/


    public Mono<Void> createArticle(ArticleCreateDto articleCreateDto) {
        Article article = new Article();
        article.setName(articleCreateDto.getName());
        article.setPrice(articleCreateDto.getPrice());
        return this.articleRepository.save(article).then();
    }

    public Flux<ArticleBasicDto> readAllArticles() {
        return this.articleRepository.findAll().map(article -> new ArticleBasicDto(article.getName(), article.getPrice(), article.getStock()));
    }
}
