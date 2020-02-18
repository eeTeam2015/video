layui.config({
    base: '/static/plugins/layui/lay/modules/'
    }).use(['table','layer', 'form','jquery'], function(){
    var table = layui.table;
    var form = layui.form;

    table.render({
        elem: '#permission',
        url: '/user/permission/select-sys-permission-info',
        toolbar: '#toolbar',
        title: '权限信息表',
        cellMinWidth: 80, //全局定义常规单元格的最小宽度
        cols:
            [
                [
                    {type: 'checkbox', fixed: 'left', width:40},
                    {field: 'name', title: '名称',sort: true},
                    {field: 'url', title: '路径',sort: true },
                    {field: 'description', title: '描述'},
                    {field: 'type', title: '类型'},
                    {field: 'remark', title: '备注'},
                    {fixed: 'right', title:'操作', toolbar: '#operate-bar', width:150}
                ]
            ]
        ,
        id:'permissionReload',
        page: true
    });

    var $ = layui.$, active = {
        reload: function(){
            var search_name = $('#search_name');

            //执行重载
            table.reload('permissionReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    name: search_name.val()
                }
            });
        }
    };

    $('.permissionTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //头工具栏事件
    table.on('toolbar(permission)', function(obj){
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
            case 'add-permission':
                action.add_or_update("新增",$('#add-form').html(),data);
                break;
        };
    });

    //监听行工具事件
    table.on('tool(permission)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('真的要删除权限【'+data.name+"】吗?", function(index){
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "/user/permission/delete-sys-permission-info",
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

            /**初始化值*/
            action.add_or_update("编辑",$('#add-form').html(),data);


        }
    });

    //编辑或者新增表格数据
    var action = {
        add_sys_permission_info_request:function(index,permission_add_form){
            $.ajax({
                type: "post",
                dataType: "json",
                url: "/user/permission/add-or-update-sys-permission-info",
                contentType: "application/json;charset=UTF-8",//指定消息请求类型
                data: JSON.stringify(permission_add_form),//将js对象转成json对象
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
        add_or_update: function (title, content,permission) {
            layer.open({
                title: title,
                area: ['600px','600px'],
                anim: 5,
                content: content,
                resize:'false',
                btn: ['保存', '取消'],
                success:function () {
                    var permission_add_layui_form = $('#permission-add-layui-form');

                    if (permission != undefined) {
                        //设置启用属性的初始值
                        permission_add_layui_form.find("#add_update_type option[val-name='"+permission.type+"']").attr("selected","selected");
                    }

                    $.ajax({
                        type: "get",
                        dataType: "json",
                        url: "/user/permission/get-all-sys-permission-info",
                        success: function (data_rtn) {
                            var add_update_pid = permission_add_layui_form.find("#add_update_pid");
                            var data_permission = data_rtn.list;
                            add_update_pid.append("<option value='0'>请选择</option>");
                            for (var i = 0; i < data_permission.length; i++) {
                                if (permission != undefined) {
                                    if(permission.id == data_permission[i].id){
                                        continue;
                                    }
                                    if(permission.pid == data_permission[i].id){
                                        add_update_pid.append("<option value='" + data_permission[i].id + "' selected='selected'>" + data_permission[i].name + "</option>");
                                    }else{
                                        add_update_pid.append("<option value='" + data_permission[i].id + "'>" + data_permission[i].name + "</option>");
                                    }
                                }else{
                                    add_update_pid.append("<option value='" + data_permission[i].id + "'>" + data_permission[i].name + "</option>");
                                }
                            }

                            if(permission!=undefined) {
                                delete permission['type'];
                                delete permission['pid'];
                                form.val('permission-add-layui-form', permission);
                                permission_add_layui_form.append($("#add_update_hidde_form").html());
                                $("#add_update_hidde_id").val(permission.id);
                            }
                            //表单渲染
                            form.render('select', 'permission-add-layui-form');
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
                yes: function (index, layero) {
                    var permission_add_form = layero.find('#permission-add-layui-form').serializeJSON();
                    var check = true;
                    if (permission_add_form.name == '') {
                        layer.alert('权限名称不能为空');
                        check = false;
                    } else if (permission_add_form.alias == '') {
                        layer.alert('权限别名不能为空');
                        check = false;
                    }
                    if (check) {
                        action.add_sys_permission_info_request(index,permission_add_form);
                    }
                }
            });
        }
    };
});