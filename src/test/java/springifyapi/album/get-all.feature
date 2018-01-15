# @ignore
Feature: Get all albums

Background:
* url baseUrl
* def albumStructure = read('classpath:springifyapi/common/schemas/album.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/albums_links.js')

Scenario: get all albums and validate schema

Given path 'albums'
When method get
Then status 200

And match response._embedded.albumResources == '#[] albumStructure'
And match response._links == linksStructure
And match response.page == pageStructure
