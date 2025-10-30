package com.zhuxi.common.shared.constant;

/**
 * @author zhuxi
 */
public class BusinessMessage {

    // -----------用户类-------------------------
    public static final String USER_NOT_EXIST = "用户不存在";
    public static final String USERNAME_IS_EXIST = "用户名已存在";
    public static final String PHONE_IS_EXIST = "手机号已存在";
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String USER_IS_LOCK = "用户被锁定";
    public static final String USERNAME_OR_PASSWORD_ERROR = "账号或者密码错误";
    public static final String USER_DATA_ERROR = "数据异常，请稍后刷新重试";
    public static final String USER_OLD_PASSWORD_ERROR = "旧密码错误";
    public static final String UPDATE_PW_ERROR = "修改密码失败,请稍后重试";
    public static final String REGISTER_ERROR = "注册失败,请稍后重试";
    public static final String UPDATE_INFO_ERROR = "修改失败,请稍后重试";
    public static final String ROLE_NOT_EXIST = "账户数据异常，请稍后重试或联系管理员";
    public static final String USER_ID_IS_NULL = "账户数据异常，请稍后重试或联系管理员";

    // -----------地址类-------------------------
    public static final String ADD_ADDRESS_ERROR = "添加地址失败,请稍后重试";
    public static final String CANCEL_DEFAULT_ADDRESS_ERROR = "取消已有默认地址出错,请稍后重试或手动把已有的默认地址取消";
    public static final String DELETE_ADDRESS_ERROR = "删除失败,请稍后重试";
    public static final String ADDRESS_COUNT_ERROR = "地址数量已达到最大上限（20个）";
    public static final String UPDATE_ADDRESS_ERROR = "修改地址失败,请稍后重试";
    public static final String DEFAULT_ADDRESS_JUST_IS_ONE = "默认地址只能有一个";
    public static final String ALREADY_DEFAULT_ADDRESS = "该地址已经是默认地址";

    // -----------文件类-------------------------
    public static final String FILE_DATA_SAVE_ERROR = "文件信息写入失败，请稍后重试";
    public static final String FILE_DATA_UPDATE_ERROR = "文件信息更新失败，请稍后重试";

    // -----------商品类-------------------------
    public static final String GET_CATEGORY_ERROR = "获取商品分类失败，请稍后重试";
    public static final String SAVE_PRODUCT_ERROR = "发布商品失败，请稍后重试";
    public static final String UPDATE_PRODUCT_ERROR = "修改商品失败，请稍后重试";
    public static final String SAVE_PRODUCT_ERROR_SAVE_DRAFT = "发布商品失败，已为您保存草稿，请稍后重试";
    public static final String GET_SH_CONDITION_ERROR = "获取商品成色失败，请稍后重试";
    public static final String GET_SH_PRODUCT_LIST_ERROR = "获取二手商品列表失败，请稍后重试";
    public static final String PRODUCT_ID_IS_NULL = "当前系统繁忙,情稍后重试或联系管理员";
    public static final String DEL_PRODUCT_ERROR = "删除商品失败，请稍后重试";

}
