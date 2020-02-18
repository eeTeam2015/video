/**
 * Created by 10589 on 2016/10/6.
 */

/*
eval(function (p, a, c, k, e, r) {
    e = function (c) {
        return c.toString(36)
    };
    if ('0'.replace(0, e) == 0) {
        while (c--) r[e(c)] = k[c];
        k = [function (e) {
            return r[e] || e
        }];
        e = function () {
            return '[2-8a-gi-s]'
        };
        c = 1
    }
    ;
    while (c--) if (k[c]) p = p.replace(new RegExp('\\b' + e(c) + '\\b', 'g'), k[c]);
    return p
}('function loadSlide(w,h){c 2=1;document.write(\'<d classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="e://a.macromedia.f/pub/b/cabs/g/swflash.cab#version=9,0,28,0" i="\'+w+\'" j="\'+h+\'"><3 4="movie" 5="/\'+7+\'8/6/6.k" /><3 4="l" 5="m"><3   4="wmode"   5="transparent"><3 4="allowscriptaccess" 5="always"><3 4="n" 5="o"><3 4="p" 5="2=\'+2+\'&q=/\'+7+\'8/6/"><r src="/\'+7+\'8/6/6.k" p="2=\'+2+\'&q=/\'+7+\'8/6/" l="m" pluginspage="e://www.adobe.f/b/a/a.cgi?P1_Prod_Version=ShockwaveFlash" 2="application/x-b-g" n="o" i="\'+w+\'" j="\'+h+\'"></r></d>\')}c s=new AJAX();s.setcharset("GBK");', [], 29, '||type|param|name|value|slide|sitePath|pic||download|shockwave|var|object|http|com|flash||width|height|swf|quality|high|allowfullscreen|true|flashvars|domain|embed|ajax'.split('|'), 0, {}));
*/


function reportErr(id){openWin("/"+sitePath+"js/err.html?id="+id,400,220,350,250,0)}

function kankan(id){openWins("http://kk.xunbo.cc/Html/GP"+id+".html",800,480,150,150,0)}

function pinglun(id,key){openWin("/"+sitePath+"comment/?id="+id+"&type=1&title="+encodeURI(key),800,480,150,150,0)}

function viewComment(url,type){
    var url;
    url+='&type='+(type=='news' ? 2 : 1);
    $.ajax.get(url,function(obj) {
            if (obj.responseText=="err"){
                set($("comment_list"),"<font color='red'>发生错误</font>")
            }else{
                set($("comment_list"),obj.responseText)
            }
        }
    );
}

function submitComment(id){
    if($("m_author").value.length<1){alert('请填写昵称');return false;}
    if($("m_content").value.length<1){alert('请填写内容');return false;}
    $.ajax.postf(
        $("f_comment"),function(obj){
            var x=obj.responseText;
            if(x=="ok"){
                viewComment(id,1);alert('小弟我感谢您的评论!');
            }else if(x=="havecomment"){
                alert('小样儿你手也太快了，歇会儿再来评论吧！');
            }else if(x=="cn"){
                alert('你必需输入中文才能发表');
            }else{
                alert('发生错误');
            }
        });
}

function diggVideo(id,div){
    $.ajax.get(
        "/"+sitePath+"inc/$.ajax.asp?id="+id+"&action=digg",function (obj){
            var returnValue=Number(obj.responseText)
            if (!isNaN(returnValue)){set($(div),returnValue);alert('(*^__^*) 嘻嘻……，顶得我真舒服！');}else if(obj.responseText=="err"){alert('顶失败')}else if(obj.responseText=="havescore"){alert('(*^__^*) 嘻嘻…… 这么热心啊，您已经顶过了！')}
        }
    );
}

function treadVideo(id,div){
    $.ajax.get("/"+sitePath+"inc/$.ajax.asp?id="+id+"&action=tread",function (obj){
            var returnValue=Number(obj.responseText)
            if(!isNaN(returnValue)){set($(div),returnValue);alert('小样儿，居然敢踩我！');}else if(obj.responseText=="err"){alert('踩失败')}	else if(obj.responseText=="havescore"){alert('我晕，您已经踩过了，想踩死我啊！')}
        }
    )
}

//--xiu shanchu--

function getVideoHit(vid){
    $.ajax.get("/"+sitePath+"inc/$.ajax.asp?action=hit&id="+vid,function (obj){
            var result=obj.responseText
            if(result=="err"){set($('hit'),'发生错误')}else{set($('hit'),result);}
        }
    );
}

