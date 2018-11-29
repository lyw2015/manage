var $table = $('#organization_data');
$(function () {

    $.get("/dictionaries/getDictionariesItemByIdentification", {
        identification: "dept_type"
    }, function (data) {
        var html = '<option value=""></option>';
        $.each(data, function (ind, val) {
            html += '<option value="' + val.itemName + '">' + val.itemValue + '</option>';
        })
        $("#dept_type").empty().html(html);
    });
    loadTableData();
});

function searchByCondition() {
    $table.bootstrapTable("refresh");
}

function loadTableData() {
    $table.bootstrapTable({
        url: "/organization/searchOrganization",
        queryParams: function (params) {
            return decodeURIComponent($("form").serialize(), true);
        },
        idField: 'id',
        toolbar: '#toolbar',
        showRefresh: true,
        showToggle: true,
        showColumns: true,
        minimumCountColumns: 2,
        showExport: true,
        exportDataType: 'basic',
        exportOptions: {
            fileName: '机构信息'
        },
        columns: [
            {
                field: 'name',
                title: '机构名称'
            }, {
                field: 'fullName',
                title: '机构全称'
            }, {
                field: 'parentFullName',
                title: '父级机构'
            }, {
                field: 'type',
                title: '机构类型'
            }, {
                field: 'personInCharge',
                title: '机构负责人'
            }, {
                field: 'officePhone',
                title: '机构电话'
            }, {
                field: 'updateTime',
                title: '更新时间'
            }, {
                field: 'description',
                title: '机构描述'
            }, {
                field: 'options',
                title: '操作',
                align: 'center',
                width: '80',
                events: operateEvents,
                formatter: function (value, row, index) {
                    return [
                        '<a class="mupdate btn btn-xs btn-info"><i class="fa fa-pencil-square-o" ></i></a>',
                        '<a class="mdelete btn btn-xs btn-danger" style="margin-left:5px;"><i class="fa fa-trash-o" ></i></a>'
                    ].join('');
                }
            }
        ],
        treeShowField: 'name',
        parentIdField: 'parentId',
        onResetView: function (data) {
            $table.treegrid({
                //initialState: 'collapsed',// 所有节点都折叠
                initialState: 'expanded',// 所有节点都展开，默认展开
                expanderExpandedClass: 'glyphicon glyphicon-minus',
                expanderCollapsedClass: 'glyphicon glyphicon-plus',
                onChange: function () {
                    $table.bootstrapTable('resetWidth');
                }
            });
            //$table.treegrid('getRootNodes').treegrid('expand');
        }
    });
}

window.operateEvents = {
    'click .mupdate': function (e, value, row, index) {
        var updatePageId = "mechanisms-update-panel";
        try {
            top.closeTabByPageId(updatePageId);
        } catch (e) {
        }
        top.addTabs({
            id: updatePageId,
            title: '修改机构',
            close: true,
            url: 'pages/organization/mechanisms-info.html?id=' + row.id,
            urlType: 'relative'
        });
    },
    'click .mdelete': function (e, value, row, index) {
        $.ajax({
            type: "post",
            data: {
                id: row.id
            },
            url: "/organization/updateOrganizationStatus",
            success: function (data) {
                if (data.success) {
                    searchByCondition();
                    parent.toastr.success("机构删除成功");
                } else {
                    parent.toastr.error(data.message);
                }
            }
        });
    }
};