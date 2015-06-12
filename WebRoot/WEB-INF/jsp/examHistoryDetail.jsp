<%@page import="com.ccc.test.utils.StringUtil"%>
<%@page import="com.ccc.test.utils.NumberUtil"%>
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
<%!
	String getKnowledgsNames (List<KnowledgeInfo> ks){
		if ( ListUtil.isNotEmpty(ks) ){
			StringBuffer sb = new StringBuffer();
			for ( KnowledgeInfo kinfo : ks ){
				sb.append(kinfo.getName()).append("、");
			}
			return sb.substring(0,sb.length() - 1);
		}
		return "";
	}

	QuestionInfo getQuestionByAnswerLog(UserAnswerLogInfo log,List<QuestionInfo> sources){
		QuestionInfo ret = new QuestionInfo();
		if (log != null ){
			if ( ListUtil.isNotEmpty(sources) ){
				for ( QuestionInfo info : sources ){
					if ( info.getId() == log.getQid() ){
						ret = info;
						break;
					}
				}
			}
		}
		return ret;
	}
%>
<% 
	UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalValues.SESSION_USER);
	if ( user == null ){
		user = new UserInfo();
	}
	String usertype = user.getType();
	
	UserInfo student = (UserInfo)request.getAttribute("student");
	if ( student == null )student = new UserInfo();
	DiyPaperInfo detail = (DiyPaperInfo)request.getAttribute("detailPaper");
	if ( detail == null )detail = new DiyPaperInfo();
	long createTime = detail.getCreateTime() == null ? 0L : detail.getCreateTime();
	String timeStr = TimeUtil.format(createTime, "yyyy年MM月dd日    hh:mm");
	List<UserAnswerLogInfo> answerLogInfos = detail.getAnswerLogInfos();
	List<QuestionInfo> questionInfos = detail.getQuestionInfos();
	List<QuestionInfo> recommendQuestInfos = detail.getRecommendQuestInfos();
	List<KnowledgeInfo> chooseKnowledgeInfos = detail.getChooseKnowledgeInfos();
	List<KnowledgeInfo> goodKnowledgeInfos = detail.getGoodKnowledgeInfos();
	List<KnowledgeInfo> badKnowledgeInfos = detail.getBadKnowledgeInfos();
	List<KnowledgeInfo> midKnowledgeInfos = detail.getMidKnowledgeInfos();
	
	String chooseknowledgesName = getKnowledgsNames(chooseKnowledgeInfos);
	String badknowledgesName = getKnowledgsNames(badKnowledgeInfos);
	String goodKnowledgeName = getKnowledgsNames(goodKnowledgeInfos);
	int rightCnt = detail.getRightCounts();
	int wrongCnt = detail.getWrongCounts();
	long totaltime = detail.getUseTime() == null ? 0L : detail.getUseTime();
	float scoreRate = 0;
	if ( rightCnt + wrongCnt != 0 ){
		scoreRate = 1.0f * rightCnt / ( rightCnt + wrongCnt );
	}
	int rankOfUsedTime = detail.getRankOfUsedTime();
	int rankOfScore = detail.getRankOfScore();
	String speedRank = ""; //速度竞争力
	String scoreRank = "";//分数竞争力
	if ( rankOfUsedTime == 0 ){
		speedRank = "真遗憾，你的做题时间与全国同学平均做题时间相比，慢于全国平均水平，要做多练习基础知识题！";
	} else if ( rankOfUsedTime == 1 ){
		speedRank = "你的做题时间与全国同学平均做题时间相比，处于全国平均水平，要巩固基础知识，多做练习！";
	} else if ( rankOfUsedTime == 2 ){
		speedRank = "恭喜您，你的做题时间与全国同学平均做题时间相比，快于全国平均水平，继续加油哦~";
	}
	
	if (rankOfScore == 0){
		scoreRank = "真遗憾，你的成绩低于全国平均分，要重新学习基础知识，巩固练习！";
	} else if (rankOfScore == 1){
		scoreRank = "你的做题得分与全国同学平均做题得分持平，要巩固基础知识，提升分数！";
	} else if (rankOfScore == 2){
		scoreRank = "恭喜您，你的成绩高于全国平均分，要在掌握基础知识之后辅助做些难题，保持战斗力！";
	}
	String learnLevel = detail.getLearnLevel();
	
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
				<em><%=student.getUsername()%>的测试评估报告</em>
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
			  								QuestionInfo quest = getQuestionByAnswerLog(info, questionInfos);
			  								if ( quest == null )quest = new QuestionInfo();
			  								String rwStr = "";
			  								String rwClazzStyle = "";
			  								if ( info.getAnsResult() == 0 ){
			  									rwStr = "正确";
			  									rwClazzStyle = "ans_correct";
			  								} else {
			  									rwStr = "错误";
			  									rwClazzStyle = "ans_wrong";
			  								}
			  								String defaultStr = "--";
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
							  						<td><a href="<%=quest.getQuestionUrl()%>" target="_blank"><img src="<%=StringUtil.getDefaultStrIfNull(quest.getQuestionUrl(), "img/default_quest.jpg")%>"/></a></td>
							  						<td><%=StringUtil.getDefaultStrIfNull(quest.getLevel(), defaultStr)%></td>
							  						<td><%=StringUtil.getDefaultStrIfNull(questknowledgesName, defaultStr)%></td>
							  						<td><%=StringUtil.getDefaultStrIfNull(info.getUser_answer(), defaultStr)%></td>
							  						<td><%=StringUtil.getDefaultStrIfNull(info.getRight_answer(), defaultStr)%></td>
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
					<p>本次测试的得分率为<span class="dfl"><%=NumberUtil.formatNumber(scoreRate*100, "###.#")%>%</span>。
					你的成绩处于<span class="learn_level"><%=StringUtil.getDefaultStrIfNull(learnLevel, "--")%></span>水平。
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
						</tr>
						<tr>
							<td class="good_knows"><%= StringUtil.getDefaultStrIfNull(goodKnowledgeName, "--")%></td>
							<td class="bad_knows"><%= StringUtil.getDefaultStrIfNull(badknowledgesName, "--")%></td>
						</tr>
					</table>
				</div>
				<!-- 竞争力分析 -->
				<div class="jzlfx">
					<h4>竞争力分析：</h4>
					<div class="jzl_box">
						<h4>做题速度竞争力</h4>
						<div class="jzl_tip"><%=speedRank %></div>
						<h4>做题分数竞争力</h4>
						<div class="jzl_tip"><%=scoreRank %></div>
					</div>

				</div>
			</div>
			<!-- 资源推荐 -->
			<div class="zytj_content"  id="content-3">
				<div class="zytj_hd">通过本次测试诊断，系统为<span class="stuname"><%=student.getUsername()%></span>同学推荐了以下资源：</div>
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
							  						<td><a href="<%=quest.getQuestionUrl()%>" target="_blank"><img src="<%=StringUtil.getDefaultStrIfNull(quest.getQuestionUrl(), "img/default_quest.jpg")%>"/></a></td>
							  						<td><%=StringUtil.getDefaultStrIfNull(quest.getLevel(), "--")%></td>
							  						<td><%=StringUtil.getDefaultStrIfNull(questknowledgesName, "--")%></td>
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
