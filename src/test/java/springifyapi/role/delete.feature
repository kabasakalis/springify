# @ignore
Feature:  Delete a role

  Background:
    # create a role just to delete it
    * def postData = {}
    * def payload = {name: 'SUPERADMIN' }
    * set postData.path = 'roles'
    * set postData.payload = payload
    * def post = callonce read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }

  Scenario: delete an role
    Given url post.genericPostResult.location
    When method delete
    Then status 200
    And match response contains {name: 'SUPERADMIN' }

