package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.bo.SystemConfigBo;
import org.lc.admin.system.entities.entity.SystemConfig;
import org.lc.admin.system.entities.request.SystemConfigRequest;

import java.util.List;

/**
 * Description: 系统配置mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 21:50
 */
public interface SystemConfigMapper {
    /**
     * Description:  根据配置参数查询系统配置
     *
     * @param config 配置参数
     * @return {@link SystemConfig } 系统配置实体
     * @date 2022-09-11 22:05
     */
    SystemConfig selectConfig(SystemConfig config);

    /**
     * Description: 根据配置键查询配置数据
     *
     * @param configKey 配置key
     * @return {@link String } 配置value
     * @date 2022-09-20 20:11
     */
    String selectConfigValueByKey(@Param("configKey") String configKey);

    /**
     * Description: 查询配置列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemConfig }> 配置列表数据
     * @date 2022-09-23 21:50
     */
    List<SystemConfig> selectConfigList(SystemConfigRequest requestParam);

    /**
     * Description: 根据配置id查询配置数据
     *
     * @param configId 配置id
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-23 21:51
     */
    SystemConfig selectConfigById(@Param("configId") Long configId);

    /**
     * Description: 根据配置键查询配置数据
     *
     * @param configKey 配置键
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-23 21:52
     */
    SystemConfig selectConfigByKey(@Param("configKey") String configKey);

    /**
     * Description: 根据配置键查询唯一配置数据
     *
     * @param configKey 配置键
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-23 21:53
     */
    SystemConfig selectConfigKeyUnique(@Param("configKey") String configKey);

    /**
     * Description: 插入配置数据
     *
     * @param config 配置数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:53
     */
    Integer insertConfig(SystemConfig config);

    /**
     * Description: 更新配置数据
     *
     * @param config 配置数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:55
     */
    Integer updateConfig(SystemConfig config);

    /**
     * Description: 根据配置id删除配置数据
     *
     * @param configId 配置id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:56
     */
    Integer deleteConfigById(@Param("configId") Long configId);

    /**
     * Description: 根据配置id列表删除配置数据
     *
     * @param configIds 配置id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:36
     */
    Integer deleteConfigByIds(@Param("configIds") List<Long> configIds);

    /**
     * Description: 查询配置bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemConfigBo }> 配置bo列表数据
     * @date 2022-09-23 21:58
     */
    List<SystemConfigBo> selectConfigBoList(SystemConfigRequest requestParam);

    /**
     * Description: 根据配置键查询配置模块
     *
     * @param configKey 配置键
     * @return {@link String } 配置模块
     * @date 2022-10-01 21:18
     */
    String selectConfigModuleByKey(@Param("configKey") String configKey);
}
