package org.lc.admin.quartz.factory;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * Description: 任务工厂
 * 解决job中service注入为空的问题。
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-09 11:13
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    @Resource
    private AutowireCapableBeanFactory capableBeanFactory;

    /**
     * Description: 创建job实例
     *
     * @param bundle 包
     * @return {@link Object } job实例
     * @throws Exception 异常
     * @date 2022-10-09 11:12
     */
    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        //调用父类的方法
        final Object jobInstance = super.createJobInstance(bundle);
        //进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}
