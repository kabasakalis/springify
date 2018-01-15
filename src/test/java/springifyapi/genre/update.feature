# @ignore
Feature: Update genre

Background:
  * url baseUrl

Scenario: update a genre
  Given path 'genres', 7
  And request { name: 'Eurodance and EDM' }
  When method patch
  Then status 200
  And match response contains { name: 'Eurodance and EDM' }
