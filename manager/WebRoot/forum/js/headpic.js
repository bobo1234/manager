/**
 * 页面初始化加载的方法
 */
function initFun() {
	if (secure.find) {
		dialog = BootstrapDialog.loading();
		dialog.close();
//		findListInfo();
	}
	if (!secure.add) {
		$('button.add-btn').remove();
	}
	if (secure.add) {
		$('button.add-btn').removeClass('hide');
	}
}
/*
 * 获取部门列表 
 *  
 */
function findListInfo() {
	$.post('mgr/findHeadPic', function(data) {
		dialog.close();
		var tbody = $('tbody.tbody').empty();
		if (!$.isSuccess(data)) return;
		$.each(data.body, function(i, v) {
			$('<tr></tr>')
			.append($('<td></td>').append(v.deptId))
			.append($('<td></td>').append(v.deptName))
			.append($('<td></td>').append(v.time))
			.append($('<td></td>').append(v.creator))
			.append($('<td></td>').append(v.fullName))
			.append($('<td></td>').append(v.deptDescription))
			.append($('<td></td>').append(analyzeBtns(v)))
			.appendTo(tbody);
		});
	}, 'json');
	
}
/*
 * 分析操作按钮 
 *  
 */
function analyzeBtns(v) {
	var btns = "";
	btns += secure.modify ? "<button type='button' class='btn btn-primary btn-xs' onclick='showModifyBox("+ v.deptId + ")'><span class='glyphicon glyphicon-pencil'></span>编辑</button>" : "";
	btns += secure.del ? "<button type='button' class='btn btn-danger btn-xs' onclick='hintDelete(" + v.deptId + ")'><span class='glyphicon glyphicon-remove'></span>删除</button>" : "";
	return btns;
}


/*
 * 提示并确定删除部门信息 
 *  
 */
function hintDelete(id) {
	if (!id) return;
	BootstrapDialog.confirm("请确认是否要删除该部门?<br /><span class='placeholder'>PS: 同时会删除该部门下的所有职位!</span>", function(result) {
		if (!result) return;
		dialog = BootstrapDialog.isSubmitted();
		$.getJSON('mgr/department/deleteDepartment', {deptId : id}, function(data) {
			dialog.close();
			if (!$.isSuccess(data)) return;
			findListInfo();
			BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
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
 * 添加部门信息 
 *  
 */
function addDepartment() {
	$.isSubmit = true;
	dept.deptName = $.verifyForm($('input.addName'), true);
	dept.deptDesc = $.verifyForm($('textarea.addDesc'), false);
	if (!$.isSubmit) return;
	dialog = BootstrapDialog.isSubmitted();
	$.post('mgr/department/addDepartment', {name : dept.deptName,desc : dept.deptDesc}, function(data) {
		dialog.close();
		if (!$.isSuccess(data)) return;
		BootstrapDialog.hideModel($('div.add-box'));
		BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
		findListInfo();
	}, 'json');
}