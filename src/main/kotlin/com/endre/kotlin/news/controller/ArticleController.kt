package com.endre.kotlin.news.controller

import com.endre.kotlin.news.entity.ArticleDto
import com.endre.kotlin.news.service.ArticleService
import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder


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


    @ApiOperation("Get all articles")
    @GetMapping
    fun getAll(@ApiParam("The country name")
            @RequestParam("country", required = false)
            country : String?,

            @ApiParam("The id of the author who wrote the article")
            @RequestParam("authorId", required = false)
            authorId: String?): ResponseEntity<List<ArticleDto>> {
        return articleService.findBy(country, authorId)
    }


    @ApiOperation("Create new Article")
    @PostMapping
    fun post(@RequestBody articleDto: ArticleDto): ResponseEntity<Long> {
        return articleService.createArticle(articleDto)
    }


    @ApiOperation("Get single article by id")
    @GetMapping(path = ["/{id}"])
    fun get(@ApiParam("The id of the article")
            @PathVariable("id")
            pathId: String?) : ResponseEntity<ArticleDto> {
        return articleService.find(pathId)
    }

    @ApiOperation("Update whole article with new information")
    @PutMapping(path = ["/{id}"])
    fun update(@ApiParam("The id of the article")
               @PathVariable("id")
               pathId: String?,
               @ApiParam("The data of the article")
               @RequestBody
               articleDto: ArticleDto): ResponseEntity<Void> {
        return articleService.update(pathId, articleDto)
    }

    @ApiOperation("Update part of article")
    @PatchMapping(path = ["/{id}"])
    fun patch(@ApiParam("The id of the article")
            @PathVariable("id")
            pathId: String?,
            @ApiParam("The partial patch")
            @RequestBody
            jsonPatch: String) : ResponseEntity<Void> {
        return articleService.patch(pathId, jsonPatch)
    }

    @ApiOperation("Delete an article by id")
    @DeleteMapping(path = ["/{id}"])
    fun delete(@ApiParam("id")
                @PathVariable("id")
                pathId: String?) : ResponseEntity<Any> {
        return articleService.delete(pathId)
    }

    @ApiOperation("Get single article by id")
    @ApiResponses(ApiResponse(code = 301, message = "Deprecated URI, moved permanently."))
    @GetMapping(path = ["/id/{id}"])
    @Deprecated
    fun deprecatedFindById(
            @ApiParam("Id of an article")
            @PathVariable("id")
            pathId: String?) : ResponseEntity<ArticleDto> {
        return ResponseEntity.status(301)
                .location(UriComponentsBuilder.fromUriString("$contextPath/articles/$pathId")
                        .build()
                        .toUri())
                .build()
    }
}