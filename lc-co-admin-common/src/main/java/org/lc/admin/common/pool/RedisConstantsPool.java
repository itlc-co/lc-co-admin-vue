package org.lc.admin.common.pool;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;

import java.util.List;
import java.util.Map;

/**
 * Description: redis通用常量
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:35
 */
public class RedisConstantsPool {

    /**
     * redis缓存键模板
     */
    public static final String REDIS_KEY_TEMPLATE = "{applicationName}::{moduleName}::{name}::{key}";

    /**
     * redis缓存键应用名称标识
     */
    public static final String REDIS_KEY_APPLICATION_NAME_FLAG = "applicationName";
    /**
     * redis缓存键模块名称标识
     */
    public static final String REDIS_KEY_MODULE_NAME_FLAG = "moduleName";
    /**
     * redis缓存键名称标识
     */
    public static final String REDIS_KEY_NAME_FLAG = "name";
    /**
     * redis缓存键标识
     */
    public static final String REDIS_KEY_FLAG = "key";


    /**
     * 用户认证模块
     */
    public static final String AUTH_MODULE = "system_auth";
    /**
     * 系统配置模块
     */
    public static final String SYSTEM_CONFIG_MODULE = "system_config";
    /**
     * 系统字典模块
     */
    public static final String SYSTEM_DICT_MODULE = "system_dict";
    /**
     * 请求过滤模块
     */
    public static final String REQUEST_MODULE = "system_request";


    /**
     * 验证码名称
     */
    public static final String AUTH_CAPTCHA_NAME = "captcha_code";
    /**
     * 身份验证令牌token名称
     */
    public static final String AUTH_TOKEN_NAME = "auth_token";
    /**
     * 认证密码重试次数key名称
     */
    public static final String AUTH_PWD_RETRY_CNT_NAME = "pwd_retry_count";

    /**
     * 系统用户缓存名称
     */
    public static final String SYSTEM_USER_NAME = "system_user";
    /**
     * 系统菜单缓存名称
     */
    public static final String SYSTEM_MENU_NAME = "system_menu";
    /**
     * 系统通知缓存名称
     */
    public static final String SYSTEM_NOTICE_NAME = "system_notice";
    /**
     * 系统操作缓存名称
     */
    public static final String SYSTEM_OPERATION_NAME = "system_operation";
    /**
     * 系统任务缓存名称
     */
    public static final String SYSTEM_JOB_NAME = "system_job";
    /**
     * 系统common缓存名称
     */
    public static final String SYSTEM_COMMON_NAME = "system_common";

    /**
     * 系统index缓存名称
     */
    public static final String SYSTEM_INDEX_NAME = "system_index";
    /**
     * 系统认证缓存名称
     */
    public static final String SYSTEM_AUTH_NAME = "system_auth";

    /**
     * 重复请求缓存名称
     */
    public static final String REPEAT_REQUEST_NAME = "repeat_request";
    /**
     * 请求速率限制器名称
     */
    public static final String REQUEST_RATE_LIMITER_NAME = "rate_limiter";


    /**
     * 缓存模块map
     */
    public static final Map<String, String> MODULES = MapUtil.newHashMap();
    /**
     * 缓存名称map
     */
    public static final Map<String, List<Map<String, String>>> NAMES = MapUtil.newHashMap();


    static {
        // 封装缓存模块map
        MODULES.put(AUTH_MODULE, "auth认证模块");
        MODULES.put(SYSTEM_CONFIG_MODULE, "系统配置模块");
        MODULES.put(SYSTEM_DICT_MODULE, "系统字典模块");
        MODULES.put(REQUEST_MODULE, "系统请求模块");

        // 封装缓存名称map
        NAMES.put(AUTH_MODULE, ListUtil.toList(MapUtil.ofEntries(MapUtil.entry("name", AUTH_TOKEN_NAME), MapUtil.entry("remark", "认证token")), MapUtil.ofEntries(MapUtil.entry("name", AUTH_CAPTCHA_NAME), MapUtil.entry("remark", "验证码")), MapUtil.ofEntries(MapUtil.entry("name", AUTH_PWD_RETRY_CNT_NAME), MapUtil.entry("remark", "密码错误次数"))));
        NAMES.put(SYSTEM_CONFIG_MODULE, ListUtil.toList(MapUtil.ofEntries(MapUtil.entry("name", SYSTEM_USER_NAME), MapUtil.entry("remark", "系统用户")), MapUtil.ofEntries(MapUtil.entry("name", SYSTEM_INDEX_NAME), MapUtil.entry("remark", "系统index")), MapUtil.ofEntries(MapUtil.entry("name", SYSTEM_AUTH_NAME), MapUtil.entry("remark", "系统认证"))));
        NAMES.put(SYSTEM_DICT_MODULE, ListUtil.toList(MapUtil.ofEntries(MapUtil.entry("name", SYSTEM_USER_NAME), MapUtil.entry("remark", "系统用户")), MapUtil.ofEntries(MapUtil.entry("name", SYSTEM_MENU_NAME), MapUtil.entry("remark", "系统菜单")), MapUtil.ofEntries(MapUtil.entry("name", SYSTEM_NOTICE_NAME), MapUtil.entry("remark", "系统公告")), MapUtil.ofEntries(MapUtil.entry("name", SYSTEM_OPERATION_NAME), MapUtil.entry("remark", "系统操作")), MapUtil.ofEntries(MapUtil.entry("name", SYSTEM_JOB_NAME), MapUtil.entry("remark", "系统任务")), MapUtil.ofEntries(MapUtil.entry("name", SYSTEM_COMMON_NAME), MapUtil.entry("remark", "系统公共"))));
        NAMES.put(REQUEST_MODULE, ListUtil.toList(MapUtil.ofEntries(MapUtil.entry("name", REPEAT_REQUEST_NAME), MapUtil.entry("remark", "重复请求")), MapUtil.ofEntries(MapUtil.entry("name", REQUEST_RATE_LIMITER_NAME), MapUtil.entry("remark", "请求限流"))));

    }


}


