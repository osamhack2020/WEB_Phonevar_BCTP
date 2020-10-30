function tableintype() {
    console.log(11);
      var num = $('tr').length-1;
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