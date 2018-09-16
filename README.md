# KotlinNewsApplication

<!--- Travis CI build status banner -->
[![Build Status](https://travis-ci.org/synend16/KotlinNewsApplication.svg?branch=master)](https://travis-ci.org/synend16/KotlinNewsApplication)

This is a News Application API written in Kotlin. I make use of SpringBoot to run this API. Swagger is also included to present all methods allowed in this API. For tests I'm using H2 imbedded database, and Rest Assured to perform tests on the API-endpoints. 


## Supported requests ##

### Articles ###
 - GET          (Get all articles)
 - GET /{id}    (Get single article by id)
 - POST         (Create new article)
 - PATCH /{id}  (Update part of an article)
 - PUT /{id}    (Update whole article)
 - DELETE /{id} (Delete article)

#### HTTP Body (ArticleDto) ####
{
   "articleId": "string",
   "authorId": "string",
   "country": "string",
   "creationTime": "ZonedDateTime",
   "text": "string"
}
