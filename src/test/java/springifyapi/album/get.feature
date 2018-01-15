# @ignore
Feature: Get one album

Background:
* url baseUrl
* def id = 2
* def albumStructure = read('classpath:springifyapi/common/schemas/album.js')

Scenario: get an album and validate schema

Given path 'albums', id
When method get
Then status 200
And match response contains { title: 'Abigail' }
And match response == albumStructure
