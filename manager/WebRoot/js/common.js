var secure = null; // 权限参数
var page = 1; // 默认页面
var dialog = null;
var moduleCode = "";
var curRoleId;
var webSocket;//通信


var type = {};
type.FIND = 1;
type.DELETE = 2;
type.MODIFY = 3;
type.ADD = 4;


$(function() {
	
	var url = window.location.pathname;
	var purl = window.location.href;
	if(url.indexOf('html')!=-1&&purl.indexOf('?')==-1){
		var page = url.substring(url.lastIndexOf('/')+1, url.lastIndexOf('html')+4); //页面的地址,不带后面参数
		if(page!='index.html'&&page!='login.html'&&page!=''){
			//根据页面地址获取菜单的code
			var data=ajax.json.get("mgr/findByPage?page="+page);
			if (!$.isSuccess(data))//未登录
				return;
			moduleCode=data.body;
		}
	}
	if(moduleCode>=0){//页面菜单权限判断
		findMenu(moduleCode, initFun);
		$('td.table-val').css('padding', '5px');
//		connect();//连接websocket
	}
	
//		$('input.date-before').on('click', function() {
//			WdatePicker({
//				maxDate : '%y-%M-{%d}'
//			});
//		});
//		$('input.date-after').on('click', function() {
//			WdatePicker({
//				minDate : '%y-%M-{%d}'
//			});
//		});
//		$('input.date').on('click', function() {
//			WdatePicker();
//		});
});
/**
 * 原生ajax方法封装
 */
var ajax = {};
ajax.createPoster = function() {   
  return window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();  
};
ajax.get = function(url){
  var iPoster=this.createPoster();
  var strResult=" ";
  iPoster.open("GET", url, false);
  try{
    iPoster.send("");
    if((iPoster.readyState==4||iPoster.readyState=="complete") && (iPoster.status==200)){
      strResult = iPoster.responseText;
    }
    else{
      strResult = "Error: Can not connect to the server. ";
    }
  }
  catch(err){
    return "";
  }
  iPoster = null;
  return strResult;
};

ajax.post = function(url, content){
  var iPoster=this.createPoster();
  var strResult=" ";
  iPoster.open("POST", url, false); 
  iPoster.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
  //iPoster.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');   
  try{
    iPoster.send(content);
    if((iPoster.readyState==4) && (iPoster.status==200)){
      strResult = iPoster.responseText;
    }
    else{
      strResult = "Error: Can not connect to the server. ";
    }
  }
  catch(err){
    return "";
  }

  iPoster = null; 
  return strResult;  
};

ajax.json={};
ajax.json.get=function(url){
  try{
    return eval("(" + ajax.get(url) + ");");
  }
  catch(e){
    return null;
  }
};

ajax.json.post=function(url,content){
	  try{
	    return eval("(" + ajax.post(url,content) + ");");
	  }
	  catch(e){
	    return null;
	  }
};


//封装的一些方法
var com = {};

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
};
//隐藏某元素（id:需要显示的元素的id属性值）
com.show = function(id){
    com.id(id).style.display = 'block';
};

//显示某元素（id:需要隐藏的元素的id属性值）
com.hide = function(id){
    com.id(id).style.display = 'none';
};
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
 * 普通的创建下拉框
 * @param curDepartient 默认值
 * @param pkid 数据集主键id
 * @param pname 数据集名称
 * @param eml 页面元素
 * @param method 请求方法
 * @param tip 友好提示
 */
function createSel(curDepartient,pkid,pname, eml,method,tip) {
	eml.empty().append("<option value=''>"+tip+"</option>");
	$.getJSON(method, function(data) {
		if(!$.isSuccess(data)) return;
		$.each(data.body, function(i,v){
			$("<option "+analyzeSelect(v.pkid,curDepartient) +" value="+v.pkid+"></option>")
			.append(v.pname)
			.appendTo(eml);
		});
	}); 
}; 

function analyzeSelect(id, curDepartient){
	return curDepartient > 0 && id == curDepartient ? " selected=true " : "" ;
}

/**
 * 通用的大弹窗表单循环赋值方法
 * @param json
 * @return
 */
