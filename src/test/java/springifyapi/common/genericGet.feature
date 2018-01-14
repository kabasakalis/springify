Feature: Get a resource

Background:
  * print 'Starting generic get with getData', karate.pretty(getData)
  * url baseUrl
  * def genericGetResult = {}
Scenario: get a resource
  # Given path postData.path
  Given url getData.url
  When method get
  Then status 200
  And set genericGetResult.response = response
  * print 'Setting GenericGetResult', karate.pretty(genericGetResult)
