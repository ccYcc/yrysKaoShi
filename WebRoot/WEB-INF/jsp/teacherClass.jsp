<%@page import="com.ccc.test.utils.StringUtil"%>
<%@page import="com.ccc.test.utils.PropertiesUtil"%>
<%@page import="com.ccc.test.pojo.TeacherPaperInfo"%>
<%@page import="com.ccc.test.utils.ListUtil"%>
<%@page import="com.ccc.test.pojo.GroupInfo"%>
<%@page import="com.ccc.test.pojo.UserInfo"%>
<%@page import="com.ccc.test.utils.GlobalValues"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% 

	List<GroupInfo> myClazzs = (List<GroupInfo>)request.getAttribute("groups");
	List<TeacherPaperInfo> myPapers = (List<TeacherPaperInfo>)request.getAttribute("papers");
	List<UserInfo> myStudents = (List<UserInfo>)request.getAttribute("students");
	int gid = (Integer)request.getAttribute("groupId");
	GroupInfo curGroup = null;
	int curGroupIndex = -1;
	if ( ListUtil.isNotEmpty(myClazzs) ){
		int i = 0;
		for ( GroupInfo g : myClazzs ){
			if ( gid == g.getId() ){
				curGroup = g;
				curGroupIndex = i;
				break;
			}
			i++;
		}
	} else {
		curGroup = new GroupInfo();
		curGroup.setName("");
		curGroup.setDescription("");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	    <base href="<%=basePath%>"/>
		<title>我的班级</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
		<link rel="stylesheet" href="./js/themes/default/style.css" />
		<link rel="stylesheet" type="text/css" href="./css/globe.css"/>
		<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
		<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
		<link href="./css/teacher-class.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="./js/render.js"></script>
		<script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
		<script src="./js/jquery-ui.js"></script>

		<script type="text/javascript">
		    $(function() {
		    	renderUserHead(type_teacher);
		    	renderMainPage(type_teacher);
		    	$(".class_btns input").button();
		    	var newclazz = false;
		    	var pid = 0;
		    	var currentClazzId = "<%=curGroupIndex%>";
		    	function refresh(cid){
		    		location.href='jsp/toTeacherClass?id='+cid;
		    	}
		    	function addOrUpdateClass(){
		    		var name = $("#class_name_input").val();
		    		var desc = $("#class_desc_input").val();
		    		if ( !name ){
		    			alert("请输入班级名字");
		    			return;
		    		}
		    		var clazz = {"name":name,"description":desc};
		    		var url = '';
		    		if (!newclazz){
		    			url = 'teacher/json/updateGroup';
		    			clazz.id = <%=curGroup.getId()%>;
		    		} else {
		    			url = 'teacher/json/createGroup';
		    		}
		    		$.ajax({
  						method:"post",
  						dataType : "json",
  						data:{
  							'clazz':JSON.stringify(clazz),
  						},
  						url:url,
  						beforeSend:function(){
  							
  						},
  						success:function(data){
  							if ( data ){
  								refresh(data);
  							} else {
  								alert("操作失败，请重试");
  							}
  							hideClassDialog();
  						},
  						error:function(e){
  							console.log(e);
  							alert("操作失败，请重试");
  						}
  					});
		    	}
		    	function deletePaper(){
		    		var gid = <%=curGroup.getId()%>;
		    		if ( gid && pid ){
		    			$.ajax({
	  						method:"post",
	  						dataType : "json",
	  						data:{
	  							'gid':gid,
	  							'pid':pid
	  						},
	  						url:"teacher/json/deletePaper",
	  						success:function(data){
	  							if ( data ){
	  								refresh(data);
	  							} else {
	  								alert("操作失败，请重试");
	  							}
	  							hideClassDialog();
	  						},
	  						error:function(e){
	  							console.log(e);
	  							alert("操作失败，请重试");
	  						}
	  					});
		    		} else {
		    			alert("操作有误，删除对象为空");
		    		}
		    	}
		    	
		    	function deleteClass(){
		    		var clazz = {};
		    		clazz.id = <%=curGroup.getId()%>;
		    		$.ajax({
  						method:"post",
  						dataType : "json",
  						data:{
  							'clazz':JSON.stringify(clazz),
  						},
  						url:"teacher/json/deleteGroup",
  						success:function(data){
  							if ( data ){
  								alert("删除成功");
  								refresh(data);
  							} else {
  								alert("操作失败，请重试");
  							}
  							hideClassDialog();
  						},
  						error:function(e){
  							console.log(e);
  							alert("操作失败，请重试");
  						}
  					});
		    	}
		    	function findClazzByCid(cid){
		    		var finder = "li[id='clazz-"+cid+"']";
		    		return $(finder);
		    	}
		    	function onClickClazz(cid){
		    		
		    		if ( cid == currentClazzId )return;
		    		refresh(cid);
		    	}
		    	function showClassDialog(dialogid,title){
		    		$("#dialog_mask").show();
		    		$(dialogid).dialog("open");
		    		if ( title ){
		    			$(dialogid).attr({"title":title});
		    		}
		    	}
		    	function hideClassDialog(){
		    		$("#dialog_mask").hide();
		    		$("#dialog").dialog("close");
		    		$("#delete_class_dialog").dialog("close");
		    		$("#delete_paper_dialog").dialog("close");
		    		newclazz = false;
		    	}
		    	
		    	initDialog("#dialog",666,560,addOrUpdateClass,hideClassDialog);
		    	initDialog("#delete_class_dialog",666,360,deleteClass,hideClassDialog);
		    	initDialog("#delete_paper_dialog",666,360,deletePaper,hideClassDialog);
		    	$("li[id|='clazz']").each(function(i){
	  				$(this).on({"click":function(event){
	  					var len = "clazz-".length;
	  					var cid = this.id.substring(len);
	  					onClickClazz(cid);
	  				}});
	  			});
		    	$("#add_clazz_btn").on({"click":function(){
		    		newclazz = true;
		    		$("#class_name_input").val("");
		    		$("#class_desc_input").val("");
		    		showClassDialog("#dialog","新建班级");
		    	}});
		    	$("#update_class_btn").on({"click":function(){
		    		newclazz = false;
		    		$("#class_name_input").val("<%=curGroup.getName()%>");
		    		$("#class_desc_input").val("<%=curGroup.getDescription()%>");
		    		showClassDialog("#dialog","修改班级");
		    	}});
		    	$("#delete_class_btn").on({"click":function(){
		    		showClassDialog("#delete_class_dialog","删除班级");
		    	}});
		    	$("button[id|='delpaperid']").each(function(i){
	  				$(this).on({"click":function(event){
	  					var len = "delpaperid-".length;
	  					pid = this.id.substring(len);
	  					showClassDialog("#delete_paper_dialog","删除试卷");
	  				}});
	  			});
		    	renderBtn($("#add_clazz_btn"));
		    	hideClassDialog();
		    });
		</script>
	</head>
  
<body>
<!------ 头部 ------------>
	<div class="btm_border">
	<div class="h_bg">
	<div class="wrap">
	<div class="header">
		<div class="logo">
			<h1><a href="javascript:void(0)"><img class="logo_img" src="img/logo1.png" alt=""/></a></h1>
		</div>
		<div class="user-icon">
			<a href="javascript:void(0)" id="head_icon_link">
				<img class="head_user_img" id="photo" alt="" src="${sessionScope.session_user.headUrl}" width="48px" height="48px"/>
				${sessionScope.session_user.username}
			</a>
			<a href="user/loginOut.do">登出</a>
		</div>	
		<div class="clear"></div>
	</div>
	<div class='h_btm'>
		<div class='cssmenu'>
				<ul>
				    <li><a name="mainpage"><span>首页</span></a></li>
				    <li class="active"><a><span>我的班级</span></a></li>
				 </ul>
		</div>
	<div class="clear"></div>
	</div>
	</div>
	</div>
	</div>
	<div class="content">
		<div class="class_wrap">
			<div class="class_list">
				<div>
					<input id="add_clazz_btn" type="button" value="+创建班级"/>
				</div>
				<ul class="class_list_ul">
				<% 
					if ( ListUtil.isNotEmpty(myClazzs) ){
						for ( GroupInfo clazz : myClazzs ){
							String currentItemStyle = "";
							if (curGroup == clazz ){
								currentItemStyle = "class_list_current_item";
							}
							%>
							<li class="class_list_item <%=currentItemStyle%>" id="clazz-<%=clazz.getId()%>">
								<a class="class_list_item_link">
									<span class="class_name"><%=clazz.getName()%></span>
								</a>
							</li>
							<%
						}		
					} else {
						%>
							<p>您还没创建班级</p>
						<%
					}
				%>
				</ul>
			</div>
			<div class="classmates">
				<div class="class_desc_btns_warp">
					<span class="class_desc">班级描述/公告：
					<%=StringUtil.getDefaultStrIfNull(curGroup.getDescription(), "暂无班级描述.")%>
					</span>
					<%
						if ( curGroup.getId() > 0 ){
							%>
							<div class="class_btns">
							<input type="button" value="修改" id="update_class_btn"/>
							<input type="button" value="删除" id="delete_class_btn"/>
							</div>
							<%
						}
					%>

				</div>
				
				<div class="class_papers_wrap">
					<span class="">班级中的试卷：</span>
					<ul class="papers_list">
						<% 
							if ( ListUtil.isNotEmpty(myPapers) ){
								for ( TeacherPaperInfo paper : myPapers ){
									%>
									<li class="papers_item">
										<div>
											<a href="<%=paper.getPaperUrl()%>" target="_blank">
												<span class="info_name"><%=paper.getName()%></span>
											</a>
											<button id="delpaperid-<%=paper.getId()%>" title="将试卷从班级中删除">移除</button>
										</div>
									</li>
									<%
								}		
							} else {
								%>
								<p>暂无试卷</p>
								<%
							}
						%>
						
					</ul>
				</div>
				<span class="">班中的同学：</span>
				<ul class="classmates_list clear">
					<% 
						if ( ListUtil.isNotEmpty(myStudents) ){
							for ( UserInfo user : myStudents ){
								%>
								<li class="classmates_item" id="stu-<%=user.getId()%>">
									<div class="img_wrap">
										<a>
											<img src="<%=StringUtil.getDefaultStrIfNull(user.getHeadUrl(), "img/default_user_pic.jpg")%>" class="user_pic_img" />
										</a>
									</div>
									<div class="txt_wrap">
										<p class="info_name"><%=user.getUsername()%></p>
										<p class="info_desc"><%=StringUtil.getDefaultStrIfNull(user.getDescription(), "暂无个人描述")%></p>
										<div>
										<form action="exam/history" method="post">
											<input type="hidden" value="<%=user.getId() %>" name="uid"/>
											<input type="submit" value="查看"/>
										</form>
											
										</div>
									</div>
								</li>
								<%
							}		
						} else {
							%>
							<p>暂无学生</p>
							<%
						}
					%>	

				</ul>
			</div>
		</div>
	</div>
	<div id="dialog_mask" >
		<div id="dialog" title="创建班级">
			<div id="class_input_div">
				<p>
					<label for="class_name_input" class="input_label">班级名字：</label>
					<input name="className" type="text" id="class_name_input" value="" />
				</p>
				<br />
				<p>
					<label for="class_desc_input" class="input_label">班级描述：</label>
					<textarea  name="classDesc" id="class_desc_input" rows="4" cols="16"  ></textarea>
				</p>
			</div>
		</div>
		<div id="delete_class_dialog" title="删除班级">
			<div id="class_delete_tip">
				<p>
					你确定要删除这个班么？班中的学生和试卷将会一同删除！
				</p>
			</div>
		</div>
		<div  id="delete_paper_dialog" title="删除试卷">
			<div id="class_delete_tip">
				<p>
					你确定要删除这个试卷么？
				</p>
			</div>
		</div>
	</div>
	<div class="clear"/>
	<!--底部区域-->
	<div class="main_bg">
		<!--footer-->
		<div class="ftr-bg">
			<div class="wrap">
				<div class="footer">
					<div class="copy">
						<p class="w3-link"><%=PropertiesUtil.getString("CopyrightStr") %></p>
					</div>
				<div class="clear"></div>	
			</div>
			</div>
		</div>
	</div>
</body>
</html>
