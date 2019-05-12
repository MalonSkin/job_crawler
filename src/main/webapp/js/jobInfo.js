$(function () {

    $('#table').bootstrapTable({
        method: 'get',
        url: "jobInfo/findAll",//请求路径
        striped: true, //是否显示行间隔色
        pageNumber: 1, //初始化加载第一页
        pagination: true,//是否分页
        paginationShowPageGo: true,
        sidePagination: 'server',//server:服务器端分页|client：前端分页
        pageSize: 10,//单页记录数
        pageList: [10, 20, 50],//可选择单页记录数
        showRefresh: true,//刷新按钮
        queryParams: function (params) {//上传服务器的参数
            var temp = {//如果是在服务器端实现分页，limit、offset这两个参数是必须的
                size: params.limit, // 每页显示数量
                offset: params.offset, // SQL语句起始索引
                page: (params.offset / params.limit) + 1, //当前页码

                Name: $('#search_name').val(),
                Tel: $('#search_tel').val()
            };
            return temp;
        },
        columns: [
            {
                field: 'id',
                title: 'ID'
            },
            {
                field: 'companyName',
                title: '公司名称'
            },
            {
                field: 'companyAddr',
                title: '公司地址'
            },
            {
                field: 'companyInfo',
                title: '公司信息',
                formatter: function (value, row, index) {
                    if (!value) {
                        return "";
                    }
                    var val = value.substr(0, 20);
                    return val + '<button class="btn btn-success btn-xs detail" data-value="' + value + '" data-title="公司信息"></button>';
                }
            },
            {
                field: 'jobName',
                title: '职位名称'
            },
            {
                field: 'jobAddr',
                title: '工作地点'
            },
            {
                field: 'jobInfo',
                title: '工作信息',
                formatter: function (value, row, index) {
                    if (!value) {
                        return "";
                    }
                    var val = value.substr(0, 20);
                    return val + '<button class="btn btn-success btn-xs detail" data-value="' + value + '" data-title="工作信息"></button>';
                }
            },
            {
                field: 'salaryMin',
                title: '最低薪资'
            },
            {
                field: 'salaryMax',
                title: '最高薪资'
            },
            {
                field: 'url',
                title: '职位详情页',
                formatter: function (value, row, index) {
                    if (!value) {
                        return "";
                    }
                    return '<a target="_blank" href="' + value + '">' + value + '</a>';
                }
            },
            {
                field: 'time',
                title: '发布时间'
            }
        ]
    });

    $(document).on("click", "button.detail", function () {
        var value = $(this).data("value");
        var title = $(this).data("title");
        $("#myModal h4 .modal-title").text(title);
        $("#myModal div .modal-body").text(value);
        $('#myModal').modal({
            keyboard: true
        });
    });

});
