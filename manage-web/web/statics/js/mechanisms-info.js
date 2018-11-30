var id;
$(function () {
    loadDeptType();
    initDeptTree();
});

function update() {
    id = parent.getUrlParam(window.location.search, "id");
    if (id) {
        $.get("/organization/getOrganizationById", {
            id: id
        }, function (result) {
            if (result.success) {
                setValue(result.data);
            } else {
                parent.toastr.error(result.message);
            }
        });
    }
}

function setValue(data) {
    $("input[name='id']").val(data.id);
    $("input[name='parentId']").val(data.parentId);
    $("input[name='parentName']").val(data.parentFullName);
    $("#dept_type").val(data.type);
    $("input[name='name']").val(data.name);
    $("input[name='fullName']").val(data.fullName);
    $("input[name='personInCharge']").val(data.personInCharge);
    $("input[name='officePhone']").val(data.officePhone);
    $("input[name='mailbox']").val(data.mailbox);
    $("input[name='postalCode']").val(data.postalCode);
    $("input[name='address']").val(data.address);
    $("textarea[name='description']").val(data.description);
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
                    $("input[name='parentId']").val(data.id);
                    $("input[name='parentName']").val(data.text);
                });
                $('#organTree').on('nodeUnselected', function (event, data) {
                    $("input[name='parentId']").val("");
                    $("input[name='parentName']").val("");
                });
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}

function clean() {
    $("input[name='parentId']").val("");
    $("input[name='parentName']").val("");
}

function loadDeptType() {
    $.get("/dictionaries/getDictionariesItemByIdentification", {
        identification: "dept_type"
    }, function (data) {
        var html = '';
        $.each(data, function (ind, val) {
            html += '<option value="' + val.itemName + '">' + val.itemValue + '</option>';
        })
        $("#dept_type").empty().html(html);

        update();
    });
}

function submitOrgani() {
    if (!$("input[name='name']").val() || !$("input[name='fullName']").val()) {
        parent.toastr.error("名称不能为空");
        return false;
    }
    var url = "/organization/addOrganization";
    if (id) {
        url = "/organization/updateOrganization";
    }
    $.ajax({
        type: "post",
        data: decodeURIComponent($("#form-organ").serialize(), true),
        url: url,
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