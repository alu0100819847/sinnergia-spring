package org.sinnergia.sinnergia.spring.business_controllers;

import org.sinnergia.sinnergia.spring.documents.Article;
import org.sinnergia.sinnergia.spring.documents.User;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.UserLandingDto;
import org.sinnergia.sinnergia.spring.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ArticleController {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }


    public Mono<ArticleBasicDto> createArticle(ArticleBasicDto articleBasicDto){
        Article article = new Article();
        article.setName(articleBasicDto.getName());
        article.setPrice(articleBasicDto.getPrice());

        return this.articleRepository.save(article).map(newArticle -> new ArticleBasicDto(newArticle.getName(), newArticle.getPrice(), newArticle.getStock()));
    }

}
