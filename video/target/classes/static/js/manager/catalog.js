$(function () {
    $(".nav-title").click(function () {
        $(this).parent().find(".nav2-ul").toggle();
        if($(this).find(".nav-icon").css("background-position")=="-144px -27px"){
            $(this).find(".nav-icon").css("background-position","-92px -27px");
        }else{
            $(this).find(".nav-icon").css("background-position","-144px -27px");
        }
    });

    $(".add_CataLog_btn").click(function () {
        var catalog_name = $(".catalog_name").text();
        if(catalog_name!=""){
            $.ajax({
                url: "/catalog/manager/addCataLog.html",
                type: "POST",
                dataType: "json",
                data: "name=" + catalog_name,
                success: backFunction,
                error:function (e) {
                    if(e.responseJSON.status=='403'){
                        alert(e.responseJSON.message+":你没有操作权限!");
                    }else{
                        alert(e.responseJSON.message);
                    }
                }
            });
        }else{
            alert("字段为空！");
        }
    });

    $(".add_SubClass_btn").click(function () {
        var subClass_name = $(".subClass_name").text();
        if(subClass_name!="") {
            $.ajax({
                url: "/catalog/manager/addSubClass.html",
                type: "POST",
                dataType: "json",
                data: "cataLogId=" + $(".cataLog_id").val() + "&name=" + $(".subClass_name").text(),
                success: backFunction,
                error:function (e) {
                    if(e.responseJSON.status=='403'){
                        alert(e.responseJSON.message+":你没有操作权限!");
                    }else{
                        alert(e.responseJSON.message);
                    }
                }
            });
        }else{
            alert("字段为空！");
        }
    });

    $(".add_Type_btn").click(function () {
        var type_name = $(".type_name").text();
        if(type_name!=""){
            $.ajax({
                url: "/catalog/manager/addType.html",
                type: "POST",
                dataType: "json",
                data: "subClassId=" + $(".subClass_id").val() + "&name=" + $(".type_name").text(),
                success: backFunction,
                error:function (e) {
                    if(e.responseJSON.status=='403'){
                        alert(e.responseJSON.message+":你没有操作权限!");
                    }else{
                        alert(e.responseJSON.message);
                    }
                }
            });
        }else{
            alert("字段为空！");
        }
    });

    $(".add_Loc_btn").click(function () {
        var loc_name = $(".loc_name").text();
        if(loc_name!=""){
            $.ajax({
                url: "/catalog/manager/addLoc.html",
                type: "POST",
                dataType: "json",
                data: "name=" + $(".loc_name").text(),
                success: backFunction,
                error:function (e) {
                    if(e.responseJSON.status=='403'){
                        alert(e.responseJSON.message+":你没有操作权限!");
                    }else{
                        alert(e.responseJSON.message);
                    }
                }
            });
        }else{
            alert("字段为空！");
        }
    });

    $(".add_Level_btn").click(function () {
        var level_name = $(".level_name").text();
        if(level_name!="") {
            $.ajax({
                url: "/catalog/manager/addLevel.html",
                type: "POST",
                dataType: "json",
                data: "name=" + $(".level_name").text(),
                success: backFunction,
                error:function (e) {
                    if(e.responseJSON.status=='403'){
                        alert(e.responseJSON.message+":你没有操作权限!");
                    }else{
                        alert(e.responseJSON.message);
                    }
                }
            });
        }else{
            alert("字段为空！");
        }
    });

    $(".add_Decade_btn").click(function () {
        var decade_name = $(".decade_name").text();
        if(decade_name!="") {
            $.ajax({
                url: "/catalog/manager/addDecade.html",
                type: "POST",
                dataType: "json",
                data: "name=" + $(".decade_name").text(),
                success: backFunction,
                error:function (e) {
                    if(e.responseJSON.status=='403'){
                        alert(e.responseJSON.message+":你没有操作权限!");
                    }else{
                        alert(e.responseJSON.message);
                    }
                }
            });
        }else{
            alert("字段为空！");
        }
    });

    $(".cataLogId_subClass").change(function () {
        var catalog_id = $(this).val();
        $.ajax({
            url: "/video/manager/getSubClass.html",
            type: "get",
            dataType: "json",
            data: "cataLogId=" + catalog_id,
            success: function (data) {
                $(".subClassId").find("option").remove();
                var jss = data;
                for (var i = 0; i < jss.length; i++) {
                    var op = "<option value='" + jss[i].id + "'>" + jss[i].name + "</option>";
                    $(".subClassId").append($(op));
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

    $.ajax({
        url: "/video/manager/getSubClass.html",
        type: "get",
        dataType: "json",
        data:"cataLogId=" + $(".cataLogId_subClass").val(),
        success: function (data) {
            var jss = data;
            for (var i = 0; i < jss.length; i++) {
                var op1 = "<option value='" + jss[i].id + "'>" + jss[i].name + "</option>";
                $(".subClassId").append($(op1));
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

function backFunction(data) {
    if (data != "0") {
        location.reload();
    }
}