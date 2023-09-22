<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <%--1、引入jQuery--%>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <%--2、引入bootstrape框架--%>
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <%--3、引入bootstrap_DateTimePicker插件--%>
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <title>演示日历插件</title>

    <script type="text/javascript">
        $(function () {
            //当容器加载完成，对容器调用工具函数
            $("#myDate").datetimepicker({
                language:'zh-CN',//语言
                format:'yyyy-mm-dd',//日期格式
                minView:'month',//可以选择的最小视图
                initData:new Date(),//初始化显示日期
                autoclose:true,//选择完日期之后是否自动关闭
                todayBtn:true,//是否显示当前日期的按钮，默认为false
                clearBtn:true//设置是否显示清空按钮，默认是false
            });
        })
    </script>
</head>
<body>

<%--diable既不能改也不能提交 readonly不能改但是能提交--%>
<input type="text" id="myDate" readonly>



</body>
</html>
