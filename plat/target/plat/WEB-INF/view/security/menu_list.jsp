<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"
       scope="page" />
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, user-scalable=no" />
    <meta charset="UTF-8" />
    <title>系统菜单列表</title>
    <%@ include file="/common/static.jsp"%>
</head>
<body>
<div id="container" class="layui-fluid">
    <div class="layui-collapse">

        <div class="layui-colla-item">
            <h2 class="layui-colla-title">查询</h2>
            <div class="layui-colla-content layui-show">
                <!-- 表单开始 -->
                <form class="layui-form" action="">
                <div class="layui-form-item">
                        <label class="layui-form-label">菜单名称</label>
                        <div class="layui-input-block" >
                            <input type="text" name="menuName" autocomplete="off" class="layui-input">
                        </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-form-label">所属系统</div>
                     <div class="layui-input-block">
                        <select name="systemId" lay-filter="systemId">
                            <option value=""></option>
                            <c:forEach items="${systemList}" var="system">
                               <option value="${system.systemId}">${system.systemName}</option>
                           </c:forEach>
                        </select>
                     </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">菜单类型</label>
                    <div class="layui-input-block">
                            <select name="menuType" lay-filter="menuType">
                                <option value=""></option>
                                <option value="0">菜单</option>
                                <option value="1">功能</option>
                            </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit="query" lay-filter="query">查询</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
                </form>
                <!-- 表单结束 -->
            </div>
        </div>
    </div><!--折叠箱结束 -->

    <table  id="table" lay-filter="table"></table>

    <script language="JavaScript">
        layui.use(['element', 'layer', 'table','jquery'], function(){
            var element = layui.element;
            var form = layui.form;
            var table = layui.table;
            var $ = layui.jquery;
           // var layer = layui.layer;

            defaultGrid.url = _ctx + '/menu/list';
            defaultGrid.cols = [ [ {type:'radio'},{
                field : 'menuId',
                title : '功能代码',
                hide:true
            }, {
                field : 'menuName',
                title : '功能名称',
                width : fixWidth(0.2),
                align : 'center'
            }, {
                field : 'menuJc',
                title : '功能简称',
                width : fixWidth(0.2),
                align : 'center'
            }, {
                field : 'menuUrl',
                title : '访问地址',
                width : fixWidth(0.2),
                align : 'center'
            }, {
                field : 'pmenuName',
                title : '上级功能菜单',
                width : fixWidth(0.2),
                align : 'center'
            }, {
                field : 'menuType',
                title : '菜单类型',
                width : fixWidth(0.2),
                align : 'center'
            }
            ] ];
            table.render(defaultGrid);

            form.on('submit(query)', function(data){
                defaultGrid.where = data.field;
                table.reload('table',defaultGrid);
                return false;
            });


            //监听工具条
            table.on('toolbar(table)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                var checkData = table.checkStatus('table').data; //idTest 即为基础参数 id 对应的值
                if(obj.event === 'add'){ //新增
                    layer.open({
                        type: 2,
                        title:'新增',
                        area: ['50%', '90%'],
                        maxmin:true,
                        content: [_ctx+'/menu/onEdit']
                    });
                } else if(obj.event === 'delete'){ //删除
                    if(checkData.length != 1){
                        layer.msg("请选择一行数据");
                        return ;
                    }
                    layer.confirm('真的删除行么', function(index){
                        layer.close(index);
                        $.post(_ctx + '/menu/delete',{menuId:checkData[0].menuId},function(data){
                            layer.msg(data.msg);
                            table.reload('table');
                        })
                    });
                } else if(obj.event === 'update'){ //编辑
                    if(checkData.length != 1){
                        layer.msg("请选择一行数据");
                        return ;
                    }
                    layer.open({
                        type: 2,
                        title:'编辑',
                        area: ['50%', '90%'],
                        maxmin:true,
                        content: [_ctx+'/menu/onEdit?menuId='+checkData[0].menuId]
                    });
                }
            });

            //监听行单击事件
            table.on('row(test)', function(obj){
                console.log(obj.tr) //得到当前行元素对象
                console.log(obj.data) //得到当前行数据
                //obj.del(); //删除当前行
                //obj.update(fields) //修改当前行数据
            });
            //监听行双击事件
            table.on('rowDouble(test)', function(obj){
                //obj 同上
            });
        });

    </script>

</div>
</body>
</html>