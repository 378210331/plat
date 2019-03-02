<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"
       scope="page"/>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, user-scalable=no"/>
    <meta charset="UTF-8"/>
    <title>菜单编辑</title>
    <%@ include file="/common/static.jsp"%>
</head>
<body>


<div id="container">
    <div class="layui-fluid">
        <!-- 表单开始 -->
        <form class="layui-form" action="">
            <input type="hidden" name="editType" id="editType" value="${editType}"/>
            <div class="layui-form-item">
                <label class="layui-form-label">菜单编码</label>
                <div class="layui-input-block" >
                    <input type="text" name="menuId" value="${menuId}"  id="menuId" autocomplete="off" class="layui-input" readonly="true">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">菜单名称</label>
                <div class="layui-input-block" >
                    <input type="text" name="menuName" autocomplete="off" class="layui-input" id="menuName" lay-verify="required">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">菜单简称</label>
                <div class="layui-input-block" >
                    <input type="text" name="menuJc" autocomplete="off" class="layui-input" id="menuJc">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">菜单类型</label>
                <div class="layui-input-block">
                    <select name="menuType" lay-filter="menuType" id="menuType" lay-verify="required">
                        <option value=""></option>
                        <option value="0">菜单</option>
                        <option value="1">功能</option>
                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-form-label">所属系统</div>
                <div class="layui-input-block">
                    <select name="systemId" lay-filter="systemId" id="systemId" lay-verify="required">
                        <c:forEach items="${systemList}" var="system">
                            <option value="${system.systemId}">${system.systemName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-form-label">上级菜单</div>
                <div class="layui-input-block">
                    <select name="pid" lay-filter="pid" id="pid" lay-verify="required">
                        <option value="0" selected>根目录</option>
                        <c:forEach items="${pMenuList}" var="pMenu">
                            <option value="${pMenu.menuId}">${pMenu.menuName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">菜单URL</label>
                <div class="layui-input-block" >
                    <input type="text" name="menuUrl" autocomplete="off" class="layui-input" id="menuUrl">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">菜单图标</label>
                <div class="layui-input-block" >
                    <input type="text" name="menuIcon" autocomplete="off" class="layui-input" id="menuIcon">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">排序号</label>
                <div class="layui-input-block" >
                    <input type="number" name="sortNum" autocomplete="off" class="layui-input" id="sortNum" lay-verify="required" value="1">
                </div>
            </div>


            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="save" lay-filter="save">保存</button>
                </div>
            </div>
        </form>
        <!-- 表单结束 -->
</div>
</div>

<script language="JavaScript">
    layui.use(['element','form','layer'], function() {
        var element = layui.element;
        var form = layui.form;
        var $ = layui.jquery;
        var editType = $("#editType").val();
        if(editType == "update"){
            var menuId = $('#menuId').val();
            $.get(_ctx + "/menu/query?menuId=" + menuId, function (data) {
                if (data.res) {
                    var item = data.data[0];
                    $('#menuId').val(item.menuId);
                    $('#menuName').val(item.menuName);
                    $('#menuType').val(item.menuTypeDm);
                    $('#menuUrl').val(item.menuUrl);
                    $('#pid').val(item.pid);
                    $('#sortNum').val(item.sortNum);
                    $('#menuIcon').val(item.menuIcon);
                    $('#menuJc').val(item.menuJc);
                    $('#systemId').val(item.systemId);
                    form.render();
                }
            });
        }

        form.on('submit(save)', function(data){
            var url = "";
            if(data.field.editType == 'save'){
                url = _ctx + "/menu/save";
            }else {
                url = _ctx + "/menu/update";
            }
            $.post(url,data.field,function(res){
                if(res.res){
                    parent.layui.table.reload('table');
                }else{
                    layer.msg(res.msg);
                }
                var _index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(_index);
            })
            return false;
        });


    });
</script>

</body>
</html>