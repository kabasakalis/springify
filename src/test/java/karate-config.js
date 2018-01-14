function() {
  karate.log('CONFIGURATION STARTS');
  karate.configure('logPrettyRequest', true);
  karate.configure('logPrettyResponse', true);
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  var port = karate.properties['springify.server.port'];
  karate.log('PORT FROM SERVER', karate.pretty(port));
  if (!port) {
    port = 8081;
  }
  var config = {};
  config.user = 'admin' ;

  config.baseUrl = 'http://127.0.0.1:' + port + '/api/' ;
  karate.set('baseUrl', config.baseUrl);
  var result = karate.call('classpath:springifyapi/common/setHeaders.feature', {config: config});
  karate.configure('headers', result.defaultHeaders);
  config.auth = { jwtToken: result.jwtToken };
  karate.log('CONFIG OBJECT', karate.pretty(config));
  return config;
}
