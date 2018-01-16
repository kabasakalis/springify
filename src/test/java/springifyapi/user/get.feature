# @ignore
Feature: Get one user

Background:
* url baseUrl
* def id = 1
* def userStructure = read('classpath:springifyapi/common/schemas/user.js')

Scenario: get a user and validate schema

Given path 'users', id
When method get
Then status 200
And match response contains { username: 'admin' }
And match response == userStructure
