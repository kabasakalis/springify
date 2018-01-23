# @ignore
Feature: Login

Background:
* def creds = read('classpath:springifyapi/data/user.json')
* url baseUrl
* def userStructure = read('classpath:springifyapi/common/schemas/user.js')

Scenario: User is logged in in and a token is returned
Given url baseUrl
And path 'login'
And request creds
When method post
Then status 200
And match response == userStructure
And match response.token == '#string'
