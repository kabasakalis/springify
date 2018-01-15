# @ignore
Feature: Update artist

Background:
  * url baseUrl

Scenario: update an artist
  Given path 'artists', 34
  And request { name: 'Postie Malone' }
  When method patch
  Then status 200
  And match response contains { name: 'Postie Malone' }
