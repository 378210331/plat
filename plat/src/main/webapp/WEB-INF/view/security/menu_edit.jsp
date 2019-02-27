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
    <%@ include file="/common/bootstarp-ui.jsp" %>

    <script language="JavaScript">
        $(function () {
            var menuId = $("#old").val();
            if (menuId != "") {
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
                    }
                })
            } else {
                $('.delete').hide();
            }
        });

        function resetClick() {
            var _index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(_index);
        }

        function save() {
            var menuId = $("#old").val();
            var url = "";
            if (menuId == "") {
                url = _ctx + "/menu/save"
            } else {
                url = _ctx + "/menu/update"
            }
            var menuId = $('#menuId').val();
            var menuName = $('#menuName').val();
            var menuType = $('#menuType').val();
            var systemId = $('#systemId').val()
            var menuUrl = $('#menuUrl').val();
            var pid = $('#pid').val();
            var sortNum = $('#sortNum').val();
            var menuIcon = $('#menuIcon').val();
            var menuJc = $('#menuJc').val();
            if (menuName == "") {
                layer.alert("菜单名称不能为空");
                return;
            }
            if (menuType == "") {
                layer.alert("菜单类型不能为空");
                return;
            }
            if (menuUrl == "" && menuType != 1) {
                layer.alert("菜单url不能为空");
                return;
            }
            if(menuType == 1){
                menuUrl="";
            }
            if (pid == "") {
                layer.alert("上级菜单不能为空");
                return;
            }
            if (menuJc == "" && menuType == 1) {
                parent.layer.alert("菜单简称不能为空");
                return;
            }
            var param = {
                menuId: menuId,
                menuName: menuName,
                menuType: menuType,
                menuUrl: menuUrl,
                pid: pid,
                sortNum: sortNum,
                menuIcon: menuIcon,
                menuJc: menuJc,
                systemId:systemId
            }
            $.post(url, param, function (data) {
                if (data.res) {
                    parent.query();
                    resetClick();
                } else {
                    layer.alert(data.msg);
                    resetClick();
                }
            })
        }

        function del() {
            var menuId = $('#menuId').val();
            layer.confirm('确定删除？', {
                btn: ['是', '否']
            }, function () {
                $.post(_ctx + "/menu/delete", {menuId: menuId}, function (data) {
                    parent.layer.closeAll();
                    parent.layer.alert(data.msg);
                    parent.query();
                })
            });
        }

    </script>
</head>
<body>
<input type="hidden" id="old" value="${menuId}">

<div id="container">

    <div class="row-fluid">
        <div class="col-md-12 column">
            <h3 class="text-center">
                功能菜单新增/编辑
            </h3>
        </div>
    </div>

    <div class="row-fluid ">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-1 control-label"> <span class="red">*</span>菜单代码</label>
                <div class="col-sm-4">
                    <input class="form-control" id="menuId" type="text" readonly="true" value="${newId}" />
                </div>
                <label class="col-sm-1 control-label" > <span class="red">*</span>菜单名称</label>
                <div class="col-sm-4">
                    <input class="form-control" id="menuName" type="text" />
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-1 control-label"> <span class="red">*</span>菜单简称</label>
                <div class="col-sm-4">
                    <input class="form-control" id="menuJc" type="text"  />
                </div>
                <label class="col-sm-1 control-label" >菜单URL</label>
                <div class="col-sm-4">
                    <input class="form-control" id="menuUrl" type="text" />
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-1 control-label" >上级菜单</label>
                <div class="col-sm-4">
                    <select class="form-control" id="pid">
                        <option  selected value=""></option>
                        <c:forEach items="${pMenuList}" var="pmenu">
                            <option value="${pmenu.menuId}">${pmenu.menuName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-1 control-label" >子系统</label>
                <div class="col-sm-4">
                    <select class="form-control" id="systemId">
                        <option  selected value=""></option>
                        <c:forEach items="${systemList}" var="system">
                            <option value="${system.systemId}">${system.systemName}</option>
                        </c:forEach>
                    </select>
                </div>
                <label class="col-sm-1 control-label" >菜单类型</label>
                <div class="col-sm-4">
                    <select class="form-control" id="menuType">
                        <option value="1">菜单</option>
                        <option  selected value="2">功能</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 control-label">菜单图标路径</label>
                <div class="col-sm-4">
                    <input class="form-control" id="menuIcon" type="text"  />
                </div>
                <label class="col-sm-1 control-label" >菜单序号</label>
                <div class="col-sm-4">
                    <input class="form-control" id="sortNum" type="number" />
                </div>
            </div>

        </form>
    </div>

    <div class="row-fluid ">
        <div class="text-center">
            <button type="button" class="btn btn-success" onclick="save()">保存</button>
            <button type="button" class="btn btn-danger" onclick="resetClick()">关闭</button>
            <button type="button" class="btn btn-danger delete" onclick="del()">删除</button>
        </div>
    </div>

</div>
</body>
</html>