/*
 Navicat MySQL Data Transfer

 Source Server         : nas
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : yangwen.i234.me:3306
 Source Schema         : bubbling_frame2.0

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 06/05/2021 21:22:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_ac_func
-- ----------------------------
DROP TABLE IF EXISTS `t_ac_func`;
CREATE TABLE `t_ac_func`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `FUNC_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VIEW_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IMAGE_PATH` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `UPDATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL,
  `IS_VALID` int(0) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ac_func
-- ----------------------------
INSERT INTO `t_ac_func` VALUES ('1', '0', '根节点', NULL, NULL, 'syscreate', '2019-12-19 21:42:51', NULL, NULL, 2);
INSERT INTO `t_ac_func` VALUES ('2', '1', '系统权限', '', '&#xe604;', 'syscreate', '2019-12-19 21:44:05', NULL, NULL, 1);
INSERT INTO `t_ac_func` VALUES ('4', '2', '用户管理', '/home/user', '&#xe602;', 'syscreate', '2019-12-19 21:44:47', NULL, NULL, 1);
INSERT INTO `t_ac_func` VALUES ('4028fc8b6f3d4c16016f3d4f98c40000', '2', '部门管理', '/home/org', '&#xe601;', '00001', '2019-12-25 21:51:00', '00001', '2019-12-25 21:51:00', 1);
INSERT INTO `t_ac_func` VALUES ('5', '2', '角色管理', '/home/role', '&#xe603;', 'syscreate', '2019-12-19 21:44:47', NULL, NULL, 1);
INSERT INTO `t_ac_func` VALUES ('6', '2', '菜单管理', '/home/func', '&#xe605;', 'syscreate', '2019-12-19 21:44:47', NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_ac_org
-- ----------------------------
DROP TABLE IF EXISTS `t_ac_org`;
CREATE TABLE `t_ac_org`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ORG_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `PID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级节点',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `IS_VALID` int(0) NULL DEFAULT NULL COMMENT '是否有效（1有效2无效）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ac_org
-- ----------------------------
INSERT INTO `t_ac_org` VALUES ('1', '根节点', '0', 'syscreate', '2019-12-29 21:25:26', NULL, NULL, 1);
INSERT INTO `t_ac_org` VALUES ('2', '开发部门', '1', 'syscreate', '2019-12-29 21:25:26', NULL, NULL, 1);
INSERT INTO `t_ac_org` VALUES ('3', '维护部门', '1', 'syscreate', '2019-12-29 21:25:26', NULL, NULL, 1);
INSERT INTO `t_ac_org` VALUES ('4', '开发一组', '2', 'syscreate', '2019-12-29 21:25:26', NULL, NULL, 1);
INSERT INTO `t_ac_org` VALUES ('4028fc8b6f56cfab016f56cff7650000', '开发二组', '2', 'syscreate', '2019-12-29 21:25:26', '00001', '2020-01-01 14:40:45', 1);
INSERT INTO `t_ac_org` VALUES ('53b0ff23dfc7b04fbd6a87fcb48883cf', '维护一部', '3', '001', '2021-04-18 04:43:26', '001', '2021-04-18 21:48:24', 1);
INSERT INTO `t_ac_org` VALUES ('c5beb3a83d8782468d9887746b3e126d', '维护二部', '3', '001', '2021-04-18 12:44:12', NULL, NULL, 2);

-- ----------------------------
-- Table structure for t_ac_role
-- ----------------------------
DROP TABLE IF EXISTS `t_ac_role`;
CREATE TABLE `t_ac_role`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ROLE_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `IS_VALID` int(0) NULL DEFAULT NULL COMMENT '是否有效（1有效2无效）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ac_role
-- ----------------------------
INSERT INTO `t_ac_role` VALUES ('00001', '管理员', 'sys', '2020-01-18 19:40:25', NULL, NULL, 1);
INSERT INTO `t_ac_role` VALUES ('40281e816fb875e8016fb87a597e0001', '程序员鼓励师', '00001', '2020-01-18 19:47:04', '00001', '2020-01-18 20:07:11', 1);
INSERT INTO `t_ac_role` VALUES ('a59219678978c3ded2e4c3017d8853d1', 'test2', '001', '2021-05-04 22:36:49', NULL, NULL, 1);
INSERT INTO `t_ac_role` VALUES ('bc3ce95258458ab1e775a7e19c722fb4', 'test2', '001', '2021-05-04 19:43:12', '001', '2021-05-04 19:43:17', 2);
INSERT INTO `t_ac_role` VALUES ('dde8ce1021c79830d698c8f42a2907db', 'test3', '001', '2021-05-04 22:36:53', NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_ac_role_func
-- ----------------------------
DROP TABLE IF EXISTS `t_ac_role_func`;
CREATE TABLE `t_ac_role_func`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FUNC_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `ROLE_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门id',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `IS_VALID` int(0) NULL DEFAULT NULL COMMENT '是否有效（1有效2无效）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ac_role_func
-- ----------------------------
INSERT INTO `t_ac_role_func` VALUES ('6a59a862fb0ece80ab144b7d9a372b71', '4', '40281e816fb875e8016fb87a597e0001', '001', '2021-05-04 20:59:09', '001', '2021-05-04 21:50:31', 1);
INSERT INTO `t_ac_role_func` VALUES ('c055f346b667b4d804a29ffe69125426', '4028fc8b6f3d4c16016f3d4f98c40000', '40281e816fb875e8016fb87a597e0001', '001', '2021-05-04 20:59:12', '001', '2021-05-04 21:50:31', 1);
INSERT INTO `t_ac_role_func` VALUES ('e598dbbf899716d95eaf6dafd568e3ef', '2', '40281e816fb875e8016fb87a597e0001', '001', '2021-05-04 20:59:12', '001', '2021-05-04 21:50:31', 1);
INSERT INTO `t_ac_role_func` VALUES ('e9bcf0425b08f09ef92ce033244749be', '5', '40281e816fb875e8016fb87a597e0001', '001', '2021-05-04 20:59:12', '001', '2021-05-04 21:50:31', 1);

-- ----------------------------
-- Table structure for t_ac_role_org
-- ----------------------------
DROP TABLE IF EXISTS `t_ac_role_org`;
CREATE TABLE `t_ac_role_org`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ORG_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `ROLE_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门id',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `IS_VALID` int(0) NULL DEFAULT NULL COMMENT '是否有效（1有效2无效）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ac_role_org
-- ----------------------------
INSERT INTO `t_ac_role_org` VALUES ('40281e816fb875e8016fb87a608e0002', '4', '40281e816fb875e8016fb87a597e0001', '00001', '2020-01-18 19:47:05', NULL, NULL, 1);
INSERT INTO `t_ac_role_org` VALUES ('604a7d86869a06cdac24e8e89aa36014', '4', 'a59219678978c3ded2e4c3017d8853d1', '001', '2021-05-04 22:36:49', NULL, NULL, 1);
INSERT INTO `t_ac_role_org` VALUES ('79cea86cf48bf92819a9fb2342e6a1fd', '4', 'bc3ce95258458ab1e775a7e19c722fb4', '001', '2021-05-04 19:43:12', NULL, NULL, 1);
INSERT INTO `t_ac_role_org` VALUES ('afccc2d2fed7a7c9506456bedc4e5009', '4', 'dde8ce1021c79830d698c8f42a2907db', '001', '2021-05-04 22:36:53', NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_ac_user
-- ----------------------------
DROP TABLE IF EXISTS `t_ac_user`;
CREATE TABLE `t_ac_user`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOGIN_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `PASSWORD` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `USER_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `SEX` int(0) NULL DEFAULT NULL COMMENT '1男2女',
  `EMAIL` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `PHONE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `IS_VALID` int(0) NULL DEFAULT NULL COMMENT '是否有效（1有效2无效）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ac_user
-- ----------------------------
INSERT INTO `t_ac_user` VALUES ('001', 'admin', '96e79218965eb72c92a549dd5a330112', '管理员', NULL, '1020053105@qq.com', '13880666081', 'syscreate', '2019-12-14 13:34:13', '001', '2020-01-30 21:24:21', 1);
INSERT INTO `t_ac_user` VALUES ('128f0cef30bb5de95bfbd58dc2ca3114', 'wuren', '96e79218965eb72c92a549dd5a330112', '乌仁', 2, 'undefined@gmail.com', '15801158548', '001', '2021-04-22 22:01:31', '001', '2021-04-27 21:31:07', 2);
INSERT INTO `t_ac_user` VALUES ('9e226502d2f6195dfdec1bd4708b3841', 'yangwen', '96e79218965eb72c92a549dd5a330112', '杨文', 1, '1020053105@qq.com', '19520196860', '001', '2021-04-27 22:18:09', '001', '2021-04-27 22:25:28', 1);

-- ----------------------------
-- Table structure for t_ac_user_org
-- ----------------------------
DROP TABLE IF EXISTS `t_ac_user_org`;
CREATE TABLE `t_ac_user_org`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `USER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `ORG_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门id',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `IS_VALID` int(0) NULL DEFAULT NULL COMMENT '是否有效（1有效2无效）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ac_user_org
-- ----------------------------
INSERT INTO `t_ac_user_org` VALUES ('6aac48ee993601607262c258aea4c560', '128f0cef30bb5de95bfbd58dc2ca3114', '4', '001', '2021-04-22 22:01:31', NULL, NULL, 1);
INSERT INTO `t_ac_user_org` VALUES ('d347dd21a61b6f2c462d15363b143100', '9e226502d2f6195dfdec1bd4708b3841', '4', '001', '2021-04-27 22:18:09', NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_ac_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_ac_user_role`;
CREATE TABLE `t_ac_user_role`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `USER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `ROLE_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门id',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `IS_VALID` int(0) NULL DEFAULT NULL COMMENT '是否有效（1有效2无效）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ac_user_role
-- ----------------------------
INSERT INTO `t_ac_user_role` VALUES ('46486904d66724fd76682470f103ab3c', '9e226502d2f6195dfdec1bd4708b3841', '40281e816fb875e8016fb87a597e0001', '001', '2021-04-27 22:43:53', NULL, NULL, 1);
INSERT INTO `t_ac_user_role` VALUES ('8274d614bb52592c11b272cd55ddca64', '128f0cef30bb5de95bfbd58dc2ca3114', '40281e816fb875e8016fb87a597e0001', '001', '2021-04-24 21:48:39', NULL, NULL, 2);
INSERT INTO `t_ac_user_role` VALUES ('d903401797709ec011d1dbd0357e5a40', '128f0cef30bb5de95bfbd58dc2ca3114', '40281e816fb875e8016fb87a597e0001', '001', '2021-04-24 21:48:52', NULL, NULL, 1);
INSERT INTO `t_ac_user_role` VALUES ('e1574a52952e100d92725b3f47ea0032', '128f0cef30bb5de95bfbd58dc2ca3114', '40281e816fb875e8016fb87a597e0001', '001', '2021-04-24 21:40:05', '001', '2021-04-24 21:47:14', 2);

SET FOREIGN_KEY_CHECKS = 1;
