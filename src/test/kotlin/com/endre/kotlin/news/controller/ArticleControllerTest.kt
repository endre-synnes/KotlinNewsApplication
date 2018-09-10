package com.endre.kotlin.news.controller

import com.endre.kotlin.news.NewsTestBase
import com.endre.kotlin.news.entity.ArticleDto
import io.restassured.RestAssured.delete
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test

class ArticleControllerTest : NewsTestBase() {


    @Test
    fun testGet() {
        given().accept(BASE_JSON)
                .auth().preemptive().basic("user", "user").`when`()
                .get()
                .then()
                .statusCode(200)
                .body("size()", equalTo(0))
    }

    @Test
    fun testCreateAndGet() {
        val dto = ArticleDto(null, authorId = "author", text = "Some text", country = "Norway", creationTime = null)

        val id = given().contentType(BASE_JSON)
                .auth().preemptive().basic("userrrrrrrrr", "user").`when`()
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        given().accept(BASE_JSON)
                .auth().preemptive().basic("user", "user").`when`()
                .get()
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))

        given().accept(BASE_JSON)
                .auth().preemptive().basic("user", "user").`when`()
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("articleId", equalTo(id))
                .body("authorId", equalTo(dto.authorId))
                .body("text", equalTo(dto.text))
                .body("country", equalTo(dto.country))
    }

    @Test
    fun testDoubleDelete() {
        val dto = ArticleDto(null, "author", "text", "Norway", null)

        val id = given().contentType(BASE_JSON)
                .auth().preemptive().basic("user", "user").`when`()
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        delete("/$id").then().statusCode(204)

        delete("/$id").then().statusCode(404)
    }
}