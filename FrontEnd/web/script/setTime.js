$(function(){
	var weekdayStratTime;
    var weekdayEndTime;
	var holidayStratTime;
	var holidayEndTime;
	var day;
	
	function hideTime(){
        day = $("input[name='day']:checked").val();
		if(day == "평일"){
		   	$('.weekdayTime').show();
			$('.holidayTime').hide();
		} else {
			$('.weekdayTime').hide();
			$('.holidayTime').show();
		}
	}
	hideTime();
	
    $("input[name='day']").click(function(){
        hideTime();
    });
    
	$('.timeBox').focusout(function(){
		var time = $(this).val();
		var replaceTime = time.replace(/\:/g, "");
		var timeType = $(this).attr('id');
		
		if(replaceTime.length >= 4 && replaceTime.length < 5) {
            var hours = replaceTime.substring(0, 2);
            var minute = replaceTime.substring(2, 4);
			
            if(isFinite(hours + minute) == false) {
                alert("문자는 입력하실 수 없습니다.");
                $(this).val("00:00");
                return false;
            }
			
			if(hours + minute > 2400) {
                alert("시간은 24시를 넘길 수 없습니다.");
                $(this).val("24:00");
                return false;
            }
			
            if(minute > 60) {
                alert("분은 60분을 넘길 수 없습니다.");
                $(this).val(hours+":00");
                return false;
            }
			
			if(day == "평일"){
				if(timeType == "0"){
					weekdayStratTime = hours + ":" + minute;
				} else {
					weekdayEndTime = hours + ":" + minute;
				}
			} else {
				if(timeType == "0"){
					holidayStratTime = hours + ":" + minute;
				} else {
					holidayEndTime = hours + ":" + minute;
				}
			}
			
        }else{
            alert("시간을 다시 입력해주시기 바랍니다.");
        }
	});
	
});