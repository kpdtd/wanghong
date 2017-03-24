<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="modal-body" data-width="720">
	<div class="portlet-body form row-fluid">
		<div class="portlet-body">
			<div class="accordion in collapse" id="accordion1"
				style="height: auto;">
				<div class="accordion-group">
					<div class="accordion-heading">
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion1" href="#collapse_1"> 视频添加/详情 <i
							class="icon-angle-right"></i>
						</a>
					</div>
					<div id="collapse_1" class="accordion-body in collapse"
						style="height: auto;">
						<div class="accordion-inner">
							<form id="video_form" action="#"
								class="form-horizontal dialog" onsubmit="return false;"
								enctype="multipart/form-data">
								<input type="hidden" id="id" name="id"/>
								<input type="hidden" id='actorId' name="actorId" value='${actorId }'>
								<div class="control-group">
									<label class="control-label">视频截图<span class="required">*</span></label>
									<div class="controls">
										<div class="fileupload fileupload-new"
											data-provides="fileupload">
											<div class="fileupload-new thumbnail"
												style="width: 150px; height: 115px;">
												<img src="../media/image/defalut.jpg" alt="" />
											</div>
											<div class="fileupload-preview fileupload-exists thumbnail"
												style="max-width: 100px; max-height: 75px; line-height: 20px;"></div>
											<div>
												<span class="btn btn-file"> <span
													class="fileupload-new">选择图片</span> <span
													class="fileupload-exists">更改</span> <input type="file"
													class="default" name="image" />
												</span> <a href="#" class="btn fileupload-exists"
													data-dismiss="fileupload">删除</a>
											</div>
										</div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">视频<span class="required">*</span></label>
									<div class="controls">
										<div class="fileupload fileupload-new"
											data-provides="fileupload">
											<div class="fileupload-new thumbnail"
												style="width: 150px; height: 115px;">
												<img src="../media/image/timg.jpg" alt="" />
											</div>
											<div class="fileupload-preview fileupload-exists thumbnail"
												style="max-width: 100px; max-height: 75px; line-height: 20px;"></div>
											<div>
												<span class="btn btn-file"> <span
													class="fileupload-new">选择视频</span> <span
													class="fileupload-exists">更改</span> <input type="file"
													class="default" name="video" />
												</span> <a href="#" class="btn fileupload-exists"
													data-dismiss="fileupload">删除</a>
											</div>
										</div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="firstName">价格<span
										class="required">*</span></label>
									<div class="controls">
										<input type="text" name="price" class="m-wrap span10"
											placeholder="元" value="" maxlength="18">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="firstName">状态<span
										class="required">*</span></label>
									<div class="controls">
										<select name="status" class="m-wrap span10">
											<option value="1" selected>显示</option>
											<option value="0">不显示</option>
										</select>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="firstName">视频介绍</label>
									<div class="controls">
										<textarea name="introduction" rows="" cols=""
											class="m-wrap span10" placeholder="最长128字符" maxlength="128"></textarea>
									</div>
								</div>
							</form>
							<div class="modal-footer">
								<button type="button" class="btn green" id="submit_btn">保存</button>
								<button type="button" data-dismiss="modal" class="btn">关闭</button>
							</div>
						</div>
					</div>
					<div class="accordion-group">
						<div class="accordion-heading">
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion1" href="#collapse_2"> 视频信息<i
								class="icon-angle-right"></i>
							</a>
						</div>
						<div id="collapse_2" class="accordion-body in collapse"
							style="height: auto;">
							<div class="accordion-inner">
								<table
									class="table table-striped table-bordered table-hover table-full-width dataTable"
									id="video_Tables" aria-describedby="sample_2_info">
									<thead>
										<tr>
											<th>截图</th>
											<th>价格(元)</th>
											<th>状态</th>
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
		<script>
		var actorId = "${actorId}";
		initPage(actorId);
			function initPage(actorId) {
				var oTable = $('#video_Tables').dataTable({
					"bLengthChange" : false, //改变每页显示数据数量 可选的每页展示的数据数量，默认为10条
					"iDisplayLength" : 50, // 默认煤业显示条数
					"bDestroy" : true,
					"bServerSide" : true, // 使用服务器端处理
					"bSort": false, //排序功能 默认为true
					"searching" : false, // 是否增加搜索功能
					"sDom": "<'row-fluid'<f>r>t<'row-fluid'<'span6'i><'span6'p>>", // table布局
			        "aaSorting" : [[ 0, "desc" ]], 
			        "fnServerParams" : function(aoData) {
						aoData.push({ "name" : "actorId", "value" : actorId });
					},
					"sServerMethod" : "POST",
					"sAjaxSource" : "./getActorVideo",
					"aoColumns" : [{
						"sClass" : "center",
						"mDataProp" : "image",
						"mRender" : function(obj) {
							if (obj != null) {
								return obj;
							} else {
								return '';
							}
						}
					},{
						"sClass" : "center",
						"mDataProp" : "price"
					},{
						"sClass" : "center",
						"mDataProp" : "state",
						"mRender" : function(obj) {
							if (obj != 0) {
								return '显示';
							} else {
								return "不显示";
							}
						}
					},{
						"sClass" : "center",
						"mDataProp" : "createTime",
						"mRender" : function(obj) {
							if (obj != null) {
								return new Date(obj).toLocaleString();
							} else {
								return '';
							}
						}
					},{
						"sClass" : "center",
						"mDataProp" : "id",
						"fnCreatedCell" : function(nTd,sData, oData, iRow,iCol) {
							var html = "";
							//html += " <a class='btn mini yellow' href='javascript:;' onclick=confirm('" + data + "','您确定要删除该视频信息吗？')>删除</a>";
							return $(nTd).html(html);
						}
					} ],
					"sPaginationType" : "bootstrap",
					"oLanguage" : {
						"sProcessing" : "处理中...",
						"sZeroRecords" : "没有匹配的记录", // 无记录的情况下显示的表格信息
						"sInfoEmpty" : "显示第 0 至 0 项记录，共 0 项", // 当没有数据时显示的页脚信息
						"sInfo" : "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
						"oPaginate" : {
							"sFirst" : "首页",
							"sPrevious" : "上一页",
							"sNext" : "下一页",
							"sLast" : "尾页"
						}
					}
				});
			}

			function confirm(id, value) {
				$.ajax({
					type : 'POST',
					url : './delete',
					dataType : "text",
					data : {
						id : id
					},
					success : function(data, status) {
						initPage();
					}
				})
			}

			$("#submit_btn").click(function() {
				var id = $("#initID").val();
				if (id != "" && id != null) {
					$("#video_form").attr("action", "./editActorVideo");
				} else {
					$("#video_form").attr("action", "./addActorVideo");
				}
				if ($('#video_form').valid()) {
					App.Ajax.submit('video_form', {
						fn : function(json) {
							App.Tables.refresh('video');
						}
					});
				}
			});

			App.validate('video_form', {
				rules : {
					"image" : {
						required : true
					},
					"video" : {
						required : true
					},
					"price" : {
						required : true,
						number : true
					}
				}
			});
		</script>