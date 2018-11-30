var $table = $('#users_data');
$(function () {
    loadCondiction();
    loadData();
});

function searchByCondition() {
    $table.bootstrapTable("refresh");
}

function loadData() {
    $table.bootstrapTable({
        url: '/user/queryUser',
        dataField: "list",
        queryParams: function (params) {
            return top.serializeParams(params, $("form").serialize())
        },
        toolbar: '#toolbar',
        showRefresh: true,
        showToggle: true,
        showColumns: true,
        minimumCountColumns: 2,
        pagination: true,
        sidePagination: "server",
        pageNumber: 1,
        pageSize: 10,
        pageList: [10, 20, 30, 50, 100],
        paginationPreText: "上一页",
        paginationNextText: "下一页",
        showExport: true,
        exportDataType: 'basic',
        exportOptions: {
            fileName: '用户信息'
        },
        columns: [
            {
                field: 'account',
                title: '登录账号'
            }, {
                field: 'username',
                title: '用户名称'
            }, {
                field: 'organizationName',
                title: '所属机构'
            }, {
                field: 'roleNames',
                title: '担任角色'
            }, {
                field: 'type',
                title: '用户类型'
            }, {
                field: 'mailbox',
                title: '邮箱'
            }, {
                field: 'phone',
                title: '手机号码'
            }, {
                field: 'officePhone',
                title: '办公号码'
            }, {
                field: 'updateTime',
                title: '更新时间'
            }, {
                field: 'status',
                title: '状态',
                formatter: function (value, row, index) {
                    if (value == 1)
                        return '正常';
                    else if (value == 2)
                        return '<i class="text-bold text-warning">锁定</i>';
                    else if (value == 3)
                        return '<i class="text-bold text-danger">停用</i>';
                }
            }, {
                field: 'options',
                title: '操作',
                align: 'center',
                width: '120',
                events: operateEvents,
                formatter: function (value, row, index) {
                    var options = new Array();
                    options.push('<a class="uupdate btn btn-xs btn-info"><i class="fa fa-pencil-square-o" ></i></a>');
                    if (row.status == 3) {
                        options.push('<a class="uenable btn btn-xs btn-success" title="启用用户" style="margin-left:5px;"><i class="fa fa-check-square" ></i></a>');
                    } else {
                        options.push('<a class="udisable btn btn-xs btn-danger" title="停用用户" style="margin-left:5px;"><i class="fa fa-ban" ></i></a>');
                    }
                    options.push('<a class="udelete btn btn-xs btn-danger" style="margin-left:5px;"><i class="fa fa-trash-o" ></i></a>');
                    options.push('<a class="ureset btn btn-xs btn-primary" title="重置密码" style="margin-left:5px;"><i class="fa fa-repeat" ></i></a>');
                    return options.join('');
                }
            }
        ]
    });
}

window.operateEvents = {
    'click .uupdate': function (e, value, row, index) {
        var updatePageId = "user-update-panel";
        try {
            top.closeTabByPageId(updatePageId);
        } catch (e) {
        }
        top.addTabs({
            id: updatePageId,
            title: '修改用户',
            close: true,
            url: 'pages/organization/user-info.html?id=' + row.id,
            urlType: 'relative'
        });
    },
    'click .uenable': function (e, value, row, index) {
        $.ajax({
            type: "post",
            data: {
                id: row.id
            },
            url: "/user/enableUser",
            success: function (data) {
                if (data.success) {
                    searchByCondition();
                    parent.toastr.success("启用用户成功");
                } else {
                    parent.toastr.error(data.message);
                }
            }
        });
    },
    'click .udisable': function (e, value, row, index) {
        $.ajax({
            type: "post",
            data: {
                id: row.id
            },
            url: "/user/disableUser",
            success: function (data) {
                if (data.success) {
                    searchByCondition();
                    parent.toastr.success("停用用户成功");
                } else {
                    parent.toastr.error(data.message);
                }
            }
        });
    },
    'click .udelete': function (e, value, row, index) {
        $.ajax({
            type: "post",
            data: {
                id: row.id
            },
            url: "/user/removeUser",
            success: function (data) {
                if (data.success) {
                    searchByCondition();
                    parent.toastr.success("删除用户成功");
                } else {
                    parent.toastr.error(data.message);
                }
            }
        });
    },
    'click .ureset': function (e, value, row, index) {
        $.ajax({
            type: "post",
            data: {
                id: row.id
            },
            url: "/user/resetUserPwd",
            success: function (data) {
                if (data.success) {
                    parent.toastr.success("密码重置成功");
                } else {
                    parent.toastr.error(data.message);
                }
            }
        });
    }
};


function loadCondiction() {
    initDeptTree();
    $.get("/dictionaries/getDictionariesItemByIdentification", {
        identification: "user_type"
    }, function (data) {
        var html = '<option value=""></option>';
        $.each(data, function (ind, val) {
            html += '<option value="' + val.itemName + '">' + val.itemValue + '</option>';
        })
        $("#user_type").empty().html(html);
    });

    $.get("/role/allRole", function (data) {
        var html = '<option value=""></option>';
        $.each(data, function (ind, val) {
            html += '<option value="' + val.id + '">' + val.name + '</option>';
        })
        $("#roles").empty().html(html);
    });
}

function initDeptTree() {
    $.ajax({
        type: "get",
        url: "/organization/treeOrganization",
        success: function (data) {
            if (data.success) {
                $('#organTree').treeview({
                    data: recursionMenu(data.data),
                    levels: 3,
                    showBorder: false
                });
                $('#organTree').on('nodeSelected', function (event, data) {
                    $("input[name='organizationId']").val(data.id);
                    $("input[name='organizationName']").val(data.text);
                });
                $('#organTree').on('nodeUnselected', function (event, data) {
                    $("input[name='organizationId']").val("");
                    $("input[name='organizationName']").val("");
                });
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

function recursionMenu(data) {
    var nodes = [];
    $.each(data, function (index, value) {
        var node = {};
        node["id"] = value.id;
        node["text"] = value.name;
        if (value.organizationList && value.organizationList.length > 0) {
            node["nodes"] = recursionMenu(value.organizationList);
        }
        nodes.push(node);
    })
    return nodes;
}