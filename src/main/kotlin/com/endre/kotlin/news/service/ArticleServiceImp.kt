package com.endre.kotlin.news.service

import com.endre.kotlin.news.entity.ArticleDto
import com.endre.kotlin.news.repository.ArticleRepository
import com.endre.kotlin.news.util.ArticleConverter
import com.endre.kotlin.news.util.ArticleConverter.Companion.convertFromDto
import com.endre.kotlin.news.util.ArticleConverter.Companion.convertToDto
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
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

    override fun findBy(country: String?, authorId: String?): ResponseEntity<List<ArticleDto>> {

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

    override fun find(pathId: String?): ResponseEntity<ArticleDto> {
        val id: Long

        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {
            return ResponseEntity.status(404).build()
        }

        val dto = articleRepository.findById(id).orElse(null) ?: return ResponseEntity.status(404).build()

        return ResponseEntity.ok(ArticleConverter.convertToDto(dto))
    }

    override fun update(pathId: String?, dto: ArticleDto): ResponseEntity<Void> {
        val id: Long

        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {
            return ResponseEntity.status(400).build()
        }

        if (!articleRepository.existsById(id)) {
            return ResponseEntity.status(404).build()
        }

        if (dto.text == null || dto.authorId == null || dto.country == null) {
            return ResponseEntity.status(400).build()
        }

        var article = articleRepository.findById(id).get()

        article.country = dto.country!!
        article.authorId = dto.authorId!!
        article.text = dto.text!!

        articleRepository.save(article).id

        return ResponseEntity.status(204).build()
    }

    override fun patch(pathId: String?, jsonBody: String): ResponseEntity<Void> {
        val id: Long

        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {
            return ResponseEntity.status(400).build()
        }

        if (!articleRepository.existsById(id)) {
            return ResponseEntity.status(404).build()
        }

        val jackson = ObjectMapper()

        val jsonNode: JsonNode

        try {
            jsonNode = jackson.readValue(jsonBody, JsonNode::class.java)
        } catch (e: Exception) {
            //Invalid JSON data as input
            return ResponseEntity.status(400).build()
        }

        if (jsonNode.has("articleId")) {
            //shouldn't be allowed to modify the counter id
            return ResponseEntity.status(409).build()
        }

        var article = articleRepository.findById(id).get()

        if (jsonNode.has("authorId")){
            val authorId = jsonNode.get("authorId")
            if (authorId.isTextual){
                article.authorId = authorId.asText()
            } else {
                return ResponseEntity.status(400).build()
            }
        }

        if (jsonNode.has("text")){
            val text = jsonNode.get("text")
            if (text.isTextual){
                article.text = text.asText()
            } else {
                return ResponseEntity.status(400).build()
            }
        }

        if (jsonNode.has("country")){
            val country = jsonNode.get("country")
            if (country.isTextual){
                article.country = country.asText()
            } else {
                return ResponseEntity.status(400).build()
            }
        }

        articleRepository.save(article).id

        return ResponseEntity.status(204).build()
    }

    override fun delete(pathId: String?): ResponseEntity<Any> {
        val id: Long

        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {
            return ResponseEntity.status(400).build()
        }

        if (!articleRepository.existsById(id)) {
            return ResponseEntity.status(404).build()
        }

        articleRepository.deleteById(id)
        return ResponseEntity.status(204).build()
    }
}