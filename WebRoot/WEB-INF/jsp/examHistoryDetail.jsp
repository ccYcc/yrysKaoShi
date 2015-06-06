<%@page import="com.ccc.test.pojo.QuestionInfo"%>
<%@page import="com.ccc.test.pojo.KnowledgeInfo"%>
<%@page import="com.ccc.test.pojo.UserAnswerLogInfo"%>
<%@page import="com.ccc.test.pojo.DiyPaperInfo"%>
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
	DiyPaperInfo detail = (DiyPaperInfo)request.getAttribute("detailPaper");
	if ( detail == null )detail = new DiyPaperInfo();
	String timeStr = TimeUtil.format(detail.getCreateTime(), "yyyy年MM月dd日    hh:mm");
	String chooseknowledgesName = "";
	List<UserAnswerLogInfo> answerLogInfos = detail.getAnswerLogInfos();
	List<QuestionInfo> questionInfos = detail.getQuestionInfos();
	List<QuestionInfo> recommendQuestInfos = detail.getRecommendQuestInfos();
	List<KnowledgeInfo> chooseKnowledgeInfos = detail.getChooseKnowledgeInfos();
	List<KnowledgeInfo> goodKnowledgeInfos = detail.getGoodKnowledgeInfos();
	List<KnowledgeInfo> badKnowledgeInfos = detail.getBadKnowledgeInfos();
	List<KnowledgeInfo> midKnowledgeInfos = detail.getMidKnowledgeInfos();
	if ( ListUtil.isNotEmpty(chooseKnowledgeInfos) ){
		StringBuffer sb = new StringBuffer();
		for ( KnowledgeInfo kinfo : chooseKnowledgeInfos ){
			sb.append(kinfo.getName()).append("、");
		}
		chooseknowledgesName = sb.substring(0,sb.length() - 1);
	}
	int rightCnt = 0;
	int wrongCnt = 0;
	long totaltime = detail.getUseTime();
	if ( ListUtil.isNotEmpty(answerLogInfos) ){
		for ( UserAnswerLogInfo uaInfo : answerLogInfos ){
			if ( uaInfo.getAnsResult() == 0 ){
				rightCnt++;
			} else {
				wrongCnt++;
			}
		}
	}
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
	<link rel="stylesheet" type="text/css" href="./css/exam-history-detail.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.structure.css"/>
	<link rel="stylesheet" type="text/css" href="./css/jquery-ui.theme.css"/>
  	<script src="./js/jquery-1.11.3.js"></script>
  	<script src="./js/jquery-ui.js"></script>
  	<script type="text/javascript" src="./js/render.js"></script>
  		
  	<script type="text/javascript">
  		$(function(){
	    	renderUserHead("<%=usertype%>");
	    	renderMainPage("<%=usertype%>");
  			$("#dialog_mask").hide();
  			$("li[id|='tab']").each(function(i){
  				$(this).off("click").on({"click":function(){
  					var tmp = "tab-".length;
  					var tid = $(this).attr("id").substring(tmp);
  					$("li[id|='tab']").each(function(i){
  						$(this).removeClass("active");
  					});
  					$("div[id|='content']").each(function(i){
  						$(this).hide();
  					});
  					$(this).addClass("active");
  					var cs = "#content-"+tid;
  					$(cs).show();
  				}});
  			});
			$("#tab-1").trigger("click");
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
				    <li class="" ><a name="mainpage"><span>首页</span></a></li>
				    <li class="active" id="tab-1"><a><span>试卷概况</span></a></li>
				    <li class="" id="tab-2"><a><span>评估报告</span></a></li>
				    <li class="" id="tab-3"><a><span>资源推荐</span></a></li>
				 </ul>
			</div>
		<div class="clear"></div>
		</div>
		</div>
		</div>
		</div>
		<!-- 详细内容区 -->
		<div class="content">
			<div class="paper_header">
				<h2>《<%=detail.getPaperName()%>》</h2>
				<em><%=user.getUsername()%>的测试评估报告</em>
			</div>
			<hr />
			<!-- 卷面情况内容区 -->
			<div class="jmqk_content" id="content-1">
				<!-- 卷面情况 汇总 -->
				<div class="jmqk_hz">
					<span>本次测试用时：</span><span class="usedtime"><strong><%=TimeUtil.secondsToReadableStr(totaltime)%></strong></span>
					<span>答对：</span><span class="rightcount"><strong><%=rightCnt%>道</strong></span>
					<span>答错：</span><span class="wrongcount"><strong><%=wrongCnt%>道</strong></span>
				</div>
				<!-- 卷面情况 表格区 -->
				<div class="jmqk_table">
					<table>
						<tr>
							<td class="tit"><strong>考查知识点</strong></td>
							<td class="choose_knowledges"><%=chooseknowledgesName%></td>
						</tr>
						<tr>
							<td class="tit"><strong>测试时间</strong></td>
							<td class="createtime"><%=timeStr%></td>
						</tr>
					</table>
				</div>
				<!-- 卷面情况 答题历史列表 -->
				<div class="ans_log_list">
					<div class="table_header"><strong>答题情况</strong></div>
					<div class="loglist_table">
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
			  								QuestionInfo quest = questionInfos.get(i);
			  								String rwStr = "";
			  								String rwClazzStyle = "";
			  								if ( info.getAnsResult() == 0 ){
			  									rwStr = "正确";
			  									rwClazzStyle = "ans_correct";
			  								} else {
			  									rwStr = "错误";
			  									rwClazzStyle = "ans_wrong";
			  								}
			  								List<KnowledgeInfo> questKnowledgs = quest.getKnowledges();
			  								String questknowledgesName = "";
			  								if ( ListUtil.isNotEmpty(questKnowledgs) ){
			  									StringBuffer sb = new StringBuffer();
			  									for ( KnowledgeInfo kinfo : questKnowledgs ){
			  										sb.append(kinfo.getName()).append("、");
			  									}
			  									questknowledgesName = sb.substring(0,sb.length() - 1);
			  								}
			  								i++;
			  								%>
				  								<tr>
							  						<td><%=i%></td>
							  						<td><a href="<%=quest.getQuestionUrl()%>" target="_blank"><img src="<%=quest.getQuestionUrl()%>"/></a></td>
							  						<td><%=quest.getLevel()%></td>
							  						<td><%=questknowledgesName%></td>
							  						<td><%=info.getUser_answer()%></td>
							  						<td><%=info.getRight_answer()%></td>
							  						<td class="<%=rwClazzStyle %>"><%=rwStr%></td>
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
			<!-- 评估报告 -->
			<div class="pgbg_content"  id="content-2">
				<!-- 诊断综述 -->
				<div class="zdzs">
					<h4>诊断综述：</h4>
					<p>本次测试的得分率为<span class="dfl">0%</span>。
					你的成绩处于<span class="learn_level"></span>水平。
					涉及的知识点有<span class="choose_knowledges">“<%=chooseknowledgesName%>”。</span>
					</p>
				</div>
				<!-- 知识点掌握情况 -->
				<div class="zwqk">
					<h4>知识点掌握情况：</h4>
					<table>
						<tr>
							<td class="tit"><strong>已掌握知识点</strong></td>
							<td class="tit"><strong>未掌握知识点</strong></td>
							<td class="tit"><strong>待提高知识点</strong></td>
						</tr>
						<tr>
							<td class="good_knows">知识点/知识点/知识点\</td>
							<td class="bad_knows">知识点/知识点/知识点\</td>
							<td class="mid_knows">知识点/知识点/知识点\</td>
						</tr>
					</table>
				</div>
				<!-- 竞争力分析 -->
				<div class="jzlfx">
					<h4>竞争力分析：</h4>
					<div class="jzl_box">
						<h4>做题速度竞争力</h4>
						<div class="jzl_tip">真遗憾，你的成绩低于全国平均分，要重新学习基础知识，巩固练习！</div>
						<h4>做题分数竞争力</h4>
						<div class="jzl_tip">真遗憾，你的成绩低于全国平均分，要重新学习基础知识，巩固练习！</div>
					</div>

				</div>
			</div>
			<!-- 资源推荐 -->
			<div class="zytj_content"  id="content-3">
				<div class="zytj_hd">通过本次学情诊断，<span class="stuname">天街飘影®</span>同学在<span class="choose_knowledges">知识点。知识点2。。。</span>方面掌握<span class="knowledges_level">欠缺</span>，以下资源是专为你推荐的：</div>
				<div class="recommend_list_table">
						<table>
			  					<% 
			  						if ( ListUtil.isNotEmpty(recommendQuestInfos) ){
			  							%>
			  							<tr class="first_row">
					  						<td>序号</td>
					  						<td>题目</td>
					  						<td>难度</td>
					  						<td>知识点</td>
			  							</tr>
			  							<%
			  							int i = 0;
			  							for ( QuestionInfo quest : recommendQuestInfos ){
			  								List<KnowledgeInfo> questKnowledgs = quest.getKnowledges();
			  								String questknowledgesName = "";
			  								if ( ListUtil.isNotEmpty(questKnowledgs) ){
			  									StringBuffer sb = new StringBuffer();
			  									for ( KnowledgeInfo kinfo : questKnowledgs ){
			  										sb.append(kinfo.getName()).append("、");
			  									}
			  									questknowledgesName = sb.substring(0,sb.length() - 1);
			  								}
			  								i++;
			  								%>
				  								<tr>
							  						<td><%=i%></td>
							  						<td><a href="<%=quest.getQuestionUrl()%>" target="_blank"><img src="<%=quest.getQuestionUrl()%>"/></a></td>
							  						<td><%=quest.getLevel()%></td>
							  						<td><%=questknowledgesName%></td>
							  						<td></td>
							  					</tr>
			  								<%
			  								
			  							}
			  						} else {
			  							%>
			  								<div><span>很抱歉，暂无推荐资源，我们会尽快补充更多资源。</span></div>
			  							<%
			  						}
								%>
  							</table>
					</div>
			</div>
		</div>
	 	<div id="dialog_mask" >
			<div id="dialog" title="">
			</div>
		</div>
  </body>
</html>