function getNewsHit(nid){
    $.ajax.get("/"+sitePath+"inc/$.ajax.asp?action=hitnews&id="+nid,function (obj){
            var result=obj.responseText
            if(result=="err"){set($('hit'),'发生错误')}else{set($('hit'),result);}
        }
    );
}

function diggNews(id,div){
    $.ajax.get("/"+sitePath+"inc/$.ajax.asp?id="+id+"&action=diggnews",function (obj){
            var returnValue=Number(obj.responseText)
            if (!isNaN(returnValue)){set($(div),returnValue);alert('(*^__^*) 嘻嘻……，顶得我真舒服！');}else if(obj.responseText=="err"){alert('顶失败')}else if(obj.responseText=="havescore"){alert('(*^__^*) 嘻嘻…… 这么热心啊，您已经顶过了！')}
        }
    );
}

function treadNews(id,div){
    $.ajax.get("/"+sitePath+"inc/$.ajax.asp?id="+id+"&action=treadnews",function (obj){
            var returnValue=Number(obj.responseText)
            if(!isNaN(returnValue)){set($(div),returnValue);alert('小样儿，居然敢踩我！');}else if(obj.responseText=="err"){alert('踩失败')}	else if(obj.responseText=="havescore"){alert('我晕，您已经踩过了，想踩死我啊！')}
        }
    );
}

function markNews(vd,d,t,s,l,c){
    window['markscore'+(c==1 ? 1 : 0)](vd,d,t,s,parseInt(l)<0 ? 5 : l,'news');
}

function alertFrontWin(zindex,width,height,alpha,str){
    openWindow(zindex,width,height,alpha)
    set($("msgbody"),str)
}

function regexpSplice(url,pattern,spanstr) {
    pattern.exec(url);
    return (RegExp.$1+spanstr+ RegExp.$2);
}

function getPageValue(pageGoName){
    var pageGoArray,i,len,pageValue
    pageGoArray=getElementsByName('input',pageGoName) ; len=pageGoArray.length
    for(i=0;i<len;i++){
        pageValue=pageGoArray[i].value;
        if(pageValue.length>0){return pageValue;}
    }
    return ""
}

function getPageGoUrl(maxPage,pageDiv,surl){
    var str,goUrl
    var url=location.href
    pageNum=getPageValue(pageDiv)
    if (pageNum.length==0||isNaN(pageNum)){alert('输入页码非法');return false;}
    if(pageNum>maxPage){pageNum=maxPage;}
    pageNum=pageNum<1 || pageNum==1 ? '' : pageNum;
    location.href=surl.replace('<page>',pageNum).replace('-.','.').replace('_.','.').replace(/\?\.\w+/i,'');
}

function goSearchPage(maxPage,pageDiv,surl){
    getPageGoUrl(maxPage,pageDiv,surl);
}

function leaveWord(){
    if($("m_author").value.length<1){alert('昵称必须填写');return false;}
    if($("m_content").value.length<1){alert('内容必须填写');return false;}
    $.ajax.postf($("f_leaveword"),function(obj){
        var x=obj.responseText;
        if(x=="ok"){
            viewLeaveWordList(1);alert('留言成功，多谢支持！');$("m_content").value='';
        }else if(x=="haveleave"){
            alert('小样儿你手也太快了，歇会儿再来留言吧！');
        }else if(x=="haveleave"){
            alert('小样儿你手也太快了，歇会儿再来留言吧！');
        }else if(x=="cn"){
            alert('你必需输入中文才能发表');
        }else{
            alert('发生错误');
        }
    });
}

function viewLeaveWordList(page){
    view('leavewordlist');
    $.ajax.get(
        "/"+sitePath+"gbook.asp?action=list&page="+page+"&t="+(new Date()).getTime(),
        function(obj) {
            if (obj.responseText=="err"){
                set($("leavewordlist"),"<font color='red'>发生错误</font>")
            }else{
                set($("leavewordlist"),obj.responseText)
            }
        }
    );
}

function loginLeaveWord(){
    if($("m_username").value.length<1||$("m_pwd").value.length<1){alert('用户名密码不能为空');return false;}
    $.ajax.postf(
        $("f_loginleaveword"),
        function(obj){if(obj.responseText=="ok"){closeWin();alert('登陆成功');setLoginState();viewLeaveWordList(1);}else if(obj.responseText=="no"){alert('用户名或密码不正确');}else if(obj.responseText=="err"){alert('发生错误');}else{setLoginState();}}
    );
}

