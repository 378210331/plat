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
    <title>系统列表</title>
    <%@ include file="/common/bootstarp-ui.jsp"%>


    <script language="JavaScript">
        $(function() {
            query();
        });

        function query(){
            var queryParams = {};
            queryParams.systemName= $("#systemName").val();
            queryParams.dec= $("#dec").val();
            queryParams.ip= $("#ip").val();
            queryParams.defaultUrl= $("#defaultUrl").val();
            defaultGrid.queryParams = queryParams;
            defaultGrid.url = _ctx + "/system/list";
            defaultGrid.onDblClickRow = function(rowIndex, rowData) {
                var systemId = rowData.systemId;
                edit(systemId);
            };
            defaultGrid.columns =  [ [ {
                field : 'systemId',
                title : '系统代码',
                align : 'center',
                hidden:true
            }, {
                field : 'systemName',
                title : '系统名称',
                width : fixWidth(0.2),
                align : 'center'
            }, {
                field : 'dec',
                title : '系统描述',
                width : fixWidth(0.4),
                align : 'center'
            }, {
                field : 'ip',
                title : '系统ip',
                width : fixWidth(0.2),
                align : 'center'
            }, {
                field : 'defaultUrl',
                title : '系统默认地址',
                width : fixWidth(0.2),
                align : 'center'
            }
            ] ];
            console.table(defaultGrid);
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
                    content : [ _ctx + '/system/onEdit',
                        'no' ],
                    success : function(layero, index) {
                    }
                });
        }

        function edit(systemId){
                layer.open({
                    type : 2,
                    title : false,
                    skin:'layui-layer-rim',
                    area : [ '80%', '100%' ],
                    shadeClose : false,
                    closeBtn : 2,
                    content : [ _ctx + '/system/onEdit?systemId=' + systemId,
                        'no' ],
                    success : function(layero, index) {
                    }
                });
        }

        function resetClick(){
            $("#systemId").val("");
            $("#systemName").val("");
            $("#dec").val("");
            $("#ip").val("");
            $("#defaultUrl").val("");
        }

    </script>
</head>
<body>
<div id="container" class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-12 column">
            <h3>
                子系统维护
            </h3>
        </div>
    </div>
    <div class="row-fluid">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-1 control-label">系统代码</label>
                <div class="col-sm-4">
                    <input class="form-control" id="systemId" type="text" />
                </div>
                <label class="col-sm-1 control-label" >系统名称</label>
                <div class="col-sm-4">
                    <input class="form-control" id="systemName" type="text" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 control-label" >系统描述</label>
                <div class="col-sm-4">
                    <input class="form-control" id="dec" type="text" />
                </div>
                <label class="col-sm-1 control-label" >系统ip</label>
                <div class="col-sm-4">
                    <input class="form-control" id="ip" type="text" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-1 control-label" >系统默认地址</label>
                <div class="col-sm-4">
                    <input class="form-control" id="defaultUrl" type="text" />
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
        <div id="table" ></div>
    </div>
</div>
</body>
</html>