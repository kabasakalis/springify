# @ignore
Feature: User with USER role cannot access role endpoints

  Background:
    * url baseUrl
    * def config = {}
    * set config.user = 'user'
    * set config.adminpwd = ''
    * def result = callonce read('classpath:springifyapi/common/setHeaders.feature') {config: '#(config)'}
    * def auth_header = 'Bearer ' + result.token
    * configure headers = { Authorization:  '#(auth_header)' }

  Scenario: can read role
    Given path 'roles', 1
    When method get
    Then status 200

  Scenario: can read roles
    Given path 'roles'
    When method get
    Then status 200

  Scenario: cannot update role
    Given path 'roles', 4
    And request { name: 'WEAK_ROLE' }
    When method patch
    Then status 403

  Scenario: cannot delete role
    Given path 'roles', 1
    When method delete
    Then status 403

  Scenario: cannot create a new role
    * def payload = {name: 'NEW_ROLE' }
    Given path 'roles'
    And request payload
    When method post
    Then status 403
