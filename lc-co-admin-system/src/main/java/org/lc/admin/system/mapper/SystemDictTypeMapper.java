package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.bo.SystemDictTypeBo;
import org.lc.admin.system.entities.entity.SystemDictType;
import org.lc.admin.system.entities.request.SystemDictTypeRequest;

import java.util.List;

/**
 * Description: 系统字典类型mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 14:47
 */
public interface SystemDictTypeMapper {
    /**
     * Description: 查询字典类型列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictType }> 字典类型列表数据
     * @date 2022-09-23 20:32
     */
    List<SystemDictType> selectDictTypeList(SystemDictTypeRequest requestParam);

    /**
     * Description: 根据字典id查询字典类型数据
     *
     * @param dictId 字典id
     * @return {@link SystemDictType } 字典类型数据
     * @date 2022-09-23 20:33
     */
    SystemDictType selectDictTypeById(@Param("dictId") Long dictId);

    /**
     * Description: 插入字典类型数据
     *
     * @param dictType 字典类型数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:33
     */
    Integer insertDictType(SystemDictType dictType);

    /**
     * Description: 根据字典类型名称查询字典类型数据
     *
     * @param dictType 类型名称
     * @return {@link SystemDictType } 字典类型数据
     * @date 2022-09-23 20:34
     */
    SystemDictType selectDictTypeByTypeName(@Param("dictType") String dictType);

    /**
     * Description: 更新字典类型数据
     *
     * @param dictType 字典类型
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:36
     */
    Integer updateDictType(SystemDictType dictType);

    /**
     * Description: 根据字典id删除字典类型数据
     *
     * @param dictId 字典id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:37
     */
    Integer deleteDictTypeById(@Param("dictId") Long dictId);

    /**
     * Description: 根据字典id列表删除字典类型数据
     *
     * @param dictIds 字典id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:23
     */
    Integer deleteDictTypeByIds(@Param("dictIds") List<Long> dictIds);

    /**
     * Description: 查询所有字典类型数据
     *
     * @return {@link List }<{@link SystemDictType }> 所有字典类型数据
     * @date 2022-09-23 20:38
     */
    List<SystemDictType> selectDictTypeAll();

    /**
     * Description: 查询字典类型Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictTypeBo }>  字典类型Bo列表数据
     * @date 2022-09-23 20:40
     */
    List<SystemDictTypeBo> selectDictTypeBoList(SystemDictTypeRequest requestParam);
}
