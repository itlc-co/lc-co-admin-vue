package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.common.exec.DictException;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.bo.SystemDictTypeBo;
import org.lc.admin.system.entities.entity.SystemDictType;
import org.lc.admin.system.entities.request.SystemDictTypeRequest;
import org.lc.admin.system.mapper.SystemDictTypeMapper;
import org.lc.admin.system.service.SystemDictDataService;
import org.lc.admin.system.service.SystemDictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 系统字典类型service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 14:45
 */
@Service
public class SystemDictTypeServiceImpl implements SystemDictTypeService {

    @Resource
    private SystemDictTypeMapper dictTypeMapper;

    @Resource
    private SystemDictDataService dictDataService;


    /**
     * Description: 查询字典类型列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictType }> 字典类型列表数据
     * @date 2022-09-23 20:32
     */
    @Override
    public List<SystemDictType> selectDictTypeList(SystemDictTypeRequest requestParam) {
        return this.dictTypeMapper.selectDictTypeList(requestParam);
    }

    /**
     * Description: 根据字典id查询字典类型数据
     *
     * @param dictId 字典id
     * @return {@link SystemDictType } 字典类型数据
     * @date 2022-09-23 20:33
     */
    @Override
    public SystemDictType selectDictTypeById(Long dictId) {
        return this.dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * Description: 插入字典类型数据
     *
     * @param dictType 字典类型数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:33
     */
    @Override
    public Integer insertDictType(SystemDictType dictType) {
        // 设置创建修改者
        String userName = SecurityUtils.getUserName();
        dictType.setCreateBy(userName);
        dictType.setUpdateBy(userName);
        return this.dictTypeMapper.insertDictType(dictType);
    }

    /**
     * Description: 根据字典类型名称查询字典类型数据
     *
     * @param dictType 类型名称
     * @return {@link SystemDictType } 字典类型数据
     * @date 2022-09-23 20:34
     */
    @Override
    public SystemDictType selectDictTypeByTypeName(String dictType) {
        return this.dictTypeMapper.selectDictTypeByTypeName(dictType);
    }

    /**
     * Description: 更新字典类型数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:35
     */
    @Override
    @Transactional
    public Integer updateDictType(SystemDictTypeRequest requestParam) {
        // bean实体转换并设置字典id
        SystemDictType dictType = BeanUtil.toBean(requestParam, SystemDictType.class);
        dictType.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getDictId()));
        return this.updateDictType(dictType);
    }

    /**
     * Description: 更新字典类型数据
     *
     * @param dictType 字典类型
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:35
     */
    @Override
    public Integer updateDictType(SystemDictType dictType) {
        // 设置修改者
        dictType.setUpdateBy(SecurityUtils.getUserName());

        // 未修改前的字典类型
        SystemDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getId());

        // 同步修改字典数据中的字典类型
        this.dictDataService.updateDictDataDictType(oldDict.getDictType(), dictType.getDictType());

        // 更新字典类型信息
        return this.dictTypeMapper.updateDictType(dictType);
    }

    /**
     * Description: 根据字典id删除字典类型数据
     *
     * @param dictId 字典id
     * @return {@link SystemDictType } 字典类型数据
     * @date 2022-10-06 22:22
     */
    @Override
    public SystemDictType deleteDictTypeById(Long dictId) {
        SystemDictType dictType = this.selectDictTypeById(dictId);
        // 已分配字典值则无法删除
        if (dictDataService.selectCntDictDataByDictType(dictType.getDictType()) > 0) {
            throw new DictException(StatusMsg.DICT_EXIST_DATA.getCode(), StrUtil.format("{}" + StatusMsg.DICT_EXIST_DATA.getMsg(), dictType.getDictName()));
        }
        if (this.dictTypeMapper.deleteDictTypeById(dictId) <= 0) {
            // 删除失败字典类型为null
            dictType = null;
        }
        return dictType;
    }

    /**
     * Description: 根据字典id列表删除字典类型数据
     *
     * @param dictIds 字典id列表
     * @return {@link List }<{@link SystemDictType }> 字典类型列表数据
     * @date 2022-09-23 20:37
     */
    @Override
    public List<SystemDictType> deleteDictTypeByIds(List<Long> dictIds) {
        List<SystemDictType> dictTypes = ListUtil.list(false);
        dictIds.forEach((dictId) -> {
            SystemDictType dictType = this.selectDictTypeById(dictId);
            // 已分配字典值则无法删除
            if (dictDataService.selectCntDictDataByDictType(dictType.getDictType()) > 0) {
                throw new DictException(StatusMsg.DICT_EXIST_DATA.getCode(), StrUtil.format("{}" + StatusMsg.DICT_EXIST_DATA.getMsg(), dictType.getDictName()));
            }
            if (this.dictTypeMapper.deleteDictTypeById(dictId) > 0) {
                dictTypes.add(dictType);
            }
        });
        return dictTypes;
    }

    /**
     * Description: 编辑字典类型数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:38
     */
    @Override
    public Integer editDictType(SystemDictTypeRequest requestParam) {
        // 检查字典名称唯一性
        if (this.checkDictTypeNameUnique(requestParam)) {
            throw new DictException(StatusMsg.DICT_TYPE_NOT_UNIQUE);
        }
        // 修改字典类型实体数据
        return this.updateDictType(requestParam);
    }

    /**
     * Description: 添加字典类型数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:39
     */
    @Override
    public Integer addDictType(SystemDictTypeRequest requestParam) {
        // 检查字典名称唯一性
        if (this.checkDictTypeNameUnique(requestParam)) {
            throw new DictException(StatusMsg.DICT_TYPE_NOT_UNIQUE);
        }
        // 插入字典类型实体信息
        return this.insertDictType(requestParam);
    }

    /**
     * Description: 根据请求参数导出字典类型列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException ioe异常
     * @date 2022-09-23 20:39
     */
    @Override
    public void export(SystemDictTypeRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, this.selectDictTypeBoList(requestParam), SystemDictTypeBo.class, "字典信息");
    }

    /**
     * Description: 查询字典类型Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictTypeBo }>  字典类型Bo列表数据
     * @date 2022-09-23 20:40
     */
    @Override
    public List<SystemDictTypeBo> selectDictTypeBoList(SystemDictTypeRequest requestParam) {
        return this.dictTypeMapper.selectDictTypeBoList(requestParam);
    }

    /**
     * Description: 查询所有字典类型数据
     *
     * @return {@link List }<{@link SystemDictType }> 所有字典类型数据
     * @date 2022-09-23 20:38
     */
    @Override
    public List<SystemDictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * Description: 插入字典类型数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:40
     */
    @Override
    public Integer insertDictType(SystemDictTypeRequest requestParam) {
        return this.insertDictType(BeanUtil.toBean(requestParam, SystemDictType.class));
    }

    /**
     * Description: 检查字典类型名称是否唯一
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-23 20:41
     */
    private boolean checkDictTypeNameUnique(SystemDictTypeRequest requestParam) {
        // 防止空指针 前端可能传dictId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getDictId(), requestParam.getId());
        Long dictId = ObjectUtil.isNull(id) ? -1L : id;

        // 字典类型加载字典信息
        String dictTypeStr = requestParam.getDictType();
        SystemDictType dictType = this.selectDictTypeByTypeName(dictTypeStr);

        // 是否唯一
        return ObjectUtil.isNotNull(dictType) && !ObjectUtil.equals(dictId, dictType.getId());

    }


}
