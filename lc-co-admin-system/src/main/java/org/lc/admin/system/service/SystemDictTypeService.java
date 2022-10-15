package org.lc.admin.system.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.lc.admin.system.entities.bo.SystemDictTypeBo;
import org.lc.admin.system.entities.entity.SystemDictType;
import org.lc.admin.system.entities.request.SystemDictTypeRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 系统字典类型service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 14:45
 */
public interface SystemDictTypeService {

    /**
     * Description: 查询字典类型列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictType }> 字典类型列表数据
     * @date 2022-09-23 20:25
     */
    List<SystemDictType> selectDictTypeList(SystemDictTypeRequest requestParam);

    /**
     * Description: 根据字典id查询字典类型数据
     *
     * @param dictId 字典id
     * @return {@link SystemDictType } 字典类型数据
     * @date 2022-09-23 20:26
     */
    SystemDictType selectDictTypeById(Long dictId);

    /**
     * Description: 插入字典类型数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:26
     */
    Integer insertDictType(SystemDictTypeRequest requestParam);

    /**
     * Description: 插入字典类型数据
     *
     * @param dictType 字典类型数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:26
     */
    Integer insertDictType(SystemDictType dictType);

    /**
     * Description: 根据字典类型名称查询字典类型数据
     *
     * @param dictType 类型名称
     * @return {@link SystemDictType } 字典类型数据
     * @date 2022-09-23 20:27
     */
    SystemDictType selectDictTypeByTypeName(String dictType);

    /**
     * Description: 更新字典类型数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:28
     */
    Integer updateDictType(SystemDictTypeRequest requestParam);

    /**
     * Description: 更新字典类型数据
     *
     * @param dictType 字典类型
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:28
     */
    Integer updateDictType(SystemDictType dictType);

    /**
     * Description: 根据字典id列表删除字典类型数据
     *
     * @param dictIds 字典id列表
     * @return {@link List }<{@link SystemDictType }> 字典类型列表数据
     * @date 2022-10-02 10:11
     */
    List<SystemDictType> deleteDictTypeByIds(List<Long> dictIds);

    /**
     * Description: 根据字典id删除字典类型数据
     *
     * @param dictId 字典id
     * @return {@link SystemDictType } 字典类型数据
     * @date 2022-10-02 10:11
     */
    SystemDictType deleteDictTypeById(Long dictId);

    /**
     * Description: 查询所有字典类型数据
     *
     * @return {@link List }<{@link SystemDictType }> 所有字典类型数据
     * @date 2022-09-23 20:28
     */
    List<SystemDictType> selectDictTypeAll();

    /**
     * Description: 编辑字典类型数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:29
     */
    Integer editDictType(SystemDictTypeRequest requestParam);

    /**
     * Description: 添加字典类型数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:29
     */
    Integer addDictType(SystemDictTypeRequest requestParam);

    /**
     * Description: 根据请求参数导出字典类型列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException ioe异常
     * @date 2022-10-07 17:53
     */
    void export(SystemDictTypeRequest requestParam, HttpServletResponse response) throws IOException;

    /**
     * Description: 查询字典类型Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictTypeBo }>  字典类型Bo列表数据
     * @date 2022-09-23 20:29
     */
    List<SystemDictTypeBo> selectDictTypeBoList(SystemDictTypeRequest requestParam);
}
