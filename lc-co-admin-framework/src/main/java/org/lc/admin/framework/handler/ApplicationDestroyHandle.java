package org.lc.admin.framework.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Description: 销毁application应用程序处理
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-04 17:09
 */
@Component
public class ApplicationDestroyHandle {

    public static final Logger log = LoggerFactory.getLogger(ApplicationDestroyHandle.class);


    /**
     * Description: 摧毁application前执行
     *
     * @date 2022-10-04 17:07
     */
    @PreDestroy
    public void destroy() {

    }


}
