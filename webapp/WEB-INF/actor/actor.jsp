<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<%@ include file="/common/meta.jsp"%>
<%@include file="/common/footer.jsp"%>
<!-- BEGIN HEAD -->
<head>
<style type="text/css">
.row {
	margin-left: 0px;
}
</style>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-footer-fixed">
	<!-- BEGIN HEADER -->
	<%@ include file="/common/nav.jsp"%>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<%@ include file="/common/menu.jsp"%>
		<!-- END SIDEBAR -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<ul class="breadcrumb">
							<li><i class="icon-list"></i><a href="＃">主播</a><i class="icon-angle-right"></i></li>
							<li><a href="#">列表</a></li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-th"></i>列表
								</div>
							</div>
							<div class="portlet-body">
								<div id="sample_2_wrapper" class="dataTables_wrapper form-inline" role="grid">
									<div class="row-fluid">
										<div class="control-group">
											<select id="websiteid" class="m-wrap xsmall" placeholder="来源"></select>
											<select id="recommend" class="m-wrap xsmall" placeholder="编辑推荐"></select> 
											<select id="state" class="m-wrap xsmall" placeholder="状态"></select>
											<div class="input-prepend">
												<span class="add-on"><i class="icon-calendar"></i></span>
											<input class="m-wrap xsmall date-picker"  size="16" id="startTime" placeholder="开始时间" type="text" value="" />
											</div>
											<div class="input-prepend">
												<span class="add-on"><i class="icon-calendar"></i></span>
											<input class="m-wrap xsmall date-picker"  size="16" id="endTime" placeholder="结束时间" type="text" value="" />
											</div>
											<input class="m-wrap xsmall" size="10" id="actorId" type="text" placeholder="主播ID">
											<input class="m-wrap xsmall" size="10" id="name" type="text" placeholder="姓名">
											<input class="m-wrap xsmall" size="10" id="nickname" type="text" placeholder="昵称">
											<input class="m-wrap xsmall" size="10" id="videoNum" type="text" placeholder="视频数>=" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();">
											<button id="search" class="btn red"><i class="icon-search"></i> 查询</button>
											<a id="add" class="btn green" ><i class="icon-plus"></i> 添加</a>
										</div>
									</div>
									<div class="row-fluid">
										<table class="table table-striped table-bordered table-hover table-full-width dataTable" id="portlet_Tables"
											aria-describedby="sample_2_info">
											<thead>
												<tr>
													<th>ID</th>
													<th>头像</th>
													<th>姓名</th>
													<th>昵称</th>
													<th>状态</th>
													<th>来源</th>
													<th>评论数</th>
													<th>视频数</th>
													<th>人气值</th>
													<th>关注数</th>
													<th>创建时间</th>
													<th>操作</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		jQuery(document).ready(function() {
			App.init(); // initlayout and core plugins
			UIModals.init();
			TableManaged.init();
			initPage();
			FormComponents.init();
			$('.btn.green').click(function(){
				   App.Modal.load('./add',{},{'title':'添加主播'});
			});
			document.getElementById("websiteid").innerHTML = getWebsite("");
			constructDictionaryOption("RECOMMENT","","recommend","编辑推荐");
			constructDictionaryOption("ACTOR_STATE","","state","是否上线");
		});
		$("#search").bind('click', function() {
			initPage();
		});
		
		function confirm(id,state,value){
			$.ajax({
				type : 'POST',
				url : './onlineActor',
				dataType : "text",
				data : {
					id : id,
					state : state
				},
				success : function(data, status) {
					initPage();
				}
			})
		}
		function initPage() {
			var name = document.getElementById("name").value;
			var nickname = document.getElementById("nickname").value;
			var websiteid = document.getElementById("websiteid").value;
			var actorId = document.getElementById("actorId").value;
			var recommend = document.getElementById("recommend").value;
			var state = document.getElementById("state").value;
			var startTime = document.getElementById("startTime").value;
			var endTime = document.getElementById("endTime").value;
			var videoNum = document.getElementById("videoNum").value;
			var oTable = $('#portlet_Tables').dataTable({
				"bLengthChange" : false, //改变每页显示数据数量 可选的每页展示的数据数量，默认为10条
				"iDisplayLength" : 20, // 默认煤业显示条数
				"bDestroy" : true,
				"bServerSide" : true, // 使用服务器端处理
				"searching" : false, // 是否增加搜索功能
				"sDom": "<'row-fluid'<f>r>t<'row-fluid'<'span6'i><'span6'p>>", // table布局
		        "aoColumnDefs": [{'bSortable': false,'aTargets': [1,4,5,6,7,8]}],
		        "aaSorting" : [[ 0, "desc" ]], 
				"fnServerParams" : function(aoData) {
					aoData.push({ "name" : "name", "value" : name });
					aoData.push({ "name" : "nickname", "value" : nickname });
					aoData.push({ "name" : "websiteid", "value" : websiteid });
					aoData.push({ "name" : "actorId", "value" : actorId });
					aoData.push({ "name" : "recommend", "value" : recommend });
					aoData.push({ "name" : "state", "value" : state });
					aoData.push({ "name" : "startTime", "value" : startTime });
					aoData.push({ "name" : "endTime", "value" : endTime });
					aoData.push({ "name" : "videoNum", "value" : videoNum });
				},
				"sServerMethod" : "POST",
				"sAjaxSource" : "getList",
				"aoColumns" : [{
					"sClass" : "center",
					"mDataProp" : "id"
				},{
					"sClass" : "center",
					"mDataProp" : "icon",
					"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
						var html = "";
						if(oData.icon != null && oData.icon != ''){
							html += '<img src="../downloadFile.jsp?identity='+oData.systemPath+'&path='+oData.icon+'" width="38" height="54"/>';
						}
						return $(nTd).html(html);	
					}
				},{
					"sClass" : "center",
					"mDataProp" : "name",
					"mRender" : function(obj){
						if(obj != null){
							return cutString(obj,20);
						}else{
							return "";
						}
					}
				}, {
					"sClass" : "center",
					"mDataProp" : "nickname",
					"mRender" : function(obj){
						if(obj != null){
							return cutString(obj,20);
						}else{
							return "";
						}
					}
				}, {
					"sClass" : "center",
					"mDataProp" : "state",
					"mRender" : function(obj){
						if(obj != 0){
							return '上线';
						}else{
							return "下线";
						}
					}
				}, {
					"sClass" : "center",
					"mDataProp" : "websiteId",
					"mRender" : function (obj){
						var name = "";
						$.each(JSON.parse('${actorWebSiteObj}'), function(tindex, telement) {
							if (telement.id == obj) {
								name = telement.websiteName;
							}
						});
						return name;
					}
				}, {
					"sClass" : "center",
					"mDataProp" : "commentNum"
				},{
					"sClass" : "center",
					"mDataProp" : "videoNum"
				},{
					"sClass" : "center",
					"mDataProp" : "fans"
				},{
					"sClass" : "center",
					"mDataProp" : "attentionNum"
				},{
					"sClass" : "center",
					"mDataProp" : "createTime",
					"mRender" : function(obj){
						if(obj != null){
						 	return new Date(obj).toLocaleString();
						}else{
							return '';
						}
					}
				}, {
					"sClass" : "center",
					"mDataProp" : "id",
					"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
						var html = "";
						html += '<a class="btn mini purple" onclick="edit('+oData.id+')"><i class="icon-edit"></i>编辑</a>';
						if(oData.state == 0 || oData.state == null){
							html += " <a class='btn mini red' href='javascript:;' onclick=confirm('"+oData.id+"',"+oData.state+",'您确认要上线该主播吗?')><i class='icon-ok'></i> 上线</a>";
						}else{
							html += " <a class='btn mini green' href='javascript:;' onclick=confirm('"+oData.id+"',"+oData.state+",'您确认要下线该主播吗?')><i class='icon-remove'></i> 下线</a>";
						}
						html += ' <a class="btn mini brownamber" onclick=actorTagAddModal('+oData.id+')><i class="icon-tag"></i> 标签</a>';
						return $(nTd).html(html);	
					}
				} ],
				"sPaginationType": "bootstrap",
				"oLanguage": {
		           	"sProcessing" : "处理中...",
		           	"sZeroRecords" : "没有匹配的记录", // 无记录的情况下显示的表格信息
		           	"sInfoEmpty" : "显示第 0 至 0 项记录，共 0 项", // 当没有数据时显示的页脚信息
		           	"sInfo" : "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
		           	"oPaginate": {
		           		"sFirst" : "首页",
		              	"sPrevious": "上一页",
		              	"sNext": "下一页",
		              	"sLast" : "尾页"
		           	}
				}
			});
		}
		function edit(id){
			App.Modal.load("./add",{'id' : id},{"title":"编辑主播"});
		}
		
		function actorTagAddModal(id){
			App.Modal.load("./addTag",{'id' : id},{"title":"主播标签"});
		}
	</script>
</body>
<!-- END BODY -->
</html>