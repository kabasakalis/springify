# @ignore
# NOTICE: A user is usually created with posting at the sign-up endpoint.
# This endpoint is generated from the generic AbstractBaseController and is tested for completeness.
# It shouldn't be used for signing up a user.
Feature: Post a user

  Background:
    # set up post data
    * def postData = {}
    * def username = 'editor-' + uuid
    * def email = username + '@gmail.com'
    * def password = '$2a$10$UClj/ChiXzRXcT6HvSdOpePZFzTIJs9CYDagygovYbM0TDzMRe7Di'
    * def payload = {username: '#(username)', email: '#(email)' , password: '#(password)' }
    * set postData.path = 'users'
    * set postData.payload = payload
    * def post = call read('classpath:springifyapi/common/genericPost.feature') { postData: '#(postData)' }

  Scenario: create a new User

    Given url post.genericPostResult.location
    When method get
    Then status 200
    And  match response contains {username: '#(username)', email: '#(email)'}
