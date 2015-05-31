/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : db_yrys_test

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-05-31 17:42:55
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户回答题目记录表';

-- ----------------------------
-- Table structure for tb_data_collect
-- ----------------------------
DROP TABLE IF EXISTS `tb_data_collect`;
CREATE TABLE `tb_data_collect` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `kid` int(11) DEFAULT NULL COMMENT '知识点id',
  `use_time` bigint(20) DEFAULT '0' COMMENT '答题总时间 单位秒s',
  `ans_number` int(11) DEFAULT '0' COMMENT '答题数量',
  `ans_right_number` int(11) DEFAULT '0' COMMENT '答题正确数量',
  `kid_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户回答题目统计表';

-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `gid` int(11) NOT NULL COMMENT '班级id',
  `name` varchar(100) DEFAULT NULL COMMENT '班级名字',
  `decription` varchar(255) DEFAULT NULL COMMENT '班级描述',
  `create_time` bigint(20) DEFAULT '0' COMMENT '创建班级时间',
  `validtion` int(11) DEFAULT '0' COMMENT '加入班级验证：0不用验证，1群组同意验证',
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级信息表';

-- ----------------------------
-- Table structure for tb_knowledge_node
-- ----------------------------
DROP TABLE IF EXISTS `tb_knowledge_node`;
CREATE TABLE `tb_knowledge_node` (
  `id` int(11) NOT NULL COMMENT '知识点id',
  `name` varchar(30) DEFAULT NULL COMMENT '知识点名字',
  `description` varchar(255) DEFAULT NULL COMMENT '知识点描述',
  `create_time` bigint(20) DEFAULT '0',
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识点信息表';

-- ----------------------------
-- Table structure for tb_knowledge_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_knowledge_relation`;
CREATE TABLE `tb_knowledge_relation` (
  `nid` int(11) DEFAULT NULL COMMENT '知识点id',
  `pid` int(11) DEFAULT NULL COMMENT '知识点父节点id',
  `cids` int(11) DEFAULT NULL COMMENT '知识点子节点ids列表，用，隔开'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识点关系表';

-- ----------------------------
-- Table structure for tb_paper
-- ----------------------------
DROP TABLE IF EXISTS `tb_paper`;
CREATE TABLE `tb_paper` (
  `pid` int(11) NOT NULL COMMENT '试卷id',
  `name` varchar(255) DEFAULT NULL COMMENT '试卷名字',
  `paper_url` varchar(255) DEFAULT NULL COMMENT '试卷文档地址url',
  `create_time` bigint(20) DEFAULT '0' COMMENT '试卷创建时间',
  `questions` text COMMENT '试卷中的题目id列表 id之间用，隔开',
  `teacher_id` int(11) DEFAULT NULL COMMENT '上传者（老师）id',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试卷信息表';

-- ----------------------------
-- Table structure for tb_question
-- ----------------------------
DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE `tb_question` (
  `qid` int(11) NOT NULL COMMENT '题目id',
  `question_url` varchar(255) DEFAULT NULL COMMENT '题目的文档地址',
  `answer` varchar(10) DEFAULT NULL COMMENT '问题的答案',
  `level` varchar(30) DEFAULT NULL COMMENT '题目难度',
  `type` varchar(20) DEFAULT NULL COMMENT '题目类型，如选择题',
  `flag` int(11) DEFAULT NULL COMMENT 'flag=0:管理员上传的题目；flag=1：试卷中的题目',
  `options` varchar(30) DEFAULT NULL,
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
-- Table structure for tb_stu_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_stu_group`;
CREATE TABLE `tb_stu_group` (
  `id` int(11) NOT NULL COMMENT '记录的id',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `gid` int(11) DEFAULT NULL COMMENT '班级id',
  `create_time` bigint(20) DEFAULT '0' COMMENT '建立关系时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与班级关系表';

-- ----------------------------
-- Table structure for tb_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_userinfo`;
CREATE TABLE `tb_userinfo` (
  `uid` int(11) NOT NULL COMMENT '用户id列',
  `password` varchar(64) DEFAULT NULL COMMENT '用户密码',
  `usertype` varchar(20) DEFAULT NULL COMMENT '用户角色',
  `username` varchar(40) DEFAULT NULL COMMENT '用户名字',
  `head_url` varchar(255) DEFAULT NULL COMMENT '头像地址url',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `birthday` bigint(20) DEFAULT '0' COMMENT '生日',
  `create_time` bigint(20) DEFAULT '0' COMMENT '注册时间',
  `description` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `realname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Table structure for tb_validation
-- ----------------------------
DROP TABLE IF EXISTS `tb_validation`;
CREATE TABLE `tb_validation` (
  `id` int(11) NOT NULL COMMENT '验证消息id',
  `uid` int(11) DEFAULT '0' COMMENT '发出验证请求用户id',
  `gid` int(11) DEFAULT '0' COMMENT '要加入班级的id',
  `create_time` bigint(20) DEFAULT '0' COMMENT '请求时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验证信息表';

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
