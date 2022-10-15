package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.common.exec.DictException;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.bo.SystemDictDataBo;
import org.lc.admin.system.entities.entity.SystemDictData;
import org.lc.admin.system.entities.request.SystemDictDataRequest;
import org.lc.admin.system.mapper.SystemDictDataMapper;
import org.lc.admin.system.service.SystemDictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 系统字典数据service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 20:40
 */
@Service
public class SystemDictDataServiceImpl implements SystemDictDataService {


    @Resource
    private SystemDictDataMapper dictDataMapper;


    /**
     * Description: 根据字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return {@link List }<{@link SystemDictData }> 字典数据列表
     * @date 2022-09-11 20:41
     */
    @Override
    public List<SystemDictData> selectDictDataByDictType(String dictType) {
        return dictDataMapper.selectDictDataByDictType(dictType);
    }

    /**
     * Description: 更新数据字典类型名称
     *
     * @param oldDictType 老字典类型名称
     * @param newDictType 新字典类型名称
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:50
     */
    @Override
    public Integer updateDictDataDictType(String oldDictType, String newDictType) {
        return this.dictDataMapper.updateDictDataDictType(oldDictType, newDictType);
    }

    /**
     * Description: 根据字典类型查询字典数据数量
     *
     * @param dictType 字典类型
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:50
     */
    @Override
    public Integer selectCntDictDataByDictType(String dictType) {
        return this.dictDataMapper.selectCntDictDataByDictType(dictType);
    }

    /**
     * Description: 查询字典数据列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictData }> 字典数据列表数据
     * @date 2022-09-23 20:51
     */
    @Override
    public List<SystemDictData> selectDictDataList(SystemDictDataRequest requestParam) {
        return this.dictDataMapper.selectDictDataList(requestParam);
    }

    /**
     * Description: 根据字典数据id查询字典数据
     *
     * @param dataId 字典数据id
     * @return {@link SystemDictData } 字典数据
     * @date 2022-09-23 20:51
     */
    @Override
    public SystemDictData selectDictDataByDataId(Long dataId) {
        return this.dictDataMapper.selectDictDataByDataId(dataId);
    }

    /**
     * Description: 插入字典数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:52
     */
    @Override
    public Integer insertDictData(SystemDictDataRequest requestParam) {
        return this.insertDictData(BeanUtil.toBean(requestParam, SystemDictData.class));
    }

    /**
     * Description: 插入字典数据
     *
     * @param dictData 字典数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:52
     */
    @Override
    public Integer insertDictData(SystemDictData dictData) {
        // 设置创建修改者
        String userName = SecurityUtils.getUserName();
        dictData.setCreateBy(userName);
        dictData.setUpdateBy(userName);
        return this.dictDataMapper.insertDictData(dictData);
    }

    /**
     * Description: 更新字典数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:53
     */
    @Override
    public Integer updateDictData(SystemDictDataRequest requestParam) {
        // 转换bean实体并设置id
        SystemDictData dictData = BeanUtil.toBean(requestParam, SystemDictData.class);
        dictData.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getDataId()));
        return this.updateDictData(dictData);
    }

    /**
     * Description: 更新字典数据
     *
     * @param dictData 字典数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:53
     */
    @Override
    public Integer updateDictData(SystemDictData dictData) {
        // 设置修改者
        String userName = SecurityUtils.getUserName();
        dictData.setUpdateBy(userName);
        return this.dictDataMapper.updateDictData(dictData);
    }

    /**
     * Description: 添加字典数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:55
     */
    @Override
    public Integer addDictData(SystemDictDataRequest requestParam) {
        // 检查字典数据唯一性
        if (this.checkDictDataUnique(requestParam)) {
            throw new DictException(StatusMsg.DICT_DATA_EXIST);
        }
        return this.insertDictData(requestParam);
    }

    /**
     * Description: 检查字典数据唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-23 20:55
     */
    private boolean checkDictDataUnique(SystemDictDataRequest requestParam) {
        // 防止空指针 前端可能传dataId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getDataId());
        Long dataId = ObjectUtil.isNull(id) ? -1L : id;

        // 根据字典类型和字典值查询字典数据（唯一）
        SystemDictData dictData = this.selectDictDataByDictTypeAndDictValue(requestParam.getDictType(), requestParam.getDictValue());

        // 是否唯一
        return ObjectUtil.isNotNull(dictData) && !ObjectUtil.equals(dataId, dictData.getId());

    }

    /**
     * Description: 编辑字典数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:56
     */
    @Override
    public Integer editDictData(SystemDictDataRequest requestParam) {
        // 检查字典数据唯一性
        if (this.checkDictDataUnique(requestParam)) {
            throw new DictException(StatusMsg.DICT_DATA_EXIST);
        }
        return this.updateDictData(requestParam);
    }

    /**
     * Description: 根据字典类型查询字典模块名称
     *
     * @param dictType 字典类型
     * @return {@link String } 字典模块名称
     * @date 2022-10-02 11:42
     */
    @Override
    public String selectDictModuleByDictType(String dictType) {
        return this.dictDataMapper.selectDictModuleByDictType(dictType);
    }

    /**
     * Description: 查询字典数据bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictDataBo }>  字典数据bo列表数据
     * @date 2022-09-24 00:19
     */
    @Override
    public List<SystemDictDataBo> selectDictDataBoList(SystemDictDataRequest requestParam) {
        return this.dictDataMapper.selectDictDataBoList(requestParam);
    }

    /**
     * Description: 根据请求参数导出字典数据列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-09-24 00:19
     */
    @Override
    public void export(SystemDictDataRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, this.selectDictDataBoList(requestParam), SystemDictDataBo.class, "字典数据");
    }

    /**
     * Description: 根据字典类型与字典值查询字典数据
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return {@link SystemDictData } 字典数据
     * @date 2022-09-23 20:56
     */
    @Override
    public SystemDictData selectDictDataByDictTypeAndDictValue(String dictType, String dictValue) {
        return this.dictDataMapper.selectDictDataByDictTypeAndDictValue(dictType, dictValue);
    }

    /**
     * Description: 根据字典数据id删除字典数据
     *
     * @param dataId 字典数据id
     * @return {@link SystemDictData } 字典数据
     * @date 2022-10-06 22:26
     */
    @Override
    public SystemDictData deleteDictDataByDataId(Long dataId) {
        SystemDictData dictData = this.selectDictDataByDataId(dataId);
        if (this.dictDataMapper.deleteDictDataByDataId(dataId) <= 0) {
            dictData = null;
        }
        return dictData;
    }

    /**
     * Description: 根据字典数据id列表删除字典数据
     *
     * @param dataIds 字典数据id列表
     * @return {@link List }<{@link SystemDictData }> 字典列表数据
     * @date 2022-09-23 20:54
     */
    @Override
    public List<SystemDictData> deleteDictDataByDataIds(List<Long> dataIds) {
        return dataIds.stream().map((dataId) -> {
            SystemDictData dictData = this.selectDictDataByDataId(dataId);
            if (this.dictDataMapper.deleteDictDataByDataId(dataId) <= 0) {
                dictData = null;
            }
            return dictData;
        }).collect(Collectors.toList());
    }


}
