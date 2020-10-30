$(function(){
	var slt;
	var pw;
	$(".option").click(function(){
		slt = $(this).children('input').attr('id');
	});
	$(".pw").click(function(){
		$(this).css("border", "none");
	});
	$("#login").click(function(){
		login();
	});
	
	$(".pw").keypress(function(e) { 
		if (e.keyCode == 13){
			login();
		}    
	});
    
    function login(){
        pw = $(".pw").val();
		switch(slt){
			case "0" : if(pw=="a"){
                            location.href = 'management.html?unitName=전투지휘훈련단';
                        } else{
							$(".noneID").css("display","none");
                            $(".nonePW").css("display","block");
							$(".pw").css("border", "3px solid #ff0071");
                        }; break;
				
			case "1" :	if(pw=="aa"){
                            location.href = 'management.html?unitName=제9보병사단';
                        } else{
                            $(".noneID").css("display","none");
                            $(".nonePW").css("display","block");
							$(".pw").css("border", "3px solid #ff0071");
                        }; break;
				
			case "2" : if(pw=="aaa"){
                            location.href = 'management.html?unitName=수도기계화보병사단';
                        } else{
                            $(".noneID").css("display","none");
                            $(".nonePW").css("display","block");
							$(".pw").css("border", "3px solid #ff0071");
                        }; break;
				
			case "3" : if(pw=="aaaa"){
                            location.href = 'management.html?unitName=전투지휘훈련단';
                        } else{
                            $(".noneID").css("display","none");
                            $(".nonePW").css("display","block");
							$(".pw").css("border", "3px solid #ff0071");
                        }; break;
				
			default:
						$(".noneID").css("display","block");
                        $(".nonePW").css("display","none");
						;
		}
    }
});