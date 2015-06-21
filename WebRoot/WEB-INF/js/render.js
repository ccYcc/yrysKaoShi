var type_teacher = '老师';
var type_student= '学生';
var type_amdin = '管理员';
var map = {
		'管理员':[
		       {'href':"jsp/toAdminMain",'text':"首页"},
		       {'href':"jsp/editUser",'text':"个人中心"},
		       {'href':"jsp/statistic",'text':"统计信息"}],
		'学生':[
		      {'href':"jsp/toStudentMain",'text':"首页"},
		      {'href':"jsp/toStudentClass",'text':"我的班级"},
		      {'href':"jsp/editUser",'text':"个人中心"},
		      {'href':"validations/getValidations",'text':"消息中心"}],
		'老师':[{'href':"jsp/toTeacherMain",'text':"首页"},
		      {'href':"jsp/editUser",'text':"个人中心"},
		      {'href':"validations/getValidations",'text':"消息中心"}],
	};
function renderUserHead(uType){
	try {
		var usermap = map[uType];
		var len = usermap.length;
		if ( usermap && len){
			for ( var i = 0 ; i < len ; i++ ){
				var child = usermap[i];
				var href = child['href'];
				var text = child['text'];
				if ( href == '' ){
					href = "javscript:void(0)";
				}
				if ( "个人中心" === text ){
					$("#head_icon_link").attr({"href":href});
				}
			}
		}
	} catch (e) {
		console.log(e);
	}
}
function renderMainPage(uType){
	try {
		var usermap = map[uType];
		var len = usermap.length;
		if ( usermap && len){
			for ( var i = 0 ; i < len ; i++ ){
				var child = usermap[i];
				var href = child['href'];
				var text = child['text'];
				if ( href == '' ){
					href = "javscript:void(0)";
				}
				if ( "首页" === text ){
					$("a[name=mainpage]").attr({"href":href});
				}
			}
		}
	} catch (e) {
		console.log(e);
	}
}
function renderTabs(uType,activeText,ulParent){
	
	try {
		var usermap = map[uType];
		var len = usermap.length;
		if ( usermap && len){
			ulParent.empty();
			for ( var i = 0 ; i < len ; i++ ){
				var child = usermap[i];
				var clazz = '';
				var href = '';
				var text = child['text'];
				if ( activeText == text ){
					clazz = " class='active'";
				}else {
					href = child['href'];
				}
				if ( href == '' ){
					href = "javscript:void(0)";
				}
				if ( "首页" === text ){
					$("#head_icon_link").attr({"href":href});
				}
				var childnode = child = "<li "+clazz+"><a href='"+href+"'><span>"+text+"</span></a></li>&nbsp;";
				ulParent.append(childnode);
			}
			
		}
	} catch (e) {
		console.log(e);
	}
	
}


function showResultIfNeed(result) {
	if ( result && (result != null || result != 'null') ){
		alert(result);
	}
}

function renderBtn(btn) {
	btn.addClass("action_btn");
}

function initDialog(id,minWidth,maxHeigth,okfun,cancel){
	$(id).dialog({
		minWidth :minWidth,
		maxHeigth:maxHeigth,
		buttons: {
			"确定": okfun,
	        "取消": cancel
	     },
		close:cancel
	});
}
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}