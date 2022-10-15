package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.bo.SystemDictDataBo;
import org.lc.admin.system.entities.entity.SystemDictData;
import org.lc.admin.system.entities.request.SystemDictDataRequest;

import java.util.List;

/**
 * Description: 系统字典数据mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 20:42
 */
public interface SystemDictDataMapper {

    /**
     * Description: 根据字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return {@link List }<{@link SystemDictData }> 字典数据列表
     * @date 2022-09-11 20:44
     */
    List<SystemDictData> selectDictDataByDictType(@Param("dictType") String dictType);

    /**
     * Description: 更新数据字典类型名称
     *
     * @param oldDictType 老字典类型名称
     * @param newDictType 新字典类型名称
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:50
     */
    Integer updateDictDataDictType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);

    /**
     * Description: 根据字典类型查询字典数据数量
     *
     * @param dictType 字典类型
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:50
     */
    Integer selectCntDictDataByDictType(@Param("dictType") String dictType);

    /**
     * Description: 查询字典数据列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictData }> 字典数据列表数据
     * @date 2022-09-23 20:51
     */
    List<SystemDictData> selectDictDataList(SystemDictDataRequest requestParam);

    /**
     * Description: 根据字典数据id查询字典数据
     *
     * @param dataId 字典数据id
     * @return {@link SystemDictData } 字典数据
     * @date 2022-09-23 20:52
     */
    SystemDictData selectDictDataByDataId(@Param("dataId") Long dataId);

    /**
     * Description: 插入字典数据
     *
     * @param dictData 字典数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:52
     */
    Integer insertDictData(SystemDictData dictData);

    /**
     * Description: 更新字典数据
     *
     * @param dictData 字典数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:54
     */
    Integer updateDictData(SystemDictData dictData);

    /**
     * Description: 根据字典数据id删除字典数据
     *
     * @param dataId 字典数据id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:28
     */
    Integer deleteDictDataByDataId(@Param("dataId") Long dataId);

    /**
     * Description: 根据字典类型与字典值查询字典数据
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return {@link SystemDictData } 字典数据
     * @date 2022-09-23 20:56
     */
    SystemDictData selectDictDataByDictTypeAndDictValue(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * Description: 查询字典数据bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictDataBo }>  字典数据bo列表数据
     * @date 2022-09-24 00:19
     */
    List<SystemDictDataBo> selectDictDataBoList(SystemDictDataRequest requestParam);

    /**
     * Description: 根据字典类型查询字典模块名称
     *
     * @param dictType 字典类型
     * @return {@link String } 字典模块名称
     * @date 2022-10-01 20:19
     */
    String selectDictModuleByDictType(@Param("dictType") String dictType);

    /**
     * Description: 根据数据id列表删除字典数据
     *
     * @param dataIds 字典数据id列表
     * @return {@link Integer } 记录数
     * @date 2022-10-01 21:33
     */
    Integer deleteDictDataByDataIds(@Param("dataIds") List<Long> dataIds);
}
