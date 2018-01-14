@ignore
Feature: get all genres

Background:
* url baseUrl
* def genresBaseUrl = baseUrl + 'genres'

Scenario: retrieve all with links and pagination

Given path 'genres'
When method get
Then status 200

And match response._embedded.genreResources == '#[]'
And match response._links.self.href == genresBaseUrl + '?page=0&size=20'
And match response._links.artists.href == baseUrl + 'artists'
And match response._links.playlists.href == baseUrl + 'playlists'
And match response._links.home.href == baseUrl
# And match response._links contains { first: '#notnull' }
# And match response._links contains { last: '#notnull' }
# And match response._links contains { next: '#notnull' }
And match response.page ==
"""
{
  size: '#number',
  totalElements: '#number',
  totalPages: '#number',
  number: '#number'
}
"""
