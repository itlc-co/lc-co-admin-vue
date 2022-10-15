package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.common.exec.ConfigException;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.bo.SystemConfigBo;
import org.lc.admin.system.entities.entity.SystemConfig;
import org.lc.admin.system.entities.request.SystemConfigRequest;
import org.lc.admin.system.mapper.SystemConfigMapper;
import org.lc.admin.system.service.SystemConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 系统配置service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-23 21:48
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Resource
    private SystemConfigMapper configMapper;

    /**
     * Description: 根据配置键查询配置数据
     *
     * @param configKey 配置key
     * @return {@link String } 配置value
     * @date 2022-09-20 20:11
     */
    @Override
    public String selectConfigValueByKey(String configKey) {
        return this.configMapper.selectConfigValueByKey(configKey);
    }

    /**
     * Description: 查询配置列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemConfig }> 配置列表数据
     * @date 2022-09-23 21:49
     */
    @Override
    public List<SystemConfig> selectConfigList(SystemConfigRequest requestParam) {
        return this.configMapper.selectConfigList(requestParam);
    }

    /**
     * Description: 根据配置id查询配置数据
     *
     * @param configId 配置id
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-23 21:51
     */
    @Override
    public SystemConfig selectConfigById(Long configId) {
        return this.configMapper.selectConfigById(configId);
    }

    /**
     * Description: 根据配置键查询配置数据
     *
     * @param configKey 配置键
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-23 21:52
     */
    @Override
    public SystemConfig selectConfigByKey(String configKey) {
        return this.configMapper.selectConfigByKey(configKey);
    }

    /**
     * Description: 插入配置数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:52
     */
    @Override
    public Integer insertConfig(SystemConfigRequest requestParam) {
        return this.insertConfig(BeanUtil.toBean(requestParam, SystemConfig.class));
    }

    /**
     * Description: 检查配置键唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-10-02 11:35
     */
    private boolean checkConfigKeyUnique(SystemConfigRequest requestParam) {
        // 防止空指针 前端可能传dictId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getConfigId(), requestParam.getId());
        Long configId = ObjectUtil.isNull(id) ? -1L : id;

        // 字典类型加载字典信息
        String configKey = requestParam.getConfigKey();
        SystemConfig config = this.selectConfigKeyUnique(configKey);

        // 是否唯一
        return ObjectUtil.isNotNull(config) && !ObjectUtil.equals(configId, config.getId());

    }

    /**
     * Description: 插入配置数据
     *
     * @param config 配置数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:52
     */
    @Override
    public Integer insertConfig(SystemConfig config) {
        // 设置修改创建者
        String userName = SecurityUtils.getUserName();
        config.setCreateBy(userName);
        config.setUpdateBy(userName);
        return this.configMapper.insertConfig(config);
    }

    /**
     * Description: 根据配置键查询唯一配置数据
     *
     * @param configKey 配置键
     * @return {@link SystemConfig } 配置数据
     * @date 2022-09-23 21:53
     */
    @Override
    public SystemConfig selectConfigKeyUnique(String configKey) {
        return this.configMapper.selectConfigKeyUnique(configKey);
    }

    /**
     * Description: 更新配置数据
     *
     * @param config 配置数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:55
     */
    @Override
    public Integer updateConfig(SystemConfig config) {
        config.setUpdateBy(SecurityUtils.getUserName());
        return this.configMapper.updateConfig(config);
    }

    /**
     * Description: 添加配置数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:57
     */
    @Override
    public Integer addConfig(SystemConfigRequest requestParam) {
        // 检查配置键名唯一性
        if (this.checkConfigKeyUnique(requestParam)) {
            throw new ConfigException(StatusMsg.CONFIG_KEY_NOT_UNIQUE);
        }
        return this.insertConfig(requestParam);
    }

    /**
     * Description: 编辑配置数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:58
     */
    @Override
    public Integer editConfig(SystemConfigRequest requestParam) {
        // 检查配置键名唯一性
        if (this.checkConfigKeyUnique(requestParam)) {
            throw new ConfigException(StatusMsg.CONFIG_KEY_NOT_UNIQUE);
        }
        return this.updateConfig(requestParam);
    }

    /**
     * Description: 根据请求参数导出配置列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-09-23 21:58
     */
    @Override
    public void export(SystemConfigRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, this.selectConfigBoList(requestParam), SystemConfigBo.class, "配置信息");
    }

    /**
     * Description: 根据配置键查询配置模块
     *
     * @param configKey 配置键
     * @return {@link String } 配置模块
     * @date 2022-10-01 21:18
     */
    @Override
    public String selectConfigModuleByKey(String configKey) {
        return this.configMapper.selectConfigModuleByKey(configKey);
    }

    /**
     * Description: 查询配置bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemConfigBo }> 配置bo列表数据
     * @date 2022-09-23 21:58
     */
    @Override
    public List<SystemConfigBo> selectConfigBoList(SystemConfigRequest requestParam) {
        return this.configMapper.selectConfigBoList(requestParam);
    }

    /**
     * Description: 根据配置id删除配置数据
     *
     * @param configId 配置id
     * @return {@link SystemConfig } 配置数据
     * @date 2022-10-06 22:34
     */
    @Override
    public SystemConfig deleteConfigById(Long configId) {
        // 根据配置id查询配置实体
        SystemConfig config = this.selectConfigById(configId);
        if (CharUtil.equals(config.getIsBuiltin(), 'Y', true)) {
            // 内置配置无法删除
            throw new ConfigException(StatusMsg.CONFIG_BUILTIN_NOT_DELETE.getCode(), StrUtil.format("{}为" + StatusMsg.CONFIG_BUILTIN_NOT_DELETE.getMsg(), config.getConfigKey()));
        }
        if (this.configMapper.deleteConfigById(configId) <= 0) {
            // 删除失败配置数据为null
            config = null;
        }
        return config;
    }

    /**
     * Description: 根据配置id列表删除配置数据
     *
     * @param configIds 配置id列表
     * @return {@link List }<{@link SystemConfig }> 配置列表数据
     * @date 2022-09-23 21:56
     */
    @Override
    public List<SystemConfig> deleteConfigByIds(List<Long> configIds) {
        List<SystemConfig> configs = ListUtil.list(false);
        configIds.forEach((configId) -> {
            // 根据配置id查询配置实体
            SystemConfig config = this.selectConfigById(configId);
            if (CharUtil.equals(config.getIsBuiltin(), 'Y', true)) {
                // 内置配置无法删除
                throw new ConfigException(StatusMsg.CONFIG_BUILTIN_NOT_DELETE.getCode(), StrUtil.format("{}为" + StatusMsg.CONFIG_BUILTIN_NOT_DELETE.getMsg(), config.getConfigKey()));
            }
            this.configMapper.deleteConfigById(configId);
            configs.add(config);
        });
        return configs;
    }

    /**
     * Description: 更新配置数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 21:54
     */
    @Override
    public Integer updateConfig(SystemConfigRequest requestParam) {
        // 设置配置id
        SystemConfig config = BeanUtil.toBean(requestParam, SystemConfig.class);
        config.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getConfigId()));
        return this.updateConfig(config);
    }

    /**
     * Description: 根据配置参数查询系统配置
     *
     * @param config 配置参数
     * @return {@link SystemConfig } 系统配置实体
     * @date 2022-09-11 22:08
     */
    @Override
    public SystemConfig selectConfig(SystemConfig config) {
        return this.configMapper.selectConfig(config);
    }


}
