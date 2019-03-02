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
    <title>角色编辑/新增</title>
    <%@ include file="/common/bootstarp-ui.jsp"%>

    <script language="JavaScript">
        $(function () {
            var roleId = $("#roleId").val();
            if(roleId != ""){
                $.get(_ctx + "/role/query?roleId="+roleId,function(data){
                    if(data.res){
                        var item = data.data[0];
                        $('#roleName').val(item.roleName);
                        $('#rolems').val(item.rolems);
                    }
                })
            }else{
                $('.delete').hide();
            }
        });

        function resetClick() {
            var _index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(_index);
        }

        function save() {
            var roleId = $("#roleId").val();
            var url = "";
            if(roleId ==""){
                url = _ctx + "/role/save"
            }else{
                url = _ctx + "/role/update"
            }
            var roleName =  $('#roleName').val();
            var rolems =  $('#rolems').val();
            if(roleName ==""){
                layer.alert("角色名称不能为空");
                return ;
            }
            if(rolems ==""){
                layer.alert("角色描述不能为空");
                return ;
            }
            var param = {
                roleId :roleId,
                roleName:roleName,
                rolems:rolems
            }
            $.post(url,param,function(data){
                if(data.res){
                    parent.query();
                    resetClick();
                }else{
                    parent.layer.alert(data.msg);
                    resetClick();
                }
            })
        }

        function del() {
            var roleId = $('#roleId').val();
            layer.confirm('确定删除？', {
                btn: ['是','否']
            }, function(){
                $.post(_ctx + "/role/delete",{roleId:roleId},function(data){
                    parent.layer.closeAll();
                    parent.layer.alert(data.msg);
                    parent.query();
                })
            });
        }

    </script>
</head>
<body>
<input type="hidden" id="roleId" value="${roleId}">

<div id="container" class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-12 column">
            <h3 class="text-center">
                角色新增/编辑
            </h3>
        </div>
    </div>
    <div class="row-fluid">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-1 control-label"> <span class="red">*</span>角色名称</label>
                <div class="col-sm-6">
                    <input class="form-control" id="roleName" type="text"  />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 control-label"> <span class="red">*</span>角色描述</label>
                <div class="col-sm-6">
                    <input class="form-control" id="rolems" type="text"  />
                </div>
            </div>
        </form>

        <div class="row-fluid">
            <div class="text-center">
                <button type="button" class="btn btn-success" onclick="save()">保存</button>
                <button type="button" class="btn btn-danger" onclick="resetClick()">关闭</button>
                <button type="button" class="btn btn-danger delete" onclick="del()">删除</button>
            </div>
        </div>

    </div>
</body>
</html>