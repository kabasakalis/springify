@ignore
Feature: configure headers per user role

Background:
* print 'Configuration passed to setHeaders: ', karate.pretty(config)
* def creds = read('classpath:springifyapi/data/'+ config.user + '.json')
* url baseUrl


Scenario:
Given url baseUrl
And path 'login'
And request creds
When method post
Then status 200

* def token = response.token
* print 'jwtToken has been assigned: ', karate.pretty(token)
* def defaultHeaders =  callonce read('classpath:springifyapi/common/HeadersFun.js')  { jwtToken:  '#(token)' }
* configure headers = defaultHeaders
