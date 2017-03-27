<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
	<meta charset="utf-8" />
	<title>登录页面</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<link href="media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="media/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
	<link href="media/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="media/css/style-metro.css" rel="stylesheet" type="text/css"/>
	<link href="media/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="media/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="media/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<link href="media/css/login.css" rel="stylesheet" type="text/css"/>
	<link rel="shortcut icon" href="media/image/favicon.ico" />
</head>
<body class="login">
	<div class="logo">
		<img src="media/image/logo-big.png" alt="" /> 
	</div>
	<div class="content">
		<form id="form_sample_1" class="form-vertical login-form" role="form" method="POST">
			<h3 class="form-title">登录</h3>
			<div class="control-group">
				<p id="showError" style="color:red;" class="alert-danger"></p>
			</div>
			<div class="control-group">
				<label class="control-label visible-ie8 visible-ie9">用户名</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-user"></i>
						<input class="m-wrap placeholder-no-fix" placeholder="用户名" name="sysUsers.userAccount" type="text" autofocus  value="1">
					</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="controls">
					<div class="input-icon left">
						<i class="icon-lock"></i>
						<input class="m-wrap placeholder-no-fix" placeholder="密码" name="sysUsers.userPassword" type="password" value="1">
					</div>
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn green pull-right">登录<i class="m-icon-swapright m-icon-white"></i></button>
			</div>
		</form>
	</div>
	<div class="copyright">
		2017 &copy; 管理平台.
	</div>
	<script src="media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="media/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<script src="media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>      
	<script src="media/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="media/js/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="media/js/jquery.blockui.min.js" type="text/javascript"></script>  
	<script src="media/js/jquery.cookie.min.js" type="text/javascript"></script>
	<script src="media/js/jquery.uniform.min.js" type="text/javascript" ></script>
	<script src="media/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="media/js/app.js" type="text/javascript"></script>
	<script src="media/js/bootstrap-modal.js" type="text/javascript"></script>
	<script src="media/js/bootstrap-modalmanager.js" type="text/javascript"></script>
	<script src="media/js/ui-modals.js" type="text/javascript"></script>
	<script src="media/js/login.js" type="text/javascript"></script>
	<script>
	var form1 = $('#form_sample_1');
	form1.validate({
		onsubmit:true,// 是否在提交是验证 
		errorElement : 'span', //default input error message container
		errorClass : 'alert-danger', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		rules : {
			"sysUsers.userAccount" : {
				required : true
			},
			"sysUsers.userPassword" : {
				required : true
			}
		},
		messages : { // custom messages for radio buttons and checkboxes
			"sysUsers.userAccount" : {
				required : "请输入用户名"
			},
			"sysUsers.userPassword" : {
				required : "请输入密码"
			}
		},
		submitHandler : function(form) {
			var url = "./login/login";
			$.ajax({
				type : 'POST',
				url : url,
				dataType : "json",
				async : false,
				data : form1.serialize(),
				success : function(respData) {
					if(respData.success != true){
						$("#showError").html(respData.msg);
						//return false;
					}
					else{
						$("#showError").html("");
						
					}
					window.location = "${basePath}index.jsp";
				}
			});
		}
	});
	</script>
</html>