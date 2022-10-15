package org.lc.admin.common.pool;

/**
 * Description: 任务常量池
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 13:47
 */
public class JobConstantPool {
    /**
     * 任务目标调用违规字符串
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache"};
    /**
     * 任务目标调用白名单
     */
    public static final String[] JOB_WHITELIST_STR = {"org.lc.admin.quartz.task"};
    /**
     * 任务远程调用前缀
     */
    public static final String[] REMOTE_INVOKE_PRE_STR = {"rmi:", "ldap:", "ldaps:"};
}
