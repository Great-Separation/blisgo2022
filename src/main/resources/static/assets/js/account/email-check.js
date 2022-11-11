$('#email').change(function () {
    let url = document.location.href.split("/").pop();
	$.ajax({
		type: "post",
		url: "check",
		async: false,
		dataType: 'json',
		data: { email: $('#email').val() },
		success: function (data) {
			if (data) {
				switch (url) {
					case "register":
						$('#valid').text("사용 가능한 메일입니다.");
						$('#email').addClass("is-valid");
						$('#email').removeClass("is-invalid");
						$('#submit').removeAttr("disabled");
						break;
					case "verify":
                        $('#invalid').text("등록되지 않은 메일입니다.");
						$('#email').addClass("is-invalid");
						$('#email').removeClass("is-valid");
						$('#submit').attr("disabled", "");
						break;
				}

			} else {
				switch (url) {
					case "register":
						$('#invalid').text("이미 사용중인 메일입니다.");
						$('#email').addClass("is-invalid");
						$('#email').removeClass("is-valid");
						$('#submit').attr("disabled", "");
						break;
					case "verify":
						$('#email').addClass("is-valid");
						$('#email').removeClass("is-invalid");
						$('#submit').removeAttr("disabled");
						$('#token').attr("value", document.location.href + "/" + btoa($('#email').val()));
						break;
				}
			}
		},
		error: function () {
			console.log("회원 조회 실패");
		},
		complete: function () {
		}
	});
});