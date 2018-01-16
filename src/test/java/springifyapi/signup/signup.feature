# @ignore
Feature: Sign up a user

  Background:
    # set up post data
    * def postData = {}
    * def username = 'jonathan-' + uuid
    * def email = username + '@gmail.com'
    * def password = '11111111'
    * def payload = {username: '#(username)', email: '#(email)' , password: '#(password)', passwordConfirm: '#(password)'}
    * set postData.path = 'sign-up'
    * set postData.payload = payload
    * def post = call read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }

  Scenario: verify that a new user is signed up

    Given url post.genericPostResult.location
    When method get
    Then status 200
    And  match response contains {username: '#(username)', email: '#(email)'}
