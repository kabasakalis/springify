# @ignore
Feature: Update a role

Background:
  * url baseUrl

Scenario: update a role
  Given path 'roles', 1
  And request { name: 'ADMIN' }
  When method patch
  Then status 200
  And match response contains { name: 'ADMIN' }
