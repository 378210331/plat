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
    <title>用户列表</title>
    <%@ include file="/common/bootstarp-ui.jsp"%>

    <script language="JavaScript">
        $(function() {
            $.get(_ctx+"/swjg/getSwjgTree",function(data){
                var zNodes = data;
                $.fn.zTree.init($("#swjgTree"), singleTreeSetting, zNodes);
                $(".treeObj").bind("click",function(){showMenu($(this));});//点击显示树
            });

            query();
        });

        function query(){
            var queryParams = {};
            queryParams.userName= $("#userName").val();
            queryParams.loginName= $("#loginName").val();
            queryParams.jgDm= $("#jgDm").val();
            defaultGrid.queryParams = queryParams;
            defaultGrid.url = _ctx + "/user/list";
            defaultGrid.onDblClickRow = function(rowIndex, rowData) {
                var userId = rowData.userId;
                edit(userId);
            };
            defaultGrid.columns =  [ [ {
                field : 'userId',
                title : '用户代码',
                align : 'center',
                hidden:true
            }, {
                field : 'userName',
                title : '用户名称',
                width : fixWidth(0.3),
                align : 'center'
            }, {
                field : 'loginName',
                title : '用户登录名',
                width : fixWidth(0.2),
                align : 'center'
            }, {
                field : 'swjgmc',
                title : '所属机构',
                width : fixWidth(0.2),
                align : 'center'
            }, {
                field : 'lrrq',
                title : '注册日期',
                width : fixWidth(0.1),
                align : 'center'
            }, {
                field : 'yxsjq',
                title : '有效日期起',
                width : fixWidth(0.1),
                align : 'center'
            }, {
                field : 'yxsjz',
                title : '有效日期止',
                width : fixWidth(0.1),
                align : 'center'
            }
            ] ];
            $("#table").datagrid(defaultGrid);
        }

        function add(){
                layer.open({
                    type : 2,
                    title : false,
                    skin:'layui-layer-rim',
                    area : [ '80%', '100%' ],
                    shadeClose : false,
                    closeBtn : 2,
                    content : [ _ctx + '/user/onEdit',
                        'no' ],
                    success : function(layero, index) {
                    }
                });
        }

        function edit(userId){
                layer.open({
                    type : 2,
                    title : false,
                    skin:'layui-layer-rim',
                    area : [ '80%', '100%' ],
                    shadeClose : false,
                    closeBtn : 2,
                    content : [ _ctx + '/user/onEdit?userId=' + userId,
                        'no' ],
                    success : function(layero, index) {
                    }
                });
        }

        function resetClick(){
            $("#userName").val("");
            $("#loginName").val("");
            $("#jgDm").val("");
            $("#jgDm").parent().find(".treeObj").val("");
        }

    </script>
</head>
<body>
<div id="container" class="container-fluid" >
    <div class="row-fluid">
        <div class="col-md-12 column">
            <h3>
                人员维护
            </h3>
        </div>
    </div>
    <div class="row-fluid">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-1 control-label">用户名称</label>
                <div class="col-sm-4">
                    <input class="form-control" id="userName" type="text" />
                </div>
                <label class="col-sm-1 control-label" >用户登录名称</label>
                <div class="col-sm-4">
                    <input class="form-control" id="loginName" type="text" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 control-label" >所属机关</label>
                <div class="col-sm-4">
                    <input class="treeObj form-control" type="text" readonly/>
                    <ul id="swjgTree" class="ztree" style="margin-top:0; width:100%; height: 100%;display:none;"></ul>
                    <input type="hidden" name="value" id="jgDm">
                </div>
            </div>

        </form>
    </div>
    <div class="row-fluid">
        <div class="text-center">
            <button type="button" class="btn btn-success" onclick="query()">搜索</button>
            <button type="button" class="btn btn-danger" onclick="resetClick()">重置</button>
            <button type="button" class="btn btn-primary" onclick="add()">新增</button>
        </div>
    </div>
    <div class="row-fluid">
        <div id="table"></div>
    </div>
</div>
</body>
</html>