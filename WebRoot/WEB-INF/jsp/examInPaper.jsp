<%@page import="com.ccc.test.utils.ListUtil"%>
<%@page import="com.ccc.test.pojo.QuestionInfo"%>
<%@page import="java.io.Serializable"%>
<%@page import="com.ccc.test.pojo.UserInfo"%>
<%@page import="com.ccc.test.utils.GlobalValues"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserInfo user = (UserInfo)session.getAttribute(GlobalValues.SESSION_USER);
List<QuestionInfo> questions = (List<QuestionInfo>)request.getAttribute("questions");;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>"/>
    
    <title>测试中</title>
    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="./css/globe.css"/>
	<link rel="stylesheet" type="text/css" href="./css/exam-inpaper.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
  	<script src="./js/jquery-1.11.3.min.js"></script>
  	<script src="./js/jquery-ui.min.js"></script>
  	<script src="./js/timer.jquery.js"></script>
  	<script type="text/javascript" src="./js/render.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			renderUserHead(type_student);
  			var examType = "<%=GlobalValues.EXAM_TYPE_IN_PAPER%>";
  			var ansLogs = [];//保存回答记录的数组
  			$("#examType").val(examType);
  			//$("#examType").val(paperName);
  			//显示对话框
  			function showDialog(title,content){
				$("#dialog_content").text(content);
				$("#dialog").attr("title",title);
				$("#dialog_mask").addClass("dialog_mask");
				$("#dialog").dialog({
					close:function(){
						$("#dialog_mask").removeClass("dialog_mask");
					}
				});
  			}
  			function fillAnsLogs(){
  				$("input:radio[name|='answer']:checked").each(function(i){
  					var nameid = $(this).attr("name");
  					var tmp = "answer-".length;
  					var qid = nameid.substring(tmp);
  					var value = $(this).val();
  					var isright = 1;//错误
  					if ( "答对" == value ){
  						isright = 0;//正确	
  					}
  					log = {};
  					log.uid = "<%=user.getId()%>";
  					log.qid = qid;
  					log.ansResult = isright;
  					ansLogs.push(log);
  				});
  				$("#answerLogs").val(JSON.stringify(ansLogs));
  			}
  			//提交结束考试表单注册事件 
  			$("#endExamForm").submit(function(event){
  				fillAnsLogs();
  				if ( ansLogs.length ){
  					return;
  				}
  				alert("你还没做题。");
  				event.preventDefault();
  			});
  			//初始化渲染视图
  			$( ".quest_item" ).buttonset();
  			$( "#endExam" ).button();
  		});
  	</script>
  </head>
  
  <body>
  	<div class="btm_border">
		<div class="h_bg">
		<div class="wrap">
		<div class="header">
			<div class="logo">
				<h1><a href="javascript:void(0)"><img class="logo_img" src="img/logo1.png" alt=""/></a></h1>
			</div>
			<div class="user-icon">
				<a href="javascript:void(0)" id="head_icon_link">
					<img class="head_user_img" id="photo" alt="" src="${sessionScope.session_user.headUrl}" />
					${sessionScope.session_user.username}
				</a>
				<a href="user/loginOut.do">登出</a>
			</div>	
			<div class="clear"></div>
		</div>
		</div>
		</div>
	</div>
		<div class="content">
			<form action="exam/endExam" id="endExamForm" method="post" class="endExamForm">
				<ul class="quest_list">
				<%
					if ( ListUtil.isNotEmpty(questions) ){
						%>
						<p>试卷中共有<%=questions.size()%>条题目</p>
						<%
						int i = 0;
						for ( QuestionInfo quest : questions ){
							i++;
							%>
							<li class="quest_item">
								<span class="quest_num_span">第<%=i%>题：</span>
								<span id="fast_answer">
				  					<input type="radio" value="做对" name="answer-<%=i%>" id="r5-<%=i%>" checked="checked"/>
					  				<label for="r5-<%=i%>">做对</label>
					  				<input type="radio" value="做错" name="answer-<%=i%>" id="r6-<%=i%>"/>
					  				<label for="r6-<%=i%>">做错</label>
				  				</span>
							</li>
							<%
						}
					}
				%>
				</ul>
					<input type="hidden" id="examType" value="${examType}" name="examType"/>
					<input type="hidden" id="answerLogs" name="answerLogs"/>
					<input type="hidden" id="paperName" name="paperName"/>
						<input type="submit" value="提交并查看评估报告" id="endExam" class="endExam"/>
				</form>
		</div>
  	<div class="footer" id="footer">
  		<div>© 2015 朝阳创新工作室版权所有</div>
  	</div>
  </body>
</html>
