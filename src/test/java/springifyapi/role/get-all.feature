# @ignore
Feature: Get all roles

Background:
* url baseUrl
* def roleStructure = read('classpath:springifyapi/common/schemas/role.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/roles_links.js')

Scenario: get all roles and validate schema

Given path 'roles'
When method get
Then status 200

And match response._embedded.roleResources == '#[] roleStructure'
And match response._links == linksStructure
And match response.page == pageStructure
