package com.endre.kotlin.news.controller

import com.endre.kotlin.news.NewsTestBase
import com.endre.kotlin.news.entity.ArticleDto
import io.restassured.RestAssured.delete
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Test
import org.springframework.beans.factory.annotation.Value

class ArticleControllerTest : NewsTestBase() {

    @Value("\${username}")
    private lateinit var username : String

    @Value("\${password}")
    private lateinit var password : String

    @Test
    fun testGet() {
        given().accept(BASE_JSON)
                .auth().preemptive().basic(username, password).`when`()
                .get()
                .then()
                .statusCode(200)
                .body("size()", equalTo(0))
    }

    @Test
    fun testCreateAndGet() {

        val dto = defaultDto()

        val id = create()

        given().accept(BASE_JSON)
                .auth().preemptive().basic(username, password).`when`()
                .get()
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))

        given().accept(BASE_JSON)
                .auth().preemptive().basic(username, password).`when`()
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
        val id = create()

        delete("/$id").then().statusCode(204)

        delete("/$id").then().statusCode(404)
    }

    @Test
    fun testUpdate() {

        val id = create()

        val oldDto = getById(id)

        val dto = getById(id)
        dto.text = dto.text + dto.text
        update(dto)

        assertNotEquals(oldDto.text, getById(id).text)
        assertEquals(id, dto.articleId)
    }

    @Test
    fun testPatchAuthorId() {
        val id = create()

        val modifiedValue = getById(id).authorId + "231"

        patchWithMergeJSon(id, "{\"authorId\":\"$modifiedValue\"}", 204)

        val dto = getById(id)
        assertNotNull(dto.authorId)
        assertEquals(modifiedValue, dto.authorId)

    }

    @Test
    fun testDeprecatedGet() {

        val id = create()

        val dto = defaultDto()

        given().accept(BASE_JSON)
                .auth().preemptive().basic(username, password).`when`()
                .pathParam("id", id)
                .get("/id/{id}")
                .then()
                .statusCode(200)
                .body("articleId", equalTo(id))
                .body("authorId", equalTo(dto.authorId))
                .body("text", equalTo(dto.text))
                .body("country", equalTo(dto.country))
    }

    private fun create() : String{
        return given().contentType(BASE_JSON)
                .auth().preemptive().basic(username, password).`when`()
                .body(defaultDto())
                .post()
                .then()
                .statusCode(201)
                .extract().asString()
    }

    private fun update(dto: ArticleDto) {

        given().contentType(ContentType.JSON)
                .auth().preemptive().basic(username, password).`when`()
                .body(dto)
                .put("${dto.articleId}")
                .then()
                .statusCode(204)
    }

    private fun getById(id: String) : ArticleDto {
        return given().accept(BASE_JSON)
                .auth().preemptive().basic(username, password).`when`()
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .`as`(ArticleDto::class.java)
    }

    private fun patchWithMergeJSon(id: String, jsonBody: String, statusCode: Int) {
        given().contentType(ContentType.JSON)
                .auth().preemptive().basic(username, password).`when`()
                .body(jsonBody)
                .patch(id)
                .then()
                .statusCode(statusCode)
    }

    private fun defaultDto() : ArticleDto {
        return ArticleDto(null,
                authorId = "1001",
                text = "Some text",
                country = "Norway",
                creationTime = null)
    }
}