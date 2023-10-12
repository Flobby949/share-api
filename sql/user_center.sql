SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `phone` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `roles` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '角色',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '头像地址',
  `bonus` int NOT NULL DEFAULT 300 COMMENT '积分',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1710535621719756801 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分享' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '18962521753', '123123', 'flobby', 'admin', 'https://i2.100024.xyz/2023/10/07/kh58mh.webp', 100, '2023-09-16 06:23:50', '2023-09-16 06:23:50');
INSERT INTO `user` VALUES (2, '18962521111', '123123', 'lavaclone', 'user', 'https://i2.100024.xyz/2023/10/07/kh5a1c.webp', 100, '2023-09-18 12:38:51', '2023-09-18 12:38:51');

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bonus_event_log
-- ----------------------------
DROP TABLE IF EXISTS `bonus_event_log`;
CREATE TABLE `bonus_event_log` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Id',
                                   `user_id` bigint DEFAULT NULL COMMENT '用户id',
                                   `value` int DEFAULT NULL COMMENT '积分操作值',
                                   `event` varchar(20) DEFAULT NULL COMMENT '积分事件（签到、投稿、兑换等）',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `description` varchar(100) DEFAULT NULL COMMENT '描述',
                                   PRIMARY KEY (`id`),
                                   KEY `fk_bonus_event_log_user1_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='积分变更记录表';

-- ----------------------------
-- Records of bonus_event_log
-- ----------------------------
BEGIN;
INSERT INTO `bonus_event_log` (`id`, `user_id`, `value`, `event`, `create_time`, `description`) VALUES (1, 1, -20, 'BUY', '2023-09-26 14:38:33', '兑换分享');
INSERT INTO `bonus_event_log` (`id`, `user_id`, `value`, `event`, `create_time`, `description`) VALUES (2, 2, -5, 'BUY', '2023-09-26 14:44:04', '兑换分享');
INSERT INTO `bonus_event_log` (`id`, `user_id`, `value`, `event`, `create_time`, `description`) VALUES (3, 1, -10, 'BUY', '2023-09-26 14:55:09', '兑换分享');
INSERT INTO `bonus_event_log` (`id`, `user_id`, `value`, `event`, `create_time`, `description`) VALUES (4, 1, -10, 'BUY', '2023-09-26 15:00:49', '兑换分享');
INSERT INTO `bonus_event_log` (`id`, `user_id`, `value`, `event`, `create_time`, `description`) VALUES (5, 2, 50, 'CONTRIBUTE', '2023-09-26 15:02:17', '投稿加积分');
INSERT INTO `bonus_event_log` (`id`, `user_id`, `value`, `event`, `create_time`, `description`) VALUES (6, 2, 50, 'CONTRIBUTE', '2023-09-26 15:04:18', '投稿加积分');
INSERT INTO `bonus_event_log` (`id`, `user_id`, `value`, `event`, `create_time`, `description`) VALUES (7, 2, 50, 'CONTRIBUTE', '2023-09-26 15:04:39', '投稿加积分');
INSERT INTO `bonus_event_log` (`id`, `user_id`, `value`, `event`, `create_time`, `description`) VALUES (8, 1, -10, 'BUY', '2023-09-26 15:04:58', '兑换分享');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;