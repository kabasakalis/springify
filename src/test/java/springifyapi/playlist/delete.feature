# @ignore
Feature:  Delete

  Background:
    # create a genre just to delete it
    * def postData = {}
    * def payload = {name: 'EDM'}
    * set postData.path = 'genres'
    * set postData.payload = payload
    * def post = call read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }

  Scenario: delete a genre
    Given url post.genericPostResult.location
    When method delete
    Then status 200
    And match response contains { name: 'EDM' }
