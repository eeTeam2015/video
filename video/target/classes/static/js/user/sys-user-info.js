layui.config({
        base: '/static/plugins/layui/lay/modules/'
    }).extend({
        formSelects: 'formSelects-v4'
    }).use(['form', 'layedit', 'laydate','table','layer','jquery','formSelects'], function(){
    var table = layui.table;
    var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
    var formSelects = layui.formSelects;
    table.render({
        elem: '#user',
        url: '/user/select-sys-user-info',
        toolbar: '#toolbar',
        title: '用户信息表',
        cellMinWidth: 80, //全局定义常规单元格的最小宽度
        cols:
            [
                [
                    {type: 'checkbox', fixed: 'left', width:40},
                    {field: 'name', title: '用户',sort: true},
                    {field: 'account', title: '账户',sort: true },
                    {field: 'status', title: '状态'},
                    {field: 'enable', title: '启用'},
                    {field: 'lastLoginTime', title: '最后登录时间'},
                    {field: 'lastLoginIpAddress', title: '最后登录Ip地址'},
                    {field: 'loginCount', title: '登录总次数'},
                    {field: 'remark', title: '备注'},
                    {fixed: 'right', title:'操作', toolbar: '#operate-bar', width:150}
                ]
            ]
        ,
        id:'userReload',
        page: true
    });

    var $ = layui.$, active = {
        reload: function(){
            var search_name = $('#search_name');
            var search_alias = $('#search_alias');

            //执行重载
            table.reload('userReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    name: search_name.val(),
                    alias: search_alias.val()
                }
            });
        }
    };

    $('.userTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //头工具栏事件
    table.on('toolbar(user)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：'+ data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选': '未全选');
                break;
            case 'add-user':
                action.add_or_update("新增",$('#add-form').html());
                break;
        };
    });

    //监听行工具事件
    table.on('tool(user)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('真的要删除用户【'+data.name+"】吗?", function(index){
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "/user/delete-sys-user-info",
                    data:"id="+data.id,
                    success: function (data) {
                        if (data.code == '0') {
                            obj.del();
                            active.reload();
                            layer.close(index);
                        }
                        layer.msg(data.message);
                    },
                    error:function (e) {
                        if(e.responseJSON.status=='403'){
                            layer.msg(e.responseJSON.message+":你没有操作权限!");
                        }else{
                            layer.msg(e.responseJSON.message);
                        }
                    }
                });

            });
        } else if(obj.event === 'edit'){
            //初始化表单
            action.add_or_update("编辑",$('#add-form').html(),data);

            //获取表单对象
            var user_add_layui_form = $('#user-add-layui-form');

            //设置启用属性的初始值
            user_add_layui_form.find("#add_update_enable option[val-name='"+data.enable+"']").attr("selected","selected");

            //表单初始赋值
            delete data['enable'];
            form.val('user-add-layui-form', data);

            //表单渲染
            form.render('select','user-add-layui-form');

            //添加隐藏id
            user_add_layui_form.append($("#add_update_hidde_form").html());
            $("#add_update_hidde_id").val(data.id);
        }
    });

    //编辑或者新增表格数据
    var action = {
        add_sys_user_info_request:function(index,user_add_form){
            $.ajax({
                type: "post",
                dataType: "json",
                url: "/user/add-or-update-sys-user-info",
                contentType: "application/json;charset=UTF-8",//指定消息请求类型
                data: JSON.stringify(user_add_form),//将js对象转成json对象
                success: function (data) {
                    if (data.code == '0') {
                        //关闭弹窗
                        layer.close(index);
                        active.reload();
                    }
                    layer.msg(data.message);
                },
                error:function (e) {
                    if(e.responseJSON.status=='403'){
                        layer.msg(e.responseJSON.message+":你没有操作权限!");
                    }else{
                        layer.msg(e.responseJSON.message);
                    }
                }
            });
        },
        add_or_update: function (title, content,data) {
            layer.open({
                title: title,
                area: ['600px','600px'],
                anim: 5,
                content: content,
                resize:'false',
                btn: ['保存', '取消'],
                yes: function (index, layero) {
                    var user_add_form = layero.find('#user-add-layui-form').serializeJSON();
                    var check = true;
                    if (user_add_form.name == '') {
                        layer.alert('用户名称不能为空');
                        check = false;
                    } else if (user_add_form.account == '') {
                        layer.alert('账户不能为空');
                        check = false;
                    }
                    if (check) {
                        //获取角色选中值添加到传入后台的参数中
                        var value = formSelects.value('role-select', 'valStr');
                        user_add_form['role'] = value;

                        //请求后台进行更新
                        action.add_sys_user_info_request(index,user_add_form);
                    }
                }
            });

            //获取所有的角色信息并初始化角色信息
            action.get_all_sys_role_info_request(data);
            //表单渲染
            form.render();
        },
        /**
         * 获取所有角色信息请求后台
         * @param index
         * @param user_add_form
         */
        get_all_sys_role_info_request:function(user_data){
            $.ajax({
                type: "get",
                dataType: "json",
                url: "/user/role/get-all-sys-role-info",
                contentType: "application/json;charset=UTF-8",//指定消息请求类型
                success: function (data) {

                    if (user_data == undefined) {
                        //新增
                        $("#add_update_role").empty();
                        for (var i = 0; i < data.length; i++) {
                            var role = data[i];
                            $("#add_update_role").append("<option value=" + role.id + ">" + role.name + "</option>");
                        }
                        formSelects.render();
                    }else {
                        //查询关联角色信息
                        $.ajax({
                            type: "get",
                            dataType: "json",
                            url: "/user/select-sys-user-relation-role-info",
                            data:"userId="+user_data.id,
                            success: function (relation_data) {
                                $("#add_update_role").empty();
                                for (var i = 0; i < data.length; i++) {
                                    var role = data[i];
                                    var flag = false;
                                    for (var j = 0; j < relation_data.length; j++) {
                                        var relation_role = relation_data[j];
                                        if (relation_role.roleId == role.id) {
                                            $("#add_update_role").append("<option value=" + role.id + " selected='selected'>" + role.name + "</option>");
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if(!flag){
                                        $("#add_update_role").append("<option value="+role.id+">"+role.name+"</option>");
                                    }
                                }
                                formSelects.render();
                            },
                            error:function (e) {
                                if(e.responseJSON.status=='403'){
                                    layer.msg(e.responseJSON.message+":你没有操作权限!");
                                }else{
                                    layer.msg(e.responseJSON.message);
                                }
                            }
                        });
                    }
                },
                error:function (e) {
                    if(e.responseJSON.status=='403'){
                        layer.msg(e.responseJSON.message+":你没有操作权限!");
                    }else{
                        layer.msg(e.responseJSON.message);
                    }
                }
            });
        }
    };
});