package org.lc.admin.framework.async.decorator;

import org.lc.admin.common.utils.servlet.AsyncServletUtils;
import org.lc.admin.common.utils.servlet.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskDecorator;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 异步任务装饰器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:49
 */
public class AsyncTaskDecorator implements TaskDecorator {

    public static final Logger log = LoggerFactory.getLogger(AsyncTaskDecorator.class);

    /**
     * Description: 装修
     *
     * @param runnable run任务
     * @return {@link Runnable } 任务
     * @date 2022-10-02 15:50
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        // 获取当前请求实例
        HttpServletRequest request = ServletUtils.getRequest();
        return () -> {
            try {
                // 异步工具类设置异步请求实例
                AsyncServletUtils.setRequest(request);
                runnable.run();
            } finally {
                // 异步任务执行后移除请求
                AsyncServletUtils.remove();
            }
        };
    }
}
