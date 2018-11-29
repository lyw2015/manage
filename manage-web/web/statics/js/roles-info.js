var $tree = $('#menuTree'), roleId, menuIds;
$(function () {
    loadRoleType();
});

submitRole = function () {
    if (!$("input[name='name']").val()) {
        parent.toastr.error("名称不能为空");
        return false;
    }

    var checked = $tree.treeview('getChecked');
    if (checked.length > 0) {
        menuIds = '';
        $.each(checked, function (ind, val) {
            menuIds += val.id;
            if (checked.length > ind + 1) {
                menuIds += ",";
            }
        })
    }

    var suburl = "/role/addRole";
    if (roleId) {
        suburl = "/role/updateRole";
    }
    $.ajax({
        type: "post",
        url: suburl,
        data: {
            id: $("input[name='id']").val(),
            name: $("input[name='name']").val(),
            type: $("#role_type").val(),
            description: $("textarea[name='description']").val(),
            menuIds: menuIds
        },
        success: function (data) {
            if (data.success) {
                parent.toastr.success(data.message);
                setTimeout("top.closeCurrentTab()", 1000);
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

updateRole = function () {
    roleId = parent.getUrlParam(window.location.search, "id");
    if (roleId) {
        $.get("/role/findRoleById", {
            id: roleId
        }, function (result) {
            if (result.success) {
                setValue(result.data);
            } else {
                parent.toastr.error(result.message);
            }
        });
    } else {
        initMenuTree();
    }
}

function setValue(data) {
    menuIds = data.menuIds;
    initMenuTree();
    $("input[name='id']").val(data.id);
    $("#role_type").val(data.type);
    $("input[name='name']").val(data.name);
    $("textarea[name='description']").val(data.description);
}

loadRoleType = function () {
    $.get("/dictionaries/getDictionariesItemByIdentification", {
        identification: "role_type"
    }, function (data) {
        var html = '';
        $.each(data, function (ind, val) {
            html += '<option value="' + val.itemName + '">' + val.itemValue + '</option>';
        })
        $("#role_type").empty().html(html);

        updateRole();
    });
}

//---------------------------------tree-----------------------------
initMenuTree = function () {
    $.ajax({
        type: "get",
        url: "/menu/getAllMenu",
        success: function (data) {
            if (data.success) {
                $tree.treeview({
                    data: recursionMenu(data.data),
                    levels: 2,
                    showCheckbox: true,
                    showBorder: false,
                    multiSelect: true,
                    highlightSelected: false,
                    onNodeSelected: executeHandler,
                    onNodeUnselected: executeHandler,
                    onNodeChecked: nodeChecked,
                    onNodeUnchecked: nodeUnchecked
                });
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

recursionMenu = function (data) {
    var nodes = [];
    $.each(data, function (index, value) {
        var node = {};
        node.id = value.id;
        node.text = value.name + "&nbsp;&nbsp;&nbsp;" + value.url;
        node.icon = value.icon;
        if (menuIds && menuIds.indexOf(value.id) != -1) {
            node.state = {checked: true};
        }
        if (value.children && value.children.length > 0) {
            node.nodes = recursionMenu(value.children);
        }
        nodes.push(node);
    })
    return nodes;
}

function methodHandler(method) {
    $tree.treeview(method, {silent: true});
}

function executeHandler(event, node) {
    if (node.state.checked)
        nodeUnchecked(event, node);
    else
        nodeChecked(event, node);
}

var nodeCheckedSilent = false;

function nodeChecked(event, node) {
    if (nodeCheckedSilent) {
        return;
    }
    nodeCheckedSilent = true;
    checkAllParent(node);
    checkAllSon(node);
    nodeCheckedSilent = false;
}

var nodeUncheckedSilent = false;

function nodeUnchecked(event, node) {
    if (nodeUncheckedSilent)
        return;
    nodeUncheckedSilent = true;
    uncheckAllParent(node);
    uncheckAllSon(node);
    nodeUncheckedSilent = false;
}

//选中全部父节点
function checkAllParent(node) {
    $tree.treeview('checkNode', node.nodeId, {silent: true});
    var parentNode = $tree.treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    } else {
        checkAllParent(parentNode);
    }
}

//取消全部父节点
function uncheckAllParent(node) {
    $tree.treeview('uncheckNode', node.nodeId, {silent: true});
    var siblings = $tree.treeview('getSiblings', node.nodeId);
    var parentNode = $tree.treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    }
    var isAllUnchecked = true;  //是否全部没选中
    for (var i in siblings) {
        if (siblings[i].state.checked) {
            isAllUnchecked = false;
            break;
        }
    }
    if (isAllUnchecked) {
        uncheckAllParent(parentNode);
    }

}

//级联选中所有子节点
function checkAllSon(node) {
    $tree.treeview('checkNode', node.nodeId, {silent: true});
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i in node.nodes) {
            checkAllSon(node.nodes[i]);
        }
    }
}

//级联取消所有子节点
function uncheckAllSon(node) {
    $tree.treeview('uncheckNode', node.nodeId, {silent: true});
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i in node.nodes) {
            uncheckAllSon(node.nodes[i]);
        }
    }
}