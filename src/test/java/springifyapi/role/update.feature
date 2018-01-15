# @ignore
Feature: Update

Background:
  * url baseUrl

Scenario: update a genre
  Given path 'genres', 2
  And request { name: 'Thrash Metal' }
  When method patch
  Then status 200
  And match response contains { name: 'Thrash Metal' }
