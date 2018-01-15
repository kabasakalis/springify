Feature: Artist Associations

Background:
  # set up post data for new album
  * def postData = {}
  * def album_payload = {title: 'Revival', year: '2017'}
  * set postData.path = 'albums'
  * set postData.payload =  album_payload
  # call generic post with postdata
  * def post = callonce read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }
  # get response and headers
  * def postResponse = post.genericPostResult.response
  * def location = post.genericPostResult.location
  * print 'postResult', karate.pretty(postResponse)

Scenario: Verify newly created album

Given url location
When method get
Then status 200
And  match response contains { title: 'Revival', year: '2017' }

Scenario: Associate the newly created album with artist 'Eminem'
Given url baseUrl
And path '/artists/29/albums'
And header Content-Type = 'text/uri-list'
And request location
When method put
Then status 204

Scenario: Verify that the album has been associated with artist 'Eminem'
Given url baseUrl
And path '/artists/29/albums'
When method get
Then status 200
And  match response._embedded.albumResources[*].title contains 'Revival'

Scenario: Remove association of album with this artist
Given url baseUrl
And path '/artists/29/albums/' + postResponse.id
When method delete
Then status 204

Scenario: Verify that the new album is now not association with artist 'Eminem'
Given url baseUrl
And path '/artists/29/albums'
When method get
Then status 200
And  match response._embedded.albumResources[*].title !contains 'Revival'

Scenario: Get all albums of this artist and verify the json schema
* def albumStructure = read('classpath:springifyapi/common/schemas/album.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/albums_links.js')
Given url baseUrl
And path '/artists/1/albums/'
When method get
Then status 200
# Validate schema
And match response._embedded.albumResources == '#[] albumStructure'
And match response._links == linksStructure
And match response.page == pageStructure

Scenario: Get genre of artist
Given url baseUrl
And path '/artists/1/genre'
When method get
Then status 200
# Validate artist
And match response.name == 'Metal'
