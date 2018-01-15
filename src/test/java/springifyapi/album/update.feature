# @ignore
Feature: Update album

Background:
  * url baseUrl

Scenario: update an album
  Given path 'albums', 62
  And request { title: 'Now You See Me Part 2' }
  When method patch
  Then status 200
  And match response contains { title: 'Now You See Me Part 2' }
