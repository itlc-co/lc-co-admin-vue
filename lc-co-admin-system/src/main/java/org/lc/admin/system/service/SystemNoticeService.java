package org.lc.admin.system.service;

import org.lc.admin.system.entities.entity.SystemNotice;
import org.lc.admin.system.entities.request.SystemNoticeRequest;

import java.util.List;

/**
 * Description: 系统通知模块service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 19:37
 */
public interface SystemNoticeService {
    /**
     * Description: 查询公告列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemNotice }> 公告列表数据
     * @date 2022-09-23 19:07
     */
    List<SystemNotice> selectNoticeList(SystemNoticeRequest requestParam);

    /**
     * Description: 根据公告id查询公告数据
     *
     * @param noticeId 公告id
     * @return {@link SystemNotice } 公告数据
     * @date 2022-09-23 19:08
     */
    SystemNotice selectNoticeById(Long noticeId);

    /**
     * Description: 插入公告数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:09
     */
    Integer insertNotice(SystemNoticeRequest requestParam);

    /**
     * Description: 插入公告数据
     *
     * @param notice 公告数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:09
     */
    Integer insertNotice(SystemNotice notice);

    /**
     * Description: 更新公告数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:09
     */
    Integer updateNotice(SystemNoticeRequest requestParam);

    /**
     * Description: 更新公告数据
     *
     * @param notice 公告数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:09
     */
    Integer updateNotice(SystemNotice notice);


    /**
     * Description: 根据公告id删除公告数据
     *
     * @param noticeId 公告id
     * @return {@link Integer } 公告数据
     * @date 2022-09-23 19:10
     */
    Integer deleteNoticeById(Long noticeId);

    /**
     * Description: 根据公告id列表删除公告数据
     *
     * @param noticeIds 公告id列表
     * @return {@link Integer } 公告数据
     * @date 2022-09-23 19:10
     */
    Integer deleteNoticeByIds(List<Long> noticeIds);

    /**
     * Description: 根据公告标题查询公告数据
     *
     * @param noticeTitle 公告标题
     * @return {@link SystemNotice } 公告数据
     * @date 2022-09-23 19:10
     */
    SystemNotice selectNoticeByNoticeTitle(String noticeTitle);

    /**
     * Description: 添加公告数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:11
     */
    Integer addNotice(SystemNoticeRequest requestParam);

    /**
     * Description: 编辑公告数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:11
     */
    Integer editNotice(SystemNoticeRequest requestParam);
}
