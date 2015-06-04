<%@page import="com.ccc.test.pojo.QuestionInfo"%>
<%@page import="com.ccc.test.pojo.KnowledgeInfo"%>
<%@page import="com.ccc.test.pojo.UserAnswerLogInfo"%>
<%@page import="com.ccc.test.utils.NumberUtil"%>
<%@page import="com.ccc.test.pojo.DiyPaperInfo"%>
<%@page import="com.ccc.test.service.impl.UserServiceImpl"%>
<%@page import="com.ccc.test.pojo.ValidtionInfo"%>
<%@page import="com.ccc.test.utils.Bog"%>
<%@page import="com.ccc.test.utils.StringUtil"%>
<%@page import="com.ccc.test.utils.TimeUtil"%>
<%@page import="com.ccc.test.utils.ListUtil"%>
<%@page import="com.ccc.test.pojo.UserInfo"%>
<%@page import="com.ccc.test.utils.GlobalValues"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% 
	UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalValues.SESSION_USER);
	if ( user == null ){
		user = new UserInfo();
	}
	String usertype = user.getType();
	DiyPaperInfo detail = (DiyPaperInfo)request.getAttribute("detail_paper");
	if ( detail == null )detail = new DiyPaperInfo();
	List<UserAnswerLogInfo> answerLogInfos = detail.getAnswerLogInfos();
	List<QuestionInfo> questionInfos = detail.getQuestionInfos();
	List<QuestionInfo> recommendQuestInfos = detail.getRecommendQuestInfos();
	List<KnowledgeInfo> chooseKnowledgeInfos = detail.getChooseKnowledgeInfos();
	List<KnowledgeInfo> goodKnowledgeInfos = detail.getGoodKnowledgeInfos();
	List<KnowledgeInfo> badKnowledgeInfos = detail.getBadKnowledgeInfos();
	List<KnowledgeInfo> midKnowledgeInfos = detail.getMidKnowledgeInfos();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>"/> 
    
    <title>测试详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<link rel="stylesheet" href="./js/themes/default/style.css" />
	<link rel="stylesheet" type="text/css" href="./css/globe.css"/>
	<link href="./css/template-style.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="./css/message.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script type="text/javascript" src="./js/render.js"></script>
  		
  	<script type="text/javascript">
  		$(function(){
  			var type = "<%=usertype%>";
	    	renderTabs(type,'消息中心',$(".cssmenu>ul"));
	    	renderUserHead(type);
  			$("#dialog_mask").hide();
  			$("input[id|='history_detail']").button();
  			$("input[id|='history_detail']").each(function(i){
  				$(this).unbind("click").bind({"click":function(){
  					var tmp = "history_detail-".length;
  					var hid = $(this).attr("id").substring(tmp);
  				}});
  			});
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
				<a href="javascript:void(0)"  id="head_icon_link">
					<img class="head_user_img" id="photo" alt="" src="${sessionScope.session_user.headUrl}" />
					${sessionScope.session_user.username}
				</a>
				<a href="user/loginOut.do">登出</a>
			</div>	
			<div class="clear"></div>
		</div>
		<div class='h_btm'>
			<div class='cssmenu'>
				<ul>
				    <li ><a><span>首页</span></a></li>
				    <li><a><span>我的关注</span></a></li>
				    <li><a><span>个人中心</span></a></li>
				    <li><a><span>消息中心</span></a></li>
				    <li><a><span>帮助</span></a></li>
				 </ul>
			</div>
		<div class="clear"></div>
		</div>
		</div>
		</div>
		</div>
		<div class="content">
			<div class="jmqk_content">
				<div class="jmqk_hz">
					<span>本次测试用时</span><span class="usedtime"></span>
					<span>答对</span><span class="rightcount"></span>
					<span>答错</span><span class="wrongcount"></span>
				</div>
				<div class="jmqk_table">
					<table>
						<tr>
							<td>考查知识点</td>
							<td class="choose_knowledges"></td>
						</tr>
						<tr>
							<td>测试时间</td>
							<td class="createtime"></td>
						</tr>
					</table>
				</div>
				<div class="result_list">
					<div class="table_header">答题情况：</div>
					<div class="list_table">
						<table>
	  						<tr class="first_row">
		  						<td>序号</td>
		  						<td>题目</td>
		  						<td>难度</td>
		  						<td>知识点</td>
		  						<td>你的答案</td>
		  						<td>正确答案</td>
		  						<td>对错</td>
  							</tr>
			  					<% 
			  						if ( ListUtil.isNotEmpty(answerLogInfos) ){
			  							int i = 0;
			  							for ( UserAnswerLogInfo info : answerLogInfos ){
			  								i++;
			  								QuestionInfo quest = new QuestionInfo();
			  								String rwStr = "";
			  								if ( info.getAnsResult() == 0 ){
			  									rwStr = "正确";
			  								} else {
			  									rwStr = "错误";
			  								}
			  								%>
				  								<tr>
							  						<td><%=i%></td>
							  						<td><img src="<%=quest.getQuestionUrl()%>"/></td>
							  						<td><%=quest.getLevel()%></td>
							  						<td></td>
							  						<td><%=info.getUser_answer()%></td>
							  						<td><%=info.getRight_answer()%></td>
							  						<td><%=rwStr%></td>
							  						<td></td>
							  					</tr>
			  								<%
			  							}
			  						}
								%>
  							</table>
					</div>
				</div>
			</div>
			<div class="pgbg_content">
			</div>
			<div class="zytj_content">
			</div>
		</div>
	 	<div id="dialog_mask" >
			<div id="dialog" title="">
			</div>
		</div>
  </body>
</html>
