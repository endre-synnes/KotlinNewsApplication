package com.endre.kotlin.news.repository

import com.endre.kotlin.news.entity.Article
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
@Repository
interface ArticleRepository : CrudRepository<Article, Long> {

    fun findAllByCountry(country: String): Iterable<Article>

    fun findAllByAuthorId(authorId: String): Iterable<Article>

    fun findAllByCountryAndAuthorId(country: String, authorId: String): Iterable<Article>
}