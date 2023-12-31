<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<!--  PAGINATION plugin -->
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

<script type="text/javascript">

	$(function(){
		//给创建按钮添加单击事件
		$("#creatActivityBtn").click(function () {
			//初始化工作，任意的js代码
			//清空表单获取dom对象，重置
			$("#creatActivityForm").get(0).reset();

			//弹出创建市场活动的模态窗口
			$("#createActivityModal").modal("show");
		})
		
		//给保存按钮添加单击事件
		$("#saveCreatActivityBtn").click(function () {
			//收集参数
			var owner = $("#create-marketActivityOwner").val();
			var name = $.trim($("#create-marketActivityName").val());
			var startDate = $("#create-startDate").val();
			var endDate = $("#create-endDate").val();
			var cost = $.trim($("#create-cost").val());
			var description = $.trim($("#create-description").val());

			//表单验证
			if (owner == "") {
				alert("所有者不能为空");
				return;
			}
			if (name == "") {
				alert("名称不能为空");
				return;
			}
			if (startDate != "" && endDate != "") {
				//使用字符串的大小代替日期的大小
				if (startDate > endDate) {
					//不合法
					alert("结束日期不能比开始日期小");
					return;
				}
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
			var regExp = /^(([1-9]\d*)|0)$/;
			if (!regExp.test(cost)) {
				alert("成本只能是非负整数！")
				return;
			}

			//发送请求
			$.ajax({
				url:'workbench/activity/saveCreatActivity.do',
				data:{
					owner:owner,
					name: name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data){
					if (data.code == "1") {
						//关闭模态窗口
						$("#createActivityModal").modal("hide");
						//刷新市场活动列，显示第一页数据，保持每页条数不变【等查询功能做了再写】
						//4.7===============================================================
						queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
					}else {
						//提示信息
						alert(data.message);
						//模态窗口不关闭
						$("#createActivityModal").modal("show");//可以不写
					}
				}
			});
		});

		//当容器加载完成之后，对容器调用工具函数
		//当容器加载完成，对容器调用工具函数
		/*$("#create-startDate").datetimepicker({
			language:'zh-CN',//语言
			format:'yyyy-mm-dd',//日期格式
			minView:'month',//可以选择的最小视图
			initData:new Date(),//初始化显示日期
			autoclose:true,//选择完日期之后是否自动关闭
			todayBtn:true,//是否显示当前日期的按钮，默认为false
			clearBtn:true//设置是否显示清空按钮，默认是false
		})
		$("#create-endDate").datetimepicker({
			language:'zh-CN',//语言
			format:'yyyy-mm-dd',//日期格式
			minView:'month',//可以选择的最小视图
			initData:new Date(),//初始化显示日期
			autoclose:true,//选择完日期之后是否自动关闭
			todayBtn:true,//是否显示当前日期的按钮，默认为false
			clearBtn:true//设置是否显示清空按钮，默认是false
		})*/

		//使用一个选择器选用多个标签
		$(".myDate").datetimepicker({
			language:'zh-CN',//语言
			format:'yyyy-mm-dd',//日期格式
			minView:'month',//可以选择的最小视图
			initialDate:new Date(),//初始化显示日期
			autoclose:true,//选择完日期之后是否自动关闭
			todayBtn:true,//是否显示当前日期的按钮，默认为false
			clearBtn:true//设置是否显示清空按钮，默认是false
		});

		//当市场活动主页面加载完成，查询所有数据的第一页以及所有数据的总条数，默认每页显示10条数据
		queryActivityByConditionForPage(1,10);

		//给查询按钮添加单击事件
		$("#queryActivityBtn").click(function () {
			//当用户点击查询按钮，查询出所有符合条件的数据第一页以及记录总条数
			queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
		});
		//4.6========================================================================

		//给全选按钮添加单击事件
		$("#checkAll").click(function () {
			//如果全选按钮选中，则所有复选框全部选中，否则全部取消选中
			/*if (this.checked == true) {
				//父子选择器【>只能选择直接子标签】【两个空格表示获取所有子标签】
				$("#tBody  input[type='checkbox']").prop("checked",true);
			}else {
				$("#tBody  input[type='checkbox']").prop("checked",false);
			}*/
			//简写
			$("#tBody  input[type='checkbox']").prop("checked",this.checked);
		});

		//因为此处是动态生成的元素所以无法执行一下jQuery代码
		//选中列表中的checkedBox
		/*$("#tBody input[type='checked']").click(function () {alert("执行成功");
			//如果都选中，则全选按钮也选中
			if ($("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size()) {
				$("#checkAll").prop("checked",true);
			} else {
				//如果复选框有一个没有选中，则全选按钮也取消勾选
				$("#checkAll").prop("checked",false);
			}
		});*/

		$("#tBody").on("click","input[type='checkbox']",function () {
			//如果都选中，则全选按钮也选中
			if ($("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size()) {
				$("#checkAll").prop("checked", true);
			} else {
				//如果复选框有一个没有选中，则全选按钮也取消勾选
				$("#checkAll").prop("checked", false);
			}
		});

		//给删除按钮添加单击事件
		$("#deleteActivityBtn").click(function () {
			//收集参数
			//获取列表中所有被选中的checkbox
			var checkedIds = $("#tBody input[type='checkbox']:checked");
			if (checkedIds.size() == 0) {
				alert("请选择需要删除的市场活动");
				return;
			}
			//弹出确定删除按钮
			if (window.confirm("确认删除选中的市场活动吗？")) {
				var ids = "";
				$.each(checkedIds,function () {//id=xxxx&....&id=xxxx&
					ids += "id=" + this.value + "&";
				});
					ids = ids.substr(0,ids.length - 1);////id=xxxx&....&id=xxxx
					//alert(ids);
					//发送请求
					$.ajax({
						url:'workbench/activity/deleteActivityByIds.do',
						data:ids,
						type:'post',
						dataType:'json',
						success:function (data) {
							if (data.code == '1') {
								//刷新市场活动列表，显示第一页的数据，保持每页的记录条数不变
								queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
							} else {
								//提示信息
								alert(data.message);
							}
						}
					});

			}
		});

		//给修改按钮添加单击事件
		$("#editActivityBtn").click(function () {
			//收集参数
			//获取列表中被选中的checkbox
			var checkedIds = $("#tBody input[type='checkbox']:checked");
			if (checkedIds.size() == 0) {
				alert("请选择要修改的市场活动");
				return;
			}
			if (checkedIds.size() > 1) {
				alert("每次只能选择一条数据");
				return;
			}
			//var id = checkedIds.val();
			//var id = checkedIds.get(0).value;
			var id = checkedIds[0].value;
			//发送请求
			$.ajax({
				url:'workbench/activity/queryActivityById.do',
				data:{
					id:id
				} ,
				type:'post',
				dataType:'json',
				//设置参数
				success:function (data) {
					//把市场活动的信息显示到修改的模态窗口上
					$("#edit-id").val(data.id);
					$("#edit-marketActivityOwner").val(data.owner);
					$("#edit-marketActivityName").val(data.name);
					$("#edit-startDate").val(data.startDate);
					$("#edit-endDate").val(data.endDate);
					$("#edit-cost").val(data.cost);
					$("#edit-description").val(data.description);
					//弹出模态窗口
					$("#editActivityModal").modal("show");
				}
			})
		});


		//给更新按钮添加单击事件
		$("#saveEditActivityBtn").click(function () {
			//收集参数，获取参数
			var id = $("#edit-id").val();
			var owner = $.trim($("#edit-marketActivityOwner").val());
			var name = $("#edit-marketActivityName").val();
			var startDate = $("#edit-startDate").val();
			var endDate = $.trim($("#edit-endDate").val());
			var cost = $.trim($("#edit-cost").val());
			var description = $.trim($("#edit-description").val());

			//表单验证

			//发送请求
			$.ajax({
				url:'workbench/activity/saveEditActivity.do',
				data:{
					id:id,
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code == "1") {
						//关闭模态窗口
						$("#editActivityModal").modal("hide");
						//刷新市场活动列表，保持页号和每页显示条数不变
						queryActivityByConditionForPage($("#demo_pag1").bs_pagination('getOption','currentPage'),$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
					}else {
						//提示信息
						alert(data.message);
						//模态窗口不关闭
						$("#editActivityModal").modal("show");
					}
				}
			});
		});
		//给批量导出按钮添加单击事件
		$("#exportActivityAllBtn").click(function () {
			//发送同步请求
			window.location.href = "workbench/activity/exportAllActivities.do";
		});

		//给导入按钮添加单击事件
		$("#importActivityBtn").click(function () {
			//收集参数
			var activityFileName = $("#activityFile").val();
			//获取文件后缀substr(activityFileName.lastIndexOf(".") + 1)只传一个参数表示
			//从当前字符截取到字符串结束
			//此处activityFileName.lastIndexOf(".") + 1表示从最后一个”.“所在的字符后一位开始截串
			var suffix = activityFileName.substr(activityFileName.lastIndexOf(".") + 1).toLowerCase();
			if (suffix != "xls") {
				alert("文件类型不合法！！只支持xls类型的文件");
				return;
			}
			//获取文件
			var activityFile = $("#activityFile")[0].files[0];
			//验证文件大小
			if (activityFile.size >= 5 * 1024 * 1024) {
				alert("文件大小不能超过5MB");
				return;
			}
			//FormData是ajax提供的接口【接口广义上讲就是别人已经写好的方法，让程序员去调用】
			//可以模拟键值对向后台提交参数，FormData最大的优势是：不但能提交字符串数据，还能
			//											   提交二进制数据
			var formData = new FormData();
			formData.append("activityFile",activityFile);//必须保证和Controller中变量名称一致eg、activityFile
			formData.append("userName","张三");

			//发送请求
			$.ajax({
				url:'workbench/activity/importActivity.do',
				data:formData,
				processData:false,//设置ajax向后台提交参数之前，是否把参数统一换成字符串，默认值为true
				contentType:false,//设置Ajax向后台提交参数之前，是否把所有参数统一按urlencode编码，默认是true
				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code == "1") {
						//提示成功导入记录条数
						alert("成功导入" + data.retData + "条记录");
						//关闭模态窗口
						$("#importActivityModal").modal("hide");
						//刷新市场活动列表，显示第一页的数据，保持每页显示条数不变
						queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
					}else {
						//提示信息
						alert(data.message);
						//模态窗口不关闭
						$("#importActivityModal").modal("show");
					}
				}
			});
		});
	});

	//封装函数
	function queryActivityByConditionForPage(pageNo,pageSize) {

		/*当市场活动主页面加载完成时，查询所有数据的第一页以及所有数据的总条数
		* 收集参数
		* */
		var name = $("#query-name").val();
		var owner = $("#query-owner").val();
		var startDate = $("#query-startDate").val();
		var endDate = $("#query-endDate").val();
		//var pageNo = 1;
		//var pageSize = 10;
		//发送请求
		$.ajax({
			url:'workbench/activity/queryActivityByConditionForPage.do',
			data:{
				name:name,
				owner:owner,
				startDate:startDate,
				endDate:endDate,
				pageNo:pageNo,
				pageSize:pageSize
			},
			type:'post',
			dataType:'json',
			success:function(data) {
				//显示总条数
				//$("#totalRowsB").text(data.totalRows);
				//显示市场活动的列表遍历activityList集合，拼接所有行数据
				//不使用foreach是因为data属于js的局部变量，不是作用域里的数据，jstl只能遍历作用域里的标签
				var htmlStr = "";
				$.each(data.activityList,function(index,obj) {
					htmlStr+="<tr class=\"active\">";
					htmlStr+="<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";
					htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.id+"'\">"+obj.name+"</a></td>";
					htmlStr+="<td>"+obj.owner+"</td>";
					htmlStr+="<td>"+obj.startDate+"</td>";
					htmlStr+="<td>"+obj.endDate+"</td>";
					htmlStr+="</tr>";
				});
				/*回显信息*/
				$("#tBody").html(htmlStr);

				//只要查询数据，就取消总的复选框的选中
				$("#checkAll").prop("checked",false);

				//计算总页数
				var totalPages = 1;
				if(data.totalRows % pageSize == 0){
					totalPages = data.totalRows / pageSize;
				}else {
					//小数转换成整数parseInt()
					totalPages = parseInt(data.totalRows / pageSize) + 1;
				}

				//对容器调用bs_pagination工具函数，来显示翻页信息
				$("#demo_pag1").bs_pagination({
					currentPage:pageNo,//当前页号，相当于pageNO
					//以下三个参数相对应
					rowsPerPage:pageSize,//每页显示记录数，相当于pageSize
					totalRows:data.totalRows,
					totalPages: totalPages,//总页数，必填参数
					visiblePageLinks: 5,//最多可见的卡片数，默认是5

					showGoToPage: true,//是否显示跳转到多少页，默认是true

					showRowsPerPage: true,//是否显示每页显示条数，默认是true

					showRowsInfo: true,//是否显示记录的信息，默认是true

					//用户每次切换页号，都会触发以下函数
					//每次返回切换页号之后的pageNo和pageSize
					onChangePage:function(event,pageObj) {
						//js代码
						//alert(pageObj.currentPage);
						//alert(pageObj.rowsPerPage);
						queryActivityByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);//传入变换之后的页号和显示条数
					}
				});
			}
		});
	}
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="creatActivityForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								  <c:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
								  </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-startDate" readonly>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<%--data-dismiss--%>
					<button type="button" class="btn btn-primary" id="saveCreatActivityBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startDate" value="2020-10-10">
							</div>
							<label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endDate" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveEditActivityBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="query-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="query-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="queryActivityBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="creatActivityBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editActivityBtn" ><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivityBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>

					</tbody>
				</table>
				<div id="demo_pag1"></div>
			</div>


			
			<%--<div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="totalRowsB">50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>--%>
			
		</div>
		
	</div>
</body>
</html>