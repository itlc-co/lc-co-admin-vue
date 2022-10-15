package org.lc.admin.quartz.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import org.lc.admin.common.base.pool.StrConstantsPool;
import org.lc.admin.quartz.entities.entity.QuartzJob;
import org.lc.admin.quartz.task.param.QuartzJobTaskParam;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Description: 定时任务调用工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 14:05
 */
public class QuartzJobInvokeUtils {

    /**
     * Description: 调用任务目标方法
     *
     * @param quartzJob 任务
     * @throws Exception 异常
     * @date 2022-10-11 14:05
     */
    public static void invokeMethod(QuartzJob quartzJob) throws Exception {
        // 获取任务调用目标字符串
        String jobInvokeTarget = quartzJob.getJobInvokeTarget();
        // 获取task bean实例
        String beanName = getBeanName(jobInvokeTarget);
        // 根据调用目标字符串获取方法名称
        String methodName = getMethodName(jobInvokeTarget);
        // 获取调用目标方法参数
        String jobInvokeParams = quartzJob.getJobInvokeParams();
        // 获取方法参数数组
        Object[] param = getMethodParams(jobInvokeParams);
        // 判断task bean实例名称是否为spring bean名称
        Object bean = isValidateBeanName(beanName) ? SpringUtil.getBean(beanName) : Class.forName(beanName).newInstance();
        // 调用task bean实例指定名称，参数的方法
        invokeMethod(bean, methodName, param);
    }

    /**
     * Description: 根据调用目标参数字符串获取方法参数数组
     *
     * @param jobInvokeParams 调用目标参数字符串
     * @return {@link Object[] } 方法参数数组
     * @date 2022-10-11 14:11
     */
    private static Object[] getMethodParams(String jobInvokeParams) {
        // 非空白字符串且非空json
        return StrUtil.isNotBlank(jobInvokeParams) && !StrUtil.equalsAnyIgnoreCase(jobInvokeParams, StrUtil.EMPTY_JSON) ? new Object[]{JSONUtil.toBean(jobInvokeParams, QuartzJobTaskParam.class)} : null;
    }

    /**
     * Description: 调用方法
     *
     * @param bean       bean实例
     * @param methodName 方法名称
     * @param params     参数数组
     * @throws Exception 异常
     * @date 2022-10-11 14:12
     */
    private static void invokeMethod(Object bean, String methodName, Object... params) throws Exception {
        if (ArrayUtil.isNotEmpty(params) && ArrayUtil.isAllNotEmpty(params)) {
            // 非空参方法调用
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(params));
            method.invoke(bean, params);
        } else {
            // 空参方法调用
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * Description: 根据参数数组获取参数类型class数组
     *
     * @param params 参数数组
     * @return {@link Class }<{@link ? }>{@link [] } 参数类型class数组
     * @date 2022-10-11 14:12
     */
    private static Class<?>[] getMethodParamsType(Object[] params) {
        // 转换成类型class数组
        return Arrays.stream(params).map(Object::getClass).toArray(Class[]::new);
    }

    /**
     * Description: 验证bean名称是否为spring bean
     *
     * @param beanName bean名称
     * @return boolean true 是 false 否
     * @date 2022-10-11 14:14
     */
    private static boolean isValidateBeanName(String beanName) {
        // 包含.则为全限定类名即非spring bean
        return !StrUtil.contains(beanName, StrConstantsPool.POINT_SEPARATOR);
    }

    /**
     * Description: 获取方法名称
     *
     * @param jobInvokeTarget 任务调用目标字符串 beanName.methodName()
     * @return {@link String }
     * @date 2022-10-11 14:15
     */
    private static String getMethodName(String jobInvokeTarget) {
        // 截取. ()之间的字符串即为方法名称
        return StrUtil.subBetween(jobInvokeTarget, StrConstantsPool.POINT_SEPARATOR, StrConstantsPool.PARENTHESES_AROUND);
    }

    /**
     * Description: 获取bean名称
     *
     * @param jobInvokeTarget 任务调用目标字符串  beanName.methodName()
     * @return {@link String }
     * @date 2022-10-11 14:15
     */
    private static String getBeanName(String jobInvokeTarget) {
        // 截取最后一个.之前的字符串即为bean名称
        return StrUtil.subBefore(jobInvokeTarget, StrConstantsPool.POINT_SEPARATOR, true);
    }

}
