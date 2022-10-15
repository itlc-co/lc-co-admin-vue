package org.lc.admin.system.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.lc.admin.system.entities.bo.AccessRecordBo;
import org.lc.admin.system.entities.entity.AccessRecord;
import org.lc.admin.system.entities.request.AccessRecordRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 访问记录service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 16:50
 */
public interface AccessRecordService {
    /**
     * Description: 查询访问记录列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link AccessRecord }> 访问记录列表数据
     * @date 2022-09-22 16:51
     */
    List<AccessRecord> selectAccessRecordList(AccessRecordRequest requestParam);

    /**
     * Description: 根据访问记录id列表删除访问记录数据
     *
     * @param accessIds 访问id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-22 17:20
     */
    Integer deleteAccessRecordByIds(List<Long> accessIds);

    /**
     * Description: 根据访问记录id删除访问记录数据
     *
     * @param accessId 访问id
     * @return {@link Integer } 记录数
     * @date 2022-09-22 17:20
     */
    Integer deleteAccessRecordById(Long accessId);

    /**
     * Description: 清空访问记录信息
     *
     * @return {@link Integer } 记录数
     * @date 2022-09-22 17:25
     */
    Integer cleanAccessRecord();

    /**
     * Description: 查询访问记录bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link AccessRecordBo }> 访问记录bo列表数据
     * @date 2022-09-23 22:42
     */
    List<AccessRecordBo> selectAccessRecordBoList(AccessRecordRequest requestParam);

    /**
     * Description: 根据请求参数添加访问记录数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-28 09:33
     */
    Integer add(AccessRecordRequest requestParam);

    /**
     * Description: 根据访问记录插入访问记录数据
     *
     * @param accessRecord 访问记录
     * @return {@link Integer } 记录数
     * @date 2022-09-28 09:24
     */
    Integer insertAccessRecord(AccessRecord accessRecord);

    /**
     * Description: 根据请求参数插入访问记录数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-28 09:33
     */
    Integer insertAccessRecord(AccessRecordRequest requestParam);

    /**
     * Description: 根据请求参数导出访问记录列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-10-11 17:38
     */
    void export(AccessRecordRequest requestParam, HttpServletResponse response) throws IOException;
}
