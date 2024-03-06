package com.yogesh.QlabTask.service;

import com.yogesh.QlabTask.entity.Article;
import com.yogesh.QlabTask.payloads.ArticleDto;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    public ArticleDto create(ArticleDto articleDto, String username);
    public List<ArticleDto> getAll();
    public ArticleDto getById(Long id);
    public ArticleDto update(Long id, ArticleDto articleDto);
    public void delete(Long id);
}
