$(function () {
    $('input[name="sex"]').iCheck({
        checkboxClass: 'icheckbox_flat-green',
        radioClass: 'iradio_flat-green'
    });
    renderingUser();
});

function collapseLoginRecord(button, groupClass, boolean) {
    var i = $(button).children("i");
    if (i.hasClass("fa-minus")) {
        i.removeClass("fa-minus").addClass("fa-plus");
        $("." + groupClass).hide();
        var className;
        $.each($("#loginLogTab .time-label"), function (ind, val) {
            className = $(val).attr("class");
            if (className != "time-label" && className.indexOf(groupClass) != -1) {
                collapseLoginRecord($(val).find("button"), $(val).children("span").text(), false);
            }
        })
    } else if (i.hasClass("fa-plus") && boolean) {
        i.removeClass("fa-plus").addClass("fa-minus");
        if ($("." + groupClass).length > 0) {
            $("." + groupClass).show();
        } else {
            loadLoginReocrdData(button, groupClass);
        }
    }
}

function loadLoginReocrdData(button, day) {
    $("#user_info_overlay").show();
    $.ajax({
        type: "get",
        data: {
            day: day
        },
        url: "/personal/getUserLoginRecordsByDay",
        success: function (result) {
            $("#user_info_overlay").hide();
            if (result.success) {
                var html = "";
                $.each(result.data, function (ind, val) {
                    html += buildDayHtml(val, day);
                });
                $(button).parent().parent().after(html);
            } else {
                parent.toastr.error(result.message);
            }
        }
    });
}

function buildHtml(count, day) {
    var color;
    if (day.length == 4) {
        color = "bg-red";
    } else if (day.length == 7) {
        color = "bg-green";
    } else if (day.length == 10) {
        color = "bg-aqua";
    }

    return '<li class="time-label ' + (day.length != 4 ? day.substring(0, day.lastIndexOf("-")) : "") + '" style="' + (day.length == 10 ? "display: none;" : "") + '">' +
        '<span class="' + color + '">' + day + '</span><a class="badge bg-yellow badge-number">' + count + '</a>' +
        '<div class="pull-right">' +
        '<button type="button" class="btn btn-box-tool" onclick="collapseLoginRecord(this, \'' + day + '\', true)">' +
        '<i class = "' + (day.length == 4 ? "fa fa-minus" : "fa fa-plus") + '"></i>' +
        '</button>' +
        '</div>' +
        '</li>';
}

function buildDayHtml(data, day) {
    return '<li class="' + day + '">' +
        '<i class="' + (data.deviceType == "Mobile" ? "fa fa-mobile bg-purple" : "fa fa-tv bg-blue" ) + '"></i>' +
        '<div class="timeline-item no-border">' +
        '<h3 class="timeline-header no-border">' +
        '<a>' + data.clientIp + '</a>' +
        '<span class="pull-right">' + data.loginTime + '</span>' +
        '</h3>' +
        '<div class="timeline-body">' +
        '<ul class="list-group">' +
        '<li class="list-group-item">' +
        '<span class="pull-right">' + data.deviceType + '</span>系统类型' +
        '</li>' +
        '<li class="list-group-item">' +
        '<span class="pull-right">' + data.browser + '</span>浏览器' +
        '</li>' +
        '<li class="list-group-item">' +
        '<span class="pull-right">' + (data.onlineTime ? data.onlineTime.replace("0毫秒", "") : "-") + '</span>在线时长' +
        '</li>' +
        '</ul>' +
        '</div>' +
        '</div>' +
        '</li>';
}

function setLoginRecords(data) {
    var html = '';
    $.each(data, function (ind, val) {
        html += buildHtml(val.lr_count, val.lr_login_time.replace("-order", ""))
    })
    html += '<li><i class="fa fa-clock-o bg-gray"></i></li>';
    $("#loginLogTab > ul").empty().append(html);
}

function updateUserPwd() {
    var oldpwd = $("#oldpwd").val();
    var newpwd = $("#newpwd").val();
    var confirmnewpwd = $("#confirmnewpwd").val();
    if (oldpwd && newpwd && confirmnewpwd) {
        if (newpwd === confirmnewpwd) {
            if (newpwd.length < 6) {
                parent.toastr.error("新密码长度不能小于6位")
                return false;
            }
            $("#user_info_overlay").show();
            $.ajax({
                type: "post",
                data: {
                    oldpassword: oldpwd,
                    newpassword: confirmnewpwd
                },
                url: "/personal/updateUserPwd",
                success: function (result) {
                    $("#user_info_overlay").hide();
                    if (result.success) {
                        if (result.data == -1) {
                            parent.toastr.error("密码修改失败，旧密码错误！");
                        } else if (result.data == 1) {
                            parent.toastr.success("密码修改成功，请重新登录！");
                            top.location.href = "/manage/logout";
                        } else {
                            parent.toastr.error(result.message);
                        }
                    }
                }
            });
        } else {
            parent.toastr.error("输入的新密码不一致！");
        }
    } else {
        parent.toastr.error("请输入密码！");
    }
}

function setLoginRecord(data) {
    $(".lr-loginTime").text(data.loginTime);
    $(".lr-clientIp").text(data.clientIp);
    $(".lr-deviceType").text(data.deviceType);
    $(".lr-onlineTime").text(data.onlineTime.replace("0毫秒", ""));
}

function setUserInfo(data) {
    $("#account").val(data.account);
    $("#organizationName").val(data.organizationName);
    $("#roleNames").val(data.roleNames);
    $("#username").val(data.username);
    $(".user-name").text(data.username);
    $("input[name='sex'][value='" + data.sex + "']").iCheck('check');
    $("#mailbox").val(data.mailbox);
    $("#phone").val(data.phone);
}

function renderingUser() {
    $.ajax({
        type: "get",
        url: "/personal/getUserInfo",
        success: function (result) {
            $("#user_info_overlay").hide();
            if (result.success) {
                setUserInfo(result.data["userInfo"]);
                setLoginRecord(result.data["loginRecord"]);
                setLoginRecords(result.data["dateMap"]);
            } else {
                parent.toastr.error(result.message);
            }
        }
    });
}

function updateUserInfo() {
    if (!$("#username").val()) {
        parent.toastr.error("请填写用户名！");
        return false;
    }
    var data = $("#form-user-info").serialize()
    //decodeURIComponent(data, true);
    $("#user_info_overlay").show();
    $.ajax({
        type: "post",
        data: data,
        url: "/personal/updateUserInfo",
        success: function (data) {
            $("#user_info_overlay").hide();
            if (data.success) {
                renderingUser();
                parent.toastr.success("用户信息修改成功！");
            } else {
                parent.toastr.error(data.message);
            }
        }
    });
}