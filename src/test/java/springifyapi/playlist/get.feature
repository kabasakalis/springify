# @ignore
Feature: Get one playlist

Background:
* url baseUrl
* def id = 13
* def playlistStructure = read('classpath:springifyapi/common/schemas/playlist.js')

Scenario: get a playlist and validate schema

Given path 'playlists', id
When method get
Then status 200
And match response contains { name: 'Trap' }
And match response == playlistStructure
