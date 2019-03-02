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
    <%@ include file="/common/bootstarp-ui.jsp" %>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.md5.js"></script>

    <script language="JavaScript">
        $(function () {
            //初始化树
            $.ajaxSetup({async:false});
            $.get(_ctx+"/swjg/getSwjgTree",function(data){
                var zNodes = data;
                $.fn.zTree.init($("#swjgTree"), singleTreeSetting, zNodes);
                $(".treeObj").bind("click",function(){showMenu($(this));});//点击显示树
            });

                laydate.render({
                    elem: '#yxsjq'
                });
                laydate.render({
                    elem: '#yxsjz'
                });

            var userId = $("#oid").val();
            if (userId != "") {
                $('#loginPassword').attr("readonly",true);
                $('#userId').attr("readOnly","true");
                $('#loginName').attr("readOnly","true");
                $.get(_ctx + "/user/query?userId=" + userId, function (data) {
                    if (data.res) {
                        var item = data.data[0];
                        $('#userId').val(item.userId);
                        $('#loginName').val(item.loginName);
                        $('#userName').val(item.userName);
                        $('#yxsjq').val(item.yxsjq);
                        $('#yxsjz').val(item.yxsjz);
                        var treeObj = $.fn.zTree.getZTreeObj("swjgTree");
                        var node = treeObj.getNodeByParam("id", item.jgDm, null);
                        treeObj.checkNode(node, true, true);
                        $("#jgDm").val(item.jgDm);
                        $(".treeObj").val(item.swjgmc);
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
            var url = "";
            var userId = $('#oid').val();
            if (userId == "") {
                url = _ctx + "/user/save"
            } else {
                url = _ctx + "/user/update"
            }
            var userId = $('#userId').val();
            var loginName = $('#loginName').val();
            var userName = $('#userName').val();
            var jgDm = $("#jgDm").val();
            var yxsjq = $("#yxsjq").val();
            var yxsjz = $("#yxsjz").val();
            var loginPassword = $("#loginPassword").val();
            if (userId == "") {
                layer.alert("用户id不能为空");
                return;
            }
            if (loginName == "") {
                layer.alert("用户登录名不能为空");
                return;
            }
            if (userName == "") {
                layer.alert("用户名称不能为空");
                return;
            }
            if(jgDm ==""){
                layer.alert("所属机构不能为空");
                return;
            }
            if (yxsjq == "") {
                layer.alert("有效起始日期不能为空");
                return;
            }
            if (yxsjz == "") {
                layer.alert("有效终止日期不能为空");
                return;
            }
            var param = {
                userId: userId,
                userName:userName,
                loginName:loginName,
                jgDm:jgDm,
                yxsjq:yxsjq,
                yxsjz:yxsjz
            }
            if($('#oid').val() == ""){
                param.loginPassword = $.md5(loginPassword);
            }
            $.post(url, param, function (data) {
                if (data.res) {
                    parent.query();
                    resetClick();
                } else {
                    parent.layer.alert(data.msg);
                    resetClick();
                }
            })
        }

        function del() {
            var userId = $('#userId').val();
            layer.confirm('确定删除？', {
                btn: ['是', '否']
            }, function () {
                $.post(_ctx + "/user/delete", {userId: userId}, function (data) {
                    parent.layer.closeAll();
                    parent.layer.alert(data.msg);
                    parent.query();
                })
            });
        }

    </script>
</head>
<body>
<input type="hidden" id="oid" value="${oid}">

<div id="container" class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-12 column">
            <h3 class="text-center">
                人员新增/编辑
            </h3>
        </div>
    </div>
    <div class="row ">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-2 control-label"> <span class="red">*</span>用户id</label>
                <div class="col-sm-3">
                    <input class="form-control" id="userId" type="text"   />
                </div>
                <label class="col-sm-2 control-label" >用户名称</label>
                <div class="col-sm-3">
                    <input class="form-control" id="userName" type="text" />
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-2 control-label" >用户登录名称</label>
                <div class="col-sm-3">
                    <input class="form-control" id="loginName" type="text" />
                </div>
                <label class="col-sm-2 control-label" >用户密码</label>
                <div class="col-sm-3">
                    <input class="form-control" id="loginPassword" type="password" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">所属税务机关</label>
                <div class="col-sm-3">
                    <input class="treeObj form-control" type="text" readonly value="" style="width:100%;"/>
                    <ul id="swjgTree" class="ztree" style="margin-top:0; width:100%; height: 100%;display:none;"></ul>
                    <input type="hidden" name="value" id="jgDm">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">有效起始日期</label>
                <div class="col-sm-3">
                    <input class="form-control" id="yxsjq" type="text" />
                </div>
                <label class="col-sm-2 control-label">有效终止日期</label>
                <div class="col-sm-3">
                    <input class="form-control" id="yxsjz" type="text" />
                </div>
            </div>

        </form>
    </div>

    <div class="row ">
        <div class="text-center">
            <button type="button" class="btn btn-success" onclick="save()">保存</button>
            <button type="button" class="btn btn-danger" onclick="resetClick()">关闭</button>
            <button type="button" class="btn btn-danger delete" onclick="del()">删除</button>
        </div>
    </div>

</div>
</body>
</html>