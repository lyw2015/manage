var roleIds, userId;
$(function () {
    $('input[name="sex"]').iCheck({
        checkboxClass: 'icheckbox_flat-green',
        radioClass: 'iradio_flat-green'
    });
    loadUserType();
    initDeptTree();

});

function submitUser() {
    var userInfo = {};
    userInfo.id = $("input[name='id']").val();
    userInfo.organizationId = $("input[name='organizationId']").val();
    userInfo.type = $("#user_type").val();
    userInfo.account = $("input[name='account']").val();
    userInfo.username = $("input[name='username']").val();
    userInfo.sex = $("input[name='sex']:checked").val();
    userInfo.mailbox = $("input[name='mailbox']").val();
    userInfo.phone = $("input[name='phone']").val();
    userInfo.officePhone = $("input[name='officePhone']").val();
    userInfo.description = $("textarea[name='description']").val();
    //角色
    var roles = $("#roles_data").bootstrapTable("getSelections");
    if (roles.length > 0) {
        roleIds = '';
        $.each(roles, function (ind, val) {
            roleIds += val.id;
            if (roles.length > ind + 1) {
                roleIds += ",";
            }
        })
    }
    userInfo.roleIds = roleIds;

    var suburl = "/user/saveUser";
    if (userId) {
        suburl = "/user/updateUser";
    }
    $.ajax({
        type: "post",
        url: suburl,
        data: userInfo,
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

updateUser = function () {
    userId = parent.getUrlParam(window.location.search, "id");
    if (userId) {
        $("input[name='account']").attr("readonly", "readonly");
        $.get("/user/getUserById", {
            id: userId
        }, function (result) {
            if (result.success) {
                setValue(result.data);
            } else {
                parent.toastr.error(result.message);
            }
        });
    } else {
        loadRoleData();
    }
}

setValue = function (data) {
    roleIds = data.roleIds;
    loadRoleData();
    $("input[name='id']").val(data.id);
    $("input[name='organizationId']").val(data.organizationId);
    $("input[name='organizationName']").val(data.organizationName);
    $("#user_type").val(data.type);
    $("input[name='account']").val(data.account);
    $("input[name='username']").val(data.username);
    $("input[name='sex'][value='" + data.sex + "']").iCheck('check');
    $("input[name='mailbox']").val(data.mailbox);
    $("input[name='phone']").val(data.phone);
    $("input[name='officePhone']").val(data.officePhone);
    $("textarea[name='description']").val(data.description);
}


function loadRoleData() {
    $("#roles_data").bootstrapTable({
        url: '/role/allRole',
        clickToSelect: true,
        columns: [
            {
                checkbox: true
            },
            {
                field: 'name',
                title: '角色名称'
            }, {
                field: 'type',
                title: '角色类型'
            }
        ],
        onPostBody: function () {
            if (roleIds) {
                $("#roles_data").bootstrapTable("checkBy", {
                    field: "id",
                    values: roleIds.split(",")
                })
            }
        }
    });
}

function loadUserType() {
    $.get("/dictionaries/getDictionariesItemByIdentification", {
        identification: "user_type"
    }, function (data) {
        var html = '';
        $.each(data, function (ind, val) {
            html += '<option value="' + val.itemName + '">' + val.itemValue + '</option>';
        })
        $("#user_type").empty().html(html);
    });

    updateUser();
}


//----------------------机构----------------------
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