function setValueForForm(json) {
	var a=$(".details-box table td");    
	for(var i=0;i<a.length;i++){ 
//		var iname=a[i].name;
		var iname=a[i].className;
		for(var key in json)
		{
		    if (iname==key) {
		    	a[i].innerText=json[key];
			}
		}
		 
//		if(a[i].type=='radio'||a[i].type=='checkbox'){
//			 var boxes = document.getElementsByName(iname);
//			 for(var j=0;j<boxes.length;j++){
//				 if(boxes[j].value == json[iname]){
//		                boxes[j].checked = true;
//		           }
//			    }
//			if(a[i].value==json[iname]){//单选框
//		        a[i].checked = true;
//			}else{
//				 a[i].checked = false;
//			}
//		}else{
//			a[i].value=json[iname];
//		}
	}
};

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
};




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
    if(code==13){//回车键触发搜索功能
		 $(".input-group-btn button").click();
	  }
});

/**
 * 获取权限信息, 传入模块编号及回调函数
 */
function findModuleParameter(moduleCode, initFun) {
	if (!moduleCode)
		return;
	$.getJSON('mgr/findModuleParameter', {
		moduleCode : moduleCode
	}, function(data) {
		if (!$.isSuccess(data))
			return;
		$('a.acctInfo').append(data.body.acctount);
		secure = data.body;
		if (!secure.find) {
			$('div.main').remove(); // 删除页面主要元素
			BootstrapDialog.msg("非法操作, 你没有当前页面的权限!",
					BootstrapDialog.TYPE_DANGER);
			return;
		}
		$('div.main').removeClass('hide');
		if ("0" != moduleCode) {
			var obj = $('ol.breadcrumb').empty();
			obj.append($("<li></li>").append(data.body.superModuleName));
			obj.append($("<li ckass='active'></li>").append(
					data.body.moduleName));
			$('.navbar-nav').find('.dropdown[name=' + data.body.code + ']')
					.addClass('active');
			$('title').text(
					data.body.moduleName + ' - ' + data.body.superModuleName);
		}
		
		initFun();
	});
}
/*
 * 退出登录
 */
function exit() {
	BootstrapDialog.confirm("请确认是是否需要注销登录!", function(result) {
		if (!result)
			return;
		BootstrapDialog.show({
			title : "加载中",
			closable : false,
			message : "正在加载, 请稍等..."
		});
		$.getJSON('mgr/exit', function(data) {
			if (!$.isSuccess(data))
				return;
//			webSocket.close();		
			window.location.href = "./login.html";
		});
	});
}
/*
 * 根据当前帐号的权限获取导航菜单
 */
function findMenu(moduleCode, initFun) {
	$
			.getJSON(
					'mgr/findMenu',
					function(data) {
						if (!$.isSuccess(data))
							return;
						var nav = $('ul#nav-box-ul').empty();
						$
								.each(
										data.body,
										function(i, v) {
											if (!v.moduleLevel) {
												$(
														'<li class=\'dropdown\' name=\''
																+ v.moduleCode
																+ '\'></li>')
														.append(
																$(
																		"<a href='dropdown-toggle' data-toggle='dropdown' href='javascript:void(0)'></a>")
																		.append(
																				v.moduleName
																						+ "<span class='caret'></span>"))
														.append(
																analyzeMenu(
																		v.moduleCode,
																		data.body))
														.appendTo(nav);
											}
										});
						findModuleParameter(moduleCode, initFun);
					});
}
/*
 * 获取面包绡
 */
function findBreadcrumb() {
	$.post('mgr/findBreadcrumb', {
		moduleCode : moduleCode
	}, function(data) {
		var obj = $('ol.breadcrumb').empty();
		obj.append($("<li></li>").append(data.body.superName));
		obj.append($("<li ckass='active'></li>").append(data.body.name));
		$('title').text(data.body.name + ' - ' + data.body.superName);
		$('.navbar-nav').find('.dropdown[name=' + data.body.code + ']')
				.addClass('active');
	}, 'json');
}
/*
 * 最佳面包绡
 */
function addBreadcrumb(msg) {
	$('ol.breadcrumb').find('.active').removeClass('active');
	$('ol.breadcrumb').append($("<li class='active'></li>").append(msg));
}
/*
 * 解析导航菜单
 */
