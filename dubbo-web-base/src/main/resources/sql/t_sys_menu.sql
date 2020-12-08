/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-12-08 15:41:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `grades` int(11) DEFAULT NULL COMMENT '等级',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `is_visible` int(11) DEFAULT NULL COMMENT '是否可用',
  `method` varchar(255) DEFAULT NULL COMMENT '请求方法',
  `name` varchar(255) DEFAULT NULL COMMENT '接口名字',
  `seq` int(11) DEFAULT NULL COMMENT '排序',
  `url` varchar(255) DEFAULT NULL COMMENT '访问地址',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES ('1', '2019-01-10 15:33:40', null, '1', null, '1', '', '基础设置', '1', '/basicSetting', null);
INSERT INTO `t_sys_menu` VALUES ('2', '2019-01-10 15:33:40', null, '2', null, '1', '', '用户管理', '2', '/usersManager', '1');
INSERT INTO `t_sys_menu` VALUES ('3', '2019-01-10 15:33:40', null, '3', null, '1', 'get', '用户列表', '3', '/auth/**', '2');
