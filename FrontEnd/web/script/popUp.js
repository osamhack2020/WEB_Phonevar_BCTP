function popup(){
	$(".popUp").hide();

	$(".time").click(function () {
		$(".close").text("저장");
		$(".showLog").hide();
		$(".setTime").show();
		$(".popUp").show();

		$('body').on('scroll touchmove mousewheel', function (event) {
			event.preventDefault();
			event.stopPropagation();
			return false;
		});
	});

	var cType;
	var userId;
	$("article table tbody tr").click(function () {
		cType = $(this).children('#type');
		userId = $(this).children('#id');
		console.log(userId.text());
		
		var checkType = $(this).children('#type').text();
		if (checkType == "검거" || checkType == "1") {
			$(".close").text("저장");
		} else {
			$(".close").text("닫기");
		}

		$(".setTime").hide();
		
		
		
		var phonevarlogAPI = "https://bctp.koreacentral.cloudapp.azure.com/phonevar/api/user/" + userId.text() + "/log";
		
		$.getJSON( phonevarlogAPI, {
			format: "json"
		})
		.done(function( data ) {
		$.each( data, function( i, item ) {
			if(i == "userLogs"){
				$.each( item, function( i, log) {
					var $tr = $('<tr></tr>').append(
						$('<td>').attr( "id", "logType" ).text(log.logType),
						$('<td>').attr( "id", "loggedTime" ).text(log.loggedAt),
						$('<td>').attr( "id", "createdTime" ).text(log.createdAt),
					);
					$('#loglist').append($tr);
					
				});
			}
		});
	
		if($(loglist).children().length == 0){
			$('#nologdata').text("데이터가 없습니다.");
		}
			
        });
		

		
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
		if (type == "검거" || type == "1") {
			$(".stateChange").show();
		} else {
			$(".stateChange").hide();
		}


		$('body').on('scroll touchmove mousewheel', function (event) {
			event.preventDefault();
			event.stopPropagation();
			return false;
		});
	});

	//검거상태 0
	//정상 1
	var state = 0;
	$('.stateBtn').click(function () {
		if (state == 0) {
			$('.stHandle').css('left', '50%');
			$('.left').css('color', '#000');
			$('.right').css('color', '#ecf0f3');
			state = 1;
		} else {
			$('.stHandle').css('left', '0%');
			$('.right').css('color', '#000');
			$('.left').css('color', '#ecf0f3');
			state = 0;
		}
	});

	$(".close").click(function () {
		if (state == 1) {
			data = {
				"id": userId,
				"statusCode": 200
			}
			console.log(data);
			url = "https://bctp.koreacentral.cloudapp.azure.com/phonevar/api/user";
			$.ajax({
				method: 'PUT',
				url: url,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8"
			});
			
			var currentNum = $('.current').text();
			$('.current').text(Number(currentNum) - 1);
			cType.text("정상");
			cType.css("background-color", "#289940");
			$('.stHandle').css('left', '0%');
			$('.right').css('color', '#000');
			$('.left').css('color', '#ecf0f3');
			state = 0;
		}
		$(".popUp").hide(1000, 'swing');
	});
}

