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
    <title>角色列表</title>
    <%@ include file="/common/bootstarp-ui.jsp"%>

    <script language="JavaScript">
        $(function() {
            query();
        });

        function query(){
            var queryParams = {};
            queryParams.roleId= $("#roleId").val();
            queryParams.roleName= $("#roleName").val();
            queryParams.rolems= $("#rolems").val();
            defaultGrid.queryParams = queryParams;
            defaultGrid.url = _ctx + "/role/list";
            defaultGrid.onDblClickRow = function(rowIndex, rowData) {
                var roleId = rowData.roleId;
                edit(roleId);
            };
            defaultGrid.columns =  [ [ {
                field : 'roleId',
                title : '角色代码',
                width : fixWidth(0.2),
                align : 'center'
            }, {
                field : 'roleName',
                title : '角色名称',
                width : fixWidth(0.3),
                align : 'center'
            }, {
                field : 'rolems',
                title : '角色描述',
                width : fixWidth(0.3),
                align : 'center'
            },{
                field: 'opt', title: '操作', width: 50, align: 'center',width : fixWidth(0.2),
                formatter: function (value, rec) {
                    var btn = '<a  onclick="relation(\''+rec.roleId+'\')" style="color:blue">关联功能菜单</a>'
                    return btn;
                }
            }
            ] ];
            $("#table").datagrid(defaultGrid);
        }

        function relation(roleId){
                layer.open({
                    type : 2,
                    title : false,
                    skin:'layui-layer-rim',
                    area : [ '50%', '100%' ],
                    shadeClose : false,
                    closeBtn : 2,
                    content : [ _ctx + '/role/onRelation?roleId='+roleId, 'no' ]
                });
        }


        function add(){
                layer.open({
                    type : 2,
                    title : false,
                    skin:'layui-layer-rim',
                    area : [ '80%', '100%' ],
                    shadeClose : false,
                    closeBtn : 2,
                    content : [ _ctx + '/role/onEdit',
                        'no' ],
                    success : function(layero, index) {
                    }
                });
        }

        function edit(roleId){

                layer.open({
                    type : 2,
                    title : false,
                    skin:'layui-layer-rim',
                    area : [ '80%', '100%' ],
                    shadeClose : false,
                    closeBtn : 2,
                    content : [ _ctx + '/role/onEdit?roleId=' + roleId,
                        'no' ],
                    success : function(layero, index) {
                    }
            });
        }


        function resetClick(){
            $("#roleId").val("");
            $("#roleName").val("");
            $("#rolems").val("");
        }

    </script>
</head>
<body>
<div id="container" class="container-fluid" >
    <div class="row-fluid">
        <div class="col-md-12 column">
            <h3>
                角色维护
            </h3>
        </div>
    </div>
    <div class="row-fluid">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-1 control-label">角色代码</label>
                <div class="col-sm-4">
                    <input class="form-control" id="roleId" type="text" />
                </div>
                <label class="col-sm-1 control-label" >角色名称</label>
                <div class="col-sm-4">
                    <input class="form-control" id="roleName" type="text" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 control-label" >角色描述</label>
                <div class="col-sm-4">
                    <input class="form-control" id="rolems" type="text" />
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