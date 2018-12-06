var $table = $('#roles_data');
$(function () {

    $.get("/dictionaries/getDictionariesItemByIdentification", {
        identification: "role_type"
    }, function (data) {
        var html = '<option value=""></option>';
        $.each(data, function (ind, val) {
            html += '<option value="' + val.id + '">' + val.itemValue + '</option>';
        })
        $("#role_type").empty().html(html);
    });
    loadData();

});

function searchByCondition() {
    $table.bootstrapTable("refresh");
}

function loadData() {
    $table.bootstrapTable({
        url: '/role/queryRoles',
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
            fileName: '角色信息'
        },
        columns: [
            {
                field: 'name',
                title: '角色名称'
            }, {
                field: 'typeName',
                title: '角色类型'
            }, {
                field: 'updateTime',
                title: '更新时间'
            }, {
                field: 'description',
                title: '描述'
            }, {
                field: 'options',
                title: '操作',
                align: 'center',
                width: '80',
                events: operateEvents,
                formatter: function (value, row, index) {
                    return [
                        '<a class="rupdate btn btn-xs btn-info"><i class="fa fa-pencil-square-o" ></i></a>',
                        '<a class="rdelete btn btn-xs btn-danger" style="margin-left:5px;"><i class="fa fa-trash-o" ></i></a>'
                    ].join('');
                }
            }
        ]
    });
}

window.operateEvents = {
    'click .rupdate': function (e, value, row, index) {
        var updatePageId = "roles-update-panel";
        try {
            top.closeTabByPageId(updatePageId);
        } catch (e) {
        }
        top.addTabs({
            id: updatePageId,
            title: '修改角色',
            close: true,
            url: 'pages/organization/roles-info.html?id=' + row.id,
            urlType: 'relative'
        });
    },
    'click .rdelete': function (e, value, row, index) {
        $.ajax({
            type: "post",
            data: {
                id: row.id
            },
            url: "/role/updateRoleStatus",
            success: function (data) {
                if (data.success) {
                    searchByCondition();
                    parent.toastr.success("删除角色成功");
                } else {
                    parent.toastr.error(data.message);
                }
            }
        });
    }
};