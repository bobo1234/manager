/**
 * 页面初始化加载的方法
 */
function initFun() {
	if (secure.find) {
		dialog = BootstrapDialog.loading();
		findListInfo();
	}
	if (!secure.add) {
		$('button.add-btn').remove();
	}
	if (secure.add) {
		$('button.add-btn').removeClass('hide');
	}
}

/**
 * 图片接口
 * 
 * @param base64
 * @param url
 */
function uploadPic(base64, url) {
	var title=$('input.addtitle').val();
	dialog = BootstrapDialog.onloading();
	$.post(url, {
		img : base64,
		title:title
	}, function(data) {
		dialog.close();
		if (!$.isSuccess(data)) {
			BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_DANGER);
			return;
		}
		BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
		$('#avatar-modal').modal('hide');
		findListInfo();
	}, 'json');
}
/**
 * 显示操作历史图片的窗口
 */
function showAddBox(id) {
	if (!id) return;
	alert(id);
	dialog = BootstrapDialog.loading();
	BootstrapDialog.showModel($('div.set-principal-box'));
}

/**
 * 所有启用的图片
 */
function findListInfo() {
	$.post(localhostUrl+'mgr/findUsePic',function(data) {
		dialog.close();
		if (!$.isSuccess(data))
			return;
		var tbody = $('.imglist').empty();
		$.each(data.body, function(i, v) {
			$('<div class="col-sm-6 col-md-2"> </div>')
			.append($('<div class="thumbnail">').append($('<img style="width:170px;height:150px" src='+localhostUrl+v.picurl+' />'))
			.append($("<div class='caption'> </div>").append("<p>"+v.title+"</p>")
			.append($("<p></p>").append('<a href="java:void(0)" class="btn btn-primary" onclick="move2head('+v.id+')" role="button">'+' <span class="glyphicon glyphicon-arrow-left"></span>前移')
			.append('&nbsp;&nbsp;<a href="java:void(0)" class="btn btn-default" onclick="updatepic('+v.id+')" role="button">'+' <span class="glyphicon glyphicon-minus-sign"></span>作废')	
			)		
			)
			)
			.appendTo(tbody);
		});
	}, 'json');
}

/**
 * 前移
 * @param id
 */
function move2head(id) {
	$.post(localhostUrl+'mgr/moveHead', {
		picid : id
	}, function(data) {
		dialog.close();
		if (!$.isSuccess(data))
			return;
//		BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
		findListInfo();
	}, 'json');
}

/**
 * 提示并确定作废图片
 * @param id
 */
function updatepic(id) {
	if (!id)
		return;
	BootstrapDialog
			.confirm(
					"请确认是否要作废该图片?",
					function(result) {
						if (!result)
							return;
						dialog = BootstrapDialog.isSubmitted();
						$.getJSON(localhostUrl+'mgr/updatepic', {
							picid : id,
							ifuss : 1,
						}, function(data) {
							dialog.close();
							if (!$.isSuccess(data))
								return;
							findListInfo();
							BootstrapDialog.msg(data.body,
									BootstrapDialog.TYPE_SUCCESS);
						});
					});
}


/*
 * 显示部门添加窗口
 * 
 */
function showAddBox() {
	$('.empty').removeClass('empty');
	$('input.addName').val('');
	$('textarea.addDesc').val('');
	BootstrapDialog.showModel($('div.add-box'));
}


/*
 * 显示编辑窗口 
 *  
 */
function showModifyBox(picId) {
	$('.empty').removeClass('empty');
	if (!picId) return;
	dialog = BootstrapDialog.loading();
	$('input.modifyName').val("");
	$('textarea.modifyDesc').val("");
	$.getJSON('mgr/findPicById', {picid : picId}, function(data) {
		dialog.close();
		if (!$.isSuccess(data)) return;
		$('input.modifyName').val(data.body.deptName);
		$('textarea.modifyDesc').val(data.body.deptDescription);
		$('button.modifyBtn').attr('onclick', 'modifyDept(' + data.body.deptId + ')');
		$('div.modify-box').modal({
			closable : false,
			show : true
		});
	});
}
/*
 * 添加部门信息
 * 
 */
function addDepartment() {
	$.isSubmit = true;
	dept.deptName = $.verifyForm($('input.addName'), true);
	dept.deptDesc = $.verifyForm($('textarea.addDesc'), false);
	if (!$.isSubmit)
		return;
	dialog = BootstrapDialog.isSubmitted();
	$.post('mgr/department/addDepartment', {
		name : dept.deptName,
		desc : dept.deptDesc
	}, function(data) {
		dialog.close();
		if (!$.isSuccess(data))
			return;
		BootstrapDialog.hideModel($('div.add-box'));
		BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
		findListInfo();
	}, 'json');
}