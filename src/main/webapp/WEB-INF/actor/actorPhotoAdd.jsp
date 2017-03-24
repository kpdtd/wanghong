<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="modal-body" data-width="720">
	<div class="portlet-body form row-fluid">
		<div class="row-fluid ">
			<form enctype="multipart/form-data">
			    <input id="actorPhoto" name="actorPhoto" class="file" type="file" multiple data-preview-file-type="any" data-upload-url="./uploadPhoto" data-preview-file-icon="">
			</form>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" data-dismiss="modal" class="btn">关闭</button>
</div>
<script>
	var actorId = "${actorId}";
	$('#actorPhoto').fileinput({
        language: 'zh',
        uploadUrl: './uploadPhoto',
        uploadExtraData : {actorId : actorId},
        allowedPreviewTypes : ['image']
    });
    $('#actorPhoto').on('fileuploaderror', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
        console.log(data);
        console.log('File upload error');
    });
    $('#actorPhoto').on('fileerror', function(event, data) {
        console.log(data.id);
        console.log(data.index);
        console.log(data.file);
        console.log(data.reader);
        console.log(data.files);
    });
    $('#actorPhoto').on('fileuploaded', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
        console.log('File uploaded triggered');
    });
</script>