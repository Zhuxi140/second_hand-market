

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userSn` VARCHAR(255) NOT NULL COMMENT '用户标识号',
  `username` VARCHAR(25) NOT NULL COMMENT '用户名',
  `password` VARCHAR(64) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(25) NULL DEFAULT NULL COMMENT '昵称',
  `email` VARCHAR(64) NULL DEFAULT NULL COMMENT '邮箱',
  `phone` VARCHAR(18) NOT NULL COMMENT '手机号',
  `avatar` VARCHAR(255) NULL DEFAULT NULL COMMENT '头像地址',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态',
  `role` enum('user', 'Merchant') NOT NULL DEFAULT 'user' COMMENT '账号类型(个体用户 商铺用户)',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_usersn` (`userSn`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';


DROP TABLE IF EXISTS `user_order`;
CREATE TABLE `user_order` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `orderSn` VARCHAR(255) NOT NULL COMMENT '订单号',
  `buyer_id` BIGINT UNSIGNED NOT NULL COMMENT '买家id',
  `total_amount` DECIMAL(12, 2) NOT NULL COMMENT '订单金额',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '订单状态(1待付款 2待发货 3待收货 4已完成 5已取消 6已退款)',
  `receive_address_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '收货地址id（可以为null 为null即线下直接交付）',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ordersn` (`orderSn`),
  KEY `idx_buyer_id` (`buyer_id`),
  KEY `idx_receive_address_id` (`receive_address_id`),
  CONSTRAINT `fk_order_buyer` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_address` FOREIGN KEY (`receive_address_id`) REFERENCES `user_address` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';


DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` BIGINT UNSIGNED NOT NULL COMMENT '订单表id',
  `product_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
  `sku_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '商品规格id',
  `seller_id` BIGINT UNSIGNED NOT NULL COMMENT '卖家id',
  `quantity` INT UNSIGNED NOT NULL COMMENT '购买数量',
  `unit_price` DECIMAL(12, 2) NOT NULL COMMENT '成交单价',
  `product_snapshot` JSON NOT NULL COMMENT '商品快照',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_seller_id` (`seller_id`),
  KEY `idx_sku_id` (`sku_id`),
  CONSTRAINT `fk_order_detail_order` FOREIGN KEY (`order_id`) REFERENCES `user_order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_detail_seller` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_detail_sku` FOREIGN KEY (`sku_id`) REFERENCES `product_spec` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';


DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addressSn` VARCHAR(255) NOT NULL COMMENT '地址唯一标识',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户id',
  `consignee` VARCHAR(50) NOT NULL COMMENT '收货人',
  `sex` VARCHAR(2) NULL DEFAULT NULL COMMENT '性别',
  `phone` VARCHAR(18) NOT NULL COMMENT '手机号',
  `province_code` VARCHAR(12) NULL DEFAULT NULL COMMENT '省份编码',
  `province_name` VARCHAR(32) NULL DEFAULT NULL COMMENT '省份名称',
  `city_code` VARCHAR(12) NULL DEFAULT NULL COMMENT '城市编码',
  `city_name` VARCHAR(32) NULL DEFAULT NULL COMMENT '城市名称',
  `district_code` VARCHAR(12) NULL DEFAULT NULL COMMENT '区县编码',
  `district_name` VARCHAR(32) NULL DEFAULT NULL COMMENT '区县名称',
  `detail` VARCHAR(200) NULL DEFAULT NULL COMMENT '详细地址',
  `label` VARCHAR(100) NULL DEFAULT NULL COMMENT '标签',
  `is_default` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否为默认地址（1是 0否）',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  CONSTRAINT `fk_address_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地址表';


DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_sn` VARCHAR(255) NOT NULL COMMENT '商品号',
  `seller_id` BIGINT UNSIGNED NOT NULL COMMENT '卖家id',
  `shop_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '店铺id',
  `title` VARCHAR(50) NOT NULL COMMENT '商品标题（名称）',
  `description` TEXT NULL COMMENT '商品描述',
  `category_id` BIGINT UNSIGNED NOT NULL COMMENT '分类id',
  `price` DECIMAL(12, 2) NOT NULL COMMENT '商品价格',
  `condition` TINYINT UNSIGNED NOT NULL COMMENT '商品成色 (1:全新,2:9成新,3:仅外观受损,4:8成新,5:非二手)',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '商品状态 (1在售 2已预订 3已售出 4审核中)',
  `location` VARCHAR(50) NULL DEFAULT NULL COMMENT '商品所在地区和城市',
  `view_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_sn` (`product_sn`),
  KEY `idx_seller_id` (`seller_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_shop_id` (`shop_id`),
  CONSTRAINT `fk_product_seller` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `product_sort` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';


DROP TABLE IF EXISTS `product_spec`;
CREATE TABLE `product_spec` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sku_sn` VARCHAR(255) NOT NULL COMMENT '规格号',
  `product_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
  `specifications` JSON NOT NULL COMMENT '规格描述',
  `price` DECIMAL(12, 2) NOT NULL COMMENT '该规格的价格',
  `stock` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '库存数量',
  `sku_code` VARCHAR(100) NULL DEFAULT NULL COMMENT 'SKU编码',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sku_sn` (`sku_sn`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `fk_spec_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='规格表';


DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role` enum('admin', 'super_admin') NOT NULL DEFAULT 'admin' COMMENT '账号类型(管理员 超级管理员)',
  `name` VARCHAR(25) NOT NULL COMMENT '姓名',
  `username` VARCHAR(25) NOT NULL COMMENT '用户名',
  `password` VARCHAR(64) NOT NULL COMMENT '密码',
  `phone` VARCHAR(18) NULL DEFAULT NULL COMMENT '手机号',
  `gender` VARCHAR(2) NULL DEFAULT NULL COMMENT '性别',
  `id_number` VARCHAR(18) NULL DEFAULT NULL COMMENT '身份证号',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态 (1正常 2锁定)',
  `create_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '创建者id',
  `update_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '更新者id',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_id_number` (`id_number`),
  KEY `idx_create_id` (`create_id`),
  KEY `idx_update_id` (`update_id`),
  CONSTRAINT `fk_admin_create` FOREIGN KEY (`create_id`) REFERENCES `admin` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_admin_update` FOREIGN KEY (`update_id`) REFERENCES `admin` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';


DROP TABLE IF EXISTS `product_sort`;
CREATE TABLE `product_sort` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `parent_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '父id（用于实现多级分类，NULL表示顶级分类）',
  `icon_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '分类图标链接',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  CONSTRAINT `fk_sort_parent` FOREIGN KEY (`parent_id`) REFERENCES `product_sort` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表';


DROP TABLE IF EXISTS `product_static`;
CREATE TABLE `product_static` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
  `sku_id` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '规格id (可为null)',
  `image_url` VARCHAR(255) NOT NULL COMMENT '图片url',
  `image_type` TINYINT UNSIGNED NOT NULL COMMENT '图片类型 (1封面 2商品详细图 3规格图)',
  `sort_order` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '图片排序 (值越小越靠前)',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_sku_id` (`sku_id`),
  CONSTRAINT `fk_image_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_image_sku` FOREIGN KEY (`sku_id`) REFERENCES `product_spec` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品图片表';


DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `conversation_id` VARCHAR(100) NOT NULL COMMENT '会话标识',
  `sender_id` BIGINT UNSIGNED NOT NULL COMMENT '发送者id',
  `receiver_id` BIGINT UNSIGNED NOT NULL COMMENT '接收者id',
  `content` TEXT NULL COMMENT '消息内容',
  `is_read` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否已读 (0否 1是)',
  `sent_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `idx_conversation_id` (`conversation_id`),
  KEY `idx_sender_id` (`sender_id`),
  KEY `idx_receiver_id` (`receiver_id`),
  CONSTRAINT `fk_chat_sender` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_chat_receiver` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天表';


DROP TABLE IF EXISTS `user_collect`;
CREATE TABLE `user_collect` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户id',
  `product_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`, `product_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `fk_collect_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_collect_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';


DROP TABLE IF EXISTS `user_comment`;
CREATE TABLE `user_comment` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` BIGINT UNSIGNED NOT NULL COMMENT '订单id',
  `reviewer_id` BIGINT UNSIGNED NOT NULL COMMENT '评价者id',
  `reviewee_id` BIGINT UNSIGNED NOT NULL COMMENT '被评价id',
  `rating` TINYINT UNSIGNED NOT NULL COMMENT '评分 (1~5)',
  `comment` TEXT NULL COMMENT '评价内容',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_reviewer_id` (`reviewer_id`),
  KEY `idx_reviewee_id` (`reviewee_id`),
  CONSTRAINT `fk_comment_order` FOREIGN KEY (`order_id`) REFERENCES `user_order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_reviewer` FOREIGN KEY (`reviewer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_reviewee` FOREIGN KEY (`reviewee_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评价与信用表';


DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '店主的用户ID, 关联users.id',
  `shop_name` VARCHAR(64) NOT NULL COMMENT '商铺名称',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '商铺描述',
  `logo_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '商铺logo(头像)',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '商铺状态 (1开店中 2闭店中)',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_shop_name` (`shop_name`),
  KEY `idx_user_id` (`user_id`),
  CONSTRAINT `fk_shop_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店铺表';

SET FOREIGN_KEY_CHECKS = 1;
