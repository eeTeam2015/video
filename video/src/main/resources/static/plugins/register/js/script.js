// user
var user_Boolean = false;
var password_Boolean = false;
var varconfirm_Boolean = false;
var emaile_Boolean = false;
$('.reg_user').blur(function(){
  if ((/^[0-9a-zA-Z]{4,12}$/).test($(".reg_user").val())){
    $('.user_hint').html("✔").css("color","green");
    user_Boolean = true;
  }else {
    $('.user_hint').html("×").css("color","red");
    user_Boolean = false;
  }
});
// password
$('.reg_password').blur(function(){
  if ((/^[0-9a-zA-Z]{6,16}$/).test($(".reg_password").val())){
    $('.password_hint').html("✔").css("color","green");
    password_Boolean = true;
  }else {
    $('.password_hint').html("×").css("color","red");
    password_Boolean = false;
  }
});


// password_confirm
$('.reg_confirm').blur(function(){
  if (($(".reg_password").val())==($(".reg_confirm").val()) && $(".reg_confirm").val()!=''){
    $('.confirm_hint').html("✔").css("color","green");
    varconfirm_Boolean = true;
  }else {
    $('.confirm_hint').html("×").css("color","red");
    varconfirm_Boolean = false;
  }
});


// Email
$('.reg_email').blur(function(){
  if ((/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/).test($(".reg_email").val())){
    $('.email_hint').html("✔").css("color","green");
    emaile_Boolean = true;
  }else {
    $('.email_hint').html("×").css("color","red");
    emaile_Boolean = false;
  }
});



// click
$('.red_button').click(function(){
  if(user_Boolean && password_Boolean && varconfirm_Boolean && emaile_Boolean){
      $.ajax({
          url:window.register_path,
          type:"POST",
          dataType:"json",
          data:$("#register_form").serialize(),
          success:function (data) {
              if(data.code=="1"){
                  alert("注册成功");
                  location.href=window.head_page_path;
              }else{
                  if(data.error){
                      alert(data.error);
                  }else{
                      alert("注册失败！请稍后重试");
                  }
              }
          }
      });
  }else {
    alert("请完善信息");
  }
});
