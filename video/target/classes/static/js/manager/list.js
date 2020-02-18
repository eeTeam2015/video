$(function () {
    $(".nav-title").click(function () {
        $(this).parent().find(".nav2-ul").toggle();
        if ($(this).find(".nav-icon").css("background-position") == "-144px -27px") {
            $(this).find(".nav-icon").css("background-position", "-92px -27px");
        } else {
            $(this).find(".nav-icon").css("background-position", "-144px -27px");
        }
    });
})