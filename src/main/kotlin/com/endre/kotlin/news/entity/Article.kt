package com.endre.kotlin.news.entity


import com.endre.kotlin.news.util.Country
import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
@Entity
class Article(

        @get:NotBlank @get:Size(max = 32)
        var authorId: String,

        @get:NotBlank @get:Size(max = 1024)
        var text: String,

        @get:NotNull
        var creationTime: ZonedDateTime,

        @get:Country
        var country: String,

        @get:Id @get:GeneratedValue
        var id: Long? = null

        /*
            Note how we need to explicitly state that id can be null (eg when entity
            is not in sync with database).
            The "= null" is used to provide a default value if caller does not
            provide one.
         */
)