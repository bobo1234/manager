var com = {};
//-----------------------------------------------------------------------------
com.SUCCESS = "操作成功";
com.FAIL = "操作失败";
com.DELETE = "确定要删除此条记录吗?";
com.CONFIRM = "确定执行此操作吗?";

//-----------------------------------------------------------------------------
com.json = function(str){
    try {
        return eval("(" + str + ");");
    } 
    catch (e) {
        return null;
    }
};
/**
 * 清除输入框值以及选框的选中状态
 */
com.clear = function(){
	var a=$("#form1 :input");    
	for(var i=0;i<a.length;i++){ 
		if(a[i].type=='radio'||a[i].type=='checkbox'){
			$("[name='"+a[i].name+"']").prop("checked", false);
		}else{
			a[i].value='';
		}
	}
}
/**
 * 设置只读模式
 */
com.readonly=function(){
	$('#form1 :input').attr("readonly","readonly");
	$('#form1 select').attr("disabled",true);
}
/**
 * 清除只读模式
 */
com.unreadonly=function(){
	$('#form1 :input').removeAttr("readonly");
	$('#form1 select').attr("disabled",false);
}

//隐藏某元素（id:需要显示的元素的id属性值）
com.show = function(id){
    com.id(id).style.display = 'block';
}

//显示某元素（id:需要隐藏的元素的id属性值）
com.hide = function(id){
    com.id(id).style.display = 'none';
}

//获取数据字典构造下拉框（id：select元素的id属性值；code：数据字典的代码）
com.getDataDiect = function(id, code){
  var json = getDataDictJsonObj(code);
  if (document.getElementById(id) == null) {
      alert("页面中缺少id为 \"" + id + "\" 的SELECT元素");
      return;
  }
  var option = document.createElement("option");
	option.innerHTML = "请选择";
	option.value = "";
	$("#"+id).append(option);
	for(var i = 0; i < data.length; i++){
		var option = document.createElement("option");
		option.innerHTML = data[i].typename;
		option.value = data[i].id;
		$("#"+id).append(option);
	}
};
/**
 * 请求获取数据字典的json数据
 * @return
 */
function getDataDictJsonObj(code){
	return com.json(ajax.post("getdict","typecode="+code));
}
com.RegExps = {};
com.RegExps.isNumber = /^[-\+]?\d+(\.\d+)?$/;
com.RegExps.isEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)/;
com.RegExps.isPhone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
com.RegExps.isMobile = /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/;
com.RegExps.isIdCard = /(^\d{15}$)|(^\d{17}[0-9Xx]$)/;
com.RegExps.isMoney = /^\d+(\.\d+)?$/;
com.RegExps.isZip = /^[1-9]\d{5}$/;
com.RegExps.isQQ = /^[1-9]\d{4,10}$/;
com.RegExps.isInt = /^[-\+]?\d+$/;
com.RegExps.isEnglish = /^[A-Za-z]+$/;
com.RegExps.isChinese = /^[\u0391-\uFFE5]+$/;
com.RegExps.isUrl = /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
com.RegExps.isDate = /^\d{4}-\d{1,2}-\d{1,2}$/;
com.RegExps.isTime = /^\d{4}-\d{1,2}-\d{1,2}\s\d{1,2}:\d{1,2}:\d{1,2}$/;
/**
 * 校验数据
 */
com.RegExps.verify=function(value,reg){
	return reg.test(value);
};


/**
 * 获取当前日期
 * @return
 */
function getDate(){
	var date=new Date();
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	year=year.toString();
	month=month.toString();
	return year+"-"+(month.length<2?'0'+month:month)+"-01";
};
/**
 * 根据class创建下拉框
 * @param id
 * @param data
 * @return
 */
