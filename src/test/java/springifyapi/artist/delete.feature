# @ignore
Feature:  Delete an artist

  Background:
    # create an album just to delete it
    * def postData = {}
    * def payload = {name: 'Foo Fighters', country: 'USA' }
    * set postData.path = 'artists'
    * set postData.payload = payload
    * def post = call read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }

  Scenario: delete an artist
    Given url post.genericPostResult.location
    When method delete
    Then status 200
    And match response contains {name: 'Foo Fighters', country: 'USA' }
