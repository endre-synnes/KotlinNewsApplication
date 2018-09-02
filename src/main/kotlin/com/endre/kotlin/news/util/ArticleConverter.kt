package com.endre.kotlin.news.util

import com.endre.kotlin.news.entity.Article
import com.endre.kotlin.news.entity.ArticleDto
import java.time.ZonedDateTime

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
class ArticleConverter {

    companion object {

        fun convertFromDto(article: ArticleDto) : Article {
            return Article(article.authorId!!, article.text!!, ZonedDateTime.now(), article.country!!)
        }


        fun convertFromDto(articles: Iterable<ArticleDto>): List<Article> {
            return articles.map { convertFromDto(it) }
        }


        fun convertToDto(article: Article): ArticleDto {
            return ArticleDto(
                    article.id.toString(),
                    article.authorId,
                    article.text,
                    article.country,
                    article.creationTime)
                    .apply { id = article.id?.toString() }
        }

        fun convertToDto(articles: Iterable<Article>): List<ArticleDto> {
            return articles.map { convertToDto(it) }
        }
    }
}