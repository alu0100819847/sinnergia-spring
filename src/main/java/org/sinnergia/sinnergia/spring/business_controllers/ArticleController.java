package org.sinnergia.sinnergia.spring.business_controllers;


import org.sinnergia.sinnergia.spring.documents.Article;
import org.sinnergia.sinnergia.spring.documents.Category;
import org.sinnergia.sinnergia.spring.dto.ArticleBasicDto;
import org.sinnergia.sinnergia.spring.dto.ArticleCreateDto;
import org.sinnergia.sinnergia.spring.dto.ArticleUpdateDto;
import org.sinnergia.sinnergia.spring.exceptions.NotFoundException;
import org.sinnergia.sinnergia.spring.exceptions.UnsupportedExtension;
import org.sinnergia.sinnergia.spring.repositories.ArticleRepository;
import org.sinnergia.sinnergia.spring.repositories.CategoryRepository;
import org.sinnergia.sinnergia.spring.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ArticleController {

    private ArticleRepository articleRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    public Mono<Void> createArticle(ArticleCreateDto articleCreateDto) {

        Mono<Article> articleMono = this.searchCategory(articleCreateDto.getCategoryId())
                .map(category -> {
                    Article article = this.buildArticle(articleCreateDto);
                    article.setCategory(category);
                    return article;
                })
                .switchIfEmpty(Mono.just(this.buildArticle(articleCreateDto)));
        return this.articleRepository.saveAll(articleMono).then();
    }

    public Flux<ArticleBasicDto> readAllArticles() {

        return this.articleRepository.findAll().map(article -> {
            Path path = Paths.get(ImageService.defaultImageUri);
            if(!article.getImageName().equals("")){
                path = Paths.get(article.getImageName());
            }
            Resource resource = new FileSystemResource(path.toString());
            ArticleBasicDto articleBasicDto = new ArticleBasicDto(article.getId(), article.getName(), article.getPrice(), article.getStock(), article.getDescription(), article.getCategory());
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

    public Mono<Void> update(ArticleUpdateDto articleUpdateDto) {
        Mono<Article> updateArticle = this.articleRepository.findById(articleUpdateDto.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Article id " + articleUpdateDto.getId())))
                .map(article -> {
                    article.setName(articleUpdateDto.getName());
                    article.setPrice(articleUpdateDto.getPrice());
                    article.setStock(articleUpdateDto.getStock());
                    article.setDescription(articleUpdateDto.getDescription());
                    article.setCategory(null);
                    return article;
                });
        Mono<Category> categoryMono = this.searchCategory(articleUpdateDto.getCategoryId());
        updateArticle = Flux.combineLatest(categoryMono, updateArticle, (category, article) -> {
            article.setCategory(category);
            return article;
        }).next().switchIfEmpty(updateArticle);

        return this.articleRepository.saveAll(updateArticle).then();
    }

    public Mono<Void> updateWithImage(ArticleUpdateDto articleUpdateDto, MultipartFile file) {
        Mono<Article> updateArticle = this.articleRepository.findById(articleUpdateDto.getId())
                .switchIfEmpty(Mono.error(new NotFoundException("Article id " + articleUpdateDto.getId())))
                .map(article -> {
                    article.setName(articleUpdateDto.getName());
                    article.setPrice(articleUpdateDto.getPrice());
                    article.setStock(articleUpdateDto.getStock());
                    article.setDescription(articleUpdateDto.getDescription());
                    ImageService imageService = new ImageService();
                    String src = imageService.getPath(article.getId(), file.getOriginalFilename());
                    article.setImage(src);
                    try{
                        imageService.saveImage(file);
                    } catch (IOException error){
                        throw new UnsupportedExtension("No se ha podido procesar la imagen.");
                    }
                    return article;
                });
        Mono<Category> categoryMono = this.searchCategory(articleUpdateDto.getCategoryId());
        updateArticle = Flux.combineLatest(categoryMono, updateArticle, (category, article) -> {
            article.setCategory(category);
            return article;
        }).next().switchIfEmpty(updateArticle);
        return this.articleRepository.saveAll(updateArticle).then();
    }

    public Mono<Void> delete(String id) {
        return this.articleRepository.deleteById(id);
    }

    public Mono<Void> createArticleImage(ArticleCreateDto articleCreateDto, MultipartFile file) {
        ImageService imageService = new ImageService();
        Mono<Article> articleMono = this.searchCategory(articleCreateDto.getCategoryId())
                .map(category -> {
                    Article article = this.buildArticle(articleCreateDto);
                    article.setCategory(category);
                    return article;
                })
                .switchIfEmpty(Mono.just(this.buildArticle(articleCreateDto)));

        Mono<Article> articleMonoComplete = this.articleRepository.saveAll(articleMono).map(articleSaved -> {
            String src = imageService.getPath(articleSaved.getId(), file.getOriginalFilename());
            articleSaved.setImage(src);
            try{
                imageService.saveImage(file);
            } catch (IOException error){
                throw new UnsupportedExtension("No se ha podido procesar la imagen.");
            }
            return articleSaved;
        }).next();

        return this.articleRepository.saveAll(articleMonoComplete).then();
    }

    private Mono<Category> searchCategory(String categoryId){
        return this.categoryRepository.findById(categoryId);
    }

    private Article buildArticle(ArticleCreateDto articleCreateDto){
        Article article = new Article(articleCreateDto.getName(), articleCreateDto.getPrice(), articleCreateDto.getDescription());
        article.setStock(articleCreateDto.getStock());
        return article;
    }

    public Flux<ArticleBasicDto> readByCategory(String category) {
        Mono<Category> categoryMono = this.categoryRepository.findOneByName(category);
        return this.articleRepository.findAllByCategory(categoryMono).map(article -> {
            Path path = Paths.get(ImageService.defaultImageUri);
            if(!article.getImageName().equals("")){
                path = Paths.get(article.getImageName());
            }
            Resource resource = new FileSystemResource(path.toString());

            ArticleBasicDto articleBasicDto = new ArticleBasicDto(article.getId(), article.getName(), article.getPrice(), article.getStock(), article.getDescription(), article.getCategory());
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
}



