Feature: Can post

  Background:
    * url baseUrl

  Scenario: create genre
    Given path 'genres'
    And request { name: 'EDM' }
    When method post
    Then status 201
    And match response contains { name: 'EDM' }

