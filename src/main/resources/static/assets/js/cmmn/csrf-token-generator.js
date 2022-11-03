$.ajaxPrefilter(function (options) {
  var headerName = $("meta[name='_csrf_header']").attr("content");
  var token = $("meta[name='_csrf']").attr("content");
  if (options.method === 'POST') {
    options.headers = options.headers || {};
    options.headers[headerName] = token;
  }
});