package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.entity.SystemNotice;
import org.lc.admin.system.entities.request.SystemNoticeRequest;

import java.util.List;

/**
 * Description: 系统通知mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 19:40
 */
public interface SystemNoticeMapper {
    /**
     * Description: 查询公告列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemNotice }> 公告列表数据
     * @date 2022-09-23 19:11
     */
    List<SystemNotice> selectNoticeList(SystemNoticeRequest requestParam);

    /**
     * Description: 根据公告id查询公告数据
     *
     * @param noticeId 公告id
     * @return {@link SystemNotice } 公告数据
     * @date 2022-09-23 19:13
     */
    SystemNotice selectNoticeById(@Param("noticeId") Long noticeId);

    /**
     * Description: 插入公告数据
     *
     * @param notice 公告数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:15
     */
    Integer insertNotice(SystemNotice notice);

    /**
     * Description: 更新公告数据
     *
     * @param notice 公告数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:14
     */
    Integer updateNotice(SystemNotice notice);

    /**
     * Description: 根据公告id列表删除公告数据
     *
     * @param noticeIds 公告id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:18
     */
    Integer deleteNoticeByIds(@Param("noticeIds") List<Long> noticeIds);

    /**
     * Description: 根据公告标题查询公告数据
     *
     * @param noticeTitle 公告标题
     * @return {@link SystemNotice } 公告数据
     * @date 2022-09-23 19:16
     */
    SystemNotice selectNoticeByNoticeTitle(@Param("noticeTitle") String noticeTitle);

    /**
     * Description: 根据公告id删除公告数据
     *
     * @param noticeId 公告id
     * @return {@link Integer } 公告数据
     * @date 2022-10-06 22:17
     */
    Integer deleteNoticeById(@Param("noticeId") Long noticeId);
}
