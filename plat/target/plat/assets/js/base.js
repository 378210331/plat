var _ctx = getPath();

function getPath(){
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    return pathName.substr(0,index+1);
}

/**
 * datagrid兼容后端，基础配置 start
 */
function  onBeforeLoad(param)  {
    param.pageSize = param.rows;
    delete param.rows;
    param.currentPage = param.page;
    delete param.page;
}

var defaultGrid =
{
     elem : '#table'
    ,url:_ctx + '/menu/list'
    ,toolbar: 'default'
    ,skin:'line'
    ,cols: [[]]
    ,page: true
    ,loading: true
    ,queryParams:{}
};

/**
 * 列表宽
 * @param percent
 * @return
 * @author OUYANG
 */
function fixWidth(percent){
    var bodyWidth = document.body.clientWidth;
    return Math.floor((bodyWidth-30) * percent);
}


/*----------------------------------------------------------Ztree-----------------------------------------------*/

var treeSetting = {//下拉树设置
    check: {
        enable: true,
        chkboxType: {"Y":"s", "N":"s"}
    },
    view: {
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: beforeClick,
        onCheck: onCheck
    }
};

var singleTreeSetting = {//下拉树设置
    check: {
        enable: true,
        chkStyle: "radio",
        radioType: "all"
    },
    view: {
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: onCheck,
        onCheck: onCheck
    }

};

function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getCheckedNodes(true);
    var value = "";
    var caption = "";
    for (var i=0, l=nodes.length; i<l; i++) {
        value += nodes[i].id + ",";
        caption +=nodes[i].name + ",";
    }
    if (value.length > 0 ) {
        value = value.substring(0, value.length-1);
        caption = caption.substring(0, caption.length-1);
    }
    var inputObj = $("#"+treeId).parent().find(".treeObj");
    var subObj = $("#"+treeId).parent().find("input[name = 'value']");
    inputObj.val(caption);
    subObj.val(value);
}

function showMenu(inputObj) {
    var offset = inputObj.offset();
    inputObj.parent().find("ul").css({left:offset.left + "px", top:offset.top + inputObj.outerHeight() + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}
function hideMenu(event) {
    $(".ztree").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    var id = event.target.id;
    if(id ==undefined || (id.indexOf('check')==-1 &&  id.indexOf("span")==-1 && id.indexOf("switch")==-1)){
        hideMenu(event);
    }
}

