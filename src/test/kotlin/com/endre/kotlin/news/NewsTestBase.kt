package com.endre.kotlin.news

import com.endre.kotlin.news.entity.ArticleDto
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(NewsApplication::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class NewsTestBase {

    @LocalServerPort
    protected var port = 0

    @Before
    @After
    fun clean() {

        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = "/articles/api/articles"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        val list = given().accept(ContentType.JSON).get()
                .then()
                .statusCode(200)
                .extract()
                .`as`(Array<ArticleDto>::class.java)
                .toList()

        list.stream().forEach {
            given().pathParam("id", it.articleId)
                    .delete("/{id}")
                    .then()
                    .statusCode(204)}

        given().get()
                .then()
                .statusCode(200)
                .body("size()", equalTo(0))
    }
}
