# @ignore
Feature: Update a user

Background:
  * url baseUrl

Scenario: update a user
  Given path 'users', 4
  And request { username: 'admin3', email: 'admin3@springify.com', password: '66666666' }
  When method patch
  Then status 200
  And match response contains { username: 'admin3', email: 'admin3@springify.com' }
