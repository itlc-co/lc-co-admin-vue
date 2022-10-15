package org.lc.admin.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.exec.ServiceException;

import java.util.List;

/**
 * Description: 断言工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-29 14:45
 */
public class AssertUtils {

    /**
     * Description: 是否为真
     *
     * @param expression 表达式
     * @param statusMsg  状态消息
     * @date 2022-10-02 15:56
     */
    public static void isTrue(boolean expression, StatusMsg statusMsg) {
        if (!expression) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否为假
     *
     * @param expression 表达式
     * @param statusMsg  状态消息
     * @date 2022-10-02 15:56
     */
    public static void isFalse(boolean expression, StatusMsg statusMsg) {
        if (expression) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否不为空白字符串
     *
     * @param str       str字符串
     * @param statusMsg 状态消息
     * @date 2022-09-29 14:52
     */
    public static void isNotEmpty(String str, StatusMsg statusMsg) {
        if (StrUtil.isEmpty(str)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否为空白字符串
     *
     * @param str       字符串
     * @param statusMsg 状态消息
     * @date 2022-10-02 15:56
     */
    public static void isEmpty(String str, StatusMsg statusMsg) {
        if (StrUtil.isNotEmpty(str)) {
            throw new ServiceException(statusMsg);
        }
    }


    /**
     * Description: 是否不为空白字符串
     *
     * @param str       字符串
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:02
     */
    public static void isNotBlank(String str, StatusMsg statusMsg) {
        if (StrUtil.isBlank(str)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否为空白字符串
     *
     * @param str       字符串
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:03
     */
    public static void isBlank(String str, StatusMsg statusMsg) {
        if (StrUtil.isNotBlank(str)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否都不为空白字符串
     *
     * @param str       字符串数组
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:03
     */
    public static void isAllNotBlank(StatusMsg statusMsg, String... str) {
        if (!StrUtil.isAllNotBlank(str)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否都为空白字符串
     *
     * @param str       字符串列表
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:03
     */
    public static void isAllBlank(StatusMsg statusMsg, String... str) {
        if (!StrUtil.isAllBlank(str)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否都不为空
     *
     * @param str       字符串列表
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:03
     */
    public static void isAllNotEmpty(StatusMsg statusMsg, String... str) {
        if (!StrUtil.isAllNotEmpty(str)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否都为空
     *
     * @param str       字符串列表
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:04
     */
    public static void isAllEmpty(StatusMsg statusMsg, String... str) {
        if (!StrUtil.isAllEmpty(str)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否为空
     *
     * @param object    对象
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:04
     */
    public static void isNull(Object object, StatusMsg statusMsg) {
        if (ObjectUtil.isNotNull(object)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否不为空
     *
     * @param object    对象
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:04
     */
    public static void isNotNull(Object object, StatusMsg statusMsg) {
        if (ObjectUtil.isNull(object)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否为空
     *
     * @param object    对象
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:04
     */
    public static void isEmpty(Object object, StatusMsg statusMsg) {
        if (ObjectUtil.isNotEmpty(object)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否不为空
     *
     * @param object    对象
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:04
     */
    public static void isNotEmpty(Object object, StatusMsg statusMsg) {
        if (ObjectUtil.isEmpty(object)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否都为空
     *
     * @param objects   对象数组
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:04
     */
    public static void isAllNull(StatusMsg statusMsg, Object... objects) {
        if (!ArrayUtil.isAllNull(objects)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否都不为空
     *
     * @param objects   对象数组
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:05
     */
    public static void isAllNotNull(StatusMsg statusMsg, Object... objects) {
        if (!ArrayUtil.isAllNotNull(objects)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否都为empty
     *
     * @param objects   对象数组
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:05
     */
    public static void isAllEmpty(StatusMsg statusMsg, Object... objects) {
        if (!ArrayUtil.isAllEmpty(objects)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否都不为empty
     *
     * @param objects   对象数组
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:05
     */
    public static void isAllNotEmpty(StatusMsg statusMsg, Object... objects) {
        if (!ArrayUtil.isAllNotEmpty(objects)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否都不为empty
     *
     * @param objects   对象数组
     * @param statusMsg 状态消息
     * @date 2022-10-02 17:05
     */
    public static void isNotEmpty(Object[] objects, StatusMsg statusMsg) {
        if (ArrayUtil.isEmpty(objects)) {
            throw new ServiceException(statusMsg);
        }
    }

    /**
     * Description: 是否真
     *
     * @param expression 表达式
     * @date 2022-10-02 17:05
     */
    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否假
     *
     * @param expression 表达式
     * @date 2022-10-02 17:05
     */
    public static void isFalse(boolean expression) {
        if (expression) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }


    /**
     * Description: 是否不为空字符串
     *
     * @param str str字符串
     * @date 2022-10-02 17:06
     */
    public static void isNotEmpty(String str) {
        if (StrUtil.isEmpty(str)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否为空字符串
     *
     * @param str str字符串
     * @date 2022-10-02 17:06
     */
    public static void isEmpty(String str) {
        if (StrUtil.isNotEmpty(str)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }


    /**
     * Description: 是否不为空白字符串
     *
     * @param str str字符串
     * @date 2022-10-02 17:06
     */
    public static void isNotBlank(String str) {
        if (StrUtil.isBlank(str)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否为空白字符串
     *
     * @param str str字符串
     * @date 2022-10-02 17:06
     */
    public static void isBlank(String str) {
        if (StrUtil.isNotBlank(str)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都不为空字白符串
     *
     * @param strs 字符串数组
     * @date 2022-10-02 17:06
     */
    public static void isAllNotBlank(String... strs) {
        if (StrUtil.hasBlank(strs)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都为空字白符串
     *
     * @param strs 字符串数组
     * @date 2022-10-02 17:07
     */
    public static void isAllBlank(String... strs) {
        if (!StrUtil.isAllBlank(strs)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都不为空符串
     *
     * @param strs 字符串数组
     * @date 2022-10-02 17:07
     */
    public static void isAllNotEmpty(String... strs) {
        if (!StrUtil.isAllNotEmpty(strs)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都为空符串
     *
     * @param strs 字符串数组
     * @date 2022-10-02 17:07
     */
    public static void isAllEmpty(String... strs) {
        if (!StrUtil.isAllEmpty(strs)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否为null
     *
     * @param object 对象
     * @date 2022-10-02 17:07
     */
    public static void isNull(Object object) {
        if (ObjectUtil.isNotNull(object)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否不为null
     *
     * @param object 对象
     * @date 2022-10-02 17:07
     */
    public static void isNotNull(Object object) {
        if (ObjectUtil.isNull(object)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否为空
     *
     * @param object 对象
     * @date 2022-10-02 17:07
     */
    public static void isEmpty(Object object) {
        if (ObjectUtil.isNotEmpty(object)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否不为空
     *
     * @param object 对象
     * @date 2022-10-02 17:08
     */
    public static void isNotEmpty(Object object) {
        if (ObjectUtil.isEmpty(object)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都为null
     *
     * @param objects 对象数组
     * @date 2022-10-02 17:08
     */
    public static void isAllNull(Object... objects) {
        if (!ArrayUtil.isAllNull(objects)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都不为null
     *
     * @param objects 对象数组
     * @date 2022-10-02 17:08
     */
    public static void isAllNotNull(Object... objects) {
        if (!ArrayUtil.isAllNotNull(objects)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都为空
     *
     * @param objects 对象数组
     * @date 2022-10-02 17:08
     */
    public static void isAllEmpty(Object... objects) {
        if (!ArrayUtil.isAllEmpty(objects)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都不为空
     *
     * @param objects 对象数组
     * @date 2022-10-02 17:08
     */
    public static void isAllNotEmpty(Object... objects) {
        if (!ArrayUtil.isAllNotEmpty(objects)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都不为空
     *
     * @param objects 对象数组
     * @date 2022-10-02 17:08
     */
    public static void isNotEmpty(Object[] objects) {
        if (ArrayUtil.isEmpty(objects)) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都不为空字符串
     *
     * @param strs 字符串列表
     * @date 2022-10-02 17:09
     */
    public static void isNotEmpty(List<String> strs) {
        if (StrUtil.hasEmpty(strs.toArray(new String[0]))) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }

    /**
     * Description: 是否都不为空对象
     *
     * @param objects 对象列表
     * @date 2022-10-02 17:09
     */
    public static void isNotEmptyObjects(List<Object> objects) {
        if (ObjectUtil.hasEmpty(objects.toArray(new Object[0]))) {
            throw new ServiceException(StatusMsg.SERVICE_ERROR);
        }
    }


}
