/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : healthy_manager

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2017-11-10 09:26:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hm_clock
-- ----------------------------
DROP TABLE IF EXISTS `hm_clock`;
CREATE TABLE `hm_clock` (
  `CLOCK_NO` int(11) NOT NULL AUTO_INCREMENT COMMENT '闹钟编号',
  `FREQUENCY` varchar(255) DEFAULT NULL COMMENT '频率（0：一次；1：每天一次；2：每天两次；3、每天三次）',
  `TIME1` time DEFAULT NULL COMMENT '时间1',
  `TIME2` time DEFAULT NULL COMMENT '时间2',
  `TIME3` time DEFAULT NULL COMMENT '时间3',
  `USER_NO` int(11) DEFAULT NULL COMMENT '用户编号',
  `BELL_URL` varchar(500) DEFAULT NULL COMMENT '铃声路径',
  `REMARK` varchar(400) DEFAULT NULL COMMENT '备注',
  `ITEM` varchar(255) DEFAULT NULL COMMENT '主题',
  PRIMARY KEY (`CLOCK_NO`),
  KEY `fk_clock_user_no` (`USER_NO`),
  CONSTRAINT `fk_clock_user_no` FOREIGN KEY (`USER_NO`) REFERENCES `hm_user_info` (`USER_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_clock
-- ----------------------------

-- ----------------------------
-- Table structure for hm_medical
-- ----------------------------
DROP TABLE IF EXISTS `hm_medical`;
CREATE TABLE `hm_medical` (
  `MEDICAL_NO` int(11) NOT NULL AUTO_INCREMENT COMMENT '病历编号',
  `USER_NO` int(11) DEFAULT NULL COMMENT '用户编号',
  `INQUIRY_TIME` timestamp NULL DEFAULT NULL COMMENT '就诊日期',
  `HOSPITAL_NAME` varchar(255) DEFAULT NULL COMMENT '医院名称',
  `DEPT_NAME` varchar(255) DEFAULT NULL COMMENT '科室名称',
  `DOCTOR_NAME` varchar(255) DEFAULT NULL COMMENT '医生姓名',
  `MAIN_SUIT` varchar(400) DEFAULT NULL COMMENT '主诉',
  `MEDICAL_HISTORY` varchar(1000) DEFAULT NULL COMMENT '现病史',
  `PHYSICAL_EXAMINATION` varchar(400) DEFAULT NULL COMMENT '查体',
  `DIAGNOSE` varchar(1000) DEFAULT NULL COMMENT '诊断',
  `SUGGEST` varchar(1000) DEFAULT NULL COMMENT '诊疗建议',
  `PRESCRIPTION_NO` int(11) DEFAULT NULL COMMENT '处方编号',
  `PHOTO_URL` varchar(500) DEFAULT NULL COMMENT '照片路径',
  `REMARK` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`MEDICAL_NO`),
  KEY `fk_medical_user_no` (`USER_NO`),
  CONSTRAINT `fk_medical_user_no` FOREIGN KEY (`USER_NO`) REFERENCES `hm_user_info` (`USER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_medical
-- ----------------------------
INSERT INTO `hm_medical` VALUES ('3', '1', '2017-06-12 14:56:46', '西京医院', '外壳', '失明', '双手无力', '无', '无', '无', '无', null, null, '无');
INSERT INTO `hm_medical` VALUES ('5', '1', '2017-06-12 11:32:19', '省人民医院', '2', '2', '记忆力衰退', '', '', '无', '', null, null, '');
INSERT INTO `hm_medical` VALUES ('7', '1', '2017-06-19 00:00:00', '2', '2', '2', '2', '2', '2', '2', '2', null, null, '2');
INSERT INTO `hm_medical` VALUES ('8', '1', '2017-06-19 00:00:00', '3', '3', '3', '3', '3', '3', '3', '3', null, null, '3');
INSERT INTO `hm_medical` VALUES ('16', '40', '2017-05-16 00:00:00', '西京医院', '神经内科', '史明', '双手无力', '颈椎曲度变直', '无', '特发性震颤', '随诊', null, null, '无');
INSERT INTO `hm_medical` VALUES ('18', '29', '2017-05-01 00:00:00', '西京医院', '神经内科', '史明', '双手无力', '颈椎曲度变直', '无', '特发性震颤', '随诊', null, null, '无');

-- ----------------------------
-- Table structure for hm_medicine
-- ----------------------------
DROP TABLE IF EXISTS `hm_medicine`;
CREATE TABLE `hm_medicine` (
  `MEDICINE_NO` int(11) NOT NULL AUTO_INCREMENT COMMENT '药品编号',
  `PRESCRIPTION_NO` int(11) DEFAULT NULL COMMENT '处方编号',
  `MEDICINE_NAME` varchar(255) DEFAULT NULL COMMENT '药品名称',
  `MEDICINE_SPEC` varchar(255) DEFAULT NULL COMMENT '药品规格（数量）',
  `MEDICINE_DOSAGE` varchar(255) DEFAULT NULL COMMENT '药品用量（单位）',
  `FREQUENCY` varchar(255) DEFAULT NULL COMMENT '频次',
  `WAY` varchar(255) DEFAULT NULL COMMENT '用药途径',
  `PHOTO_URL` varchar(500) DEFAULT NULL COMMENT '照片路径',
  `REMARK` varchar(4000) DEFAULT NULL COMMENT '备注',
  `PRODUCER` varchar(255) DEFAULT NULL COMMENT '生产商',
  PRIMARY KEY (`MEDICINE_NO`),
  KEY `hm_medicine_prescription_no` (`PRESCRIPTION_NO`),
  CONSTRAINT `hm_medicine_prescription_no` FOREIGN KEY (`PRESCRIPTION_NO`) REFERENCES `hm_prescription` (`PRESCRIPTION_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_medicine
-- ----------------------------
INSERT INTO `hm_medicine` VALUES ('1', '3', '盐酸阿罗洛尔片', '3*10mg*10', '1.00*10mg', '3/日', '口服', null, '备注', '');
INSERT INTO `hm_medicine` VALUES ('2', '3', '艾地苯醌片', '4*30mg*24', '1.00*30mg', '3/日', '口服', null, '备注', '');
INSERT INTO `hm_medicine` VALUES ('3', '3', '氯硝西泮片', '1*2mg*20', '0.25*2mg', '3/日', '口服', null, '备注', '');
INSERT INTO `hm_medicine` VALUES ('4', '4', '清脑复神液', '2疗程', '10mg', '3/日', '口服', null, '无', '无');
INSERT INTO `hm_medicine` VALUES ('5', '4', '星瑙灵片', '2疗程', '10mg', '3/日', '口服', null, '无', '无');
INSERT INTO `hm_medicine` VALUES ('6', '4', '1', '', '', '', '', null, '', '');
INSERT INTO `hm_medicine` VALUES ('8', '3', '22', '', '', '', '', null, '', '');
INSERT INTO `hm_medicine` VALUES ('16', '12', '腰酸', '', '', '', '', null, '', '');
INSERT INTO `hm_medicine` VALUES ('17', '12', '‘', '‘', '‘', '‘', '‘', null, '', '');

-- ----------------------------
-- Table structure for hm_prescription
-- ----------------------------
DROP TABLE IF EXISTS `hm_prescription`;
CREATE TABLE `hm_prescription` (
  `PRESCRIPTION_NO` int(11) NOT NULL AUTO_INCREMENT COMMENT '处方编号',
  `HOSPITAL_NAME` varchar(255) DEFAULT NULL COMMENT '医院/门诊名称',
  `INQUIRY_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '就诊日期',
  `REMARK` varchar(4000) DEFAULT NULL COMMENT '备注',
  `PRESCRIPTION_ABSTRACT` varchar(255) DEFAULT NULL COMMENT '处方描述',
  `PHOTO_URL` varchar(255) DEFAULT NULL COMMENT '照片路径',
  `USER_NO` int(11) DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`PRESCRIPTION_NO`),
  KEY `hm_prescription_user_no` (`USER_NO`),
  CONSTRAINT `hm_prescription_user_no` FOREIGN KEY (`USER_NO`) REFERENCES `hm_user_info` (`USER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_prescription
-- ----------------------------
INSERT INTO `hm_prescription` VALUES ('3', '西京医院', '2017-06-12 14:55:35', '', '双手无力', null, '1');
INSERT INTO `hm_prescription` VALUES ('4', '社区门诊', '2017-06-12 13:40:04', '', '社区门诊', null, '1');
INSERT INTO `hm_prescription` VALUES ('5', '1', '2017-06-19 00:00:00', '1', '1', null, '1');
INSERT INTO `hm_prescription` VALUES ('12', '西京医院', '2017-05-01 00:00:00', '无', '双手无力', null, '29');

-- ----------------------------
-- Table structure for hm_schedule
-- ----------------------------
DROP TABLE IF EXISTS `hm_schedule`;
CREATE TABLE `hm_schedule` (
  `SCHEDULE_NO` int(11) NOT NULL AUTO_INCREMENT COMMENT '提醒编号',
  `ITEM` varchar(255) DEFAULT NULL COMMENT '主题',
  `CONTENT` varchar(255) DEFAULT NULL COMMENT '内容',
  `TIME` timestamp NULL DEFAULT NULL COMMENT '时间',
  `BELL_URL` varchar(500) DEFAULT NULL COMMENT '铃声路径',
  `USER_NO` int(11) DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`SCHEDULE_NO`),
  KEY `fk_remind_user_no` (`USER_NO`) USING BTREE,
  CONSTRAINT `fk_schedule_user_no` FOREIGN KEY (`USER_NO`) REFERENCES `hm_user_info` (`USER_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_schedule
-- ----------------------------

-- ----------------------------
-- Table structure for hm_search
-- ----------------------------
DROP TABLE IF EXISTS `hm_search`;
CREATE TABLE `hm_search` (
  `SEARCH_NO` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `SEARCH_NAME` varchar(255) DEFAULT NULL COMMENT '网址名称',
  `SEARCH_URL` varchar(255) DEFAULT NULL COMMENT '网址路径',
  `USER_NO` int(11) DEFAULT NULL COMMENT '用户ID（为空表示所有用户共用）',
  `SEARCH_ABSTRACT` varchar(255) DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`SEARCH_NO`),
  KEY `fk_search_user_no` (`USER_NO`),
  CONSTRAINT `fk_search_user_no` FOREIGN KEY (`USER_NO`) REFERENCES `hm_user_info` (`USER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_search
-- ----------------------------
INSERT INTO `hm_search` VALUES ('1', '药品价格315网', 'https://www.315jiage.cn/', null, null);
INSERT INTO `hm_search` VALUES ('2', '趣医网', 'https://www.quyiyuan.com/', null, null);
INSERT INTO `hm_search` VALUES ('3', '西京医院预约挂号网', 'http://www.83215321.com/Index.aspx', null, null);
INSERT INTO `hm_search` VALUES ('6', '淘个宝', 'https://www.taobao.com', '2', null);
INSERT INTO `hm_search` VALUES ('7', '好大夫', 'http://www.haodf.com/', '1', 'miaoshushshbfh');
INSERT INTO `hm_search` VALUES ('16', '百度一哈', 'https://www.baidu.com', '1', '');
INSERT INTO `hm_search` VALUES ('26', '1', '1', '1', '');
INSERT INTO `hm_search` VALUES ('27', '2', '啊', '1', '');
INSERT INTO `hm_search` VALUES ('36', '百度', 'https://www.baidu.com', '29', '');

-- ----------------------------
-- Table structure for hm_user_info
-- ----------------------------
DROP TABLE IF EXISTS `hm_user_info`;
CREATE TABLE `hm_user_info` (
  `USER_NO` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `USER_NAME` varchar(255) DEFAULT NULL COMMENT '用户名',
  `PHONE_NUMBER` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `PASSWORD` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `LAST_LOG_TIME` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`USER_NO`),
  UNIQUE KEY `uq_phone_number` (`PHONE_NUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of hm_user_info
-- ----------------------------
INSERT INTO `hm_user_info` VALUES ('1', 'admin', '18888888888', 'cXdlcg==\n', '2017-06-21 10:10:57');
INSERT INTO `hm_user_info` VALUES ('2', 'root', '18888888881', 'cXdlcg==\n', null);
INSERT INTO `hm_user_info` VALUES ('29', 'rambler', '18829027593', 'd3hxODU0MDE5MTM0\n', '2017-06-22 23:01:43');
INSERT INTO `hm_user_info` VALUES ('39', 'wxq', '18829027389', 'd3d3\n', '2017-06-21 18:49:23');
INSERT INTO `hm_user_info` VALUES ('40', 'wxq', '13659201296', 'd3d3\n', '2017-06-21 21:00:06');
INSERT INTO `hm_user_info` VALUES ('41', 'wxq', '15686098453', 'd3d3\n', '2017-06-21 21:35:03');
