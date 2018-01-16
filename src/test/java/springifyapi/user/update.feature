# @ignore
Feature: Update a user

Background:
  * url baseUrl

Scenario: update a user
  Given path 'users', 4
  And request { username: 'admin3', email: 'admin3@springify.com', password: '$2a$10$UClj/ChiXzRXcT6HvSdOpePZFzTIJs9CYDagygovYbM0TDzMRe7Di' }
  When method patch
  Then status 200
  And match response contains { username: 'admin3', email: 'admin3@springify.com' }
