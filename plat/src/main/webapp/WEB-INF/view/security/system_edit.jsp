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
    <title>系统编辑/新增</title>
    <%@ include file="/common/bootstarp-ui.jsp"%>

    <script language="JavaScript">
        $(function () {
            var systemId = $("#systemId").val();
            if(systemId != ""){
                $.get(_ctx + "/system/query?systemId="+systemId,function(data){
                    if(data.res){
                        var item = data.data[0];
                        $('#systemName').val(item.systemName);
                        $('#dec').val(item.dec);
                        $('#ip').val(item.ip);
                        $('#defaultUrl').val(item.defaultUrl);
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
            var systemId = $("#systemId").val();
            var url = "";
            if(systemId ==""){
                url = _ctx + "/system/save"
            }else{
                url = _ctx + "/system/update"
            }
            var systemName =  $('#systemName').val();
            var dec =  $('#dec').val();
            var ip = $('#ip').val();
            var defaultUrl = $('#defaultUrl').val();
            if(systemName ==""){
                layer.alert("系统名称不能为空");
                return ;
            }
            if(dec ==""){
                layer.alert("系统描述不能为空");
                return ;
            }
            if(ip == ""){
                layer.alert("系统ip不能为空");
                return ;
            }
            var param = {
                systemId :systemId,
                systemName:systemName,
                dec:dec,
                ip:ip,
                defaultUrl:defaultUrl
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
            var systemId = $('#systemId').val();
            layer.confirm('确定删除？', {
                btn: ['是','否']
            }, function(){
                $.post(_ctx + "/system/delete",{systemId:systemId},function(data){
                    parent.layer.closeAll();
                    parent.layer.alert(data.msg);
                    parent.query();
                })
            });
        }

    </script>
</head>
<body>
<input type="hidden" id="systemId" value="${systemId}">

<div id="container" class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-12 column ">
            <h3 class="text-center">
                子系统新增/编辑
            </h3>
        </div>
    </div>
    <div class="row-fluid">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-2 control-label"> <span class="red">*</span>系统名称</label>
                <div class="col-sm-6">
                    <input class="form-control" id="systemName" type="text"  />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label"> <span class="red">*</span>系统描述</label>
                <div class="col-sm-6">
                    <input class="form-control" id="dec" type="text"  />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label"> <span class="red">*</span>系统ip</label>
                <div class="col-sm-6">
                    <input class="form-control" id="ip" type="text"  />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label"> 系统默认地址</label>
                <div class="col-sm-6">
                    <input class="form-control" id="defaultUrl" type="text"  />
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