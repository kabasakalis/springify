# @ignore
Feature: User with USER role cannot access user endpoints

  Background:
    * url baseUrl
    * def config = {}
    * set config.user = 'user'
    * def result = callonce read('classpath:springifyapi/common/setHeaders.feature') {config: '#(config)'}
    * def auth_header = 'Bearer ' + result.token
    * configure headers = { Authorization:  '#(auth_header)' }

  Scenario: cannot read user
    Given path 'users', 4
    When method get
    Then status 403

  Scenario: cannot read users
    Given path 'users'
    When method get
    Then status 403

  Scenario: cannot update user
    Given path 'users', 4
    And request { username: 'admin10' }
    When method patch
    Then status 403

  Scenario: cannot delete user
    Given path 'users', 4
    When method delete
    Then status 403

  Scenario: cannot create a new user
    * def username = 'editor-' + uuid
    * def email = username + '@gmail.com'
    * def password = '$2a$10$UClj/ChiXzRXcT6HvSdOpePZFzTIJs9CYDagygovYbM0TDzMRe7Di'
    * def payload = {username: '#(username)', email: '#(email)' , password: '#(password)' }
    Given path 'users'
    And request payload
    When method post
    Then status 403
