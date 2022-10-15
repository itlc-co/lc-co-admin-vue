package org.lc.admin.framework.handler;

import org.lc.admin.framework.cache.service.RedisCacheService;
import org.lc.admin.system.entities.entity.SystemConfig;
import org.lc.admin.system.entities.entity.SystemDictData;
import org.lc.admin.system.entities.request.SystemConfigRequest;
import org.lc.admin.system.entities.request.SystemDictDataRequest;
import org.lc.admin.system.service.SystemConfigService;
import org.lc.admin.system.service.SystemDictDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 项目应用程序初始化处理
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-28 22:52
 */
@Component
public class ApplicationInitHandle {

    public static final Logger log = LoggerFactory.getLogger(ApplicationInitHandle.class);

    @Resource
    private SystemDictDataService dictDataService;

    @Resource
    private SystemConfigService configService;

    @Resource
    private RedisCacheService redisCacheService;

    /**
     * Description: 初始化执行
     *
     * @date 2022-10-02 15:43
     */
    @PostConstruct
    public void init() {
        // 获取系统配置数据列表
        List<SystemConfig> configs = configService.selectConfigList(new SystemConfigRequest());
        // 保存到redis缓存中
        redisCacheService.saveSystemConfigList(configs);

        // 获取字典数据列表
        SystemDictDataRequest requestParam = new SystemDictDataRequest();
        requestParam.setStatus(0);
        List<SystemDictData> dictDatas = this.dictDataService.selectDictDataList(requestParam);
        // 保存到redis缓存中
        redisCacheService.saveDictDataList(dictDatas);


    }

}
