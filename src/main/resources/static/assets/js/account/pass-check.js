$('#pass').change(function () {
    if ($('#pass').val() === $('#pass-new').val()) {
        $('#pass').addClass("is-valid");
        $('#pass').removeClass("is-invalid");
        $('#submit').removeAttr("disabled");
    } else {
        $('#invalid').text("비밀번호가 일치하지 않습니다.");
        $('#pass').removeClass("is-valid");
        $('#pass').addClass("is-invalid");
        $('#submit').attr("disabled", "");
    }
});