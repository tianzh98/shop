/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : shop

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 20/04/2022 22:37:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for album
-- ----------------------------
DROP TABLE IF EXISTS `album`;
CREATE TABLE `album`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cover_pic` longblob NULL,
  `pic_count` int NULL DEFAULT NULL,
  `sort` int NULL DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '相册表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of album
-- ----------------------------

-- ----------------------------
-- Table structure for album_pic
-- ----------------------------
DROP TABLE IF EXISTS `album_pic`;
CREATE TABLE `album_pic`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `album_id` bigint NULL DEFAULT NULL,
  `pic` longblob NULL COMMENT '图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '画册图片表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of album_pic
-- ----------------------------

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `first_letter` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '首字母',
  `sort` int NULL DEFAULT NULL,
  `factory_status` int NULL DEFAULT NULL COMMENT '是否为品牌制造商：0->不是；1->是',
  `show_status` int NULL DEFAULT NULL,
  `product_count` int NULL DEFAULT NULL COMMENT '产品数量',
  `product_comment_count` int NULL DEFAULT NULL COMMENT '产品评论数量',
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌logo',
  `big_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专区大图',
  `brand_story` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '品牌故事',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES (1, '万和', 'W', 0, 1, 1, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg(5).jpg', '', 'Victoria\'s Secret的故事');