function setLoginState(){
    $.ajax.get(
        "/"+sitePath+"gbook.asp?action=state",
        function (obj){
            set($('adminleaveword'),obj.responseText);
        }
    );
}

function logOut(){
    $.ajax.get(
        "/"+sitePath+"gbook.asp?action=logout",
        function (obj){set($('adminleaveword'),'成功退出');}
    );
    setLoginState();viewLeaveWordList(1);
}

function delLeaveWord(id,page,type){
    $.ajax.get(
        "/"+sitePath+"gbook.asp?action=del&id="+id+"&type="+type,
        function (obj){
            if (obj.responseText=="ok"){viewLeaveWordList(page)}else if(obj.responseText=="err"){alert('发生错误')}else{viewLeaveWordList(page);}
        }
    );
}

function replyLeaveWord(id,page){
    alertFrontWin(101,400,160,50,"")
    set($("msgtitle"),"回复留言")
    set($("msgbody"),"<form id='replyleaveword'  action='/"+sitePath+"gbook.asp?action=reply&id="+id+"' method='post'><div><textarea id='m_replycontent'  name='m_replycontent' rows=7 cols=50></textarea><br><input type='button' value='回  复' onclick='submitReply("+page+")' class='btn' /> &nbsp;<span style='background-color:#CCCCCC;cursor:pointer;' onclick=$('m_replycontent').value+='[URL][/URL]'><b>[URL]</b></span></div></form>")
}

function viewLoginState(){
    alertFrontWin(101,300,100,50,"")
    set($("msgtitle"),"登陆留言板")
    set($("msgbody"),"<form id='f_loginleaveword'  action='/"+sitePath+"gbook.asp?action=login' method='post'  ><table><tr><td>用户名：</td><td><input type='input'  name='m_username' id='m_username' size=10 value=''/></td></tr><tr><td><input type='hidden' value='ok' name='m_login' />密码：</td><td><input type='password' id='m_pwd' name='m_pwd' size=10 value=''/></td></tr><tr><td><input type='submit' value='登  陆' class='btn'  onclick='loginLeaveWord();return false;'  /></td></tr></table></form>")
}

function submitReply(page){
    if($("m_replycontent").value.length<1){alert('回复不能为空');return false;}
    $.ajax.postf($("replyleaveword"),function(obj){if(obj.responseText=="ok"){closeWin();viewLeaveWordList(page)}else if(obj.responseText=="err"){alert('发生错误4');}else{viewLeaveWordList(page);}}
    );
}

function addFavorite(sURL, sTitle){
    try{ window.external.addFavorite(sURL, sTitle);}
    catch (e){
        try{window.sidebar.addPanel(sTitle, sURL, "");}
        catch (e)
        {alert("加入收藏失败，请使用Ctrl+D进行添加");}
    }
}

