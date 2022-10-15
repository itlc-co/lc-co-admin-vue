package org.lc.admin.framework.async.executor;


import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description: 异步线程池任务执行器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-27 22:33
 */
public class AsyncThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    public static final Logger log = LoggerFactory.getLogger(AsyncThreadPoolTaskExecutor.class);


    /**
     * Description: 销毁
     *
     * @date 2022-10-08 19:45
     */
    @Override
    public void destroy() {
        this.shutdown();
    }

    /**
     * Description: 关闭
     *
     * @date 2022-10-08 19:45
     */
    @Override
    public void shutdown() {
        log.info("<=====关闭后台异步任务线程池=====>");
        super.shutdown();
    }

    /**
     * Description: 异步任务前置处理
     * 记录监控异步线程日志（任务数等指标）
     *
     * @date 2022-09-27 21:45
     */
    private void preprocess() {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        if (ObjectUtil.isNotNull(threadPoolExecutor)) {
            log.info("[线程池监控]线程池前缀名:[{}], 总任务数:[{}], 完成任务数:[{}], 活跃任务数:[{}], 异步任务队列大小:[{}]",
                    this.getThreadNamePrefix(),
                    threadPoolExecutor.getTaskCount(),
                    threadPoolExecutor.getCompletedTaskCount(),
                    threadPoolExecutor.getActiveCount(),
                    threadPoolExecutor.getQueue().size());
        }
    }

    /**
     * Description: 提交带返回值的任务
     *
     * @param task 任务
     * @return {@link Future }<{@link T }> 返回值 Future
     * @date 2022-09-27 21:45
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        // 前置处理
        preprocess();
        return super.submit(task);
    }


    /**
     * Description: 提交任务
     *
     * @param task 任务
     * @return {@link Future }<{@link ? }> future
     * @date 2022-09-27 21:45
     */
    @Override
    public Future<?> submit(Runnable task) {
        preprocess();
        return super.submit(task);
    }

    /**
     * 执行任务逻辑
     *
     * @param task runnable实现类逻辑
     */
    @Override
    public void execute(Runnable task) {
        // 前置处理
        preprocess();
        // 执行任务
        super.execute(task);
    }

}
