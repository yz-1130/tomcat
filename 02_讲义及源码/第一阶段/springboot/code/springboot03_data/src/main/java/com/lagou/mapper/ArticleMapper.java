package com.lagou.mapper;

import com.lagou.pojo.Article;


public interface ArticleMapper {

    //根据id查询对应的文章
    public Article selectArticle(Integer id);

}
