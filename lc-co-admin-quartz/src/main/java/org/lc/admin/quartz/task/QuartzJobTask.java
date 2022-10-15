package org.lc.admin.quartz.task;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.lc.admin.quartz.task.param.QuartzJobTaskParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Description: 定时任务task
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 14:17
 */
@Component("quartzJobTask")
public class QuartzJobTask {

    public static final Logger log = LoggerFactory.getLogger(QuartzJobTask.class);

    /**
     * Description: 有参方法
     *
     * @param param 参数
     * @date 2022-10-11 14:17
     */
    public void params(QuartzJobTaskParam param) {
        log.info(StrUtil.format("当前时间:{},执行有参方法:{}", LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"), JSONUtil.toJsonStr(param)));
    }

    /**
     * Description: 空参方法
     *
     * @date 2022-10-11 14:18
     */
    public void params() {
        log.info(StrUtil.format("当前时间:{},执行无参方法", LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss")));
    }

}
