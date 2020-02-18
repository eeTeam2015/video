
// 全选样例
function checkAll(dst) {
    layui.use(['jquery', 'layer', 'authtree'], function () {
        var layer = layui.layer;
        var authtree = layui.authtree;

        authtree.checkAll(dst);
    });
}

// 全不选样例
function uncheckAll(dst) {
    layui.use(['jquery', 'layer', 'authtree'], function () {
        var layer = layui.layer;
        var authtree = layui.authtree;

        authtree.uncheckAll(dst);
    });
}

// 显示全部
function showAll(dst) {
    layui.use(['jquery', 'layer', 'authtree'], function () {
        var layer = layui.layer;
        var authtree = layui.authtree;

        authtree.showAll(dst);
    });
}

// 隐藏全部
function closeAll(dst) {
    layui.use(['jquery', 'layer', 'authtree'], function () {
        var layer = layui.layer;
        var authtree = layui.authtree;

        authtree.closeAll(dst);
    });
}

layui.config({
    base: '/static/plugins/layui/lay/modules/'
    }).extend({
        authtree: 'authtree'
    }).use(['layer', 'form','authtree','jquery','table'], function(){
    var table = layui.table;
    var form = layui.form;
    var authtree = layui.authtree;

    table.render({
        elem: '#role',
        url: '/user/role/select-sys-role-info',
        toolbar: '#toolbar',
        title: '角色信息表',
        cellMinWidth: 80, //全局定义常规单元格的最小宽度
        cols:
            [
                [
                    {type: 'checkbox', fixed: 'left', width:40},
                    {field: 'name', title: '角色名称',sort: true},
                    {field: 'alias', title: '角色别名',sort: true},
                    {field: 'remark', title: '备注'},
                    {fixed: 'right', title:'操作', toolbar: '#operate-bar', width:200}
                ]
            ]
        ,
        id:'roleReload',
        page: true
    });

    var $ = layui.$, active = {
        reload: function(){
            var search_name = $('#search_name');
            var search_alias = $('#search_alias');

            //执行重载
            table.reload('roleReload', {
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

    $('.roleTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //头工具栏事件
    table.on('toolbar(role)', function(obj){
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
            case 'add-role':
                action.add_or_update("新增",$('#add-form').html());
                break;
            case 'assign-permission':
                var data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg("请选择一个!");
                    return;
                }
                //分配权限
                action.assgin_permission("分配权限",$("#role-permission").html(),data);
                break;
        };
    });

    //监听行工具事件
    table.on('tool(role)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('真的要删除角色【'+data.name+"】吗?", function(index){
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "/user/role/delete-sys-role-info",
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
            /**
             * 初始化值
             */
            action.add_or_update("编辑",$('#add-form').html());
            var role_add_layui_form = $('#role-add-layui-form');
            form.val('role-add-layui-form',data);
            role_add_layui_form.append($("#add_update_hidde_form").html());
            $("#add_update_hidde_id").val(data.id);
        }
    });



    //编辑或者新增表格数据
    var action = {
        add_sys_role_info_request:function(index,role_add_form){
            $.ajax({
                type: "post",
                dataType: "json",
                url: "/user/role/add-or-update-sys-role-info",
                contentType: "application/json;charset=UTF-8",//指定消息请求类型
                data: JSON.stringify(role_add_form),//将js对象转成json对象
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
        add_or_update: function (title, content) {
            layer.open({
                title: title,
                area: ['600px','600px'],
                anim: 5,
                content: content,
                resize:'false',
                btn: ['保存', '取消'],
                yes: function (index, layero) {
                    var role_add_form = layero.find('#role-add-layui-form').serializeJSON();
                    var check = true;
                    if (role_add_form.name == '') {
                        layer.alert('角色名称不能为空');
                        check = false;
                    } else if (role_add_form.alias == '') {
                        layer.alert('角色别名不能为空');
                        check = false;
                    }
                    if (check) {
                        action.add_sys_role_info_request(index,role_add_form);
                    }
                }
            });
        },
        assgin_permission:function (title,content,role) {
            layer.open({
                title: title,
                area: ['600px','600px'],
                anim: 5,
                content: content,
                resize:'false',
                btn: ['保存', '取消'],
                yes: function (index) {
                    // 获取所有已选中节点
                    var checked = authtree.getChecked('#LAY-auth-tree-index');
                    var role_permission_relation_query_vo = {};
                    role_permission_relation_query_vo['roleId'] = role[0].id;
                    role_permission_relation_query_vo['permissionIds'] = checked;
                    //获取权限信息树结构
                    $.ajax({
                        url: '/user/role/add-role-permission-relation',
                        dataType: 'json',
                        type:'post',
                        contentType: "application/json;charset=UTF-8",//指定消息请求类型
                        data:JSON.stringify(role_permission_relation_query_vo),
                        success: function(data){
                            if (data.code == '0') {
                                //关闭弹窗
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


                },
                success:function () {
                    //获取权限信息树结构
                    $.ajax({
                        url: '/user/permission/get-all-sys-permission-info',
                        dataType: 'json',
                        data:"roleId="+role[0].id,
                        success: function(data){

                            //普通列表转换树接口数据
                            var trees = authtree.listConvert(data.list, {
                                primaryKey: 'id'
                                ,startPid: '0'
                                ,parentKey: 'pid'
                                ,nameKey: 'name'
                                ,valueKey: 'id'
                                ,checkedKey: data.checkedId
                            });
                            //渲染树
                            authtree.render('#LAY-auth-tree-index', trees, {
                                autowidth: true
                            });
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
            });
        },

    };
});