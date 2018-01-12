Feature: test accessing the 'actual' request made

  Background:
    * url springifyBaseUrl

  Scenario: create genre
    * header Authorization = 'Bearer ' + authInfo.authToken
    Given path 'genres'
    And request { name: 'EDM' }
    When method post
    Then status 201
    And match response == { id: '#number', name: 'EDM' }

    * def temp = karate.prevRequest
    * def requestMethod = temp.method
    * match requestMethod == 'POST'
    * def requestHeaders = temp.headers
    * match requestHeaders contains { 'Content-Type': ['application/hal+json'] }
    * def requestUri = temp.uri
    * match requestUri == demoBaseUrl + '/genres'
    # this will be of java type byte[]
    * def requestBody = temp.body
    # convert byte array to  string
    * def requestString = new java.lang.String(requestBody, 'utf-8')
    * match requestString == '{"name":"EDM"}'
