package com.zhuxi.common.infrastructure.cache;

/**
 * 为什么集中：避免各处拼接不一致和 TTL 混乱导致脏数据。
 */
public class CacheKeyUtil {
    public static String productDetail(String sn) { return "product:detail:" + sn; }
    public static String productStatics(String sn) { return "product:statics:" + sn; }
    public static String userPermission(String sn) { return "user:perm:" + sn; }
    public static final String NULL = "NULL_VALUE";
    public static final long TTL_SHORT_SEC = 300;
    public static final long TTL_LONG_SEC = 1800;
}