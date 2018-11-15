$(function () {
    initMainTable();
});

function closeModal(id) {
    $('#' + id).modal('hide');
}

function showDict(str) {
    $("input[name='identification']").removeAttr("readonly");
    $("#form_dictionaries")[0].reset();
    $(".title-name-d").text(str + "字典");
    $('#dictionariesModal').modal('show');
}

function submitDictionaries() {
    if (!$("input[name='name']").val() || !$("input[name='identification']").val()) {
        parent.toastr.error("名称和标识不能为空");
        return false;
    }
    var url = "/dictionaries/saveDictionaries";
    if ($("#d_id").val()) {
        url = "/dictionaries/updateDictionaries";
    }
    $.ajax({
        type: "post",
        data: decodeURIComponent($("#form_dictionaries").serialize(), true),
        url: url,
        success: function (data) {
            if (data.success) {
                $('#dictionaries_data').bootstrapTable("refresh");
                parent.toastr.success(data.message);
                closeModal("dictionariesModal");
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

function updateDict(id) {
    if (!id) {
        parent.toastr.error("无效字典");
        return false;
    }
    $.ajax({
        type: "get",
        data: {
            id: id
        },
        url: "/dictionaries/getDictionariesById",
        success: function (data) {
            if (data.success) {
                showDict("修改");
                $("input[name='identification']").attr("readonly", "readonly");
                data = data.data;
                $("#d_id").val(data.id);
                $("input[name='name']").val(data.name);
                $("input[name='identification']").val(data.identification);
                $("textarea[name='description']").val(data.description);
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

function removeDict(id) {
    if (!id) {
        parent.toastr.error("无效字典");
        return false;
    }
    $.ajax({
        type: "post",
        data: {
            id: id
        },
        url: "/dictionaries/updateDictionariesStatus",
        success: function (data) {
            if (data.success) {
                $("#dictionaries_data").bootstrapTable("refresh");
                parent.toastr.success("字典删除成功");
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

function showDictItem(id, name, str) {
    $("#form_dictionariesItem")[0].reset();
    $("input[name='dictId']").val(id);
    $("input[name='dictName']").val(name);
    $(".title-name-di").text(str + "字典项");
    $('#dictionariesItemModal').modal('show');
}

function updateDictItem(id, dictId, str) {
    if (!id) {
        parent.toastr.error("无效字典项");
        return false;
    }
    $.ajax({
        type: "get",
        data: {
            id: id
        },
        url: "/dictionaries/getDictionariesItemById",
        success: function (data) {
            if (data.success) {
                showDictItem(dictId, data.data.dictName, "修改");
                $("#di_id").val(data.data.id);
                $("input[name='itemName']").val(data.data.itemName);
                $("input[name='itemValue']").val(data.data.itemValue);
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

function submitDictionariesItem() {
    if (!$("input[name='itemName']").val() || !$("input[name='itemValue']").val()) {
        parent.toastr.error("名称和值不能为空");
        return false;
    }
    var url = "/dictionaries/saveDictionariesItem";
    if ($("#di_id").val()) {
        url = "/dictionaries/updateDictionariesItem";
    }
    $.ajax({
        type: "post",
        data: decodeURIComponent($("#form_dictionariesItem").serialize(), true),
        url: url,
        success: function (data) {
            if (data.success) {
                $('#dictionaries_item_data').bootstrapTable("refresh");
                parent.toastr.success(data.message);
                closeModal("dictionariesItemModal");
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

function removeDictItem(id) {
    if (!id) {
        parent.toastr.error("无效字典");
        return false;
    }
    $.ajax({
        type: "post",
        data: {
            id: id
        },
        url: "/dictionaries/updateDictionariesItemStatus",
        success: function (data) {
            if (data.success) {
                $("#dictionaries_item_data").bootstrapTable("refresh");
                parent.toastr.success("字典删除成功");
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

function initMainTable() {
    $('#dictionaries_data').bootstrapTable({
        url: "/dictionaries/getAllDictionaries",
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
                title: '字典名称'
            }, {
                field: 'identification',
                title: '字典标识'
            }, {
                field: 'updateTime',
                title: '更新时间'
            }, {
                field: 'description',
                title: '字典描述'
            }, {
                field: 'options',
                title: '操作',
                align: 'center',
                width: '100',
                formatter: function (field, row, index) {
                    return '<a href="javascript:showDictItem(\'' + row.id + '\', \'' + row.name + '\' ,\'添加\')" data-toggle="tooltip" title="添加字典项" type="button" class="btn btn-xs btn-primary"><i class="fa fa-plus-square"></i></a>' +
                        '<a href="javascript:updateDict(\'' + row.id + '\')" type="button" class="btn btn-xs btn-info" style="margin-left: 5px;"><i class="fa fa-edit"></i></a>' +
                        '<a href="javascript:removeDict(\'' + row.id + '\')" type="button" class="btn btn-xs btn-danger" style="margin-left: 5px;"><i class="fa fa-close"></i></a>';
                }
            }
        ],
        onExpandRow: function (index, row, $Subdetail) {
            initSubTable(index, row, $Subdetail);
        }
    });
}

function initSubTable(index, row, $detail) {
    var parentid = row.id;
    var cur_table = $detail.html('<table id="dictionaries_item_data"></table>').find('table');
    $(cur_table).bootstrapTable({
        url: '/dictionaries/getDictionariesItemByDid',
        queryParams: function (params) {
            return {
                dictId: parentid
            }
        },
        classes: 'table table-bordered table-hover table-condensed',
        responseHandler: function (data) {
            return data;
        },
        cache: false,
        columns: [
            {
                field: 'itemName',
                title: '字典项名称'
            }, {
                field: 'itemValue',
                title: '字典项值'
            }, {
                field: 'dictName',
                title: '所属字典'
            }, {
                field: 'options',
                title: '操作',
                align: 'center',
                width: '80',
                formatter: function (field, row, index) {
                    return '<a href="javascript:updateDictItem(\'' + row.id + '\', \'' + parentid + '\' , \'修改\')" type="button" class="btn btn-xs btn-info"><i class="fa fa-edit"></i></a>' +
                        '<a href="javascript:removeDictItem(\'' + row.id + '\')" type="button" class="btn btn-xs btn-danger" style="margin-left: 5px;"><i class="fa fa-close"></i></a>';
                }
            }
        ]
    });
};