INSERT INTO `brand` VALUES (2, '三星', 'S', 100, 1, 1, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg (1).jpg', NULL, '三星的故事');
INSERT INTO `brand` VALUES (3, '华为', 'H', 100, 1, 0, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg (2).jpg', NULL, 'Victoria\'s Secret的故事');
INSERT INTO `brand` VALUES (4, '格力', 'G', 30, 1, 0, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg (3).jpg', NULL, 'Victoria\'s Secret的故事');
INSERT INTO `brand` VALUES (5, '方太', 'F', 20, 1, 0, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg (4).jpg', NULL, 'Victoria\'s Secret的故事');
INSERT INTO `brand` VALUES (6, '小米', 'M', 500, 1, 1, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180518/5a912944N474afb7a.png', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180518/5afd7778Nf7800b75.jpg', '小米手机的故事');
INSERT INTO `brand` VALUES (21, 'OPPO', 'O', 0, 1, 1, 88, 500, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg(6).jpg', '', 'string');
INSERT INTO `brand` VALUES (49, '七匹狼', 'S', 200, 1, 1, 77, 400, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180518/1522738681.jpg', NULL, 'BOOB的故事');
INSERT INTO `brand` VALUES (50, '海澜之家', 'H', 200, 1, 1, 66, 300, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/LOGO1024.png', '', '海澜之家的故事');
INSERT INTO `brand` VALUES (51, '苹果', 'A', 200, 1, 1, 55, 200, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', NULL, '苹果的故事');
INSERT INTO `brand` VALUES (58, 'NIKE', 'N', 0, 1, 1, 33, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/timg (51).jpg', '', 'NIKE的故事');

-- ----------------------------
-- Table structure for cart_item
-- ----------------------------
DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NULL DEFAULT NULL,
  `product_sku_id` bigint NULL DEFAULT NULL,
  `member_id` bigint NULL DEFAULT NULL,
  `quantity` int NULL DEFAULT NULL COMMENT '购买数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '添加到购物车的价格',
  `product_pic` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品主图',
  `product_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `product_sub_title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品副标题（卖点）',
  `product_sku_code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku条码',
  `member_nickname` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员昵称',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` int NULL DEFAULT 0 COMMENT '是否删除',
  `product_category_id` bigint NULL DEFAULT NULL COMMENT '商品分类',
  `product_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_sn` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_attr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品销售属性:[{\"key\":\"颜色\",\"value\":\"颜色\"},{\"key\":\"容量\",\"value\":\"4G\"}]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cart_item
-- ----------------------------

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `member_id` bigint NOT NULL,
  `coupon_id` bigint NULL DEFAULT NULL,
  `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '提交时间',
  `member_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户帐号',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单总金额',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '应付金额（实际支付金额）',
  `freight_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
  `integration_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠券抵扣金额',
  `discount_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '管理员后台调整订单使用的折扣金额',
  `pay_type` int NULL DEFAULT NULL COMMENT '支付方式：0->未支付；1->支付宝；2->微信',
  `source_type` int NULL DEFAULT NULL COMMENT '订单来源：0->PC订单；1->app订单',
  `status` int NULL DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
  `order_type` int NULL DEFAULT NULL COMMENT '订单类型：0->正常订单；1->秒杀订单',
  `delivery_company` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `auto_confirm_day` int NULL DEFAULT NULL COMMENT '自动确认时间（天）',
  `integration` int NULL DEFAULT NULL COMMENT '可以获得的积分',
  `growth` int NULL DEFAULT NULL COMMENT '可以活动的成长值',
  `promotion_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动信息',
  `bill_type` int NULL DEFAULT NULL COMMENT '发票类型：0->不开发票；1->电子发票；2->纸质发票',
  `bill_header` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  `receiver_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `confirm_status` int NULL DEFAULT NULL COMMENT '确认收货状态：0->未确认；1->已确认',
  `delete_status` int NOT NULL DEFAULT 0 COMMENT '删除状态：0->未删除；1->已删除',
  `use_integration` int NULL DEFAULT NULL COMMENT '下单时使用的积分',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime NULL DEFAULT NULL COMMENT '评价时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (12, 1, 2, '201809150101000001', '2018-09-15 12:24:27', 'test', 18732.00, 16377.75, 20.00, 2344.25, 0.00, 10.00, 10.00, 0, 1, 4, 0, '', '', 15, 13284, 13284, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '江苏省', '常州市', '天宁区', '东晓街道', '111', 0, 0, NULL, NULL, NULL, NULL, NULL, '2019-11-09 16:50:28');
INSERT INTO `order` VALUES (13, 1, 2, '201809150102000002', '2018-09-15 14:24:29', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 1, 1, 1, 0, '', '', 15, 13284, 13284, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, 1000, '2018-10-11 14:04:19', NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (14, 1, 2, '201809130101000001', '2018-09-13 16:57:40', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 2, 1, 2, 0, '顺丰快递', '201707196398345', 15, 13284, 13284, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, NULL, '2018-10-13 13:44:04', '2018-10-16 13:43:41', NULL, NULL, NULL);
INSERT INTO `order` VALUES (15, 1, 2, '201809130102000002', '2018-09-13 17:03:00', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 1, 1, 3, 0, '顺丰快递', '201707196398346', 15, 13284, 13284, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 1, 0, NULL, '2018-10-13 13:44:54', '2018-10-16 13:45:01', '2018-10-18 14:05:31', NULL, NULL);
INSERT INTO `order` VALUES (16, 1, 2, '201809140101000001', '2018-09-14 16:16:16', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 2, 1, 4, 0, NULL, NULL, 15, 13284, 13284, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (17, 1, 2, '201809150101000003', '2018-09-15 12:24:27', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 0, 1, 4, 0, '顺丰快递', '201707196398345', 15, NULL, NULL, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, NULL, NULL, '2018-10-12 14:01:28', NULL, NULL, NULL);
INSERT INTO `order` VALUES (18, 1, 2, '201809150102000004', '2018-09-15 14:24:29', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 1, 1, 1, 0, '圆通快递', 'xx', 15, NULL, NULL, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, 1000, NULL, '2018-10-16 14:42:17', NULL, NULL, NULL);
INSERT INTO `order` VALUES (19, 1, 2, '201809130101000003', '2018-09-13 16:57:40', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 2, 1, 2, 0, NULL, NULL, 15, NULL, NULL, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (20, 1, 2, '201809130102000004', '2018-09-13 17:03:00', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 1, 1, 3, 0, NULL, NULL, 15, NULL, NULL, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (21, 1, 2, '201809140101000002', '2018-09-14 16:16:16', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 2, 1, 4, 0, NULL, NULL, 15, 18682, 18682, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (22, 1, 2, '201809150101000005', '2018-09-15 12:24:27', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 0, 1, 4, 0, '顺丰快递', '201707196398345', 15, 0, 0, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, NULL, NULL, '2018-10-12 14:01:28', NULL, NULL, NULL);
INSERT INTO `order` VALUES (23, 1, 2, '201809150102000006', '2018-09-15 14:24:29', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 1, 1, 1, 0, '顺丰快递', 'xxx', 15, 0, 0, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, 1000, NULL, '2018-10-16 14:41:28', NULL, NULL, NULL);
INSERT INTO `order` VALUES (24, 1, 2, '201809130101000005', '2018-09-13 16:57:40', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 2, 1, 2, 0, NULL, NULL, 15, 18682, 18682, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (25, 1, 2, '201809130102000006', '2018-09-13 17:03:00', 'test', 18732.00, 16377.75, 10.00, 2344.25, 0.00, 10.00, 5.00, 1, 1, 4, 0, NULL, NULL, 15, 18682, 18682, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨22', '18033441849', '518000', '北京市', '北京城区', '东城区', '东城街道', 'xxx', 0, 0, NULL, NULL, NULL, NULL, NULL, '2018-10-30 15:08:31');
INSERT INTO `order` VALUES (26, 1, 2, '201809140101000003', '2018-09-14 16:16:16', 'test', 18732.00, 16377.75, 0.00, 2344.25, 0.00, 10.00, 0.00, 2, 1, 4, 0, NULL, NULL, 15, 18682, 18682, '单品促销,打折优惠：满3件，打7.50折,满减优惠：满1000.00元，减120.00元,满减优惠：满1000.00元，减120.00元,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '福田区', '东晓街道', NULL, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (27, 1, NULL, '202002250100000001', '2020-02-25 15:59:20', 'test', 540.00, 540.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0, 1, 0, 0, NULL, NULL, NULL, 0, 0, '无优惠,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '南山区', '科兴科学园', NULL, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (28, 1, NULL, '202002250100000002', '2020-02-25 16:05:47', 'test', 540.00, 540.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0, 1, 0, 0, NULL, NULL, NULL, 0, 0, '无优惠,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '南山区', '科兴科学园', NULL, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (29, 1, NULL, '202002250100000003', '2020-02-25 16:07:58', 'test', 540.00, 540.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0, 1, 0, 0, NULL, NULL, NULL, 0, 0, '无优惠,无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '南山区', '科兴科学园', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `order` VALUES (30, 1, NULL, '202002250100000004', '2020-02-25 16:50:13', 'test', 240.00, 240.00, 20.00, 0.00, 0.00, 0.00, 10.00, 0, 1, 2, 0, '顺丰快递', '12333333', NULL, 0, 0, '无优惠', NULL, NULL, NULL, NULL, NULL, '大梨', '18033441849', '518000', '广东省', '深圳市', '南山区', '科兴科学园', NULL, 0, 0, NULL, '2020-02-25 16:53:29', '2020-02-25 16:54:03', NULL, NULL, '2020-02-25 16:52:51');

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单id',
  `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `product_id` bigint NULL DEFAULT NULL,
  `product_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售价格',
  `product_quantity` int NULL DEFAULT NULL COMMENT '购买数量',
  `product_sku_id` bigint NULL DEFAULT NULL COMMENT '商品sku编号',
  `product_sku_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku条码',
  `product_category_id` bigint NULL DEFAULT NULL COMMENT '商品分类id',
  `promotion_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品促销名称',
  `promotion_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品促销分解金额',
  `coupon_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `integration_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '积分优惠分解金额',
  `real_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
  `gift_integration` int NULL DEFAULT 0,
  `gift_growth` int NULL DEFAULT 0,
  `product_attr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品销售属性:[{\"key\":\"颜色\",\"value\":\"颜色\"},{\"key\":\"容量\",\"value\":\"4G\"}]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单中所包含的商品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (21, 12, '201809150101000001', 26, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg', '华为 HUAWEI P20', '华为', '6946605', 3788.00, 1, 90, '201806070026001', 19, '单品促销', 200.00, 2.02, 0.00, 3585.98, 3788, 3788, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (22, 12, '201809150101000001', 27, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg', '小米8', '小米', '7437788', 2699.00, 3, 98, '201808270027001', 19, '打折优惠：满3件，打7.50折', 674.75, 1.44, 0.00, 2022.81, 2699, 2699, '[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (23, 12, '201809150101000001', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 649.00, 1, 102, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 57.60, 0.35, 0.00, 591.05, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (24, 12, '201809150101000001', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 699.00, 1, 103, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 62.40, 0.37, 0.00, 636.23, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (25, 12, '201809150101000001', 29, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5acc5248N6a5f81cd.jpg', 'Apple iPhone 8 Plus', '苹果', '7437799', 5499.00, 1, 106, '201808270029001', 19, '无优惠', 0.00, 2.94, 0.00, 5496.06, 5499, 5499, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (26, 13, '201809150102000002', 26, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg', '华为 HUAWEI P20', '华为', '6946605', 3788.00, 1, 90, '201806070026001', 19, '单品促销', 200.00, 2.02, 0.00, 3585.98, 3788, 3788, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (27, 13, '201809150102000002', 27, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg', '小米8', '小米', '7437788', 2699.00, 3, 98, '201808270027001', 19, '打折优惠：满3件，打7.50折', 674.75, 1.44, 0.00, 2022.81, 2699, 2699, '[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (28, 13, '201809150102000002', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 649.00, 1, 102, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 57.60, 0.35, 0.00, 591.05, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (29, 13, '201809150102000002', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 699.00, 1, 103, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 62.40, 0.37, 0.00, 636.23, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (30, 13, '201809150102000002', 29, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5acc5248N6a5f81cd.jpg', 'Apple iPhone 8 Plus', '苹果', '7437799', 5499.00, 1, 106, '201808270029001', 19, '无优惠', 0.00, 2.94, 0.00, 5496.06, 5499, 5499, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (31, 14, '201809130101000001', 26, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg', '华为 HUAWEI P20', '华为', '6946605', 3788.00, 1, 90, '201806070026001', 19, '单品促销', 200.00, 2.02, 0.00, 3585.98, 3788, 3788, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (32, 14, '201809130101000001', 27, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg', '小米8', '小米', '7437788', 2699.00, 3, 98, '201808270027001', 19, '打折优惠：满3件，打7.50折', 674.75, 1.44, 0.00, 2022.81, 2699, 2699, '[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (33, 14, '201809130101000001', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 649.00, 1, 102, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 57.60, 0.35, 0.00, 591.05, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (34, 14, '201809130101000001', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 699.00, 1, 103, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 62.40, 0.37, 0.00, 636.23, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (35, 14, '201809130101000001', 29, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5acc5248N6a5f81cd.jpg', 'Apple iPhone 8 Plus', '苹果', '7437799', 5499.00, 1, 106, '201808270029001', 19, '无优惠', 0.00, 2.94, 0.00, 5496.06, 5499, 5499, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (36, 15, '201809130101000001', 26, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg', '华为 HUAWEI P20', '华为', '6946605', 3788.00, 1, 90, '201806070026001', 19, '单品促销', 200.00, 2.02, 0.00, 3585.98, 3788, 3788, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (37, 15, '201809130101000001', 27, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg', '小米8', '小米', '7437788', 2699.00, 3, 98, '201808270027001', 19, '打折优惠：满3件，打7.50折', 674.75, 1.44, 0.00, 2022.81, 2699, 2699, '[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (38, 15, '201809130101000001', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 649.00, 1, 102, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 57.60, 0.35, 0.00, 591.05, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (39, 15, '201809130101000001', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 699.00, 1, 103, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 62.40, 0.37, 0.00, 636.23, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (40, 15, '201809130101000001', 29, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5acc5248N6a5f81cd.jpg', 'Apple iPhone 8 Plus', '苹果', '7437799', 5499.00, 1, 106, '201808270029001', 19, '无优惠', 0.00, 2.94, 0.00, 5496.06, 5499, 5499, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (41, 16, '201809140101000001', 26, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg', '华为 HUAWEI P20', '华为', '6946605', 3788.00, 1, 90, '201806070026001', 19, '单品促销', 200.00, 2.02, 0.00, 3585.98, 3788, 3788, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (42, 16, '201809140101000001', 27, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg', '小米8', '小米', '7437788', 2699.00, 3, 98, '201808270027001', 19, '打折优惠：满3件，打7.50折', 674.75, 1.44, 0.00, 2022.81, 2699, 2699, '[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (43, 16, '201809140101000001', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 649.00, 1, 102, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 57.60, 0.35, 0.00, 591.05, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `order_item` VALUES (44, 16, '201809140101000001', 28, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '红米5A', '小米', '7437789', 699.00, 1, 103, '201808270028001', 19, '满减优惠：满1000.00元，减120.00元', 62.40, 0.37, 0.00, 636.23, 649, 649, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (45, 16, '201809140101000001', 29, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5acc5248N6a5f81cd.jpg', 'Apple iPhone 8 Plus', '苹果', '7437799', 5499.00, 1, 106, '201808270029001', 19, '无优惠', 0.00, 2.94, 0.00, 5496.06, 5499, 5499, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `order_item` VALUES (46, 27, '202002250100000001', 36, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg', '耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码', 'NIKE', '6799345', 100.00, 3, 163, '202002210036001', 29, '无优惠', 0.00, 0.00, 0.00, 100.00, 0, 0, NULL);
INSERT INTO `order_item` VALUES (47, 27, '202002250100000001', 36, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg', '耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码', 'NIKE', '6799345', 120.00, 2, 164, '202002210036001', 29, '无优惠', 0.00, 0.00, 0.00, 120.00, 0, 0, NULL);
INSERT INTO `order_item` VALUES (48, 28, '202002250100000002', 36, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg', '耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码', 'NIKE', '6799345', 100.00, 3, 163, '202002210036001', 29, '无优惠', 0.00, 0.00, 0.00, 100.00, 0, 0, NULL);
INSERT INTO `order_item` VALUES (49, 28, '202002250100000002', 36, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg', '耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码', 'NIKE', '6799345', 120.00, 2, 164, '202002210036001', 29, '无优惠', 0.00, 0.00, 0.00, 120.00, 0, 0, NULL);
INSERT INTO `order_item` VALUES (50, 29, '202002250100000003', 36, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg', '耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码', 'NIKE', '6799345', 100.00, 3, 163, '202002210036001', 29, '无优惠', 0.00, 0.00, 0.00, 100.00, 0, 0, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `order_item` VALUES (51, 29, '202002250100000003', 36, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg', '耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码', 'NIKE', '6799345', 120.00, 2, 164, '202002210036001', 29, '无优惠', 0.00, 0.00, 0.00, 120.00, 0, 0, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `order_item` VALUES (52, 30, '202002250100000004', 36, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg', '耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码', 'NIKE', '6799345', 120.00, 2, 164, '202002210036001', 29, '无优惠', 0.00, 0.00, 0.00, 120.00, 0, 0, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand_id` bigint NULL DEFAULT NULL,
  `product_category_id` bigint NULL DEFAULT NULL,
  `feight_template_id` bigint NULL DEFAULT NULL,
  `product_attribute_category_id` bigint NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '货号',
  `delete_status` int NULL DEFAULT NULL COMMENT '删除状态：0->未删除；1->已删除',
  `publish_status` int NULL DEFAULT NULL COMMENT '上架状态：0->下架；1->上架',
  `new_status` int NULL DEFAULT NULL COMMENT '新品状态:0->不是新品；1->新品',
  `recommand_status` int NULL DEFAULT NULL COMMENT '推荐状态；0->不推荐；1->推荐',
  `verify_status` int NULL DEFAULT NULL COMMENT '审核状态：0->未审核；1->审核通过',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `sale` int NULL DEFAULT NULL COMMENT '销量',
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `promotion_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '促销价格',
  `gift_growth` int NULL DEFAULT 0 COMMENT '赠送的成长值',
  `gift_point` int NULL DEFAULT 0 COMMENT '赠送的积分',
  `use_point_limit` int NULL DEFAULT NULL COMMENT '限制使用的积分数',
  `sub_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品描述',
  `original_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '市场价',
  `stock` int NULL DEFAULT NULL COMMENT '库存',
  `low_stock` int NULL DEFAULT NULL COMMENT '库存预警值',
  `unit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位',
  `weight` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品重量，默认为克',
  `preview_status` int NULL DEFAULT NULL COMMENT '是否为预告商品：0->不是；1->是',
  `service_ids` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮',
  `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `album_pics` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '画册图片，连产品图片限制为5张，以逗号分割',
  `detail_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `detail_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `detail_html` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '产品详情网页内容',
  `detail_mobile_html` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '移动端网页详情',
  `promotion_start_time` datetime NULL DEFAULT NULL COMMENT '促销开始时间',
  `promotion_end_time` datetime NULL DEFAULT NULL COMMENT '促销结束时间',
  `promotion_per_limit` int NULL DEFAULT NULL COMMENT '活动限购数量',
  `promotion_type` int NULL DEFAULT NULL COMMENT '促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购',
  `brand_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌名称',
  `product_category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 49, 7, 0, 0, '银色星芒刺绣网纱底裤', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 1, 1, 1, 1, 100, 0, 100.00, NULL, 0, 100, NULL, '111', '111', 120.00, 100, 20, '件', 1000.00, 0, NULL, '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', NULL, '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', NULL, NULL, NULL, 0, '七匹狼', '外套');
INSERT INTO `product` VALUES (2, 49, 7, 0, 0, '银色星芒刺绣网纱底裤2', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86578', 1, 1, 1, 1, 1, 1, 0, 100.00, NULL, 0, 100, NULL, '111', '111', 120.00, 100, 20, '件', 1000.00, 0, NULL, '银色星芒刺绣网纱底裤2', '银色星芒刺绣网纱底裤', NULL, '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '<p>银色星芒刺绣网纱底裤</p>', '<p>银色星芒刺绣网纱底裤</p>', NULL, NULL, NULL, 0, '七匹狼', '外套');
INSERT INTO `product` VALUES (3, 1, 7, 0, 0, '银色星芒刺绣网纱底裤3', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86579', 1, 1, 1, 1, 1, 1, 0, 100.00, NULL, 0, 100, NULL, '111', '111', 120.00, 100, 20, '件', 1000.00, 0, NULL, '银色星芒刺绣网纱底裤3', '银色星芒刺绣网纱底裤', NULL, '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', NULL, NULL, NULL, 0, '万和', '外套');
INSERT INTO `product` VALUES (4, 1, 7, 0, 0, '银色星芒刺绣网纱底裤4', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86580', 1, 1, 1, 1, 1, 1, 0, 100.00, NULL, 0, 100, NULL, '111', '111', 120.00, 100, 20, '件', 1000.00, 0, NULL, '银色星芒刺绣网纱底裤4', '银色星芒刺绣网纱底裤', NULL, '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', NULL, NULL, NULL, 0, '万和', '外套');
INSERT INTO `product` VALUES (5, 1, 7, 0, 0, '银色星芒刺绣网纱底裤5', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86581', 1, 0, 1, 1, 1, 1, 0, 100.00, NULL, 0, 100, NULL, '111', '111', 120.00, 100, 20, '件', 1000.00, 0, NULL, '银色星芒刺绣网纱底裤5', '银色星芒刺绣网纱底裤', NULL, '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', NULL, NULL, NULL, 0, '万和', '外套');
INSERT INTO `product` VALUES (6, 1, 7, 0, 0, '银色星芒刺绣网纱底裤6', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86582', 1, 1, 1, 1, 1, 1, 0, 100.00, NULL, 0, 100, NULL, '111', '111', 120.00, 100, 20, '件', 1000.00, 0, NULL, '银色星芒刺绣网纱底裤6', '银色星芒刺绣网纱底裤', NULL, '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', '银色星芒刺绣网纱底裤', NULL, NULL, NULL, 0, '万和', '外套');
INSERT INTO `product` VALUES (7, 1, 7, 0, 1, '女式超柔软拉毛运动开衫', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 0, 0, 0, 0, 0, 0, 249.00, 0.00, 0, 100, 0, '匠心剪裁，垂感质地', '匠心剪裁，垂感质地', 299.00, 100, 0, '件', 0.00, 0, 'string', '女式超柔软拉毛运动开衫', 'string', 'string', 'string', 'string', 'string', 'string', '2018-04-26 10:41:03', '2018-04-26 10:41:03', 0, 0, '万和', '外套');
INSERT INTO `product` VALUES (8, 1, 7, 0, 1, '女式超柔软拉毛运动开衫1', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 0, 0, 0, 0, 0, 0, 249.00, 0.00, 0, 100, 0, '匠心剪裁，垂感质地', '匠心剪裁，垂感质地', 299.00, 100, 0, '件', 0.00, 0, 'string', '女式超柔软拉毛运动开衫', 'string', 'string', 'string', 'string', 'string', 'string', '2018-04-26 10:41:03', '2018-04-26 10:41:03', 0, 0, '万和', '外套');
INSERT INTO `product` VALUES (9, 1, 7, 0, 1, '女式超柔软拉毛运动开衫1', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 0, 0, 0, 0, 0, 0, 249.00, 0.00, 0, 100, 0, '匠心剪裁，垂感质地', '匠心剪裁，垂感质地', 299.00, 100, 0, '件', 0.00, 0, 'string', '女式超柔软拉毛运动开衫', 'string', 'string', 'string', 'string', 'string', 'string', '2018-04-26 10:41:03', '2018-04-26 10:41:03', 0, 0, '万和', '外套');
INSERT INTO `product` VALUES (10, 1, 7, 0, 1, '女式超柔软拉毛运动开衫1', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 0, 0, 0, 0, 0, 0, 249.00, 0.00, 0, 100, 0, '匠心剪裁，垂感质地', '匠心剪裁，垂感质地', 299.00, 100, 0, '件', 0.00, 0, 'string', '女式超柔软拉毛运动开衫', 'string', 'string', 'string', 'string', 'string', 'string', '2018-04-26 10:41:03', '2018-04-26 10:41:03', 0, 0, '万和', '外套');
INSERT INTO `product` VALUES (11, 1, 7, 0, 1, '女式超柔软拉毛运动开衫1', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 1, 0, 1, 0, 0, 0, 249.00, 0.00, 0, 100, 0, '匠心剪裁，垂感质地', '匠心剪裁，垂感质地', 299.00, 100, 0, '件', 0.00, 0, 'string', '女式超柔软拉毛运动开衫', 'string', 'string', 'string', 'string', 'string', 'string', '2018-04-26 10:41:03', '2018-04-26 10:41:03', 0, 0, '万和', '外套');
INSERT INTO `product` VALUES (12, 1, 7, 0, 1, '女式超柔软拉毛运动开衫2', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 1, 0, 1, 0, 0, 0, 249.00, 0.00, 0, 100, 0, '匠心剪裁，垂感质地', '匠心剪裁，垂感质地', 299.00, 100, 0, '件', 0.00, 0, 'string', '女式超柔软拉毛运动开衫', 'string', 'string', 'string', 'string', 'string', 'string', '2018-04-26 10:41:03', '2018-04-26 10:41:03', 0, 0, '万和', '外套');
INSERT INTO `product` VALUES (13, 1, 7, 0, 1, '女式超柔软拉毛运动开衫3', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 1, 0, 1, 0, 0, 0, 249.00, 0.00, 0, 100, 0, '匠心剪裁，垂感质地', '匠心剪裁，垂感质地', 299.00, 100, 0, '件', 0.00, 0, 'string', '女式超柔软拉毛运动开衫', 'string', 'string', 'string', 'string', 'string', 'string', '2018-04-26 10:41:03', '2018-04-26 10:41:03', 0, 0, '万和', '外套');
INSERT INTO `product` VALUES (14, 1, 7, 0, 1, '女式超柔软拉毛运动开衫3', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 0, 0, 1, 0, 0, 0, 249.00, 0.00, 0, 100, 0, '匠心剪裁，垂感质地', '匠心剪裁，垂感质地', 299.00, 100, 0, '件', 0.00, 0, 'string', '女式超柔软拉毛运动开衫', 'string', 'string', 'string', 'string', 'string', 'string', '2018-04-26 10:41:03', '2018-04-26 10:41:03', 0, 0, '万和', '外套');
INSERT INTO `product` VALUES (18, 1, 7, 0, 1, '女式超柔软拉毛运动开衫3', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'No86577', 1, 0, 0, 1, 0, 0, 0, 249.00, 0.00, 0, 100, 0, '匠心剪裁，垂感质地', '匠心剪裁，垂感质地', 299.00, 100, 0, '件', 0.00, 0, 'string', '女式超柔软拉毛运动开衫', 'string', 'string', 'string', 'string', 'string', 'string', '2018-04-26 10:41:03', '2018-04-26 10:41:03', 0, 0, '万和', '外套');
INSERT INTO `product` VALUES (22, 6, 7, 0, 1, 'test', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180604/1522738681.jpg', '', 1, 1, 0, 0, 0, 0, 0, 0.00, NULL, 0, 0, 0, 'test', '', 0.00, 100, 0, '', 0.00, 1, '1,2', '', '', '', '', '', '', '', NULL, NULL, 0, 0, '小米', '外套');
INSERT INTO `product` VALUES (23, 6, 19, 0, 1, '毛衫测试', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180604/1522738681.jpg', 'NO.1098', 1, 1, 1, 1, 0, 0, 0, 99.00, NULL, 99, 99, 1000, '毛衫测试11', 'xxx', 109.00, 100, 0, '件', 1000.00, 1, '1,2,3', '毛衫测试', '毛衫测试', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180604/1522738681.jpg,http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180604/1522738681.jpg', '毛衫测试', '毛衫测试', '<p><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180604/155x54.bmp\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180604/APP登录bg1080.jpg\" width=\"500\" height=\"500\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180604/APP登录界面.jpg\" width=\"500\" height=\"500\" /></p>', '', NULL, NULL, 0, 2, '小米', '手机数码');
INSERT INTO `product` VALUES (24, 6, 7, 0, NULL, 'xxx', '', '', 1, 0, 0, 0, 0, 0, 0, 0.00, NULL, 0, 0, 0, 'xxx', '', 0.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 0, '小米', '外套');
INSERT INTO `product` VALUES (26, 3, 19, 0, 3, '华为 HUAWEI P20 ', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg', '6946605', 0, 1, 1, 1, 0, 100, 0, 3788.00, NULL, 3788, 3788, 0, 'AI智慧全面屏 6GB +64GB 亮黑色 全网通版 移动联通电信4G手机 双卡双待手机 双卡双待', '', 4288.00, 1000, 0, '件', 0.00, 1, '2,3,1', '', '', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ab46a3cN616bdc41.jpg,http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf5fN2522b9dc.jpg', '', '', '<p><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44f1cNf51f3bb0.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44fa8Nfcf71c10.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44fa9N40e78ee0.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad457f4N1c94bdda.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad457f5Nd30de41d.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5b10fb0eN0eb053fb.jpg\" /></p>', '', NULL, NULL, 0, 1, '华为', '手机通讯');
INSERT INTO `product` VALUES (27, 6, 19, 0, 3, '小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg', '7437788', 0, 1, 1, 1, 0, 0, 0, 2699.00, NULL, 2699, 2699, 0, '骁龙845处理器，红外人脸解锁，AI变焦双摄，AI语音助手小米6X低至1299，点击抢购', '小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待', 2699.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '<p><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b2254e8N414e6d3a.jpg\" width=\"500\" /></p>', '', NULL, NULL, 0, 3, '小米', '手机数码');
INSERT INTO `product` VALUES (28, 6, 19, 0, 3, '小米 红米5A 全网通版 3GB+32GB 香槟金 移动联通电信4G手机 双卡双待', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a9d248cN071f4959.jpg', '7437789', 0, 1, 1, 1, 0, 0, 0, 649.00, NULL, 649, 649, 0, '8天超长待机，137g轻巧机身，高通骁龙处理器小米6X低至1299，点击抢购', '', 649.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 4, '小米', '手机数码');
INSERT INTO `product` VALUES (29, 51, 19, 0, 3, 'Apple iPhone 8 Plus 64GB 红色特别版 移动联通电信4G手机', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5acc5248N6a5f81cd.jpg', '7437799', 0, 1, 1, 1, 0, 0, 0, 5499.00, NULL, 5499, 5499, 0, '【限时限量抢购】Apple产品年中狂欢节，好物尽享，美在智慧！速来 >> 勾选[保障服务][原厂保2年]，获得AppleCare+全方位服务计划，原厂延保售后无忧。', '', 5499.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 0, '苹果', '手机数码');
INSERT INTO `product` VALUES (30, 50, 8, 0, 1, 'HLA海澜之家简约动物印花短袖T恤', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5ad83a4fN6ff67ecd.jpg!cc_350x449.jpg', 'HNTBJ2E042A', 0, 1, 1, 1, 0, 0, 0, 98.00, NULL, 0, 0, 0, '2018夏季新品微弹舒适新款短T男生 6月6日-6月20日，满300减30，参与互动赢百元礼券，立即分享赢大奖', '', 98.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 0, '海澜之家', 'T恤');
INSERT INTO `product` VALUES (31, 50, 8, 0, 1, 'HLA海澜之家蓝灰花纹圆领针织布短袖T恤', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5ac98b64N70acd82f.jpg!cc_350x449.jpg', 'HNTBJ2E080A', 0, 1, 0, 0, 0, 0, 0, 98.00, NULL, 0, 0, 0, '2018夏季新品短袖T恤男HNTBJ2E080A 蓝灰花纹80 175/92A/L80A 蓝灰花纹80 175/92A/L', '', 98.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 0, '海澜之家', 'T恤');
INSERT INTO `product` VALUES (32, 50, 8, 0, NULL, 'HLA海澜之家短袖T恤男基础款', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5a51eb88Na4797877.jpg', 'HNTBJ2E153A', 0, 1, 0, 0, 0, 0, 0, 68.00, NULL, 0, 0, 0, 'HLA海澜之家短袖T恤男基础款简约圆领HNTBJ2E153A藏青(F3)175/92A(50)', '', 68.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 0, '海澜之家', 'T恤');
INSERT INTO `product` VALUES (33, 6, 35, 0, NULL, '小米（MI）小米电视4A ', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b02804dN66004d73.jpg', '4609652', 0, 1, 0, 0, 0, 0, 0, 2499.00, NULL, 0, 0, 0, '小米（MI）小米电视4A 55英寸 L55M5-AZ/L55M5-AD 2GB+8GB HDR 4K超高清 人工智能网络液晶平板电视', '', 2499.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 0, '小米', '手机数码');
INSERT INTO `product` VALUES (34, 6, 35, 0, NULL, '小米（MI）小米电视4A 65英寸', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b028530N51eee7d4.jpg', '4609660', 0, 1, 0, 0, 0, 0, 0, 3999.00, NULL, 0, 0, 0, ' L65M5-AZ/L65M5-AD 2GB+8GB HDR 4K超高清 人工智能网络液晶平板电视', '', 3999.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 0, '小米', '手机数码');
INSERT INTO `product` VALUES (35, 58, 29, 0, 11, '耐克NIKE 男子 休闲鞋 ROSHE RUN 运动鞋 511881-010黑色41码', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b235bb9Nf606460b.jpg', '6799342', 0, 1, 0, 0, 0, 0, 0, 369.00, NULL, 0, 0, 0, '耐克NIKE 男子 休闲鞋 ROSHE RUN 运动鞋 511881-010黑色41码', '', 369.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 0, 'NIKE', '男鞋');
INSERT INTO `product` VALUES (36, 58, 29, 0, 11, '耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b19403eN9f0b3cb8.jpg', '6799345', 0, 1, 1, 1, 0, 0, 0, 499.00, NULL, 0, 0, 0, '耐克NIKE 男子 气垫 休闲鞋 AIR MAX 90 ESSENTIAL 运动鞋 AJ1285-101白色41码', '', 499.00, 100, 0, '', 0.00, 0, '', '', '', '', '', '', '', '', NULL, NULL, 0, 0, 'NIKE', '男鞋');

-- ----------------------------
-- Table structure for product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `product_attribute`;
CREATE TABLE `product_attribute`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_attribute_category_id` bigint NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `select_type` int NULL DEFAULT NULL COMMENT '属性选择类型：0->唯一；1->单选；2->多选',
  `input_type` int NULL DEFAULT NULL COMMENT '属性录入方式：0->手工录入；1->从列表中选取',
  `input_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '可选值列表，以逗号隔开',
  `sort` int NULL DEFAULT NULL COMMENT '排序字段：最高的可以单独上传图片',
  `filter_type` int NULL DEFAULT NULL COMMENT '分类筛选样式：1->普通；1->颜色',
  `search_type` int NULL DEFAULT NULL COMMENT '检索类型；0->不需要进行检索；1->关键字检索；2->范围检索',
  `related_status` int NULL DEFAULT NULL COMMENT '相同属性产品是否关联；0->不关联；1->关联',
  `hand_add_status` int NULL DEFAULT NULL COMMENT '是否支持手动新增；0->不支持；1->支持',
  `type` int NULL DEFAULT NULL COMMENT '属性的类型；0->规格；1->参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品属性参数表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_attribute
-- ----------------------------
INSERT INTO `product_attribute` VALUES (1, 1, '尺寸', 2, 1, 'M,X,XL,2XL,3XL,4XL', 0, 0, 0, 0, 0, 0);
INSERT INTO `product_attribute` VALUES (7, 1, '颜色', 2, 1, '黑色,红色,白色,粉色', 100, 0, 0, 0, 1, 0);
INSERT INTO `product_attribute` VALUES (13, 0, '上市年份', 1, 1, '2013年,2014年,2015年,2016年,2017年', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (14, 0, '上市年份1', 1, 1, '2013年,2014年,2015年,2016年,2017年', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (15, 0, '上市年份2', 1, 1, '2013年,2014年,2015年,2016年,2017年', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (16, 0, '上市年份3', 1, 1, '2013年,2014年,2015年,2016年,2017年', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (17, 0, '上市年份4', 1, 1, '2013年,2014年,2015年,2016年,2017年', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (18, 0, '上市年份5', 1, 1, '2013年,2014年,2015年,2016年,2017年', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (19, 0, '适用对象', 1, 1, '青年女性,中年女性', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (20, 0, '适用对象1', 2, 1, '青年女性,中年女性', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (21, 0, '适用对象3', 2, 1, '青年女性,中年女性', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (24, 1, '商品编号', 1, 0, '', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (25, 1, '适用季节', 1, 1, '春季,夏季,秋季,冬季', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (32, 2, '适用人群', 0, 1, '老年,青年,中年', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (33, 2, '风格', 0, 1, '嘻哈风格,基础大众,商务正装', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (35, 2, '颜色', 0, 0, '', 100, 0, 0, 0, 1, 0);
INSERT INTO `product_attribute` VALUES (37, 1, '适用人群', 1, 1, '儿童,青年,中年,老年', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (38, 1, '上市时间', 1, 1, '2017年秋,2017年冬,2018年春,2018年夏', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (39, 1, '袖长', 1, 1, '短袖,长袖,中袖', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (40, 2, '尺码', 0, 1, '29,30,31,32,33,34', 0, 0, 0, 0, 0, 0);
INSERT INTO `product_attribute` VALUES (41, 2, '适用场景', 0, 1, '居家,运动,正装', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (42, 2, '上市时间', 0, 1, '2018年春,2018年夏', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (43, 3, '颜色', 0, 0, '', 100, 0, 0, 0, 1, 0);
INSERT INTO `product_attribute` VALUES (44, 3, '容量', 0, 1, '16G,32G,64G,128G', 0, 0, 0, 0, 0, 0);
INSERT INTO `product_attribute` VALUES (45, 3, '屏幕尺寸', 0, 0, '', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (46, 3, '网络', 0, 1, '3G,4G', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (47, 3, '系统', 0, 1, 'Android,IOS', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (48, 3, '电池容量', 0, 0, '', 0, 0, 0, 0, 0, 1);
INSERT INTO `product_attribute` VALUES (49, 11, '颜色', 0, 1, '红色,蓝色,绿色', 0, 1, 0, 0, 0, 0);
INSERT INTO `product_attribute` VALUES (50, 11, '尺寸', 0, 1, '38,39,40', 0, 0, 0, 0, 0, 0);
INSERT INTO `product_attribute` VALUES (51, 11, '风格', 0, 1, '夏季,秋季', 0, 0, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for product_attribute_category
-- ----------------------------
DROP TABLE IF EXISTS `product_attribute_category`;
CREATE TABLE `product_attribute_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attribute_count` int NULL DEFAULT 0 COMMENT '属性数量',
  `param_count` int NULL DEFAULT 0 COMMENT '参数数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品属性分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_attribute_category
-- ----------------------------
INSERT INTO `product_attribute_category` VALUES (1, '服装-T恤', 2, 5);
INSERT INTO `product_attribute_category` VALUES (2, '服装-裤装', 2, 4);
INSERT INTO `product_attribute_category` VALUES (3, '手机数码-手机通讯', 2, 4);
INSERT INTO `product_attribute_category` VALUES (4, '配件', 0, 0);
INSERT INTO `product_attribute_category` VALUES (5, '居家', 0, 0);
INSERT INTO `product_attribute_category` VALUES (6, '洗护', 0, 0);
INSERT INTO `product_attribute_category` VALUES (10, '测试分类', 0, 0);
INSERT INTO `product_attribute_category` VALUES (11, '服装-鞋帽', 3, 0);

-- ----------------------------
-- Table structure for product_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `product_attribute_value`;
CREATE TABLE `product_attribute_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NULL DEFAULT NULL,
  `product_attribute_id` bigint NULL DEFAULT NULL,
  `value` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 233 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储产品参数信息的表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_attribute_value
-- ----------------------------
INSERT INTO `product_attribute_value` VALUES (1, 9, 1, 'X');
INSERT INTO `product_attribute_value` VALUES (2, 10, 1, 'X');
INSERT INTO `product_attribute_value` VALUES (3, 11, 1, 'X');
INSERT INTO `product_attribute_value` VALUES (4, 12, 1, 'X');
INSERT INTO `product_attribute_value` VALUES (5, 13, 1, 'X');
INSERT INTO `product_attribute_value` VALUES (6, 14, 1, 'X');
INSERT INTO `product_attribute_value` VALUES (7, 18, 1, 'X');
INSERT INTO `product_attribute_value` VALUES (8, 7, 1, 'X');
INSERT INTO `product_attribute_value` VALUES (9, 7, 1, 'XL');
INSERT INTO `product_attribute_value` VALUES (10, 7, 1, 'XXL');
INSERT INTO `product_attribute_value` VALUES (11, 22, 7, 'x,xx');
INSERT INTO `product_attribute_value` VALUES (12, 22, 24, 'no110');
INSERT INTO `product_attribute_value` VALUES (13, 22, 25, '春季');
INSERT INTO `product_attribute_value` VALUES (14, 22, 37, '青年');
INSERT INTO `product_attribute_value` VALUES (15, 22, 38, '2018年春');
INSERT INTO `product_attribute_value` VALUES (16, 22, 39, '长袖');
INSERT INTO `product_attribute_value` VALUES (124, 23, 7, '米白色,浅黄色');
INSERT INTO `product_attribute_value` VALUES (125, 23, 24, 'no1098');
INSERT INTO `product_attribute_value` VALUES (126, 23, 25, '春季');
INSERT INTO `product_attribute_value` VALUES (127, 23, 37, '青年');
INSERT INTO `product_attribute_value` VALUES (128, 23, 38, '2018年春');
INSERT INTO `product_attribute_value` VALUES (129, 23, 39, '长袖');
INSERT INTO `product_attribute_value` VALUES (130, 1, 13, NULL);
INSERT INTO `product_attribute_value` VALUES (131, 1, 14, NULL);
INSERT INTO `product_attribute_value` VALUES (132, 1, 15, NULL);
INSERT INTO `product_attribute_value` VALUES (133, 1, 16, NULL);
INSERT INTO `product_attribute_value` VALUES (134, 1, 17, NULL);
INSERT INTO `product_attribute_value` VALUES (135, 1, 18, NULL);
INSERT INTO `product_attribute_value` VALUES (136, 1, 19, NULL);
INSERT INTO `product_attribute_value` VALUES (137, 1, 20, NULL);
INSERT INTO `product_attribute_value` VALUES (138, 1, 21, NULL);
INSERT INTO `product_attribute_value` VALUES (139, 2, 13, NULL);
INSERT INTO `product_attribute_value` VALUES (140, 2, 14, NULL);
INSERT INTO `product_attribute_value` VALUES (141, 2, 15, NULL);
INSERT INTO `product_attribute_value` VALUES (142, 2, 16, NULL);
INSERT INTO `product_attribute_value` VALUES (143, 2, 17, NULL);
INSERT INTO `product_attribute_value` VALUES (144, 2, 18, NULL);
INSERT INTO `product_attribute_value` VALUES (145, 2, 19, NULL);
INSERT INTO `product_attribute_value` VALUES (146, 2, 20, NULL);
INSERT INTO `product_attribute_value` VALUES (147, 2, 21, NULL);
INSERT INTO `product_attribute_value` VALUES (183, 31, 24, NULL);
INSERT INTO `product_attribute_value` VALUES (184, 31, 25, '夏季');
INSERT INTO `product_attribute_value` VALUES (185, 31, 37, '青年');
INSERT INTO `product_attribute_value` VALUES (186, 31, 38, '2018年夏');
INSERT INTO `product_attribute_value` VALUES (187, 31, 39, '短袖');
INSERT INTO `product_attribute_value` VALUES (198, 30, 24, NULL);
INSERT INTO `product_attribute_value` VALUES (199, 30, 25, '夏季');
INSERT INTO `product_attribute_value` VALUES (200, 30, 37, '青年');
INSERT INTO `product_attribute_value` VALUES (201, 30, 38, '2018年夏');
INSERT INTO `product_attribute_value` VALUES (202, 30, 39, '短袖');
INSERT INTO `product_attribute_value` VALUES (213, 27, 43, '黑色,蓝色');
INSERT INTO `product_attribute_value` VALUES (214, 27, 45, '5.8');
INSERT INTO `product_attribute_value` VALUES (215, 27, 46, '4G');
INSERT INTO `product_attribute_value` VALUES (216, 27, 47, 'Android');
INSERT INTO `product_attribute_value` VALUES (217, 27, 48, '3000ml');
INSERT INTO `product_attribute_value` VALUES (218, 28, 43, '金色,银色');
INSERT INTO `product_attribute_value` VALUES (219, 28, 45, '5.0');
INSERT INTO `product_attribute_value` VALUES (220, 28, 46, '4G');
INSERT INTO `product_attribute_value` VALUES (221, 28, 47, 'Android');
INSERT INTO `product_attribute_value` VALUES (222, 28, 48, '2800ml');
INSERT INTO `product_attribute_value` VALUES (223, 29, 43, '金色,银色');
INSERT INTO `product_attribute_value` VALUES (224, 29, 45, '4.7');
INSERT INTO `product_attribute_value` VALUES (225, 29, 46, '4G');
INSERT INTO `product_attribute_value` VALUES (226, 29, 47, 'IOS');
INSERT INTO `product_attribute_value` VALUES (227, 29, 48, '1960ml');
INSERT INTO `product_attribute_value` VALUES (228, 26, 43, '金色,银色');
INSERT INTO `product_attribute_value` VALUES (229, 26, 45, '5.0');
INSERT INTO `product_attribute_value` VALUES (230, 26, 46, '4G');
INSERT INTO `product_attribute_value` VALUES (231, 26, 47, 'Android');
INSERT INTO `product_attribute_value` VALUES (232, 26, 48, '3000');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '上机分类的编号：0表示一级分类',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int NULL DEFAULT NULL COMMENT '分类级别：1->1级；2->2级',
  `product_count` int NULL DEFAULT NULL,
  `product_unit` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nav_status` int NULL DEFAULT NULL COMMENT '是否显示在导航栏：0->不显示；1->显示',
  `show_status` int NULL DEFAULT NULL COMMENT '显示状态：0->不显示；1->显示',
  `sort` int NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (1, 0, '服装', 1, 100, '件', 1, 1, 1, NULL, '服装', '服装分类');
INSERT INTO `product_category` VALUES (2, 0, '手机数码', 1, 100, '件', 1, 1, 1, NULL, '手机数码', '手机数码');
INSERT INTO `product_category` VALUES (3, 0, '家用电器', 1, 100, '件', 1, 1, 1, NULL, '家用电器', '家用电器');
INSERT INTO `product_category` VALUES (4, 0, '家具家装', 1, 100, '件', 1, 1, 1, NULL, '家具家装', '家具家装');
INSERT INTO `product_category` VALUES (5, 0, '汽车用品', 1, 100, '件', 1, 1, 1, NULL, '汽车用品', '汽车用品');
INSERT INTO `product_category` VALUES (7, 1, '外套', 2, 100, '件', 1, 1, 0, '', '外套', '外套');
INSERT INTO `product_category` VALUES (8, 1, 'T恤', 2, 100, '件', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png', 'T恤', 'T恤');
INSERT INTO `product_category` VALUES (9, 1, '休闲裤', 2, 100, '件', 1, 1, 0, NULL, '休闲裤', '休闲裤');
INSERT INTO `product_category` VALUES (10, 1, '牛仔裤', 2, 100, '件', 1, 1, 0, NULL, '牛仔裤', '牛仔裤');
INSERT INTO `product_category` VALUES (11, 1, '衬衫', 2, 100, '件', 1, 1, 0, NULL, '衬衫', '衬衫分类');
INSERT INTO `product_category` VALUES (13, 12, '家电子分类1', 2, 1, 'string', 0, 1, 0, 'string', 'string', 'string');
INSERT INTO `product_category` VALUES (14, 12, '家电子分类2', 2, 1, 'string', 0, 1, 0, 'string', 'string', 'string');
INSERT INTO `product_category` VALUES (19, 2, '手机通讯', 2, 0, '件', 0, 0, 0, '', '手机通讯', '手机通讯');
INSERT INTO `product_category` VALUES (29, 1, '男鞋', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (30, 2, '手机配件', 2, 0, '', 0, 0, 0, '', '手机配件', '手机配件');
INSERT INTO `product_category` VALUES (31, 2, '摄影摄像', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (32, 2, '影音娱乐', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (33, 2, '数码配件', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (34, 2, '智能设备', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (35, 3, '电视', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (36, 3, '空调', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (37, 3, '洗衣机', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (38, 3, '冰箱', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (39, 3, '厨卫大电', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (40, 3, '厨房小电', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (41, 3, '生活电器', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (42, 3, '个护健康', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (43, 4, '厨房卫浴', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (44, 4, '灯饰照明', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (45, 4, '五金工具', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (46, 4, '卧室家具', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (47, 4, '客厅家具', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (48, 5, '全新整车', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (49, 5, '车载电器', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (50, 5, '维修保养', 2, 0, '', 0, 0, 0, '', '', '');
INSERT INTO `product_category` VALUES (51, 5, '汽车装饰', 2, 0, '', 0, 0, 0, '', '', '');

-- ----------------------------
-- Table structure for product_stock
-- ----------------------------
DROP TABLE IF EXISTS `product_stock`;
CREATE TABLE `product_stock`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NULL DEFAULT NULL,
  `product_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'product编码',
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `stock` int NULL DEFAULT 0 COMMENT '库存',
  `low_stock` int NULL DEFAULT NULL COMMENT '预警库存',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展示图片',
  `sale` int NULL DEFAULT NULL COMMENT '销量',
  `promotion_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单品促销价格',
  `lock_stock` int NULL DEFAULT 0 COMMENT '锁定库存',
  `sp_data` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品销售属性，json格式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 179 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'product的库存' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_stock
-- ----------------------------
INSERT INTO `product_stock` VALUES (98, 27, '201808270027001', 2699.00, 97, NULL, NULL, NULL, NULL, -24, '[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `product_stock` VALUES (99, 27, '201808270027002', 2999.00, 100, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"黑色\"},{\"key\":\"容量\",\"value\":\"64G\"}]');
INSERT INTO `product_stock` VALUES (100, 27, '201808270027003', 2699.00, 100, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `product_stock` VALUES (101, 27, '201808270027004', 2999.00, 100, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"容量\",\"value\":\"64G\"}]');
INSERT INTO `product_stock` VALUES (102, 28, '201808270028001', 649.00, 99, NULL, NULL, NULL, NULL, -8, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `product_stock` VALUES (103, 28, '201808270028002', 699.00, 99, NULL, NULL, NULL, NULL, -8, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `product_stock` VALUES (104, 28, '201808270028003', 649.00, 100, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `product_stock` VALUES (105, 28, '201808270028004', 699.00, 100, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `product_stock` VALUES (106, 29, '201808270029001', 5499.00, 99, NULL, NULL, NULL, NULL, -8, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `product_stock` VALUES (107, 29, '201808270029002', 6299.00, 100, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"64G\"}]');
INSERT INTO `product_stock` VALUES (108, 29, '201808270029003', 5499.00, 100, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `product_stock` VALUES (109, 29, '201808270029004', 6299.00, 100, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"64G\"}]');
INSERT INTO `product_stock` VALUES (110, 26, '201806070026001', 3788.00, 499, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `product_stock` VALUES (111, 26, '201806070026002', 3999.00, 500, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `product_stock` VALUES (112, 26, '201806070026003', 3788.00, 500, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"16G\"}]');
INSERT INTO `product_stock` VALUES (113, 26, '201806070026004', 3999.00, 500, NULL, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"32G\"}]');
INSERT INTO `product_stock` VALUES (163, 36, '202002210036001', 100.00, 100, 25, NULL, NULL, NULL, 9, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `product_stock` VALUES (164, 36, '202002210036002', 120.00, 98, 20, NULL, NULL, NULL, 6, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `product_stock` VALUES (165, 36, '202002210036003', 100.00, 100, 20, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `product_stock` VALUES (166, 36, '202002210036004', 100.00, 100, 20, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `product_stock` VALUES (167, 36, '202002210036005', 100.00, 100, 20, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `product_stock` VALUES (168, 36, '202002210036006', 100.00, 100, 20, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `product_stock` VALUES (169, 36, '202002210036007', 100.00, 100, 20, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `product_stock` VALUES (170, 36, '202002210036008', 100.00, 100, 20, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `product_stock` VALUES (171, 35, '202002250035001', 200.00, 100, 50, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `product_stock` VALUES (172, 35, '202002250035002', 240.00, 100, 50, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `product_stock` VALUES (173, 35, '202002250035003', 200.00, 100, 50, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `product_stock` VALUES (174, 35, '202002250035004', 200.00, 100, 50, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"红色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `product_stock` VALUES (175, 35, '202002250035005', 200.00, 100, 50, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `product_stock` VALUES (176, 35, '202002250035006', 200.00, 100, 50, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"38\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');
INSERT INTO `product_stock` VALUES (177, 35, '202002250035007', 200.00, 100, 50, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"夏季\"}]');
INSERT INTO `product_stock` VALUES (178, 35, '202002250035008', 200.00, 100, 50, NULL, NULL, NULL, 0, '[{\"key\":\"颜色\",\"value\":\"蓝色\"},{\"key\":\"尺寸\",\"value\":\"39\"},{\"key\":\"风格\",\"value\":\"秋季\"}]');

-- ----------------------------
-- Table structure for receive_address
-- ----------------------------
DROP TABLE IF EXISTS `receive_address`;
CREATE TABLE `receive_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人名称',
  `phone_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `default_status` int NULL DEFAULT NULL COMMENT '是否为默认',
  `post_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `province` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
  `city` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `region` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
  `detail_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址(街道)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of receive_address
-- ----------------------------
INSERT INTO `receive_address` VALUES (1, 1, '大梨', '18033441849', 0, '518000', '广东省', '深圳市', '南山区', '科兴科学园');
INSERT INTO `receive_address` VALUES (3, 1, '大梨', '18033441849', 0, '518000', '广东省', '深圳市', '福田区', '清水河街道');
INSERT INTO `receive_address` VALUES (4, 1, '大梨', '18033441849', 1, '518000', '广东省', '深圳市', '福田区', '东晓街道');

-- ----------------------------
-- Table structure for sys_default_table_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_default_table_config`;
CREATE TABLE `sys_default_table_config`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `table_page_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名称',
  `field_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'PO属性名称',
  `show_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'field_name对应前端要展示的列名称',
  `is_show` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否展示',
  `sort_num` int NULL DEFAULT NULL COMMENT '排序',
  `order_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序字段（数据库表字段名）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_default_table_config
-- ----------------------------
INSERT INTO `sys_default_table_config` VALUES (1, '/product/getProductList', 'name', '商品名称', '1', 1, 'name');
INSERT INTO `sys_default_table_config` VALUES (2, '/product/getProductList', 'productSn', '货号', '1', 1, '');
INSERT INTO `sys_default_table_config` VALUES (3, '/product/getProductList', 'description', '商品描述', '1', 1, '');
INSERT INTO `sys_default_table_config` VALUES (4, '/product/getProductList', 'originalPrice', '原价', '1', 1, '');
INSERT INTO `sys_default_table_config` VALUES (5, '/product/getProductList', 'price', '售价', '1', 1, '');
INSERT INTO `sys_default_table_config` VALUES (6, '/product/getProductList', 'brandName', '品牌名称', '1', 1, '');
INSERT INTO `sys_default_table_config` VALUES (7, '/product/getProductCateList', 'name', '分类名称', '1', 1, 'name');
INSERT INTO `sys_default_table_config` VALUES (8, '/product/getProductCateList', 'productUnit', '数量单位', '1', 3, '');
INSERT INTO `sys_default_table_config` VALUES (9, '/product/getProductCateList', 'description', '描述', '1', 4, '');
INSERT INTO `sys_default_table_config` VALUES (10, '/product/getProductCateList', 'levelShow', '分类等级', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (11, '/order/orderList', 'id', '编号', '1', 1, NULL);
INSERT INTO `sys_default_table_config` VALUES (12, '/order/orderList', 'orderSn', '订单编号', '1', 2, 'order_sn');
INSERT INTO `sys_default_table_config` VALUES (13, '/order/orderList', 'createTime', '提交时间', '1', 2, ' ');
INSERT INTO `sys_default_table_config` VALUES (14, '/order/orderList', 'memberUsername', '用户帐号', '1', 2, ' ');
INSERT INTO `sys_default_table_config` VALUES (15, '/order/orderList', 'totalAmount', '订单总金额', '1', 2, ' ');
INSERT INTO `sys_default_table_config` VALUES (16, '/order/orderList', 'payTypeName', '支付方式', '1', 2, ' ');
INSERT INTO `sys_default_table_config` VALUES (17, '/order/orderList', 'statusName', '订单状态', '1', 2, ' ');
INSERT INTO `sys_default_table_config` VALUES (18, '/product/brandList', 'id', '品牌编号', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (19, '/product/brandList', 'name', '品牌名字', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (20, '/product/brandList', 'firstLetter', '品牌首字母', '1', 2, 'first_letter');
INSERT INTO `sys_default_table_config` VALUES (21, '/product/brandList', 'sort', '排序', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (22, '/product/brandList', 'brandStory', '品牌故事', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (23, '/product/productType', 'id', '商品编号', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (24, '/product/productType', 'name', '商品类型名称', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (25, '/product/productType', 'attributeCount', '商品属性数量', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (26, '/product/productType', 'paramCount', '商品参数数量', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (27, '/productType/productTypeParam', 'id', '编号', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (28, '/productType/productTypeParam', 'name', '属性名称', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (29, '/productType/productTypeParam', 'productTypeName', '商品类型', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (30, '/productType/productTypeParam', 'inputList', '可选值列表', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (31, '/productType/productTypeParam', 'selectTypeName', '属性是否可选', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (32, '/address/addressList', 'name', '收货人姓名', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (33, '/address/addressList', 'phoneNumber', '收货人电话号码', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (34, '/address/addressList', 'postCode', '邮政编码', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (35, '/address/addressList', 'province', '省份', '1', 2, 'province');
INSERT INTO `sys_default_table_config` VALUES (36, '/address/addressList', 'city', '城市', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (37, '/address/addressList', 'region', '地区', '1', 2, NULL);
INSERT INTO `sys_default_table_config` VALUES (38, '/address/addressList', 'detailAddress', '具体地址', '1', 2, NULL);

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型（1、目录；2、菜单；3、按钮）',
  `menu_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单code',
  `pid` int NULL DEFAULT NULL COMMENT '父级id',
  `serial_no` decimal(10, 2) NULL DEFAULT NULL COMMENT '排序',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题（目录名称、菜单名称、按钮名称）',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由地址（目录和菜单）',
  `permission` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识（菜单和按钮）',
  `component_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单组件名称',
  `component_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单组件地址',
  `res_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态（0、正常；1、禁用）',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `row_version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES (1, '1', 'home', 0, 1.00, '首页', '/home', NULL, NULL, NULL, '0', 'icon-home', '2022-04-01 22:56:18', '1', '2022-04-01 22:56:21', '1', 1);
INSERT INTO `sys_resource` VALUES (2, '1', 'product', 0, 2.00, '商品', '', NULL, NULL, NULL, '0', 'icon-stage', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_resource` VALUES (3, '2', 'productList', 2, 1.00, '商品列表', '/product/productList', 'Add', 'user-manage', 'admin/user/user-manage', '0', 'user', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_resource` VALUES (5, '2', 'role', 2, 3.00, '商品分类', '/product/productCate', 'role:list', 'role-manage', 'admin/role/role-manage', '0', 'role', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_resource` VALUES (6, '2', 'interface_doc', 2, 4.00, '商品类型', '/product/productType', 'dept:list', 'dept-manage', NULL, '0', 'nested', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_resource` VALUES (7, '2', 'dep', 2, 5.00, '品牌管理', '/product/brandList', 'dept:list', 'dept-manage', 'admin/dept/dept-manage', '0', 'nested', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_resource` VALUES (10, '1', 'order', 0, 3.00, '订单', '/order', NULL, NULL, NULL, '0', 'icon-cost', '2022-04-01 22:56:18', '1', '2022-04-01 22:56:21', '1', 1);
INSERT INTO `sys_resource` VALUES (11, '2', 'order', 10, 3.00, '订单管理', '/order/orderList', NULL, NULL, NULL, '0', '', '2022-04-01 22:56:18', '1', '2022-04-01 22:56:21', '1', 1);
INSERT INTO `sys_resource` VALUES (12, '1', 'address', 0, 4.00, '地址', '/address', NULL, NULL, NULL, '0', 'icon-batch', '2022-04-20 19:26:32', NULL, '2022-04-20 19:26:35', '1', 1);
INSERT INTO `sys_resource` VALUES (13, '2', 'address', 12, 1.00, '地址管理', '/address/addressList', NULL, NULL, NULL, '0', NULL, '2022-04-20 19:26:32', NULL, '2022-04-20 19:26:35', '1', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `role_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_key` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色权限字符串',
  `role_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色状态（0、正常；1、禁用）',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '数据范围（1、全部数据权限；2、自定数据权限；3、本部门数据权限；4、本部门及以下数据权限）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `row_version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', 'ROLE_ADMIN', '0', '1', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_role` VALUES ('2', '普通用户', 'ROLE_NORMAL', '0', '4', NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res`  (
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `res_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `row_version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`role_id`, `res_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和资源关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
INSERT INTO `sys_role_res` VALUES ('1', '1', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '10', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '11', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '12', '2022-04-19 18:32:23', '1', '2022-04-19 18:32:26', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '13', '2022-04-20 19:30:43', NULL, '2022-04-20 19:30:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '2', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '3', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '4', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '5', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '6', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '7', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '8', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);
INSERT INTO `sys_role_res` VALUES ('1', '9', '2022-04-01 22:56:43', '1', '2022-04-01 22:56:46', '1', 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `password` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `real_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别（1:女、 2:男）',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '绑定的邮箱',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '绑定的手机号',
  `avatar_path` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像图片url路径',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '账号状态。0、正常；1、禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `updater` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  `row_version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`id`, `account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '1', '1', 'gll', 'gll', '1', '2022-03-26', '2331122112@zz.com', '13322332213', NULL, '0', '2022-03-26 18:46:11', '1', '2022-03-26 18:46:13', '1', 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `row_version` int NOT NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', NULL, NULL, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
