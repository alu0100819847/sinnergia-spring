package org.sinnergia.sinnergia.spring.business_controllers;

import org.sinnergia.sinnergia.spring.documents.Article;
import org.sinnergia.sinnergia.spring.documents.User;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.sinnergia.sinnergia.spring.exceptions.NotFoundException;
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

    public Mono<Void> createArticle(ArticleCreateDto articleCreateDto) {
        Article article = new Article(articleCreateDto.getName(), articleCreateDto.getPrice());
        return this.articleRepository.save(article).then();
    }

    public Flux<ArticleBasicDto> readAllArticles() {
        return this.articleRepository.findAll().map(article -> new ArticleBasicDto(article.getId(), article.getName(), article.getPrice(), article.getStock(), article.getDescription()));
    }

    public Mono<Void> update(ArticleBasicDto articleBasicDto) {
        Mono<Article> updateArticle = this.articleRepository.findById(articleBasicDto.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Article id " + articleBasicDto.getId())))
                .map(article -> {
                    article.setName(articleBasicDto.getName());
                    article.setPrice(articleBasicDto.getPrice());
                    article.setStock(articleBasicDto.getStock());
                    article.setDescription(articleBasicDto.getDescription());
                    return article;
                });
        return this.articleRepository.saveAll(updateArticle).then();
    }

    public Mono<Void> delete(String id) {
        return this.articleRepository.deleteById(id);
    }
}
