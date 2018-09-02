package com.endre.kotlin.news.controller

import com.endre.kotlin.news.service.ArticleService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */

const val BASE_JSON = "application/json;charset=UTF-8"

@Api(value = "/articles", description = "Handling of creating and retrieving articles")
@RequestMapping(
        path = ["/articles"], // when the url is "<base>/news", then this class will be used to handle it
        produces = [BASE_JSON]
)
@RestController
class ArticleController {


    @Value("\${server.servlet.context-path}")
    private lateinit var contextPath : String

    @Autowired
    private lateinit var articleService: ArticleService


}