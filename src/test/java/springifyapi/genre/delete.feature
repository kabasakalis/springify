# @ignore
Feature:  Delete genre

  Background:
    # create a genre just to delete it
    * def postData = {}
    * def payload = {name: 'EDM'}
    * set postData.path = 'genres'
    * set postData.payload = payload
    * def post = callonce read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }

  Scenario: delete a genre
    Given url post.genericPostResult.location
    When method delete
    Then status 200
    And match response contains { name: 'EDM' }
