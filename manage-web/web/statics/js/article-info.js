var roleIds, userId;
$(function () {
    $('input[name="isnotice"]').iCheck({
        checkboxClass: 'icheckbox_flat-green',
        radioClass: 'iradio_flat-green'
    });
    loadType();

    var editor = CKEDITOR.replace('description');
    CKFinder.setupCKEditor(editor, '/plugins/ckfinder/');

});

function submitData() {
    var article = {};
    article.id = $("input[name='id']").val();
    article.type = $("#content_column").val();
    article.title = $("input[name='title']").val();
    article.keywords = $("input[name='keywords']").val();
    article.description = $("textarea[name='description']").val();
    article.isnotice = $("input[name='isnotice']:checked").val();
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
    article.roleIds = roleIds;

    var suburl = "/user/saveUser";
    if (userId) {
        suburl = "/user/updateUser";
    }
    $.ajax({
        type: "post",
        url: suburl,
        data: article,
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
    }
}

setValue = function (data) {
    roleIds = data.roleIds;
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

function loadType() {
    $.get("/dictionaries/getDictionariesItemByIdentification", {
        identification: "content_column"
    }, function (data) {
        var html = '';
        $.each(data, function (ind, val) {
            html += '<option value="' + val.id + '">' + val.itemValue + '</option>';
        })
        $("#content_column").empty().html(html);
    });

    updateUser();
}

