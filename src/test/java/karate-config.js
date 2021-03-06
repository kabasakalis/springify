function() {
  karate.log('CONFIGURATION STARTSs');
  karate.log('admin pwd  ', karate.properties['karate.adminpwd']);
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
  var uuid = java.util.UUID.randomUUID();
  config.uuid = uuid.toString() ;
  config.baseUrl = 'http://127.0.0.1:' + port + '/api/' ;
  config.adminpwd = karate.properties['karate.adminpwd'];
  karate.set('baseUrl', config.baseUrl);
  //next call should be once for the whole test suite
  //unfortunately callSingle, as explained in karate docs, is not available.
  var result = karate.call('classpath:springifyapi/common/setHeaders.feature', {config: config});
  karate.configure('headers', result.defaultHeaders);
  config.auth = { jwtToken: result.token };
  karate.log('CONFIG OBJECT', karate.pretty(config));
  return config;
}
