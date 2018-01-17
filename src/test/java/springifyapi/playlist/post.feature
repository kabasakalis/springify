# @ignore
Feature: Post playlist

Background:
  # set up post data
  * def postData = {}
  * def payload = {name: 'Billboard Top 100' }
  * set postData.path = 'playlists'
  * set postData.payload =  payload
  # call generic post with postdata
  * def post = callonce read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }
  # get response and headers
  * def postResponse = post.genericPostResult.response
  * def location = post.genericPostResult.location
  * print 'genericPostResult', karate.pretty(genericPostResult)

Scenario: create a new playlist

Given url location
When method get
Then status 200
And  match response contains {name: 'Billboard Top 100' }
