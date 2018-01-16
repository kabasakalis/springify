# @ignore
Feature: Get one role

Background:
* url baseUrl
* def id = 1
* def roleStructure = read('classpath:springifyapi/common/schemas/role.js')

Scenario: get an role and validate schema

Given path 'roles', id
When method get
Then status 200
And match response contains { name: 'ADMIN' }
And match response == roleStructure
