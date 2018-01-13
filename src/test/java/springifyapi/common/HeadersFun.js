function(auth) {
  karate.log('AUTH', auth);
  var headers = {};
  headers['Content-Type'] = 'application/json';
  if (auth) {
    headers['Authorization'] = 'Bearer ' + auth.jwtToken;
  };
  return headers;
}
