$(document).ready(function () {
  $("#btn-toast").click(function () {
    $.ajax({
      type: "put",
      url: "/dictionary/dogam/" + sdictionary.dicNo,
      data: sdictionary,
      cache: false,
      async: true,
      success: function (data) {
        console.log("결과>", data);
        if (data) {
          $('#toast-dogam-success').toast('show');
        } else {
          $('#toast-dogam-fail').toast('show');
        }
      }
    })
  });
});