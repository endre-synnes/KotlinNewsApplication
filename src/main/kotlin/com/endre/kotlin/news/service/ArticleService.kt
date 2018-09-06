package com.endre.kotlin.news.service

import com.endre.kotlin.news.entity.ArticleDto
import org.springframework.http.ResponseEntity

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
interface ArticleService{

    fun createArticle(article : ArticleDto) : ResponseEntity<Long>

    fun findBy(country: String?, authorId: String?): ResponseEntity<List<ArticleDto>>

    fun find(pathId: String?): ResponseEntity<ArticleDto>

    fun delete(pathId: String?): ResponseEntity<Any>


}