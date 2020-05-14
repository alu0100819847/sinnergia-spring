package org.sinnergia.sinnergia.spring.api_rest_controllers;

import org.sinnergia.sinnergia.spring.business_controllers.ArticleController;
import org.sinnergia.sinnergia.spring.business_controllers.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ArticleResource.ARTICLE)
public class ArticleResource {
    public static final String ARTICLE ="/article";
    private ArticleController articleController;

    @Autowired
    public ArticleResource(ArticleController articleController){
        this.articleController = articleController;
    }
}
