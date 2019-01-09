$(function () {
    "use strict";
    //Make the dashboard widgets sortable Using jquery UI
    $(".connectedSortable").sortable({
        placeholder: "sort-highlight",
        connectWith: ".connectedSortable",
        handle: ".box-header, .nav-tabs",
        forcePlaceholderSize: true,
        zIndex: 999999
    });
    $(".connectedSortable .box-header, .connectedSortable .nav-tabs-custom").css("cursor", "move");

    //The Calender
    $("#calendar").datepicker({
        language: 'zh-CN',
        todayHighlight: true
    });

    initSocket();
    countUserGuest(lineChart);
});

function getIPAndPort() {
    var path = window.document.location.href;
    var localhostPaht = path.substring(0, path.indexOf(window.document.location.pathname));
    return localhostPaht.replace("http", "");
}

function initSocket() {
    var webSocket;
    if ('WebSocket' in window || 'MozWebSocket' in window) {
        webSocket = new WebSocket('ws' + getIPAndPort() + '/websocket/socketServer');
    } else {
        webSocket = new SockJS("http" + getIPAndPort() + "/sockjs/socketServer");
    }

    webSocket.onopen = function (event) {
    }
    webSocket.onerror = function (event) {
    }
    webSocket.onmessage = function (event) {
        if (event.data) {
            setValue(JSON.parse(event.data));
        }
    }
    webSocket.onclose = function (event) {
    };
}

function setValue(data) {//data:message
    data = data.data;
    if (data.account) {
        parent.setUserInfo(data)
    }
    if (data.newuser != 0) {
        $(".dashboard-tadoy-new").html(data.newuser);
    }
    if (data.onlineuser != 0) {
        $(".dashboard-oneline").html(data.onlineuser);
    }
    if (data.todayguest != 0) {
        $(".dashboard-tadoy-guest").html(data.todayguest);
    }
}

function canvasLine(labels, data) {
    var lineChartOptions = {
        showScale: true,
        scaleShowGridLines: false,// 在图标上显示网状表格
        bezierCurve: true,// 线条是否弯曲
        pointDot: true,// 显示数据线上的坐标点
        datasetFill: false,// 是否填充数据集
        legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
        responsive: true,// 响应式
    };

    var lineChart = new Chart($("#lineChart").get(0).getContext("2d"));
    lineChart.Line({
        labels: labels,
        datasets: [
            {
                label: "总量",
                // 线的颜色
                strokeColor: "rgba(60,141,188,0.8)",
                // 点的填充颜色
                pointColor: "rgba(60,141,188,0.8)",
                pointHighlightFill: "#fff",
                data: data[0]
            }, {
                label: "账号",
                strokeColor: "red",
                pointColor: "red",
                pointHighlightFill: "#fff",
                data: data[1]
            }, {
                label: "IP",
                strokeColor: "green",
                pointColor: "green",
                pointHighlightFill: "#fff",
                data: data[2]
            }
        ]
    }, lineChartOptions);
}

function countUserGuest() {
    $.ajax({
        type: "get",
        url: "/monitor/countUserGuest",
        success: function (result) {
            if (result) {
                var labels = [];
                var data = [];
                var tempdata = [];

                for (var key in result["total"]) {
                    labels.push(key);
                    tempdata.push(result["total"][key])
                }
                data.push(tempdata);

                tempdata = [];
                for (var key in result["account"]) {
                    tempdata.push(result["account"][key])
                }
                data.push(tempdata);

                tempdata = [];
                for (var key in result["ip"]) {
                    tempdata.push(result["ip"][key])
                }
                data.push(tempdata);
                canvasLine(labels, data);
            }
        }
    });
}
