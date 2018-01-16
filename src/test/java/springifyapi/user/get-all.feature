# @ignore
Feature: Get all users

Background:
* url baseUrl
* def userStructure = read('classpath:springifyapi/common/schemas/user.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/users_links.js')

Scenario: get all users and validate schema

Given path 'users'
When method get
Then status 200

And match response._embedded.userResources == '#[] userStructure'
And match response._links == linksStructure
And match response.page == pageStructure
