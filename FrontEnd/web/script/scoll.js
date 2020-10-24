$(function () {
	$('body').scroll(function () {
		if ($(this).scrollTop() > 200) {
			$('.goToTop').css("bottom", "1%");
			$('.scrollHeader').css("top", "0%");
			$('.preview').css({
				"position": "fixed",
				"box-shadow": "none",
				"box-shadow": "0 10px 20px #cbced1",
				"top": "12%",
				"left": "50%",
				"transform": "translate(-50%, -50%)"
			});
		} else {
			$('.goToTop').css("bottom", "-10%");
			$('.scrollHeader').css("top", "-20%");
			$('.preview').css({
				"position": "relative",
				"box-shadow": "none",
				"box-shadow": "13px 13px 20px #cbced1, -13px -13px 20px #fffff",
				"top": "",
				"left": "",
				"transform": ""
			});
		}
	});

	$('.goToTop').click(function () {
		$('body').scrollTop(0);
		/*$('body').animate({
			scrollTop:0
		}, 400);*/
		return false;
	});
});
