$('#input-pass-Check').on("propertychange change keyup paste", function() {
	console.log("js 실행");
	var newPass = $('#input-new-pass').val();
	var passCheck = $('#input-pass-Check').val();
	var data = { newPass: newPass, passCheck: passCheck }

	$.ajax({
		type: "post",
		url: "newPassCheck",
		data: data,
		success: function(result) {
			if (result != 'fail') {
				$('.pass_input_re_1').css("display", "inline-block");
				$('.pass_input_re_2').css("display", "none");
			} else {
				$('.pass_input_re_2').css("display", "inline-block");
				$('.pass_input_re_1').css("display", "none");
			}
		}
	}); // ajax 종료		
});