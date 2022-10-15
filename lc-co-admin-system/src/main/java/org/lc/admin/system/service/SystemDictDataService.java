package org.lc.admin.system.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.lc.admin.system.entities.bo.SystemDictDataBo;
import org.lc.admin.system.entities.entity.SystemDictData;
import org.lc.admin.system.entities.request.SystemDictDataRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 系统字典数据service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-11 20:38
 */
public interface SystemDictDataService {
    /**
     * Description: 根据字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return {@link List }<{@link SystemDictData }> 字典数据列表
     * @date 2022-09-11 20:39
     */
    List<SystemDictData> selectDictDataByDictType(String dictType);

    /**
     * Description: 更新数据字典类型名称
     *
     * @param oldDictType 老字典类型名称
     * @param newDictType 新字典类型名称
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:36
     */
    Integer updateDictDataDictType(String oldDictType, String newDictType);

    /**
     * Description: 根据字典类型查询字典数据数量
     *
     * @param dictType 字典类型
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:45
     */
    Integer selectCntDictDataByDictType(String dictType);

    /**
     * Description: 查询字典数据列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictData }> 字典数据列表数据
     * @date 2022-09-23 20:45
     */
    List<SystemDictData> selectDictDataList(SystemDictDataRequest requestParam);

    /**
     * Description: 根据字典数据id查询字典数据
     *
     * @param dataId 字典数据id
     * @return {@link SystemDictData } 字典数据
     * @date 2022-09-23 20:46
     */
    SystemDictData selectDictDataByDataId(Long dataId);

    /**
     * Description: 插入字典数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:46
     */
    Integer insertDictData(SystemDictDataRequest requestParam);

    /**
     * Description: 插入字典数据
     *
     * @param dictData 字典数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:47
     */
    Integer insertDictData(SystemDictData dictData);

    /**
     * Description: 更新字典数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:47
     */
    Integer updateDictData(SystemDictDataRequest requestParam);

    /**
     * Description: 更新字典数据
     *
     * @param dictData 字典数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:47
     */
    Integer updateDictData(SystemDictData dictData);

    /**
     * Description: 根据字典数据id列表删除字典数据
     *
     * @param dataIds 字典数据id列表
     * @return {@link List }<{@link SystemDictData }> 字典列表数据
     * @date 2022-10-01 22:15
     */
    List<SystemDictData> deleteDictDataByDataIds(List<Long> dataIds);

    /**
     * Description: 根据字典数据id删除字典数据
     *
     * @param dataId 字典数据id
     * @return {@link SystemDictData } 字典数据
     * @date 2022-10-01 22:15
     */
    SystemDictData deleteDictDataByDataId(Long dataId);

    /**
     * Description: 添加字典数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:48
     */
    Integer addDictData(SystemDictDataRequest requestParam);

    /**
     * Description: 编辑字典数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 20:48
     */
    Integer editDictData(SystemDictDataRequest requestParam);

    /**
     * Description: 根据字典类型与字典值查询字典数据
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return {@link SystemDictData } 字典数据
     * @date 2022-09-23 20:48
     */
    SystemDictData selectDictDataByDictTypeAndDictValue(String dictType, String dictValue);

    /**
     * Description: 根据请求参数导出字典数据列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-10-07 17:56
     */
    void export(SystemDictDataRequest requestParam, HttpServletResponse response) throws IOException;

    /**
     * Description: 查询字典数据bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemDictDataBo }>  字典数据bo列表数据
     * @date 2022-09-24 00:18
     */
    List<SystemDictDataBo> selectDictDataBoList(SystemDictDataRequest requestParam);

    /**
     * Description: 根据字典类型查询字典模块名称
     *
     * @param dictType 字典类型
     * @return {@link String } 字典模块名称
     * @date 2022-10-02 11:41
     */
    String selectDictModuleByDictType(String dictType);
}
