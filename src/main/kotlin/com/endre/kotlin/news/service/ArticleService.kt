package com.endre.kotlin.news.service

import com.endre.kotlin.news.entity.Article
import com.endre.kotlin.news.entity.ArticleDto
import com.endre.kotlin.news.repository.ArticleRepository
import com.google.common.base.Throwables
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import javax.validation.ConstraintViolationException

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
@Service
class ArticleService{


    @Autowired
    private lateinit var articleRepository : ArticleRepository


    fun createArticle(article : ArticleDto) : ResponseEntity<Long> {
        if (article.authorId == null || article.text == null ||
                article.country == null || article.articleId != null ||
                article.creationTime != null)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val id: Long?

        try {
            id = articleRepository.save(convertDto(article)).id
        } catch (e : Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity(id, HttpStatus.CREATED)
    }


    fun convertDto(article: ArticleDto) : Article {
        return Article(article.authorId!!, article.text!!, ZonedDateTime.now(), article.country!!)
    }
}