/*
Navicat MySQL Data Transfer

Source Server         : TribleDB
Source Server Version : 50616
Source Host           : 127.0.0.1:1206
Source Database       : db_yrys_test

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2015-06-19 00:55:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_answer_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_answer_log`;
CREATE TABLE `tb_answer_log` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `qid` int(11) DEFAULT NULL COMMENT '题目id',
  `use_time` bigint(20) DEFAULT '0' COMMENT '答题使用时间 单位秒s',
  `ans_result` int(11) DEFAULT '2' COMMENT '0代表正确，1代表错误，2未回答',
  `right_answer` varchar(256) DEFAULT NULL COMMENT '正确答案',
  `user_answer` varchar(256) DEFAULT NULL COMMENT '学生选择的答案',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户回答题目记录表';

-- ----------------------------
-- Table structure for tb_diy_paper
-- ----------------------------
DROP TABLE IF EXISTS `tb_diy_paper`;
CREATE TABLE `tb_diy_paper` (
  `pid` int(11) NOT NULL COMMENT '试卷id',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `paperName` varchar(256) DEFAULT NULL COMMENT '卷试名称',
  `wrongCounts` int(11) DEFAULT '0' COMMENT '回答错误的题目数',
  `rightCounts` int(11) DEFAULT '0' COMMENT '回答正确的题目数',
  `useTime` bigint(50) DEFAULT '0' COMMENT '回答整张试卷的用时',
  `createTime` bigint(50) DEFAULT '0' COMMENT '试卷生成的时间',
  `chooseKnowledges` varchar(255) DEFAULT NULL COMMENT '选择的知识点列表，用逗号隔开',
  `answer_logs` varchar(255) DEFAULT NULL COMMENT '题目回答记录',
  `paperLevel` varchar(255) DEFAULT NULL COMMENT '试卷难度',
  `learnLevel` varchar(255) DEFAULT NULL COMMENT '试卷掌握情况',
  `goodKnowledges` varchar(255) DEFAULT NULL COMMENT '已经掌握的知识点列表，用逗号隔开',
  `badKnowledges` varchar(255) DEFAULT NULL COMMENT '没有掌握的知识点列表，用逗号隔开',
  `midKnowledges` varchar(255) DEFAULT NULL COMMENT '掌握情况一般的知识点列表',
  `recommendQuestions` varchar(255) DEFAULT NULL COMMENT '推荐题目列表，用逗号隔开',
  `rankOfScore` int(11) DEFAULT '0' COMMENT '得分排名,0，下游；1中等，2上游',
  `rankOfUsedTime` int(11) DEFAULT '0' COMMENT '考试用时排名 0，下游；1中等，2上游',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `gid` int(11) NOT NULL COMMENT '班级id',
  `owner_id` int(11) DEFAULT NULL COMMENT '建创者id',
  `name` varchar(100) DEFAULT NULL COMMENT '班级名字',
  `description` varchar(255) DEFAULT NULL COMMENT '班级描述',
  `create_time` bigint(20) DEFAULT '0' COMMENT '创建班级时间',
  `validtion` int(11) DEFAULT '0' COMMENT '加入班级验证：0不用验证，1群组同意验证',
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级信息表';

-- ----------------------------
-- Table structure for tb_huoyue_user_statistic
-- ----------------------------
DROP TABLE IF EXISTS `tb_huoyue_user_statistic`;
CREATE TABLE `tb_huoyue_user_statistic` (
  `huoyuedate` bigint(20) DEFAULT '0' COMMENT '活跃时间 时间戳',
  `id` int(11) NOT NULL,
  `huoyuenum` int(11) DEFAULT '0' COMMENT '活跃人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_knowledge_node
-- ----------------------------
DROP TABLE IF EXISTS `tb_knowledge_node`;
CREATE TABLE `tb_knowledge_node` (
  `id` int(11) NOT NULL COMMENT '知识点id',
  `name` varchar(30) DEFAULT NULL COMMENT '知识点名字',
  `description` varchar(255) DEFAULT NULL COMMENT '知识点描述',
  `create_time` bigint(20) DEFAULT '0',
  `pid` int(11) DEFAULT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识点信息表';

-- ----------------------------
-- Table structure for tb_paper_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_paper_group`;
CREATE TABLE `tb_paper_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL COMMENT '卷试id',
  `gid` int(11) NOT NULL COMMENT '班级id',
  `createTime` bigint(50) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_question
-- ----------------------------
DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE `tb_question` (
  `qid` int(11) NOT NULL COMMENT '题目id',
  `question_url` varchar(255) DEFAULT NULL COMMENT '题目的文档地址',
  `options` varchar(255) DEFAULT NULL COMMENT '题目选项：每个选项用逗号隔开',
  `answer` varchar(10) DEFAULT NULL COMMENT '问题的答案',
  `level` varchar(30) DEFAULT NULL COMMENT '题目难度',
  `type` varchar(20) DEFAULT NULL COMMENT '题目类型，如选择题',
  `create_time` bigint(20) DEFAULT '0' COMMENT '题目创建时间',
  `wrong_count` int(11) unsigned zerofill DEFAULT '00000000000' COMMENT '回答错误的人数',
  `avg_time` float(10,2) unsigned zerofill DEFAULT '0000000.00' COMMENT '用户答题平均用时',
  `right_count` int(11) unsigned zerofill DEFAULT '00000000000' COMMENT '答对用户数',
  `flag` int(11) DEFAULT '0' COMMENT 'flag=0:管理员上传的题目；flag=1：试卷中的题目',
  `select_weight` float(11,0) DEFAULT '0' COMMENT '选择权重[0,1]，0代表不能选择，值越大越优先选择',
  PRIMARY KEY (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目信息表';

-- ----------------------------
-- Table structure for tb_quest_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `tb_quest_knowledge`;
CREATE TABLE `tb_quest_knowledge` (
  `id` int(11) NOT NULL COMMENT '记录的id',
  `qid` int(11) DEFAULT NULL COMMENT '题目id',
  `kid` int(11) DEFAULT NULL COMMENT '知识点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与班级关系表';

-- ----------------------------
-- Table structure for tb_teacher_paper
-- ----------------------------
DROP TABLE IF EXISTS `tb_teacher_paper`;
CREATE TABLE `tb_teacher_paper` (
  `pid` int(11) NOT NULL COMMENT '试卷id',
  `name` varchar(255) DEFAULT NULL COMMENT '试卷名字',
  `paper_url` varchar(255) DEFAULT NULL COMMENT '试卷文档地址url',
  `create_time` bigint(20) DEFAULT '0' COMMENT '试卷创建时间',
  `questions` text COMMENT '试卷中的题目id列表 id之间用，隔开',
  `teacher_id` int(11) DEFAULT '0' COMMENT '上传者（老师）id',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户答题自定义试卷信息表';

-- ----------------------------
-- Table structure for tb_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_userinfo`;
CREATE TABLE `tb_userinfo` (
  `uid` int(11) NOT NULL COMMENT '用户id列',
  `password` varchar(64) DEFAULT NULL COMMENT '用户密码',
  `usertype` varchar(20) DEFAULT NULL COMMENT '用户角色',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `username` varchar(40) DEFAULT NULL COMMENT '用户名字',
  `head_url` varchar(255) DEFAULT NULL COMMENT '头像地址url',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `birthday` bigint(20) DEFAULT '0' COMMENT '生日',
  `create_time` bigint(20) DEFAULT '0' COMMENT '注册时间',
  `description` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `last_login_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '最后登录时间，时间戳格式',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Table structure for tb_user_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_group`;
CREATE TABLE `tb_user_group` (
  `id` int(11) NOT NULL COMMENT '记录的id',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `gid` int(11) DEFAULT NULL COMMENT '班级id',
  `create_time` bigint(20) DEFAULT '0' COMMENT '建立关系时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与班级关系表';

-- ----------------------------
-- Table structure for tb_validation
-- ----------------------------
DROP TABLE IF EXISTS `tb_validation`;
CREATE TABLE `tb_validation` (
  `id` int(11) NOT NULL COMMENT '验证消息id',
  `request_id` int(11) unsigned DEFAULT '0' COMMENT '发出验证请求用户id',
  `accept_id` int(11) DEFAULT NULL COMMENT '收接者id',
  `group_id` int(11) DEFAULT '0' COMMENT '要加入班级的id',
  `create_time` bigint(20) DEFAULT '0' COMMENT '请求时间',
  `handle_result` varchar(10) DEFAULT NULL COMMENT '处理结果，同意或忽略',
  `message` varchar(255) DEFAULT NULL COMMENT '留言信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验证信息表';

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