function setHome(obj,vrl,url){
    try{obj.style.behavior='url(#default#homepage)';obj.setHomePage(vrl);
        this.style.behavior='url(#default#homepage)';this.setHomePage(url);}
    catch(e){
        if(window.netscape){
            try{netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");}
            catch (e){alert("此操作被浏览器拒绝！请手动设置");}
            var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
            prefs.setCharPref('browser.startup.homepage',vrl);
        }
    }
}

function addFace(id) {
    $('m_content').value += '[ps:' + id +']';
}

function openWin(url,w,h,left,top,resize){
    window.open(url,'New_Win','toolbars=0, scrollbars=1, location=0, statusbars=0,menubars=0, resizable='+(resize)+',width='+w+',height='+h+',left='+left+',top='+top);
}

function openWins(url,w,h,left,top,resize){
    window.open(url,'New_Wins','toolbars=0, scrollbars=1, location=0, statusbars=0,menubars=0, resizable='+(resize)+',width='+w+',height='+h+',left='+left+',top='+top);
}

//============扩展评分(xiu)==========

function markVideo(vd,d,t,s){
    var textStart = Array("很烂","很烂","一般","一般","不妨一看","不妨一看","比较精彩","比较精彩","棒极了","棒极了");
    var id='BT'+(new Date()).getTime();
    $.ajax.get("/"+sitePath+"inc/$.ajax.asp?id="+vd+"&action=videoscore",function (obj){
        var a=obj.responseText
        try{
            a.replace(/\[(\d+),(\d+),(\d+)\]/i,function ($0,d,t,s){
                var x=parseInt(d)+parseInt(t),y=(Math.round(s / x * 10) / 10.0) || 0;
                document.getElementById('MARK_B1').value=x;
                document.getElementById('MARK_B2').value=y;
                document.getElementById('MARK_B3').value=s;
                if(s<=0){
                    document.getElementById("filmStarScore").innerHTML = "0<i>.0</i>";
                    document.getElementById("start").className = "starB s0";
                }else{
                    var ky = y.toString();
                    ky = ky.replace(".","<i>.");
                    ky = ky + "</i>";
                    document.getElementById("filmStarScore").innerHTML = ky;
                    document.getElementById("start").className = "starB s" + parseInt(y, 10).toString();
                    if(parseInt(y, 10)==0){ var ka = 0; }else{ var ka = parseInt(y, 10)-1; }
                    document.getElementById("filmStarScoreTip").innerHTML = textStart[ka]+"("+x+"评)";
                    if(document.getElementById("renums")){ document.getElementById("renums").innerHTML = x;	}
                }
            });
        }catch(ig){}
    });
}
function startm(v){
    var textStart = Array("很烂","很烂","一般","一般","不妨一看","不妨一看","比较精彩","比较精彩","棒极了","棒极了");
    document.getElementById("filmStarScoreTip").innerHTML = textStart[v-1];
    document.getElementById("start").className = "starB s" + v.toString();
    document.getElementById("filmStarScore").innerHTML = v + "<i>.0</i>"

}
function OnStar(mid,v){
    var textStart = Array("很烂","很烂","一般","一般","不妨一看","不妨一看","比较精彩","比较精彩","棒极了","棒极了");
    $.ajax.get("/"+sitePath+"inc/$.ajax.asp?id="+mid+"&action=score&score="+v,function (obj){
        var x=''+obj.responseText;
        if(x.indexOf("havescore")!=-1){
            alert('你已经评过分啦');
        }else{
            var x = parseFloat(document.getElementById("MARK_B1").value);
            var y = parseFloat(document.getElementById("MARK_B2").value);
            var z = parseFloat(document.getElementById("MARK_B3").value);
            var kx = x + 1;//人数
            var kz = z + v;//总分
            var ky = (Math.round(kz / kx * 10) / 10) || 0;
            document.getElementById("MARK_B1").value = kx;
            document.getElementById("MARK_B2").value = ky;
            document.getElementById("MARK_B3").value = kz;
            var kky = ky.toString();
            if(kky.indexOf(".")<0){
                kky = kky + ".0";
            }
            kky = kky.replace(".","<i>.");
            kky = kky + "</i>";
            document.getElementById("filmStarScore").innerHTML = kky;
            document.getElementById("start").className = "starB s" + (parseInt(ky, 10)-1).toString();
            document.getElementById("filmStarScoreTip").innerHTML = "感谢您的参与！";
            alert("已有"+kx+"人参与评分");
            //document.getElementById("filmStarScoreTip").innerHTML = textStart[ky]+"("+kx+"评)";
            if(document.getElementById("renums")){ document.getElementById("renums").innerHTML = kx;	}
            setTimeout(kaifach,10000);
        }
    });
}
function kaifach(){
    var textStart = Array("很烂","很烂","一般","一般","不妨一看","不妨一看","比较精彩","比较精彩","棒极了","棒极了");
    var x = parseFloat(document.getElementById("MARK_B1").value);
    var y = parseFloat(document.getElementById("MARK_B2").value);
    var z = parseFloat(document.getElementById("MARK_B3").value);

    var ky = y.toString();
    if(ky.indexOf(".")<0){
        ky = ky + ".0";
    }
    ky = ky.replace(".","<i>.");
    ky = ky + "</i>";
    document.getElementById("filmStarScore").innerHTML = ky;
    document.getElementById("start").className = "starB s" + parseInt(y, 10).toString();
    if(parseInt(y, 10)>0){
        document.getElementById("filmStarScoreTip").innerHTML = textStart[parseInt(y, 10)-1]+"("+x+"评)";
    }else{
        if(z>0){
            document.getElementById("filmStarScoreTip").innerHTML = textStart[0]+"("+x+"评)";
        }else{
            document.getElementById("filmStarScoreTip").innerHTML = "";
        }
    }


}

function getSubstr(downurl){
    var resultStr = downurl
    if(downurl.indexOf("ed2k://|file|")==0){
        var tmpStr =  resultStr.split('|');
        if(tmpStr.length>3){
            if(tmpStr[2].length>0){
                resultStr = decodeURIComponent(tmpStr[2]);
            }
        }

        return resultStr;
    }else{
        return resultStr;
    }
}