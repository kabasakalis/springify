@ignore
Feature: configure headers per user role

Background:
* print 'Config passed: ', karate.pretty(config)
* def creds = read('classpath:springifyapi/data/'+ config.user + '.json')
* url baseUrl


Scenario:
Given url baseUrl
And path 'login'
And request creds
When method post
Then status 200

* def jwtToken = response.jwtToken
* print 'jwtToken OK: ', karate.pretty(jwtToken)
* def defaultHeaders =  callonce read('classpath:springifyapi/common/HeadersFun.js')  { jwtToken:  '#(jwtToken)' }
* configure headers = defaultHeaders