function analyzeMenu(code, data) {
	var ul = '';
	ul += "<ul class='dropdown-menu'>";
	$.each(data, function(i, v) {
		if (v.moduleSuperCode == code)
			ul += "<li><a href='" + v.modulePage + "'>" + v.moduleName
					+ "</a></li>";
	});
	ul += "</ul>";
	return ul;
}
BootstrapDialog.confirm = function(message, callback) {
	new BootstrapDialog({
		title : '提示信息',
		message : message,
		closable : false,
		data : {
			'callback' : callback
		},
		buttons : [
				{
					label : '取消',
					action : function(dialog) {
						// 容易理解的写法 if(typeof dialog.getData('callback') ===
						// 'function') dialog.getData('callback')(false); // or
						// callback(false);
						typeof dialog.getData('callback') === 'function'
								&& dialog.getData('callback')(false);
						dialog.close();
					}
				},
				{
					label : '确定',
					cssClass : 'btn-primary',
					action : function(dialog) {
						typeof dialog.getData('callback') === 'function'
								&& dialog.getData('callback')(true);
						dialog.close();
					}
				} ]
	}).open();
};
BootstrapDialog.alert = function(message, type) {
	new BootstrapDialog({
		title : '提示信息',
		message : message,
		type : type,
		closeabled : true,
		buttons : [ {
			label : '关闭',
			action : function(dialog) {
				dialog.close();
			}
		} ]
	}).open();
};
var dg = null;
BootstrapDialog.msg = function(message, type) {
	dg = new BootstrapDialog({
		title : '提示信息',
		message : message,
		type : type,
		closeabled : false,
		backdrop : 'static'
	}).open();
	setTimeout("hidedg()", 1500);//隐藏弹窗信息
};
function hidedg() {
	dg.close();
}
BootstrapDialog.isSubmitted = function() {
	return BootstrapDialog.show({
		title : "正在提交",
		closable : false,
		message : "请稍等, 正在提交请求!"
	});
};
BootstrapDialog.loading = function() {
	return BootstrapDialog.show({
		title : "加载中",
		closable : false,
		message : "正在加载, 请稍等..."
	});
};
BootstrapDialog.hideModel = function(eml) {
	eml.modal('hide');
};
BootstrapDialog.showModel = function(eml) {
	eml.modal({
		backdrop : 'static',
		keyboard : false
	}).modal('show');
};
(function($) {
	// 获取传递的参数
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	};
	// 获取项目根路径
	$.getRootPath = function() {
		var curWwwPath = window.document.location.href; // 获取当前网址，如：
		// http://localhost:8083/uimcardprj/share/meun.jsp
		var pathName = window.document.location.pathname; // 获取主机地址之后的目录，如：
		// uimcardprj/share/meun.jsp
		var pos = curWwwPath.indexOf(pathName);
		var localhostPaht = curWwwPath.substring(0, pos); // 获取主机地址，如：
		// http://localhost:8083
		var projectName = pathName.substring(0,
				pathName.substr(1).indexOf('/') + 1); // 获取带"/"的项目名，如：/uimcardprj
		return (localhostPaht + projectName);
	};
	// 删除空格
	$.removeTrim = function(str) {
		return str.replace(/^\s+|\s+$/g, "");
	};
	// 分页插件
	$.analysisPage = function(data) {
		$("#pagination ul").empty();
		if (data.totalPage < 2) {
			$('nav#pagination').hide();
			return;
		}
		$("#pagination").pagy({
			totalPages : data.totalPage,
			currentPage : data.nowPage,
			innerWindow : 3,
			page : function(clickPage) {
				page = clickPage;
				if (page == data.nowPage)
					return false;
				dialog = BootstrapDialog.loading();
				findListInfo();
				return true;
			}
		});
	};
	$.isSubmit = true; // 是否可提交
	$.verifyForm = function(eml, isEmpty) {
		eml.removeClass('empty');
		if (!isEmpty)
			return eml.val();
		var val = eml.val();
		if (val < 1 || val.length < 1) {
			$.isSubmit = false;
			eml.addClass('empty');
		}
		return val;
	};
	/**
	 * 复选框的选中状态
	 */
	$.findChecked = function(val) {
		return val ? " checked=true " : "";
	};
	/**
	 * 下拉框的选中
	 */
	$.findOpeion = function(id, current) {
		return id == current ? " selected=true " : "";
	};
	// 点击搜索按钮, 弹出正在加载窗口, 调用findListInfo(); 函数获取列表数据!
	$.search = function() {
		dialog = BootstrapDialog.loading();
		page=1;//搜索按钮,页数重置为第一页
		findListInfo();
	};
	// 判断返回数据的JSON头是成功还是失败
	$.isSuccess = function(data) {
		if (data.head)
			return data.head;
		if (!data.body)
			return;
		if (data.body == 'PERMISSION_DENIED' || data.body == 'UNLOGIN') {// 没有权限或者没有登录
			// if((data.body == 'PERMISSION_DENIED' || data.body == 'UNLOGIN')
			// &&
			// dialog != null){//没有权限或者没有登录
			// dialog.close();
			if (data.body == 'UNLOGIN') {
				dg=BootstrapDialog.show({
					title : "错误",
					type : BootstrapDialog.TYPE_DANGER,
					message : '您还没有登录',
					onhide : function(dialog) {
						window.location.href = "./login.html";
					}
				});
				setTimeout("hidedg()", 1500);//隐藏弹窗信息
				return;
			}
		}
		BootstrapDialog.show({
			title : "错误",
			type : BootstrapDialog.TYPE_DANGER,
			message : data.body
		});
		return data.head;
	};
})(jQuery);



