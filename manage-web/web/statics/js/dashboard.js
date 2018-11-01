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

    var lineChartOptions = {
        scaleShowGridLines: false,// 在图标上显示网状表格
        bezierCurve: true,// 线条是否弯曲
        pointDot: true,// 显示数据线上的坐标点
        datasetFill: false,// 是否填充数据集
        legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
        responsive: true,// 响应式
    };

    var lineChart = new Chart($("#lineChart").get(0).getContext("2d"));
    lineChart.Line({
        labels: ["10-26", "10-27", "10-28", "10-29", "10-30", "10-31", "11-01"],
        datasets: [
            {
                label: "访问量",
                fillColor: "rgba(60,141,188,0.9)",
                strokeColor: "rgba(60,141,188,0.8)",
                pointColor: "#3b8bba",
                pointStrokeColor: "rgba(60,141,188,1)",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(60,141,188,1)",
                data: [28, 6, 0, 19, 22, 1, 4]
            }
        ]
    }, lineChartOptions);

});
