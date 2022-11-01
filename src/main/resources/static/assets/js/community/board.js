$('#loading').css("display", "none");

$(document).ready(function () {
	var flag = true;

	$(document).on('scroll', $.throttle(500, function () {
		check_if_needs_more_content();
	}));

	function check_if_needs_more_content() {

		var pixelsFromWindowBottomToBottom = 0 + $(document).height() - $(window).scrollTop() - $(window).height();

		if (pixelsFromWindowBottomToBottom < 300) {
			if (flag) {
				$('#loading').css("display", "inline");
				$.ajax({
					type: "post",
					url: "/board/more",
					cache: false,
					success: function (boards) {
						$.each(boards, function (i, data) {
							var clone = $("#board").clone();
							clone.find("#bdNo").attr("href", "/board/" + data.bdNo);
							clone.find("#bdTitle").text(data.bdTitle);
							clone.find("#bdContent").text(data.bdContent);
							clone.find("#modifiedDate").text(convertMysqlDate2Js(data.modifiedDate));
							clone.find("#nickname").text(data.account.nickname);
							clone.find("#bdReplyCount").text(data.bdReplyCount);
							clone.find("#bdFavorite").text(data.bdFavorite);
							clone.find("#bdThumbnail").attr("src", data.bdThumbnail);
							clone.appendTo("#boards");
						});
						if (boards.length < 12) {
							console.log("더이상 없습니다.");
							flag = false;
						}
					},
					error: function () {
						console.log("조회 실패");
						flag = false;
					},
					complete: function () {
						$('#loading').css("display", "none");
					}
				});
			}
		}
	}
});

function convertMysqlDate2Js(MySQLDate) {
	let date = MySQLDate.split('T')[0];
	return date;
}