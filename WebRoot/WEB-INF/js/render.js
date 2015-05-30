var type_teacher = '老师';
var type_student= '学生';
var type_amdin = '管理员';

function renderTabs(uType,activeText,ulParent){
	var map = {
				'管理员':[
				       {'href':"jsp/toAdminMain",'text':"首页"},
				       {'href':"jsp/editUser",'text':"个人中心"}],
				'学生':[
				      {'href':"jsp/toStudentMain",'text':"首页"},
				      {'href':"",'text':"我的关注"},
				      {'href':"jsp/editUser",'text':"个人中心"},
				      {'href':"validations/getValidations",'text':"消息中心"}],
				'老师':[{'href':"jsp/toTeacherMain",'text':"首页"},
				      {'href':"jsp/toTeacherClass",'text':"我的班级"},
				      {'href':"jsp/editUser",'text':"个人中心"},
				      {'href':"validations/getValidations",'text':"消息中心"}],
			};
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