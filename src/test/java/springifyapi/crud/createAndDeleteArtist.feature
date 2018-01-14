Feature: Create an Artist, associate him with a genre, then remove the association.

Background:
  # set up post data
  * def postData = {}
  * def artist_payload = {name: 'Motley Crue', country: 'USA'}
  * set postData.path = 'artists'
  * set postData.payload =  artist_payload
  # call generic post with postdata
  * def post = call read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }
  # get response and headers
  * def postResponse = post.genericPostResult.response
  * def location = post.genericPostResult.location
  * print 'genericPostResult', karate.pretty(genericPostResult)

Scenario: Verify an artist was created

Given url location
When method get
Then status 200
And  match response contains { name: 'Motley Crue' }

Scenario: Associate the newly created Artist with genre 'Rock'
Given url baseUrl
And path '/genres/1/artists'
And header Content-Type = 'text/uri-list'
And request  location
When method put
Then status 204

Scenario: Remove association of artist with this genre
Given url baseUrl
And path '/genres/1/artists'
And header Content-Type = 'text/uri-list'
And request  location
When method put
Then status 204
