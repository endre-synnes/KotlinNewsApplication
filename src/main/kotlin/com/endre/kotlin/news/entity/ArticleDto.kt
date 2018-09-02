package com.endre.kotlin.news.entity

import com.endre.kotlin.news.util.Country
import io.swagger.annotations.ApiModelProperty
import java.time.ZonedDateTime

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
data class ArticleDto(

        @ApiModelProperty("The id of the article")
        var articleId: String? = null,

        @ApiModelProperty("The id of the author that wrote/created this article")
        var authorId: String? = null,

        @ApiModelProperty("The text of the article")
        var text: String? = null,

        @ApiModelProperty("The country this article is related to")
        @get:Country
        var country: String? = null,

        @ApiModelProperty("When the article was first created/published")
        var creationTime: ZonedDateTime? = null
) {

        @ApiModelProperty("Deprecated. Use newsId instead")
        @Deprecated
        var id: String? = null
}