/**
 * websocket连接
 */
function connect() {
	var username=ajax.json.get("mgr/getSessionUser").body;
	if(username==null||username==""){
		console.log("未登录--");
		return;
	}
	var flag=ajax.json.get("mgr/getSessionFlag");
	if (flag=="1"){
		console.log("已经启动");
		return webSocket;
	}
	var url="";
	if (window.location.protocol == 'http:') {
		url = 'ws://';
	} else {
		url = 'wss://';
	}
	var strFullPath = window.document.location.href;
	strFullPath= strFullPath.substring(7,strFullPath.lastIndexOf('/'));
	if (window.WebSocket) {//浏览器支持的话
		webSocket = new WebSocket(
				url+strFullPath+"/socketservice/" + username);
		
		webSocket.onopen = function(event) {
			ajax.json.post("mgr/setSessionFlag", username);
			console.log("连接建立成功！");
			webSocket.send("[join]||||"+username);
		};
		//接收消息
		webSocket.onmessage = function(event) {
			receiveMess(event.data);
		};
		webSocket.onerror = function(event) {
			alert(event.data);
		};

//		webSocket.onclose = function()
//		{ 
//			console.log("连接已关闭..."); 
//		};
	}
	
};

//处理接收的消息(json格式)
function receiveMess(data) {
	var message = JSON.parse(data);
	if (message.type == 'message') {//消息
		
	}else if(message.type == 'goOut'){
		alert("此用户在其它终端已经早于您登录,您暂时无法登录");
		return;
//		goOut();
	}else if(message.type == 'goOut'){
//		$("body").html("");
//		goOut("您被系统管理员强制下线");
	}else  if (message.type == 'user_list') {//在线用户
		
	}else if (message.type == 'user_join') {//用户上线
//		userlist = message.list;
	} else if (message.type == 'user_leave') {//用户下线
		
	}
	
};

//用户列表
var userlist = "";
function getUserlist(){
	websocket.send('[getUserlist]||||A');
	return userlist;
};

//强制下线
function goOut(){
	var strFullPath = window.document.location.href;
	strFullPath= strFullPath.substring(0,strFullPath.lastIndexOf('/'));
	location.href=strFullPath+"/mgr/exit";
	location.href='login.html';
}

/**
 * 给所有人发送消息
 */
function sendAllMessage() {
	if(webSocket == null) {
		alert("请先连接后再发送消息");
		return;
	}
	var msg = document.getElementById("textarea").value;
	if(msg == "") {
		alert("消息内容不能为空");
		return;
	}
	msg="[ALL]||||"+msg;
	document.getElementById("textarea").value = "";
	webSocket.send(msg);
};

/**
 * 用户退出
 */
function logOut() {
	var msg="[logout]||||A";
	webSocket.send(msg);
};
