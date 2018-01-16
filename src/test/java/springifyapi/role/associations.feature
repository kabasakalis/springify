Feature: Role Associations

Scenario: Get all users with role USER and validate the schema
* def userStructure = read('classpath:springifyapi/common/schemas/user.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/users_links.js')
Given url baseUrl
And path '/roles/3/users'
When method get
Then status 200
# Validate schema
And match response._embedded.userResources == '#[] userStructure'
And match response._links == linksStructure
And match response.page == pageStructure
