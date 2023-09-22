<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>文件上传测试</title>
</head>
<body>
<%--文件上传的表单必须满足的条件
    1、
    --%>
<form action="workbench/activity/fileUpload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="myFile"><br>
    <input type="text" name="userName"><br>
    <input type="submit" value="上传">

</form>

</body>
</html>
