# @ignore
Feature: Get one artist

Background:
* url baseUrl
* def id = 1
* def artistStructure = read('classpath:springifyapi/common/schemas/artist.js')

Scenario: get an artist and validate schema

Given path 'artists', id
When method get
Then status 200
And match response contains { name: 'King Diamond' }
And match response == artistStructure
