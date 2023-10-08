/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : content_center

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 08/10/2023 14:00:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                          `content` varchar(255) NOT NULL DEFAULT '' COMMENT '内容',
                          `show_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否显示 0:否 1:是',
                          `create_time` datetime NOT NULL COMMENT '创建时间',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of notice
-- ----------------------------
BEGIN;
INSERT INTO `notice` (`id`, `content`, `show_flag`, `create_time`) VALUES (1, '程序员的启蒙课', 1, '2023-09-29 12:36:49');
INSERT INTO `notice` (`id`, `content`, `show_flag`, `create_time`) VALUES (2, '终身学习者', 0, '2023-10-04 11:16:18');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;