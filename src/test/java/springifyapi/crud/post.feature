Feature: Can post

Background:
  * url baseUrl
Scenario: create genre
  Given path 'genres'
  And request { name: 'EDM' }
  When method post
  Then status 201
  And match response contains { name: 'EDM' }
  * print 'RESPHEAD: ', karate.pretty(responseHeaders['Location'][0])
  * def createdUrl = responseHeaders['Location'][0]
  * karate.set('createdUrl', createdUrl);
  * print 'createdUrl', karate.pretty(createdUrl)


Scenario: delete genre
  *  call read('classpath:springifyapi/crud/delete.feature')  { deleted:  '#(createdUrl)' }
# Scenario: delete the genre we create in the post feature

# Given url createdUrl
# When method delete
# Then status 200
# And match response contains { name: 'EDM' }
