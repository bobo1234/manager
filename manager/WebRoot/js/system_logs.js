var position = {};

function initFun() {
	if(secure.find){
		dialog = BootstrapDialog.loading();
		findUserSelect('', $('select.userList'));
		findListInfo();
	}
	if(!secure.add)
		$('button.add-btn').remove();
	if(secure.add)
		$('button.add-btn').removeClass('hide');
}
/*
 * 获取日志列表数据
 *  
 */
function findListInfo() {
	$.post('mgr/findLogListInfo', {
		page : page,
		Logtype : $('select.typeList').val(),
		des : $('input.searchInput').val(),
		beginTime : $('#begin_time').val(),
		endTime : $('#end_time').val(),
		acctId : $('select.userList').val()
	}, function(data){
		dialog.close();
		var tbody = $('tbody.tbody').empty();
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i, v){
			var type="";
			switch(v.logType) 
			{ 
			case 1: 
				type='FIND'; 
			break; 
			case 2: 
				type='DELETE'; 
			break; 
			case 3: 
				type='MODIFY'; 
				break; 
			case 4: 
				type='ADD'; 
				break; 
			case 5: 
				type='LOGIN';
				break; 
			default:; 
			} 
			$("<tr></tr>")
			.append($("<td></td>").append(i+1))
			.append($("<td></td>").append(v.method))
			.append($("<td></td>").append(v.description))
			.append($("<td></td>").append(v.requestIp))
			.append($("<td></td>").append(type))
			.append($("<td></td>").append(v.createDate))
			.append($("<td></td>").append(analyzeBtns(v)))
			.appendTo(tbody);
		});
	}, 'json');
	$.post('mgr/findLogPage', {
		page : page,
		des : $('input.searchInput').val(),
		Logtype : $('select.typeList').val(),
		beginTime : $('#begin_time').val(),
		endTime : $('#end_time').val(),
		acctId : $('select.userList').val()
	}, function(data){
		$.analysisPage(data.body);
	}, 'json');
}
/*
 * 解析操作按钮
 *  
 */
function analyzeBtns(v){
	var btns = "";
	btns += secure.del ? "<button type='button' class='btn btn-danger btn-xs' onclick='hintDelete(\""+v.id+"\")'><span class='glyphicon glyphicon-remove'></span>删除</button>" : "" ;
	return btns;
}
/*
 * 显示窗口
 *  
 */
function showModifyBox(id){
	$('.empty').removeClass('empty');
	if(!id) return;
	dialog = BootstrapDialog.loading();
	$.getJSON('mgr/position/findPositionById', {id:id}, function(data){
		dialog.close();
		if(!$.isSuccess(data)) return;
		position.id = data.body.poId;
		findDepartmentSelect(data.body.poDepartment, $('select.modifyDeptList'));
		$('input.modifyName').val(data.body.poName);
		$('textarea.modifyDesc').val(data.body.poDescription);
		BootstrapDialog.showModel($('div.modify-box'));
	});
}

/**
 * 获取管理人员下拉菜单
 * @param curDepartient 当前要被选中的人员
 * @param eml 要被赋值的元素
 *  
 */
function findUserSelect(curDepartient, eml) {
	eml.empty().append("<option value=''>选择操作人员</option>");
	$.getJSON('mgr/account/findAccount', function(data) {
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i,v){
			$("<option "+analyzeSelect(v.acctId,curDepartient) +" value="+v.acctId+"></option>")
			.append(v.acctNickname)
			.appendTo(eml);
		});
	}); 
}

/*
 *  
 */
function analyzeSelect(id, curDepartient){
	return curDepartient > 0 && id == curDepartient ? " selected=true " : "" ;
}
/*
 * 显示添加窗口
 *  
 */
function showAddBox(){
	$('.empty').removeClass('empty');
	$('input.addName').val("");
	$('textarea.addDesc').val("");
	findDepartmentSelect(0, $('select.addDeptList'));
	BootstrapDialog.showModel($('div.add-box'));
}
/*
 * 提示并确定删除日志
 *  
 */
function hintDelete(id){
	if(!id) return;
	BootstrapDialog.confirm("请确认是否删除该数据!", function(result){
		if(!result) return;
		dialog = BootstrapDialog.isSubmitted();
		$.getJSON('mgr/deleteLog',{id : id}, function(data){
			if(!$.isSuccess(data)) return;
			dialog.close();
			BootstrapDialog.msg(data.body, BootstrapDialog.TYPE_SUCCESS);
			findListInfo();
		});
	});
}