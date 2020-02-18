$(function(){
    try{
        $("img.lazy").lazyload({
            effect : "fadeIn"
        });
    }catch (e){
        //nothing
    }
    $("html").niceScroll();
    /*菜单*/
    $(".menu li a").wrapInner( '<span class="out"></span>');
    $(".menu li a").each(function() {
        $('<span class="over">' +  $(this).text() + '</span>').appendTo(this);
    });
    $(".menu li a").hover(function() {
        $(".out",this).stop().animate({'top':'48px'},300);
        $(".over",this).stop().animate({'top':'0px'},300);
    },function() {
        $(".out",this).stop().animate({'top':'0px'},300);
        $(".over",this).stop().animate({'top':'-48px'},300);
    });

    /*搜索框*/
    //通用头部搜索切换
    $('#search-hd .search-input').on('input propertychange',function(){
        var val = $(this).val();
        if(val.length > 0){
            $('#search-hd .pholder').hide(0);
        }else{
            var index = $('#search-bd li.selected').index();
            $('#search-hd .pholder').eq(index).show().siblings('.pholder').hide(0);
        }
    })
    $('#search-bd li').click(function(){
        var index = $(this).index();
        $('#search-hd .pholder').eq(index).show().siblings('.pholder').hide(0);
        $('#search-hd .search-input').eq(index).show().siblings('.search-input').hide(0);
        $(this).addClass('selected').siblings().removeClass('selected');
        $('#search-hd .search-input').val('');
    });

})