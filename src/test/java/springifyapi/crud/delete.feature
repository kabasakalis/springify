@ignore
Feature: delete a  genre


Background:
* url baseUrl
# id = 7, Eurodance
* def id = 7
* print 'deleted: ', karate.pretty(deleted)
* print 'createdUrl', karate.pretty(createdUrl)

Scenario: delete the genre we create in the post feature

# * call('classpath:springifyapi/crud/post.feature'
# * print 'RESPHEAD: ', karate.pretty(responseHeaders)
# * def createdUrl = responseHeaders['Location']


# Given path 'genres', id
Given url createdUrl
When method delete
Then status 200
And match response contains { name: 'EDM' }
