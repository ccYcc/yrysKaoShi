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
%>
<% 
	Serializable questions = (Serializable)request.getAttribute("questions");
	QuestionInfo curQuest = null;
	if ( questions instanceof List ){
		List<QuestionInfo> qustList = (List<QuestionInfo>)questions;
		if (ListUtil.isNotEmpty(qustList)){
			curQuest = qustList.remove(0);
		}
	} else if ( questions instanceof QuestionInfo ){
		curQuest = (QuestionInfo)questions;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>测试中</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="./css/globe.css">
	<link rel="stylesheet" type="text/css" href="./css/start-test.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css">
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css">
  	<script src="./js/jquery-1.11.3.min.js"></script>
  	<script src="./js/jquery-ui.min.js"></script>
  	<script src="./js/timer.jquery.js"></script>
  	
  	<script type="text/javascript">
  		$(function(){
  			var exerciseType = "<%=GlobalValues.EXAM_TYPE_EXERCISE%>";
  			var recommendType = "<%=GlobalValues.EXAM_TYPE_RECOMMEND%>";
  			var selectAns='',ans='';
  			var ansLogs = [];
  			var questionArray = [];
  			var curQuestion = {};
  			function checkAnswer(){
  				selectAns = $("input:radio[name='answer']:checked").val();
  				ans = "<%=curQuest.getAnswer()%>";
  				
  				if ( selectAns == '会' ){
  					return true;	
  				} else if ( selectAns == '不会' ){
  					return false;
  				} else {
  					if ( ans == selectAns ){
  						return true;
  					} else {
  						return false;
  					}
  				}
  			}
  			$( ".answer" ).buttonset();
  			$( "#sbmAnswer" ).button();
  			$( "#endExam" ).button();
  			$("#used_time").timer({
  				format:'%h时%m分%s秒'
  			});
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
  			function nextQuestion(){
  				var curType = $("#examType").val();
  				if ( curType == exerciseType ){
  					curQuestion = questionArray.pop();
  				} else if (curType == recommendType){
  					$.ajax({
  						method:"post",
  						url:"test/json/nextQuestion?answerLogs="+JSON.stringify(ansLogs),
  						beforeSend:function(){
  							
  						},
  						success:function(data){
 							curQuestion = data;
 							showCurQuestion();
  						}
  					});
  				}
  				
  			}
  			function showCurQuestion(){
  				if ( curQuestion == null ){
  					alert("没有题目了，请点击查看报告");
  				} else {
  					alert(curQuestion);
  				}
  			}
  			function showAnsLogs(){
  				$(".answer_logs").text(JSON.stringify(ansLogs));
  			}
  			$("#sbmAnswer").on('click',function(){
  				id = "<%=curQuest.getId()%>";
  				log = {};
  				log.qid = id;
  				log.usedTime = $("#used_time").data("seconds");
  				if ( checkAnswer() ){
  					log.ansResult = 0;
  				} else {
<%--					showDialog("回答错误", "正确答案是："+ans+" 你的答案是："+selectAns);--%>
					log.ansResult = 1;
  				}
  				ansLogs.push(log);
  				showAnsLogs();
  				nextQuestion();
  				$("#used_time").timer("reset");
  			});
  			
  		});
  	</script>
  </head>
  
  <body>
  	<div id="header">
  		<div class="header_box">
  		  	<div class="logo">
  				<img alt="logo" src="img/logo.png"/>
	  		</div>
	  		<div class="nav_bar">
	  			<div class="nav_bar_box">
	  				<a href="jsp/main.do" target="_blank">设置</a>
	  				<a href="jsp/main.do" target="_blank">
	  					<img class="user_logo" src="img/logo.png"/>
	  					<span class="user_name">${sessionScope.session_user.username}</span>
	  				</a>
	  				
	  			</div>
	  		</div>
  		</div>
  	</div>
  	<div id="test_center">
  		<div class="content">
  			<form action="" method="post" id="answerForm">
  				<div class="quest_proper">
  					题目难度：<span id="level"><%=curQuest.getLevel()%></span>
  					,使用时间：<span id="used_time"></span>
  				</div>
  			  	<img src="img/1.jpg" id="question_img"/>
	  			<div class="answer">
	  			<span>选择您的答案：</span>
	  				<input type="radio" value="A" name="answer" id="r1"/>
	  				<label for="r1">A</label>
	  				<input type="radio" value="B" name="answer" id="r2"/>
	  				<label for="r2">B</label>
	  				<input type="radio" value="C" name="answer" id="r3"/>
	  				<label for="r3">C</label>
	  				<input type="radio" value="D" name="answer" id="r4"/>
	  				<label for="r4">D</label>
	  				<input type="radio" value="会" name="answer" id="r5"/>
	  				<label for="r5">会</label>
	  				<input type="radio" value="不会" name="answer" id="r6" checked="checked"/>
	  				<label for="r6">不会</label>
	  				<span id="sbmAnswer">提交</span>
	  			</div>
  			</form>
			<form action="" id="endExamForm" method="post">
 				<input type="hidden" id="examType" value="${examType}"/>
 				<input type="hidden" id="usedTime"/>
 				<input type="hidden" id="answer_log"/>
  				<input type="submit" value="结束测试并查看评估报告" id="endExam"/>
 			</form>
  		</div>
  	</div>
  	<div class="footer" id="footer">
  		<div>© 2015 朝阳创新工作室版权所有</div>
  	</div>
 	<div id="dialog_mask" >
		<div id="dialog" class="dialog" title="提示！">
			<p id="dialog_content">${result}</p>
		</div>
	</div>
  </body>
</html>
