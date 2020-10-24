$(function () {
	$('body').scroll( function() {
		if ($(this).scrollTop() > 200) {
			$('.goToTop').css("bottom", "1%");
			$('.scrollHeader').css("top", "0%");
		} else {
			$('.goToTop').css("bottom", "-10%");
			$('.scrollHeader').css("top", "-20%");
		}
	});
	
	$('.goToTop').click( function() {
		$('body').scrollTop(0);
		/*$('body').animate({
			scrollTop:0
		}, 400);*/
		return false;
	});
});