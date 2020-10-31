function tableintype() {
      var num = $('article table tbody tr').length;
        var tbType = $('#myDummyTable tbody tr td#type');
        var badGay = 0;

        for(var i = 0; i < num; i++){
            if(tbType.eq(i).text() == "0"){
                tbType.eq(i).css("background-color","#289940");
                tbType.eq(i).text("정상");
            }else{
                tbType.eq(i).css("background-color","#ef325a");
                tbType.eq(i).text("검거");
                badGay++;
            }
        }

        var color;
        var typeT;
        $('#myDummyTable tbody tr').hover(function(){
            typeT = $(this).children('#type').text();
            if(typeT == "0" || typeT == "정상"){
                $(this).children('#type').css("color", "rgb(236, 240, 243, 1)");
            }else if(typeT == "1" || typeT == "검거"){
                $(this).children('#type').css("color", "rgb(236, 240, 243, 1)");
            }
        }, function(){
            $(this).children('#type').css("color", "rgb(236, 240, 243, 0)");
        });

        $('.total').text(num);
        $('.current').text(badGay);
}

function logtableintype() {
      var num = $('#loglist tr').length;
        var tbType = $('#loglist tr td#logType');
        var badGay = 0;

        for(var i = 0; i < num; i++){
            if(tbType.eq(i).text() == "0"){
                tbType.eq(i).css("background-color","#ef325a");
                tbType.eq(i).text("꺼짐");
            }else if(tbType.eq(i).text() == "1"){
                tbType.eq(i).css("background-color","#289940");
                tbType.eq(i).text("켜짐");
                badGay++;
            }else{
				tbType.eq(i).css("background-color","#f5ab45");
                tbType.eq(i).text("에러");
                badGay++;
			}
        }

        var color;
        var typeT;
        $('#loglist tr').hover(function(){
            typeT = $(this).children('#type').text();
            if(typeT == "0" || typeT == "꺼짐"){
                $(this).children('#logType').css("color", "rgb(236, 240, 243, 1)");
            }else if(typeT == "1" || typeT == "켜짐"){
                $(this).children('#logType').css("color", "rgb(236, 240, 243, 1)");
            }else if(typeT == "2" || typeT == "에러"){
                $(this).children('#logType').css("color", "rgb(236, 240, 243, 1)");
            }
        }, function(){
            $(this).children('#logType').css("color", "rgb(236, 240, 243, 0)");
        });

}