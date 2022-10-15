package org.lc.admin.generator.velocity;

import org.apache.velocity.app.Velocity;
import org.lc.admin.common.base.pool.CharacterSet;

import java.util.Properties;

/**
 * Description: VelocityEngine初始化器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-13 21:16
 */
public class VelocityInitializer {


    /**
     * 初始化vm方法
     */
    public static void initVelocity() {
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.INPUT_ENCODING, CharacterSet.UTF_8);
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
