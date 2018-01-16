# @ignore
Feature:  Delete a user

  Background:
    # create a user just to delete it

    * def postData = {}
    * def username = 'editor-' + uuid
    * def email = username + '@gmail.com'
    * def password = '55555555'
    * def payload = {username: '#(username)', email: '#(email)' , password: '#(password)' }
    * set postData.path = 'users'
    * set postData.payload = payload
    * def post = call read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }

  Scenario: delete a user
    Given url post.genericPostResult.location
    When method delete
    Then status 200
    And match response contains {username: '#(username)', email: '#(email)' }

