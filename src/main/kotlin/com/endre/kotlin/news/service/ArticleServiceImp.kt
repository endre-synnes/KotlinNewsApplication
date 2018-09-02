package com.endre.kotlin.news.service

import com.endre.kotlin.news.entity.ArticleDto
import com.endre.kotlin.news.repository.ArticleRepository
import com.endre.kotlin.news.util.ArticleConverter.Companion.convertFromDto
import com.endre.kotlin.news.util.ArticleConverter.Companion.convertToDto
import com.google.common.base.Throwables
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.validation.ConstraintViolationException

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
@Service("ArticleService")
class ArticleServiceImp : ArticleService{


    @Autowired
    private lateinit var articleRepository : ArticleRepository


    override fun createArticle(article : ArticleDto) : ResponseEntity<Long> {
        if (article.authorId == null || article.text == null ||
                article.country == null || article.articleId != null ||
                article.creationTime != null)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val id: Long?

        try {
            id = articleRepository.save(convertFromDto(article)).id
        } catch (e : Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity(id, HttpStatus.CREATED)
    }

    override fun find(country: String?, authorId: String?): ResponseEntity<List<ArticleDto>> {

        val list = if (country.isNullOrBlank() && authorId.isNullOrBlank()) {
            articleRepository.findAll()
        } else if (!country.isNullOrBlank() && !authorId.isNullOrBlank()) {
            articleRepository.findAllByCountryAndAuthorId(country!!, authorId!!)
        } else if (!country.isNullOrBlank()){
            articleRepository.findAllByCountry(country!!)
        } else {
            articleRepository.findAllByAuthorId(authorId!!)
        }

        return ResponseEntity(convertToDto(list), HttpStatus.OK)
    }



}