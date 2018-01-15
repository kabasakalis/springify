# @ignore
Feature: Get all playlists

Background:
* url baseUrl
* def playlistStructure = read('classpath:springifyapi/common/schemas/playlist.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/playlists_links.js')

Scenario: get all playlists and validate schema

Given path 'playlists'
When method get
Then status 200

And match response._embedded.playlistResources == '#[] playlistStructure'
And match response._links == linksStructure
And match response.page == pageStructure

