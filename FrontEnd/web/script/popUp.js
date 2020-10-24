$(function(){
    $(".popUp").hide();
	
	$(".time").click(function(){
        $(".close").text("저장");
		$(".showLog").hide();
		$(".setTime").show();
        $(".popUp").show();
		
		$('body').on('scroll touchmove mousewheel', function(event) {
		  event.preventDefault();
		  event.stopPropagation();
		  return false;
		});
    });
	
	var cType;
	
	$("tbody tr").click(function(){
		cType = $(this).children('#type');
        
        var checkType = $(this).children('#type').text();
		if(checkType == "검거" || checkType == "1"){
			$(".close").text("저장");
		}else{
			$(".close").text("닫기");
		}
        
		$(".setTime").hide();
		$(".showLog").show();
        $(".popUp").show();
		
		var type = $(this).children('#type').text();
		var color = $(this).children('#type').css('background-color');
		$('.sType').text(type);
		$('.sType').css('background-color', color);
		var name = $(this).children('#name').text();
		$('.sName').text(name);
		var num = $(this).children('#num').text();
		$('.sNum').text(num);
		var date = $(this).children('#date').text();
		$('.sDate').text(date);
		if(type == "검거" || type == "1"){
			$(".stateChange").show();
		}else{
			$(".stateChange").hide();
		}
		
		
		$('body').on('scroll touchmove mousewheel', function(event) {
		  event.preventDefault();
		  event.stopPropagation();
		  return false;
		});
    });
	
	var state = 0;
	$('.stateBtn').click(function(){
		if(state == 0){
			$('.stHandle').css('left','50%');
			$('.left').css('color','#000');
			$('.right').css('color','#ecf0f3');
			state = 1;
		} else{
			$('.stHandle').css('left','0%');
			$('.right').css('color','#000');
			$('.left').css('color','#ecf0f3');
			state = 0;
		}
	});
	
    $(".close").click(function(){
		if(state == 1){
			var currentNum = $('.current').text();
			$('.current').text(currentNum - 1);
			cType.text("정상");
			cType.css("background-color","#289940");
			$('.stHandle').css('left','0%');
			$('.right').css('color','#000');
			$('.left').css('color','#ecf0f3');
			state = 0;
			
		}
        $(".popUp").hide(1000, 'swing');
		$('body').off('scroll touchmove mousewheel');
    });
    
});