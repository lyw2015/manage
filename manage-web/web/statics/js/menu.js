var type = 1;
$(function () {
    InitMainTable();
    InitMenuTree();
});

RecursionMenu = function (data) {
    var nodes = [];
    $.each(data, function (index, value) {
        var node = {};
        node["id"] = value.id;
        node["text"] = value.name;
        node["icon"] = value.icon;
        if (value.children && value.children.length > 0) {
            node["nodes"] = RecursionMenu(value.children);
        }
        nodes.push(node);
    })
    return nodes;
}

InitMenuTree = function () {
    $.ajax({
        type: "get",
        url: "/menu/getAllMenu",
        success: function (data) {
            if (data.success) {
                $('#menuTree').treeview({
                    data: RecursionMenu(data.data),
                    levels: 1,
                    showBorder: false
                });
                $('#menuTree').on('nodeSelected', function (event, data) {
                    $("input[name='parentId']").val(data.id);
                    $("input[name='parentName']").val(data.text);
                    $("input[name='isChildren']").val("true");
                });
                $('#menuTree').on('nodeUnselected', function (event, data) {
                    $("input[name='parentId']").val("");
                    $("input[name='parentName']").val("");
                    $("input[name='isChildren']").val("false");
                });
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

clean = function () {
    $("input[name='parentId']").val("");
    $("input[name='parentName']").val("");
    $("input[name='isChildren']").val("false");
}


UpdateMenu = function (id, bl) {
    if (!id) {
        parent.toastr.error("无效菜单");
        return false;
    }
    type = 2;
    $(".title-name").text("修改菜单");
    $.ajax({
        type: "get",
        data: {
            id: id
        },
        url: "/menu/getMenuById",
        success: function (data) {
            if (data.success) {
                data = data.data;
                $("input[name='isChildren']").val(bl);
                $("input[name='id']").val(data.id);
                $("input[name='parentId']").val(data.parentId);
                $("input[name='parentName']").val(data.parentName);
                $("input[name='name']").val(data.name);
                $("input[name='url']").val(data.url);
                $("input[name='icon']").val(data.icon);
                $("input[name='sort']").val(data.sort);
                $("textarea[name='description']").val(data.description);
                $('#menuModal').modal('show');
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

RemoveMenu = function (id, bl) {
    if (!id) {
        parent.toastr.error("无效菜单");
        return false;
    }
    $.ajax({
        type: "post",
        data: {
            id: id
        },
        url: "/menu/removeMenu",
        success: function (data) {
            if (data.success) {
                if (bl) {
                    $("#children_menu_data").bootstrapTable("refresh");
                } else {
                    $("#menu_data").bootstrapTable("refresh");
                }
                parent.toastr.success("菜单删除成功");
                CloseModal();
                InitMenuTree();
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

SubmitForm = function () {
    if (!$("input[name='name']").val() || !$("input[name='sort']").val()) {
        parent.toastr.error("名称和序号不能为空");
        return false;
    }
    var url = (type == 1 ? "/menu/saveMenuInfo" : "/menu/updateMenuInfo");
    $.ajax({
        type: "post",
        data: decodeURIComponent($("#form_menu").serialize(), true),
        url: url,
        success: function (data) {
            if (data.success) {
                if ($("input[name='isChildren']").val() === "true") {//修改子菜单
                    $("#children_menu_data").bootstrapTable("refresh");
                } else if (!$("input[name='parentId']").val()) {//添加或修改父菜单
                    $("#menu_data").bootstrapTable("refresh");
                }
                parent.toastr.success("菜单" + (type == 1 ? "添加" : "修改") + "成功");
                CloseModal();
                InitMenuTree();
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}
/*, {
    field: 'status',
    title: '是否显示',
    align: 'center',
    formatter: function (field, row, index) {
        if (row.status == 0) {
            return '<i class="text-bold text-danger">否</i>';
        }
        return '<i class="text-success">是</i>';
    }
}*/
InitMainTable = function () {
    $('#menu_data').bootstrapTable({
        url: "/menu/getRoot",
        toolbar: '#toolbar',
        detailView: true,
        showFullscreen: true,
        showRefresh: true,
        showColumns: true,
        minimumCountColumns: 2,
        showToggle: true,
        responseHandler: function (data) {
            return data;
        },
        cache: false,
        columns: [
            {
                field: 'name',
                title: '菜单名称',
                formatter: function (field, row, index) {
                    return "<i class='" + row.icon + "'></i>" + row.name
                }
            }, {
                field: 'url',
                title: '菜单URL'
            }, {
                field: 'icon',
                title: '图标代码',
                formatter: function (field, row, index) {
                    return row.icon
                }
            }, {
                field: 'sort',
                title: '菜单排序',
                align: 'center'
            }, {
                field: 'description',
                title: '菜单描述'
            }, {
                field: 'options',
                title: '操作',
                align: 'center',
                width: '80',
                formatter: function (field, row, index) {
                    return '<a href="javascript:UpdateMenu(\'' + row.id + '\', false)" type="button" class="btn btn-xs btn-info"><i class="fa fa-edit"></i></a>' +
                        '<a href="javascript:RemoveMenu(\'' + row.id + '\', false)" type="button" class="btn btn-xs btn-danger" style="margin-left: 5px;"><i class="fa fa-close"></i></a>';
                }
            }
        ],
        onExpandRow: function (index, row, $Subdetail) {
            InitSubTable(index, row, $Subdetail);
        }
    });
}

InitSubTable = function (index, row, $detail) {
    var parentid = row.MENU_ID;
    var cur_table = $detail.html('<table id="children_menu_data"></table>').find('table');
    $(cur_table).bootstrapTable({
        url: '/menu/getChildrenByParentId',
        queryParams: function (params) {
            return {
                parentId: row.id
            }
        },
        detailView: true,
        classes: 'table table-bordered table-hover table-condensed',
        responseHandler: function (data) {
            return data;
        },
        cache: false,
        columns: [
            {
                field: 'name',
                title: '菜单名称',
                formatter: function (field, row, index) {
                    return "<i class='" + row.icon + "'></i>" + row.name
                }
            }, {
                field: 'url',
                title: '菜单URL'
            }, {
                field: 'parentName',
                title: '父级菜单'
            }, {
                field: 'icon',
                title: '图标代码',
                formatter: function (field, row, index) {
                    return row.icon
                }
            }, {
                field: 'sort',
                title: '菜单排序',
                align: 'center'
            }, {
                field: 'description',
                title: '菜单描述'
            }, {
                field: 'options',
                title: '操作',
                align: 'center',
                width: '80',
                formatter: function (field, row, index) {
                    return '<a href="javascript:UpdateMenu(\'' + row.id + '\', true)" type="button" class="btn btn-xs btn-info"><i class="fa fa-edit"></i></a>' +
                        '<a href="javascript:RemoveMenu(\'' + row.id + '\', true)" type="button" class="btn btn-xs btn-danger" style="margin-left: 5px;"><i class="fa fa-close"></i></a>';
                }
            }
        ],
        onExpandRow: function (index, row, $Subdetail) {
            InitSubTable(index, row, $Subdetail);
        }
    });
};


CloseModal = function () {
    $('#menuModal').modal('hide');
}

AddMenu = function () {
    $("#form_menu")[0].reset();
    type = 1;
    $(".title-name").text("添加菜单");
    $('#menuModal').modal('show');
}