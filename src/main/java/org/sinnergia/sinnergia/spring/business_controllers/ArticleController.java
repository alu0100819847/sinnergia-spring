package org.sinnergia.sinnergia.spring.business_controllers;


import org.sinnergia.sinnergia.spring.documents.Article;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.sinnergia.sinnergia.spring.exceptions.CredentialException;
import org.sinnergia.sinnergia.spring.exceptions.NotFoundException;
import org.sinnergia.sinnergia.spring.exceptions.UnsupportedExtension;
import org.sinnergia.sinnergia.spring.repositories.ArticleRepository;
import org.sinnergia.sinnergia.spring.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ArticleController {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Mono<Void> createArticle(ArticleCreateDto articleCreateDto) {
        Article article = new Article(articleCreateDto.getName(), articleCreateDto.getPrice(), articleCreateDto.getDescription());
        article.setStock(articleCreateDto.getStock());
        return this.articleRepository.save(article).then();
    }

    public Flux<ArticleBasicDto> readAllArticles() {

        return this.articleRepository.findAll().map(article -> {
            Path path = Paths.get(ImageService.defaultImageUri);
            if(!article.getImageName().equals("")){
                path = Paths.get(article.getImageName());
            }
            Resource resource = new FileSystemResource(path.toString());

            ArticleBasicDto articleBasicDto = new ArticleBasicDto(article.getId(), article.getName(), article.getPrice(), article.getStock(), article.getDescription());
            if(resource.exists()) {
                try {
                    articleBasicDto.setFile(Files.readAllBytes(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                path = Paths.get(ImageService.defaultImageUri);
                try {
                    articleBasicDto.setFile(Files.readAllBytes(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return articleBasicDto;
        });
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

    public Mono<Void> createArticleImage(ArticleCreateDto articleCreateDto, MultipartFile file) {
        ImageService imageService = new ImageService();
        Article article = new Article(articleCreateDto.getName(), articleCreateDto.getPrice(), articleCreateDto.getDescription());
        article.setStock(articleCreateDto.getStock());
        Mono<Article> monoArticle = this.articleRepository.save(article).map(articleSaved -> {
            String src = imageService.getPath(articleSaved.getId(), file.getOriginalFilename());
            articleSaved.setImage(src);
            try{
                imageService.saveImage(file);
            } catch (IOException error){
                throw new UnsupportedExtension("No se ha podido procesar la imagen.");
            }
            return article;
        });

        return this.articleRepository.saveAll(monoArticle).then();
    }

}
