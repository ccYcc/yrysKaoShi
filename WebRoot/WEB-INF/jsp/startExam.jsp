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
	<link rel="stylesheet" type="text/css" href="./css/start-test.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
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
  			var questArrIsFetched = false;
  			var curQuestion = {};
  			var answerType = "${answerType}";
  			
  			function checkAnswer(){
  				ans = curQuestion.answer;
  				if ( answerType == "fast" ){
  					selectAns = $("input:radio[name='answer']:checked").val();
  					if ( selectAns == '会' ){
  	  					return true;	
  	  				} else if ( selectAns == '不会' ){
  	  					return false;
  	  				}
  				} else {
  					selectAns = [];
  					$("input:checkbox[name='answer']:checked").each(function(i){
  						selectAns.push($(this).val());
  					});
  					var ansArr = ans.split(",");
 					return ansArr.sort().toString() == selectAns.sort().toString();
  				}
  			}
  			
  			if ( answerType == "fast" ){
  				$("#fast_answer").show();
  				$("#normal_answer").hide();
  			} else {
  				$("#fast_answer").hide();
  				$("#normal_answer").show();
  			}
  			$( ".answer" ).buttonset();
  			$("#nextQuest").button();
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
  					if ( !questArrIsFetched ){
  						var selectedKids = "${selectedIds}";
  						var level = "${level}";
  			  			$.ajax({
  							method:"post",
  							dataType : "json",
  							data:{
  								'selectedIds':selectedKids,
  								'level':level
  							},
  							url:"test/json/fetchExerciseQuestions",
  							beforeSend:function(){
  								
  							},
  							success:function(data){
  								questionArray = data;
  								curQuestion = questionArray.pop();
  								showCurQuestion();
  								questArrIsFetched = true;
  								for (var i = 0 ; i < data.length ; i++ ){
  									console.log(data[i]);
  								}
  							},
  							error:function(){
  								alert("获取题目失败");
  							}
  							
  						});
  					} else {
  						curQuestion = questionArray.pop();
  						showCurQuestion();
  					}
  				} else if (curType == recommendType){
  					$.ajax({
  						method:"post",
  						dataType : "json",
  						data:{
  							'answerLogs':JSON.stringify(ansLogs)
  						},
  						url:"test/json/nextQuestion",
  						beforeSend:function(){
  							
  						},
  						success:function(data){
 							curQuestion = data;
 							showCurQuestion();
  						},
  						error:function(e){
  							console.log(e);
  							curQuestion = null;
  							showCurQuestion();
  						}
  					});
  				}
  				
  			}
  			function showCurQuestion(){
  				console.log('showCurQuestion'+curQuestion);
  				if ( !curQuestion ){
  					alert("没有题目了，请点击查看报告");
  					$("#answerForm").css({"display":"none"});
  				} else {
  					$(".correct_ans_text").text("正确答案是:"+curQuestion.answer);
  					$("#used_time").timer("reset");
  					$("#level_text").text(curQuestion['level']);
  					$("#quest_id_text").text(curQuestion['id']);
  					var pn = Math.round((Math.random()+1));
  					$("#question_img").attr({"src":"img/"+pn+".jpg"});
  					var optionArr = curQuestion.options.split(',');
  					var optionlen = optionArr.length;
  					var answernode = $("#normal_answer");
  					for ( var i = 0 ; i < optionlen; i++ ){
  						var s = "<input type='checkbox' value='"+optionArr[i]+"' name='answer' id='r"+i+"'/><label for='r"+i+"'>"+optionArr[i]+"</label>";
  						answernode.append(s);
  					}
  					
  				}
  			}
  			function toggleWhenWrong(){
  				if ($(".error_answer").hasClass("hide")){
  					$(".answer").addClass("hide");
  					$(".error_answer").addClass("show");
  					$(".answer").removeClass("show");
  					$(".error_answer").removeClass("hide");
  				} else{
  					$(".answer").addClass("show");
  					$(".error_answer").addClass("hide");
  					$(".answer").removeClass("hide");
  					$(".error_answer").removeClass("show");
  				}
  			}
  			function showAnsLogs(){
  				var log_len = ansLogs.length;
  				var log_str = '';
  				for ( var i = 0 ; i < log_len ; i++ ){
  					var correct = '';
  					console.log(ansLogs[i]);
  					if ( ansLogs[i].ansResult === 1 ){
  						
  						correct = "<span class='ans_wrong'>回答错误</span>";
  					} else {
  						correct = "<span class='ans_correct'>回答正确</span>";;
  					}
  					log_str += (i+1)+'、题号：'+ansLogs[i].qid+'，'+correct+'，用时：'+ansLogs[i].usedTime+'秒<br/>';
  				}
  				$(".answer_logs_content").html(log_str);
  				var h = $(".answer_logs_content")[0].scrollHeight;
  				$(".answer_logs_content").scrollTop(h);
  			}
  			$(".sbmAnswer").on('click',function(){
  				console.log('click'+curQuestion);
  				id = curQuestion['id'];
  				log = {};
  				log.qid = id;
  				log.usedTime = $("#used_time").data("seconds");
  				if ( checkAnswer() ){
  					log.ansResult = 0;//正确
  					nextQuestion();
  				} else {
					log.ansResult = 1;//错误
					toggleWhenWrong();
  				}
  				ansLogs.push(log);
  				showAnsLogs();
  			});
  			$("#nextQuest").on('click',function(){
  				toggleWhenWrong();
  				nextQuestion();
  			});
  			nextQuestion();//开始获取第一道题目
  		});
  	</script>
  </head>
  
  <body>
   	<div class="header">
			<div class="logo">
				<h1><a href=""><img src="img/logo1.png" alt=""/></a></h1>
			</div>
			<div class="user-icon">
				<a href="">
					<img id="photo" alt="" src="img/icon2.jpg" width="48px" height="48px"/>
					${sessionScope.session_user.username}
				</a>
			</div>	
			<div class="clear"></div>
		</div>
	<div class="header_bottom_line"></div>
  	<div id="test_center">
  		<div id="answer_logs" >
  			<p>回答记录：</p>
  			<div class="answer_logs_content"></div>
  		</div>
  		<div id="content">
  			<form action="" method="post" id="answerForm" class="answerForm">
  				<div class="quest_proper">
  					题目编号：<span id="quest_id_text"></span>
  					,&nbsp;难度：<span id="level_text"></span>
  					,&nbsp;用时：<span id="used_time"></span>
  				</div>
  			  	<img src="img/1.jpg" id="question_img"/>
  			  	<div class="error_answer hide">
  			  		<span class="correct_ans_text">正确答案是：</span>
  			  		&nbsp;
  			  		<input type="button" value="下一题" id="nextQuest"/>
  			  	</div>
	  			<div class="answer">
	  				<span>选择您的答案：</span>
	  				<span id="normal_answer">
	  				</span>
	  				<span id="fast_answer">
	  					<input type="radio" value="会" name="answer" id="r5" checked="checked"/>
		  				<label for="r5">会</label>
		  				<input type="radio" value="不会" name="answer" id="r6"/>
		  				<label for="r6">不会</label>
	  				</span>
	  				<span id="sbmAnswer" class="sbmAnswer">提交</span>
	  			</div>
  			</form>
			<form action="" id="endExamForm" method="post" class="endExamForm">
 				<input type="hidden" id="examType" value="${examType}"/>
 				<input type="hidden" id="level" value="${level}"/>
 				<input type="hidden" id="selectedIds" value="${selectedIds}"/>
 				<input type="hidden" id="usedTime"/>
 				<input type="hidden" id="answer_log"/>
  				<input type="submit" value="结束测试并查看评估报告" id="endExam" class="endExam"/>
 			</form>
  		</div>
  	</div>
  	<div class="footer" id="footer">
  		<div>© 2015 朝阳创新工作室版权所有</div>
  	</div>
 	<div id="dialog_mask" >
		<div id="dialog" title="提示！">
			<p id="dialog_content">${result}</p>
		</div>
	</div>
  </body>
</html>
