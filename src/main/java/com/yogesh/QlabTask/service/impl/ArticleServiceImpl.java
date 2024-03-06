package com.yogesh.QlabTask.service.impl;

import com.yogesh.QlabTask.entity.Article;
import com.yogesh.QlabTask.entity.User;
import com.yogesh.QlabTask.payloads.ArticleDto;
import com.yogesh.QlabTask.repository.ArticleRepository;
import com.yogesh.QlabTask.repository.UserRepository;
import com.yogesh.QlabTask.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public ArticleDto create(ArticleDto articleDto, String username) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());

        // Retrieve the user by username
        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        article.setUser(user);

        // Save the article
        article = articleRepository.save(article);

        // Map the saved Article entity back to ArticleDto
        return mapArticleToDto(article);
    }
    @Override
    public List<ArticleDto> getAll() {
        // Retrieve all articles from the database
        List<Article> articles = articleRepository.findAll();

        // Map the list of Article entities to a list of ArticleDto
        return mapArticlesToDtoList(articles);
    }
    @Override
    public ArticleDto getById(Long id) {
        // Retrieve the article by id from the database
        Article article = articleRepository.findById(id).orElse(null);

        // Map the Article entity to ArticleDto
        return (article != null) ? mapArticleToDto(article) : null;
    }
    @Override
    public ArticleDto update(Long id, ArticleDto articleDto) {
        // Retrieve the article by id from the database
        Article existingArticle = articleRepository.findById(id).orElse(null);

        if (existingArticle != null) {
            // Update the existing article with the new data
            existingArticle.setTitle(articleDto.getTitle());
            existingArticle.setContent(articleDto.getContent());

            // Save the updated article
            existingArticle = articleRepository.save(existingArticle);

            // Map the updated Article entity back to ArticleDto
            return mapArticleToDto(existingArticle);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        // Delete the article by id from the database
        articleRepository.deleteById(id);
    }

    // Helper method to map Article entity to ArticleDto
    private ArticleDto mapArticleToDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        // You may add other fields as needed

        return articleDto;
    }

    // Helper method to map a list of Article entities to a list of ArticleDto
    private List<ArticleDto> mapArticlesToDtoList(List<Article> articles) {
        return articles.stream()
                .map(this::mapArticleToDto)
                .collect(Collectors.toList());
    }
}
