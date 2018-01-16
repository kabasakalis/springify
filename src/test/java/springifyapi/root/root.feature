# @ignore
Feature: Get home page

Background:
* url baseUrl
* def rootStructure = read('classpath:springifyapi/common/schemas/root.js')

Scenario: get root endpoint (home) and validate schema

When method get
Then status 200
And match response == rootStructure
