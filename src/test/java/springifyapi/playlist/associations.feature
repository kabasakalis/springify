Feature: Genre Associations

Background:
  # set up post data
  * def postData = {}
  * def artist_payload = {name: 'Motley Crue', country: 'USA'}
  * set postData.path = 'artists'
  * set postData.payload =  artist_payload
  # call generic post with postdata
  * def post = callonce read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }
  # get response and headers
  * def postResponse = post.genericPostResult.response
  * def location = post.genericPostResult.location
  * print 'postRe', karate.pretty(postResponse)

Scenario: Verify newly created genre

Given url location
When method get
Then status 200
And  match response contains { name: 'Motley Crue' }

Scenario: Associate the newly created artist with genre 'Rock'
Given url baseUrl
And path '/genres/1/artists'
And header Content-Type = 'text/uri-list'
And request location
When method put
Then status 204

Scenario: Verify that the artist has been associated with genre 'Rock'
Given url location
When method get
Then status 200
And  match response contains { genre: 'Rock' }

Scenario: Remove association of artist with this genre
Given url baseUrl
And path '/genres/1/artists/' + postResponse.id
When method delete
Then status 204

Scenario: Verify that the new artist has no genre
Given url location
When method get
Then status 200
And  match response contains { genre: '#null' }

Scenario: Get all artists of this genre and verify the json schema
* def artistStructure = read('classpath:springifyapi/common/schemas/artist.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/artists_links.js')
Given url baseUrl
And path '/genres/1/artists/'
When method get
Then status 200
# Validate schema
And match response._embedded.artistResources == '#[] artistStructure'
And match response._links == linksStructure
And match response.page == pageStructure

