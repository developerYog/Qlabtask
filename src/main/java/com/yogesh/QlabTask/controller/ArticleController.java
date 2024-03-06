package com.yogesh.QlabTask.controller;

import com.yogesh.QlabTask.entity.Article;
import com.yogesh.QlabTask.payloads.ArticleDto;
import com.yogesh.QlabTask.service.ArticleService;
import com.yogesh.QlabTask.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto articleDto, Authentication authentication) {
        String username = authentication.getName();
        ArticleDto createdArticle = articleService.create(articleDto, username);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<ArticleDto> articles = articleService.getAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Long id) {
        ArticleDto article = articleService.getById(id);
        if (article != null) {
            return new ResponseEntity<>(article, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Article with Id "+id+" not found",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody ArticleDto articleDto) {
        ArticleDto updatedArticle = articleService.update(id, articleDto);
        if (updatedArticle != null) {
            return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Article with Id "+id+" not found",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        if(articleService.getById(id)!=null) {
            articleService.delete(id);
            return new ResponseEntity<>("Article Deleted Successfully",HttpStatus.ACCEPTED);
        }else
        return new ResponseEntity<>("Article with id:"+id+"not found",HttpStatus.NOT_FOUND);
    }


}
