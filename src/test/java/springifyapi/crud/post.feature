@ignore
Feature: Create a genre

Background:
  # set up post data
  * def postData = {}
  * def payload = {name: 'Eurodance'}
  * set postData.path = 'genres'
  * set postData.payload =  payload
  # call generic post with postdata
  * def post = call read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }
  # get response and headers
  * def postResponse = post.genericPostResult.response
  * def location = post.genericPostResult.location
  * print 'genericPostResult', karate.pretty(genericPostResult)

Scenario: Verify a genre was created

Given url location
When method get
Then status 200
And  match response contains { name: 'Eurodance' }
