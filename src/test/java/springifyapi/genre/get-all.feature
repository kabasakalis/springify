# @ignore
Feature: Get All

Background:
* url baseUrl
* def genresBaseUrl = baseUrl + 'genres'
* def genreStructure = read('classpath:springifyapi/common/schemas/genre.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/genres_links.js')

Scenario: get all genres and validate schema

Given path 'genres'
When method get
Then status 200

And match response._embedded.genreResources == '#[] genreStructure'
And match response._links == linksStructure
And match response.page == pageStructure
