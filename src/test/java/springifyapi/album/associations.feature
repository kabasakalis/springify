Feature: Album Associations

Background:
  # set up post data
  * def postData = {}
  * def playlist_payload = {name: 'Best of Soundtrack'}
  * set postData.path = 'playlists'
  * set postData.payload =  playlist_payload
  # call generic post with postdata
  * def post = callonce read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }
  # get response and headers
  * def postResponse = post.genericPostResult.response
  * def location = post.genericPostResult.location
  * print 'postResult', karate.pretty(postResponse)

Scenario: Verify newly created playlist

Given url location
When method get
Then status 200
And  match response contains { name: 'Best of Soundtrack' }

Scenario: Associate the newly created playlist with album '300: Rise Of An Empire'
Given url baseUrl
And path '/albums/67/playlists'
And header Content-Type = 'text/uri-list'
And request location
When method put
Then status 204

Scenario: Verify that the playlist has been associated with album '300: Rise Of An Empire'
Given url location + '/albums'
When method get
Then status 200
And  match response._embedded.albumResources[*].title contains '300: Rise Of An Empire'

Scenario: Remove association of playlist with this album
Given url baseUrl
And path '/albums/67/playlists/' + postResponse.id
When method delete
Then status 204

Scenario: Verify that the new playlist has no associated album
Given url location + '/albums'
When method get
Then status 200
And  match response !contains { _embedded: '#object' }

Scenario: Get all playlists of this album and verify the json schema
* def playlistStructure = read('classpath:springifyapi/common/schemas/playlist.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/playlists_links.js')
Given url baseUrl
And path '/albums/67/playlists/'
When method get
Then status 200
# Validate schema
And match response._embedded.playlistResources == '#[] playlistStructure'
And match response._links == linksStructure
And match response.page == pageStructure

Scenario: Get artist of album
Given url baseUrl
And path '/albums/67/artist'
When method get
Then status 200
# Validate artist
And match response.name == 'Junkie XL'
