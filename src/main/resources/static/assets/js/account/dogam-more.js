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
                    url: "/account/dogam/more",
                    cache: false,
                    success: function (dictionaries) {
                        var sample = $("#dictionary").clone();
                        sample.find("figure").removeClass("aos-animate");
                        sample.find("img").attr("src", " ");
                        $.each(dictionaries, function (i, data) {
                            var clone = sample.clone(true);
                            clone.find("a").attr("href", "/dictionary/" + data.dictionary.dicNo);
                            clone.find("img").attr("src", data.dictionary.thumbnail);
                            clone.find("figcaption").text(data.dictionary.name);
                            clone.appendTo("#dictionaries");
                        });
                        if (dictionaries.length == 0) {
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