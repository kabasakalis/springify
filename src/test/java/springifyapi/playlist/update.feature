# @ignore
Feature: Update playlist

Background:
  * url baseUrl

Scenario: update a playlist
  Given path 'playlists', 1
  And request { name: 'Global Top 100' }
  When method patch
  Then status 200
  And match response contains { name: 'Global Top 100' }
