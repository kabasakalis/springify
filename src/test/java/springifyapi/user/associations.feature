Feature: User Associations

Background:
  # set up post data
  * def postData = {}
  * def role_payload = {name: 'GOD'}
  * set postData.path = 'roles'
  * set postData.payload =  role_payload
  # call generic post with postdata
  * def post = callonce read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }
  # get response and headers
  * def postResponse = post.genericPostResult.response
  * def location = post.genericPostResult.location
  * print 'postResult', karate.pretty(postResponse)

Scenario: Verify newly created role

Given url location
When method get
Then status 200
And  match response contains { name: 'GOD' }

Scenario: Associate the newly created role with user 'moderator'
Given url baseUrl
And path '/users/2/roles'
And header Content-Type = 'text/uri-list'
And request location
When method put
Then status 204

Scenario: Verify that the role has been associated with user 'moderator'
Given url location + '/users'
When method get
Then status 200
And  match response._embedded.userResources[*].username contains 'moderator'

Scenario: Remove association of role with this user
Given url baseUrl
And path '/users/2/roles/' + postResponse.id
When method delete
Then status 204

Scenario: Verify that the new role is not assigned to the user
Given url location + '/users'
When method get
Then status 200
And  match response !contains { _embedded: '#object' }

Scenario: Get all user roles and validate the schema
* def roleStructure = read('classpath:springifyapi/common/schemas/role.js')
* def pageStructure = read('classpath:springifyapi/common/schemas/page.js')
* def linksStructure = read('classpath:springifyapi/common/schemas/roles_links.js')
Given url baseUrl
And path '/users/1/roles'
When method get
Then status 200
# Validate schema
And match response._embedded.roleResources == '#[] roleStructure'
And match response._links == linksStructure
And match response.page == pageStructure
