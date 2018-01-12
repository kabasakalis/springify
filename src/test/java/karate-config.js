function() {
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);  
  var port = karate.properties['springifyapi.server.port'];
  if (!port) {
    // port = karate.env == 'web' ? 8090 : 8080;
      port = 8080;
  }
  var config = { springifyBaseUrl: 'http://127.0.0.1:' + port + '/api' };
  if (karate.env == 'proxy') {
    var proxyPort = karate.properties['springifyapi.proxy.port']
    karate.configure('proxy', 'http://127.0.0.1:' + proxyPort);
  }
  if (karate.env == 'contract') {
    config.paymentServiceUrl = karate.properties['payment.service.url'];
    config.queueName = karate.properties['shipping.queue.name'];
  }
  if (karate.env != 'mock' && karate.env != 'proxy' && karate.env != 'contract') {
    // 'callSingle' is guaranteed to run only once even across all threads
    // var result = karate.callSingle('classpath:springify/headers/common-noheaders.feature', config);
    // and it sets a variable called 'authInfo' used in headers-single.feature
   var jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJTcHJpbmdpZnkgVXNlcnMiLCJqdGkiOiJhZG1pbkBzcHJpbmdpZnkuY29tIiwic3ViIjoiYWRtaW4iLCJleHAiOjE1MTY0NjE5OTIsImlzcyI6ImNvbS5zcnBpbmdpZnkua2FiYXNha2FsaXMifQ.BCGnYl6-lpOPzdLHFtRkOy8hVzcmm95GyEggguby46PEYFQoQojLm57ikBQWx5nAu4Rbsd9C2WsUlwuXW89Mzg"
    // config.authInfo = { authTime: result.time, authToken: result.token };
      config.authInfo = { authToken: jwtToken };
  }
  return config;
}
