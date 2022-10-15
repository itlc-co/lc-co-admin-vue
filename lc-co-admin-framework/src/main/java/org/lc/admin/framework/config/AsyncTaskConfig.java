package org.lc.admin.framework.config;

import org.lc.admin.framework.async.decorator.AsyncTaskDecorator;
import org.lc.admin.framework.async.executor.AsyncThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description: 异步任务配置
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-27 21:48
 */
@Configuration
@EnableAsync
public class AsyncTaskConfig {

    /**
     * Description: 访问记录异步任务执行器
     *
     * @return {@link AsyncTaskExecutor } 异步任务执行器
     * @date 2022-09-27 21:50
     */
    @Bean("asyncTackExecutor")
    public AsyncTaskExecutor asyncTackExecutor() {
        AsyncThreadPoolTaskExecutor executor = new AsyncThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(500);
        // 最大线程数
        executor.setMaxPoolSize(1000);
        // 队列容量
        executor.setQueueCapacity(500);
        // 设置关闭时等待任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置等待终止秒数60秒
        executor.setAwaitTerminationSeconds(60);
        // 线程名前缀
        executor.setThreadNamePrefix("async-task-executor-");
        // 线程装饰器
        executor.setTaskDecorator(new AsyncTaskDecorator());
        // 线程池对拒绝任务的处理策略 AbortPolicy策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 线程池初始化
        executor.initialize();
        return executor;
    }

    /**
     * Description: 其他异步任务执行器
     *
     * @return {@link AsyncTaskExecutor } 异步任务执行器
     * @date 2022-09-27 21:49
     */
    @Bean("otherTaskExecutor")
    public AsyncTaskExecutor otherTaskExecutor() {
        AsyncThreadPoolTaskExecutor executor = new AsyncThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(500);
        // 最大线程数
        executor.setMaxPoolSize(1000);
        // 队列容量
        executor.setQueueCapacity(500);
        // 设置关闭时等待任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置等待终止秒数60秒
        executor.setAwaitTerminationSeconds(60);
        // 线程名前缀
        executor.setThreadNamePrefix("other-task-executor-");
        // 线程装饰器
        executor.setTaskDecorator(new AsyncTaskDecorator());
        // 线程池对拒绝任务的处理策略 CallerRunsPolicy策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程池初始化
        executor.initialize();
        return executor;
    }


}
