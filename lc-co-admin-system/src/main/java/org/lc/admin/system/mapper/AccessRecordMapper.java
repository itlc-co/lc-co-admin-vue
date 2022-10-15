package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.bo.AccessRecordBo;
import org.lc.admin.system.entities.entity.AccessRecord;
import org.lc.admin.system.entities.request.AccessRecordRequest;

import java.util.List;

/**
 * Description: 访问记录mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-22 16:53
 */
public interface AccessRecordMapper {
    /**
     * Description: 查询访问记录列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link AccessRecord }> 访问记录列表数据
     * @date 2022-09-22 16:53
     */
    List<AccessRecord> selectAccessRecordList(AccessRecordRequest requestParam);

    /**
     * Description: 根据访问记录id列表删除访问记录数据
     *
     * @param accessIds 访问id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-22 17:22
     */
    Integer deleteAccessRecordByIds(@Param("accessIds") List<Long> accessIds);

    /**
     * Description: 清空访问记录信息
     *
     * @return {@link Integer } 记录数
     * @date 2022-09-22 17:27
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
     * Description: 根据访问记录插入访问记录数据
     *
     * @param accessRecord 访问记录
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:41
     */
    Integer insertAccessRecord(AccessRecord accessRecord);

    /**
     * Description: 根据访问记录id删除访问记录数据
     *
     * @param accessId 访问id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:41
     */
    Integer deleteAccessRecordById(@Param("accessId") Long accessId);
}
