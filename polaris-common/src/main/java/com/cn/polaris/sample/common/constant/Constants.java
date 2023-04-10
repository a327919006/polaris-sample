package com.cn.polaris.sample.common.constant;

/**
 * <p>Title: Constants</p>
 * <p>Description: 常量类</p>
 */
public class Constants {

    private Constants() {

    }

    /* 通用应答码 URC-Universal Response Code */
    /**
     * 应答码：成功
     */
    public static final int CODE_SUCCESS = 0;
    /**
     * 应答码：失败
     */
    public static final int CODE_FAILURE = 1;

    /**
     * 默认页面大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 用户session
     */
    public static final String SESSION_USER = "session_user";

    /**
     * MSG
     */
    public static final String MSG_SUCCESS = "SUCCESS";
    public static final String MSG_FAIL = "FAIL";
    public static final String MSG_CMS_SUCCESS = "操作成功";
    public static final String MSG_READ_CONFIG_ERROR = "读取配置文件错误";
    public static final String MSG_NO_URL = "请求的URL不存在，请检查";
    public static final String MSG_UNSUPPORT_THIRD_TYPE = "不支持的第三方类型";
    public static final String MSG_INVALID_TOKEN = "INVALID TOKEN";
    public static final String MSG_INVALID_CLIENT = "INVALID CLIENT";
    public static final String MSG_FALLBACK = "请求失败!";

    public static final String HEADER_VERSION = "cn-version";
}
