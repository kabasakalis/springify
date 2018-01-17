# @ignore
Feature:  Delete an album

  Background:
    # create an album just to delete it
    * def postData = {}
    * def payload = {title: 'Hysteria', year: '2000' }
    * set postData.path = 'albums'
    * set postData.payload = payload
    * def post = callonce read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }

  Scenario: delete an album
    Given url post.genericPostResult.location
    When method delete
    Then status 200
    And match response contains {title: 'Hysteria', year: '2000' }

