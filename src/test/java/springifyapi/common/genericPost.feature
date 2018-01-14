Feature: Create a generic post

Background:
  * print 'Starting generic post with postData', karate.pretty(postData)
  * url baseUrl
  * def genericPostResult = {}
Scenario: create a resource
  Given path postData.path
  And request postData.payload
  When method post
  Then status 201
  # And match response contains { name: '#(nameToPost)' }
  And set genericPostResult.location = responseHeaders['Location'][0]
  And set genericPostResult.response = response
  * print 'Setting GenericPostResult', karate.pretty(genericPostResult)
