<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入jstl核心标签库--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function () {

		//给整个浏览器添加键盘按下事件
		$(window).keydown(function (e) {
			//如果按的是回车键，则提交登录请求
			if(e.keyCode == 13){
				//用代码单击登录按钮
				$("#loginBtn").click();
			}
		});

		//给"登录"按钮添加单击事件
		$("#loginBtn").click(function () {
			//收集参数
			var loginAct=$.trim($("#loginAct").val());
			var loginPwd=$.trim($("#loginPwd").val());
			var isRemPwd=$("#isRemPwd").prop("checked");

			//表单验证
			if(loginAct==""){
				alert("用户名不能为空");
				return;
			}
			if(loginPwd==""){
				alert("密码不能为空");
				return;
			}

			//显示正在验证
			//$("#msg").text("正在努力验证...");

			//发送请求
			$.ajax({
				url:'settings/qx/user/login.do',
				data:{
					loginAct:loginAct,
					loginPwd:loginPwd,
					isRemPwd:isRemPwd
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code=="1"){
						//跳转到业务主页面
						window.location.href="workbench/index.do";
					}else{
						//提示信息
						$("#msg").text(data.message);
					}
				},
				//ajax向后台发请求之前需要执行的函数
				//该函数的返回值能够决定ajax是否真正向后台发送请求：
				//返回值为true时，发送请求
				//返回值为false时，放弃发送请求
				beforeSend:function () {
					//表单验证的代码可以放到在此处进行
					$("#msg").text("正在努力验证...");
					return true;
				}
			});
		});

		
		
		
		//给创建用户按钮添加单击事件
		$("#creatUserBtn").click(function () {
			//初始化工作，任意的js代码
			//清空表单获取dom对象，重置
			$("#creatUserForm").get(0).reset();

			//弹出创建市场活动的模态窗口
			$("#createUserModal").modal("show");
		})

		//给创建用户按钮添加单击事件
		$("#saveCreatUserBtn").click(function () {
			//收集参数
			var loginAct = $("#create-marketUserOwner").val();
			var loginPwd = $.trim($("#create-marketUserName").val());
			var name = $("#create-startDate").val();
			var email = $("#create-endDate").val();

			//表单验证
			if (name == "" && email == "") {
				alert("名称不能为空");
				return;
			}
			if (loginAct == "" && loginPwd == "") {
				//使用字符串的大小代替日期的大小

				//不合法
				alert("用户或密码不能为空");
				return;
			}
			/**
			 * 正则表达式：
			 *	1、语言，语法：定义字符串的匹配模式，可以判断指定的具体字符串是否符合匹配模式
			 *	2、语法通则：
			 *		1）//：在js中定义一个正则表达式eg： var regExp = /正则表达式内容/;
			 *		2）^:表示匹配字符串的开头位置
			 *		   $:表示匹配字符串的结尾部分
			 *		3）[]: 匹配指定字符集中的一位字符eg：	var regExp = /^[abc]$/;		表示该字符只能取abc其中的一个
			 *											var regExp = /^[A-Z]$/;
			 *		4）{}:匹配次数eg：	var regExp = /^[abc]{5}$/;
			 *		  {m}：匹配m次
			 *		  {m,n}：匹配m次到n次
			 *		  {m,}：匹配m次或者更多次
			 *		5）特殊符号：
			 *			/d：匹配一位数字，相当于[0-9]
			 *			/D：匹配一位非数字
			 *			/w：匹配所有的字符：字母数字下划线。
			 *			/W：匹配非字符，除了字母数字下划线。
			 *
			 *			/*：匹配0次或者n次，相当于{0,}
			 *			/+：匹配1次或n次，相当于{1,}
			 *			/?：匹配0次或者1次，相当于{0,1}
			 */

			//发送请求
			$.ajax({
				url: 'settings/qx/user/reg.do',
				data: {
					loginAct: loginAct,
					loginPwd: loginPwd,
					name: name,
					email: email
				},
				type: 'post',
				dataType: 'text',
				success: function(data) {
					// 请求成功后的操作
					alert("用户创建成功，请登录");

					// 关闭模态窗口
					$("#createUserModal").modal("hide");
				},
				error: function(xhr, status, error) {
					// 请求失败时的操作
					console.log(error);
				}
			});
		});
	});
</script>
</head>
<body>

	<!-- 创建用户的模态窗口 -->
	<div class="modal fade" id="createUserModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">注册用户</h4>
				</div>
				<div class="modal-body">

					<form id="creatUserForm" class="form-horizontal" role="form">

						<div class="form-group">
							<label for="create-marketUserOwner" class="col-sm-2 control-label">账户<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-marketUserOwner">
							</div>
							<label for="create-marketUserName" class="col-sm-2 control-label">密码<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-marketUserName">
							</div>
						</div>
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-startDate">
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">邮箱<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-endDate">
							</div>
						</div>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<%--data-dismiss--%>
					<button type="button" class="btn btn-primary" id="saveCreatUserBtn">注册</button>
				</div>
			</div>
		</div>
	</div>


	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2022&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<%--使用el表达式获取cookie返回给前端页面--%>
						<input class="form-control" id="loginAct" type="text" value="${cookie.loginAct.value}" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" id="loginPwd" type="password" value="${cookie.loginPwd.value}" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<%--jstl标签库判断--%>
							<c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
								<input type="checkbox" id="isRemPwd" checked>
							</c:if>
							<c:if test="${empty cookie.loginAct or empty cookie.loginPwd}">
								<input type="checkbox" id="isRemPwd">
							</c:if>
							 十天内免登录   <button type="button" class="btn btn-primary" id="creatUserBtn"><span class="glyphicon glyphicon-plus"></span> 创建用户</button>
						</label>
						&nbsp;&nbsp;
						<span id="msg" style="color: red"></span>
					</div>
					<button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
