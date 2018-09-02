package com.endre.kotlin.news.util

import com.endre.kotlin.news.entity.ArticleDto
import com.endre.kotlin.news.service.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */

@Component
class DefaultData {


    @Autowired
    private lateinit var articleService: ArticleService

    @PostConstruct
    fun initializeDefault() {

        var articleDto = ArticleDto(text = "Some tex", country = "Norway", authorId = "123456")

        articleService.createArticle(articleDto)
    }

}