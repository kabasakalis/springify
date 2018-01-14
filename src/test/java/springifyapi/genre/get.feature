# @ignore
Feature: Get One

Background:
* url baseUrl
* def id = 1
* def genresBaseUrl = baseUrl + 'genres'
* def genreStructure = read('classpath:springifyapi/common/schemas/genre.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/genres_links.js')

Scenario: get a genre and validate schema

Given path 'genres', id
When method get
Then status 200
And match response contains { name: 'Rock' }
And match response == genreStructure
