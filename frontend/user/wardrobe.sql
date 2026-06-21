/*
 Navicat Premium Data Transfer

 Source Server         : MySQL80
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : wardrobe

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 23/07/2024 17:41:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `cloth_id` int(0) NULL DEFAULT NULL,
  `cloth_size` varchar(225) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `amount` int(0) NULL DEFAULT NULL,
  `user_id` int(0) NULL DEFAULT NULL,
  `date` varchar(225) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 154 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_cart
-- ----------------------------
INSERT INTO `t_cart` VALUES (1, 1, 'S', 1, 2, '2024-05-22 00:00:00');
INSERT INTO `t_cart` VALUES (2, 2, 'M', 2, 2, '2024-05-22 00:00:00');

-- ----------------------------
-- Table structure for t_clothes
-- ----------------------------
DROP TABLE IF EXISTS `t_clothes`;
CREATE TABLE `t_clothes`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '服装ID',
  `cloth_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '服装名称',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '服装图片',
  `type_id` int(0) NULL DEFAULT NULL COMMENT '服装类别',
  `style` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '服装风格',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '服装价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_clothes
-- ----------------------------
INSERT INTO `t_clothes` VALUES (1, '连衣裙', 'lyq.jpg', 1, '时尚', 299.90);
INSERT INTO `t_clothes` VALUES (2, '衬衫', 'cs.png', 1, '休闲', 199.90);
INSERT INTO `t_clothes` VALUES (3, '遮阳帽', 'zym.png', 2, '休闲', 59.90);
INSERT INTO `t_clothes` VALUES (4, '皮鞋', 'px.jpg', 3, '正式', 599.90);
INSERT INTO `t_clothes` VALUES (5, 'T恤', 'tx.png', 1, '休闲', 120.00);
INSERT INTO `t_clothes` VALUES (6, '马甲', 'mj.png', 1, '时尚', 598.00);
INSERT INTO `t_clothes` VALUES (7, '棒球帽', 'bqm.png', 2, '运动', 99.00);
INSERT INTO `t_clothes` VALUES (10, '运动鞋', '20240723140210_ydx.jpg\r\n', 3, '运动', 166.00);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `clothes_details` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '服装详细信息',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '服装详细信息',
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单价格',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '订单状态（未支付：0、未发货：1、已发货：2、已收货：3）',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单收货地址',
  `time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '提交订单的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (11, '服装编号1，连衣裙S码（299.90）×2', 599.80, '2', 8, '北京市海淀区', '2024-07-22 08:51:41');
INSERT INTO `t_order` VALUES (12, '服装编号4，皮鞋37码（599.90）×4', 2399.60, '0', 8, '北京市海淀区', '2024-07-22 08:51:41');
INSERT INTO `t_order` VALUES (17, '服装编号5，T恤S码（120.00）×1', 120.00, '1', 9, '北京市丰台区', '2024-07-23 14:43:22');

-- ----------------------------
-- Table structure for t_size
-- ----------------------------
DROP TABLE IF EXISTS `t_size`;
CREATE TABLE `t_size`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `size_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `type_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_size
-- ----------------------------
INSERT INTO `t_size` VALUES (1, 'S', 1);
INSERT INTO `t_size` VALUES (2, 'M', 1);
INSERT INTO `t_size` VALUES (3, 'L', 1);
INSERT INTO `t_size` VALUES (4, 'S', 2);
INSERT INTO `t_size` VALUES (5, 'M', 2);
INSERT INTO `t_size` VALUES (6, 'L', 2);
INSERT INTO `t_size` VALUES (7, '36', 3);
INSERT INTO `t_size` VALUES (8, '37', 3);
INSERT INTO `t_size` VALUES (9, '38', 3);
INSERT INTO `t_size` VALUES (10, '39', 3);
INSERT INTO `t_size` VALUES (11, '40', 3);
INSERT INTO `t_size` VALUES (12, '41', 3);
INSERT INTO `t_size` VALUES (13, '42', 3);

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES (1, '衣服');
INSERT INTO `t_type` VALUES (2, '帽子');
INSERT INTO `t_type` VALUES (3, '鞋');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户电话',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户地址',
  `role` int(0) NULL DEFAULT NULL COMMENT '用户角色（普通用户2、管理员1）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', 'admin', '13111111111', '山西省大同市云冈区', 1);
INSERT INTO `t_user` VALUES (8, 'zhangsan', '123123', '13122222222', '北京市昌平区', 2);
INSERT INTO `t_user` VALUES (9, 'lisi', '111111', '13133333333', '北京市丰台区', 2);

SET FOREIGN_KEY_CHECKS = 1;
