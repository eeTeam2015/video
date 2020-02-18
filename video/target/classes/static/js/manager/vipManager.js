$(function () {
    $(".nav-title").click(function () {
        $(this).parent().find(".nav2-ul").toggle();
        if ($(this).find(".nav-icon").css("background-position") == "-144px -27px") {
            $(this).find(".nav-icon").css("background-position", "-92px -27px");
        } else {
            $(this).find(".nav-icon").css("background-position", "-144px -27px");
        }
    });

    $("#create_code").click(function () {
        $.ajax({
            url:"/pay/manager/createVipCode.html",
            type:"POST",
            dataType:"json",
            data:{
                num:5
            },
            success:function (data) {
                if(data.code=="1"){
                    alert("生成加油卡成功");
                    for (var i = 0; i < data.data.length; i++) {
                        var html = [];
                        html.push("<tr>");
                        html.push("<td>");
                        html.push(data.data[i].code);
                        html.push("</td>");
                        html.push("</tr>");
                        $("#create_code_new").append(html.join(''));
                    }
                }else{
                    alert("生成加油卡失败！加油卡号不存在");
                }
            },
            error:function (e) {
                if(e.responseJSON.status=='403'){
                    alert(e.responseJSON.message+":你没有操作权限!");
                }else{
                    alert(e.responseJSON.message);
                }
            }
        });
    });
})