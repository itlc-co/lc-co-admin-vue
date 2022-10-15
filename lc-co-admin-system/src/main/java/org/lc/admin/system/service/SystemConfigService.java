package org.lc.admin.system.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.lc.admin.system.entities.bo.SystemConfigBo;
import org.lc.admin.system.entities.entity.SystemConfig;
import org.lc.admin.system.entities.request.SystemConfigRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 系统配置service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 21:48
 */
public interface SystemConfigService {

    /**
     * Description: 查询配置数据
     *
     * @param config 配置参数
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-11 22:04
     */
    SystemConfig selectConfig(SystemConfig config);

    /**
     * Description: 根据配置键查询配置数据
     *
     * @param configKey 配置key
     * @return {@link String } 配置value
     * @date 2022-09-20 20:10
     */
    String selectConfigValueByKey(String configKey);

    /**
     * Description: 查询配置列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemConfig }> 配置列表数据
     * @date 2022-09-23 21:38
     */
    List<SystemConfig> selectConfigList(SystemConfigRequest requestParam);

    /**
     * Description: 根据配置id查询配置数据
     *
     * @param configId 配置id
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-23 21:38
     */
    SystemConfig selectConfigById(Long configId);

    /**
     * Description: 根据配置键查询配置数据
     *
     * @param configKey 配置键
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-23 21:38
     */
    SystemConfig selectConfigByKey(String configKey);

    /**
     * Description: 插入配置数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:39
     */
    Integer insertConfig(SystemConfigRequest requestParam);

    /**
     * Description: 插入配置数据
     *
     * @param config 配置数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:39
     */
    Integer insertConfig(SystemConfig config);

    /**
     * Description: 根据配置键查询唯一配置数据
     *
     * @param configKey 配置键
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-23 21:39
     */
    SystemConfig selectConfigKeyUnique(String configKey);

    /**
     * Description: 更新配置数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:44
     */
    Integer updateConfig(SystemConfigRequest requestParam);

    /**
     * Description: 更新配置数据
     *
     * @param config 配置数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:44
     */
    Integer updateConfig(SystemConfig config);

    /**
     * Description: 根据配置id列表删除配置数据
     *
     * @param configIds 配置id列表
     * @return {@link List }<{@link SystemConfig }> 配置列表数据
     * @date 2022-10-01 22:00
     */
    List<SystemConfig> deleteConfigByIds(List<Long> configIds);

    /**
     * Description: 根据配置id删除配置数据
     *
     * @param configId 配置id
     * @return {@link SystemConfig } 配置数据
     * @date 2022-10-01 22:00
     */
    SystemConfig deleteConfigById(Long configId);

    /**
     * Description: 添加配置数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:45
     */
    Integer addConfig(SystemConfigRequest requestParam);

    /**
     * Description: 编辑配置数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:46
     */
    Integer editConfig(SystemConfigRequest requestParam);

    /**
     * Description: 根据请求参数导出配置列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-10-07 17:58
     */
    void export(SystemConfigRequest requestParam, HttpServletResponse response) throws IOException;

    /**
     * Description: 查询配置bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemConfigBo }> 配置bo列表数据
     * @date 2022-09-23 21:47
     */
    List<SystemConfigBo> selectConfigBoList(SystemConfigRequest requestParam);

    /**
     * Description: 根据配置键查询配置模块
     *
     * @param configKey 配置键
     * @return {@link String } 配置模块
     * @date 2022-10-01 21:18
     */
    String selectConfigModuleByKey(String configKey);
}
