# @ignore
Feature: Post a user

  Background:
    # set up post data
    * def postData = {}
    * def username = 'editor-' + uuid
    * def email = username + '@gmail.com'
    * def password = '55555555'
    * def payload = {username: '#(username)', email: '#(email)' , password: '#(password)' }
    * set postData.path = 'users'
    * set postData.payload = payload
    * def post = call read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }

  Scenario: create a new User

    Given url post.genericPostResult.location
    When method get
    Then status 200
    And  match response contains {username: '#(username)', email: '#(email)'}