function createSelByClass(id,data) {
	var option = document.createElement("option");
	option.innerHTML = "请选择";
	option.value = "";
	$("."+id).append(option);
	for(var i = 0; i < data.length; i++){
		var option = document.createElement("option");
		option.value = data[i].id+","+data[i].name;
		option.innerHTML = data[i].name;
		$("."+id).append(option);
	}
}; 
/**
 * 普通的创建下拉框
 * @param id
 * @param name 数据字段
 * @param data
 * @return
 */
function createSel(id,name,data) {
	if (document.getElementById(id) == null) {
		alert('页面元素不存在');
		return;
	}
	var option = document.createElement("option");
 	option.innerHTML = "请选择";
	option.value = "";
	$("#"+id).append(option);
	for(var i = 0; i < data.length; i++){
		var option = document.createElement("option");
		option.value = data[i].id+","+data[i].name;
		option.innerHTML = data[i].name;
		$("#"+id).append(option);
	}
}; 
/**
 * 部门的下拉框
 * @param id
 * @param data
 * @return
 */
function createDeptSel(id,data) {
	var option = document.createElement("option");
	option.innerHTML = "请选择";
	option.value = "";
	$("."+id).append(option);
	for(var i = 0; i < data.length; i++){
		var option = document.createElement("option");
		option.value = data[i].id+","+data[i].deptname;
		option.innerHTML = data[i].deptname;
		$("."+id).append(option);
	}
}; 
/**
 * 人员的下拉框
 * @param id
 * @param data
 * @return
 */
function createUserSel(id,data) {
	createSel(id,'realname',data)
}; 

/**
 * 通用的大弹窗表单循环赋值方法
 * @param json
 * @return
 */
function setValueForForm(json) {
	var a=$("#form1 :input");    
	for(var i=0;i<a.length;i++){ 
		var iname=a[i].name;
		if(a[i].type=='radio'||a[i].type=='checkbox'){
			 var boxes = document.getElementsByName(iname);
			 for(j=0;j<boxes.length;j++){
				 if(boxes[j].value == json[iname]){
		                boxes[j].checked = true;
		           }
			    }
//			if(a[i].value==json[iname]){//单选框
//		        a[i].checked = true;
//			}else{
//				 a[i].checked = false;
//			}
		}else{
			a[i].value=json[iname];
		}
	}
}

/**
 * 通用页面查询
 */
function commonSearch(requestUrl){
	$.ajax({
		url: requestUrl+"?type=1",
	    async: false,
	    data:$("#searchForm").serialize(),
	    success: function(data) {
	    	$("#dataList").html(data);
	    }
	});
}
/**
 * 通用的搜索表单循环赋值方法
 * @param params
 * @return
 */
function setSearchFormValue(params) {
	var a = $("#searchForm :input");
	var array1 = params.split("&");
	for (i = 1; i < array1.length; i++) {
		var array2 = array1[i].split("=");
		for ( var k = 0; k < a.length; k++) {
			var iname = a[k].name;
			if(iname==array2[0]){
				a[k].value = array2[1];
			}
		}
	}
}

$(document).keydown(function(e){//屏蔽回退键
    e = window.event || e;
    var code = e.keyCode || e.which;
    if (code == 8) {
        var src = e.srcElement || e.target;
        var tag = src.tagName;
        if (tag != "INPUT" && tag != "TEXTAREA") {
            e.returnValue = false; 
            return false;
        } else if ((tag == "INPUT" || tag == "TEXTAREA") && src.readOnly == true) {
            e.returnValue = false;
            return false; 
        }
    }
    if(code==13){
		 $("#sp_search").click();
	  }
});

/**
 * 获取部门
 * @return
 */
function getDept() {
	var json=com.json(ajax.get("getdept"));
	return json;
}
/**
 * 获取负责人
 * @return
 */
function getUsers() {
	var json=com.json(ajax.get("getallUser"));
	return json;
}
/**
 * 获取部件类型
 * @return
 */
function getRecord() {
	var json=com.json(ajax.get("getRecord"));
	return json;
}



