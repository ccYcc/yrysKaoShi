/*
Navicat MySQL Data Transfer

Source Server         : TribleDB
Source Server Version : 50616
Source Host           : 127.0.0.1:1206
Source Database       : db_yrys_test

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2015-05-20 23:24:42
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
-- Records of tb_answer_log
-- ----------------------------

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
-- Records of tb_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_knowledge_node
-- ----------------------------
DROP TABLE IF EXISTS `tb_knowledge_node`;
CREATE TABLE `tb_knowledge_node` (
  `id` int(11) NOT NULL COMMENT '知识点id',
  `name` varchar(30) DEFAULT NULL COMMENT '知识点名字',
  `description` varchar(255) DEFAULT NULL COMMENT '知识点描述',
  `create_time` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识点信息表';

-- ----------------------------
-- Records of tb_knowledge_node
-- ----------------------------

-- ----------------------------
-- Table structure for tb_knowledge_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_knowledge_relation`;
CREATE TABLE `tb_knowledge_relation` (
  `nid` int(11) DEFAULT NULL COMMENT '知识点id',
  `pid` int(11) DEFAULT NULL COMMENT '知识点父节点id',
  `cids` text COMMENT '知识点子节点ids列表，用，隔开'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识点关系表';

-- ----------------------------
-- Records of tb_knowledge_relation
-- ----------------------------

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
-- Records of tb_paper
-- ----------------------------
INSERT INTO `tb_paper` VALUES ('1', '/test', '/test', '1432113351471', '1,2,3,4,5,6,7,8,9,10,', '1');

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
  PRIMARY KEY (`qid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目信息表';

-- ----------------------------
-- Records of tb_question
-- ----------------------------
INSERT INTO `tb_question` VALUES ('1', '/test_url', null, null, null, '0');
INSERT INTO `tb_question` VALUES ('2', '/test_url', null, null, null, '0');
INSERT INTO `tb_question` VALUES ('3', '/test_url', null, null, null, '0');
INSERT INTO `tb_question` VALUES ('4', '/test_url', null, null, null, '0');
INSERT INTO `tb_question` VALUES ('5', '/test_url', null, null, null, '0');
INSERT INTO `tb_question` VALUES ('6', '/test_url', null, null, null, '0');
INSERT INTO `tb_question` VALUES ('7', '/test_url', null, null, null, '0');
INSERT INTO `tb_question` VALUES ('8', '/test_url', null, null, null, '0');
INSERT INTO `tb_question` VALUES ('9', '/test_url', null, null, null, '0');
INSERT INTO `tb_question` VALUES ('10', '/test_url', null, null, null, '0');

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
-- Records of tb_quest_knowledge
-- ----------------------------
INSERT INTO `tb_quest_knowledge` VALUES ('1', '1', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('2', '1', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('3', '1', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('4', '1', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('5', '1', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('6', '1', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('7', '1', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('8', '1', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('9', '1', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('10', '1', '9');
INSERT INTO `tb_quest_knowledge` VALUES ('11', '2', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('12', '2', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('13', '2', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('14', '2', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('15', '2', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('16', '2', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('17', '2', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('18', '2', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('19', '2', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('20', '2', '9');
INSERT INTO `tb_quest_knowledge` VALUES ('21', '3', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('22', '3', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('23', '3', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('24', '3', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('25', '3', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('26', '3', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('27', '3', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('28', '3', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('29', '3', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('30', '3', '9');
INSERT INTO `tb_quest_knowledge` VALUES ('31', '4', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('32', '4', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('33', '4', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('34', '4', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('35', '4', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('36', '4', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('37', '4', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('38', '4', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('39', '4', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('40', '4', '9');
INSERT INTO `tb_quest_knowledge` VALUES ('41', '5', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('42', '5', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('43', '5', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('44', '5', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('45', '5', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('46', '5', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('47', '5', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('48', '5', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('49', '5', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('50', '5', '9');
INSERT INTO `tb_quest_knowledge` VALUES ('51', '6', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('52', '6', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('53', '6', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('54', '6', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('55', '6', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('56', '6', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('57', '6', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('58', '6', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('59', '6', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('60', '6', '9');
INSERT INTO `tb_quest_knowledge` VALUES ('61', '7', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('62', '7', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('63', '7', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('64', '7', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('65', '7', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('66', '7', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('67', '7', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('68', '7', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('69', '7', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('70', '7', '9');
INSERT INTO `tb_quest_knowledge` VALUES ('71', '8', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('72', '8', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('73', '8', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('74', '8', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('75', '8', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('76', '8', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('77', '8', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('78', '8', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('79', '8', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('80', '8', '9');
INSERT INTO `tb_quest_knowledge` VALUES ('81', '9', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('82', '9', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('83', '9', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('84', '9', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('85', '9', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('86', '9', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('87', '9', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('88', '9', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('89', '9', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('90', '9', '9');
INSERT INTO `tb_quest_knowledge` VALUES ('91', '10', '0');
INSERT INTO `tb_quest_knowledge` VALUES ('92', '10', '1');
INSERT INTO `tb_quest_knowledge` VALUES ('93', '10', '2');
INSERT INTO `tb_quest_knowledge` VALUES ('94', '10', '3');
INSERT INTO `tb_quest_knowledge` VALUES ('95', '10', '4');
INSERT INTO `tb_quest_knowledge` VALUES ('96', '10', '5');
INSERT INTO `tb_quest_knowledge` VALUES ('97', '10', '6');
INSERT INTO `tb_quest_knowledge` VALUES ('98', '10', '7');
INSERT INTO `tb_quest_knowledge` VALUES ('99', '10', '8');
INSERT INTO `tb_quest_knowledge` VALUES ('100', '10', '9');

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
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of tb_userinfo
-- ----------------------------
INSERT INTO `tb_userinfo` VALUES ('1', '96f90e243d176e5f1a34f4b0b25253ab', '学生', 'ccb', null, null, '0', '0', null, null);
INSERT INTO `tb_userinfo` VALUES ('2', 'd4fbb7d8d5603db43ac2094f5955787c', '老师', 'aaaa', null, null, '0', '0', null, null);
INSERT INTO `tb_userinfo` VALUES ('3', 'b9be11166d72e9e3ae7fd407165e4bd2', '管理员', 'root', null, null, '0', '0', null, null);
INSERT INTO `tb_userinfo` VALUES ('4', 'c3284d0f94606de1fd2af172aba15bf3', '管理员', 'admin', null, null, '0', '0', null, null);
INSERT INTO `tb_userinfo` VALUES ('5', '96f90e243d176e5f1a34f4b0b25253ab', '学生', 'chenchuibo', null, null, '0', '0', null, null);
INSERT INTO `tb_userinfo` VALUES ('6', '14e1b600b1fd579f47433b88e8d85291', '学生', 'cxl', null, null, '0', '0', null, null);

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
-- Records of tb_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_validation
-- ----------------------------
DROP TABLE IF EXISTS `tb_validation`;
CREATE TABLE `tb_validation` (
  `id` int(11) NOT NULL COMMENT '验证消息id',
  `uid` int(11) DEFAULT '0' COMMENT '发出验证请求用户id',
  `gid` int(11) DEFAULT '0' COMMENT '要加入班级的id',
  `create_time` bigint(20) DEFAULT '0' COMMENT '请求时间',
  `handle_result` varchar(10) DEFAULT NULL COMMENT '处理结果，同意或忽略',
  `messages` varchar(255) DEFAULT NULL COMMENT '留言信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验证信息表';

-- ----------------------------
-- Records of tb_validation
-- ----------------------------

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
