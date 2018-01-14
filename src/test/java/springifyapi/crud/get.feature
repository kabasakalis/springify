@ignore
Feature: get one genre

Background:
* url baseUrl
* def id = 1
* def genresBaseUrl = baseUrl + 'genres'

Scenario: retrieve a genre and related links

Given path 'genres', id
When method get
Then status 200
And match response contains { name: 'Rock' }
And match response._links.self.href == genresBaseUrl + '/'+ id
And match response._links.genres.href == genresBaseUrl
And match response._links.artists.href == genresBaseUrl +'/'+ id + '/artists'
