$('#verify-submit').on("", function(){
    	var memEmail = $('.form-control.email-imput.form-control.mt-2.mb-2').val();			// .form-control.email-imput.form-control.mt-2.mb-2에 입력되는 값
    	var data = {memEmail : memEmail}				// '컨트롤에 넘길 데이터 이름' : '데이터(.form-control.email-imput.form-control.mt-2.mb-2에 입력되는 값)'
    	
    	$.ajax({
    		type : "post",
    		url : "registerCheck",
    		data : data,
    		success : function(result){
    			if(result != 'fail'){
    				$('#id_input_re_1').css("display","inline-block");
    				$('#id_input_re_2').css("display", "none");				
    			} else {
    				$('#id_input_re_2').css("display","inline-block");
    				$('#id_input_re_1').css("display", "none");				
    			}
    		}
    	}); // ajax 종료		
	});