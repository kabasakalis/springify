# @ignore
Feature: Get all artists

Background:
* url baseUrl
* def artistStructure = read('classpath:springifyapi/common/schemas/artist.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/artists_links.js')

Scenario: get all artists and validate schema

Given path 'artists'
When method get
Then status 200

And match response._embedded.artistResources == '#[] artistStructure'
And match response._links == linksStructure
And match response.page == pageStructure
