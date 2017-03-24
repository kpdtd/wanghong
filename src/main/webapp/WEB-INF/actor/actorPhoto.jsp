<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<div class="modal-body">
	<div id="photo" class="row-fluid search-images">
	</div>
</div>
<div class="modal-footer">
	<button type="button" data-dismiss="modal" class="btn">关闭</button>
</div>
<script>
var actorId = "${actorId}";
	showPV(actorId);
	function showPV(actorId){
		$.ajax({
			url : "./actorPV",
			type : "post",
			data : {actorId : actorId},
			dataType : "json",
			success : function(data) {
				var ap = data.ap;
				var imgPath = data.imgPath;
				var apList = new Array(); // 图片数组
				if(ap.length > 0){
					for(var i=0;i<ap.length;i++){
						apList[i] = '<li class="span3"><img src="../downloadFile.jsp?identity='+imgPath+'&path='+ap[i].savePath+'"" alt=""></li>';
					}
				}
				var apHtml = "";
				
				$.each(apList,function(tindex, telement) {
					if(apList.length == 0){
						return;
					}
					if(tindex == 0){
						apHtml += ('<ul class="thumbnails">' + apList[tindex]);
					}else if(tindex + 1 == apList.length){
						apHtml += (apList[tindex] + "</ul>");
					}else if((tindex + 1) % 4 == 0){
						apHtml += (apList[tindex] + '</ul><ul class="thumbnails">');
					}else if((tindex + 1) % 4 > 0){
						apHtml += apList[tindex];
					}
				});
				$("#photo").html(apHtml);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//layer.msg('操作失败！');
			}
		});
	}
</script>