package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.exec.NoticeException;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.entity.SystemNotice;
import org.lc.admin.system.entities.request.SystemNoticeRequest;
import org.lc.admin.system.mapper.SystemNoticeMapper;
import org.lc.admin.system.service.SystemNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 系统通知service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 19:38
 */
@Service
public class SystemNoticeServiceImpl implements SystemNoticeService {

    public static final Logger log = LoggerFactory.getLogger(SystemNoticeServiceImpl.class);

    @Resource
    private SystemNoticeMapper noticeMapper;


    /**
     * Description: 查询公告列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemNotice }> 公告列表数据
     * @date 2022-09-23 19:11
     */
    @Override
    public List<SystemNotice> selectNoticeList(SystemNoticeRequest requestParam) {
        return this.noticeMapper.selectNoticeList(requestParam);
    }

    /**
     * Description: 根据公告id查询公告数据
     *
     * @param noticeId 公告id
     * @return {@link SystemNotice } 公告数据
     * @date 2022-09-23 19:13
     */
    @Override
    public SystemNotice selectNoticeById(Long noticeId) {
        return this.noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * Description: 更新公告数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:13
     */
    @Override
    public Integer updateNotice(SystemNoticeRequest requestParam) {
        SystemNotice notice = BeanUtil.toBean(requestParam, SystemNotice.class);
        notice.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getNoticeId()));
        return this.updateNotice(notice);
    }

    /**
     * Description: 更新公告数据
     *
     * @param notice 公告数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:13
     */
    @Override
    public Integer updateNotice(SystemNotice notice) {
        String userName = SecurityUtils.getUserName();
        notice.setUpdateBy(userName);
        return this.noticeMapper.updateNotice(notice);
    }

    /**
     * Description: 添加公告数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:14
     */
    @Override
    public Integer addNotice(SystemNoticeRequest requestParam) {
        // 检查公告标题唯一性
        if (this.checkNoticeTitleUnique(requestParam)) {
            throw new NoticeException(StatusMsg.NOTICE_TIME_NOT_UNIQUE);
        }
        return this.insertNotice(requestParam);
    }

    /**
     * Description: 编辑公告数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:16
     */
    @Override
    public Integer editNotice(SystemNoticeRequest requestParam) {
        // 检查公告标题唯一性
        if (this.checkNoticeTitleUnique(requestParam)) {
            throw new NoticeException(StatusMsg.NOTICE_TIME_NOT_UNIQUE);
        }
        return this.updateNotice(requestParam);
    }

    /**
     * Description: 根据公告标题查询公告数据
     *
     * @param noticeTitle 公告标题
     * @return {@link SystemNotice } 公告数据
     * @date 2022-09-23 19:16
     */
    @Override
    public SystemNotice selectNoticeByNoticeTitle(String noticeTitle) {
        return this.noticeMapper.selectNoticeByNoticeTitle(noticeTitle);
    }

    /**
     * Description: 根据公告id删除公告数据
     *
     * @param noticeId 公告id
     * @return {@link Integer } 公告数据
     * @date 2022-10-06 22:16
     */
    @Override
    public Integer deleteNoticeById(Long noticeId) {
        return this.noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * Description: 根据公告id列表删除公告数据
     *
     * @param noticeIds 公告id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:17
     */
    @Override
    public Integer deleteNoticeByIds(List<Long> noticeIds) {
        return this.noticeMapper.deleteNoticeByIds(noticeIds);
    }


    /**
     * Description: 插入公告数据
     *
     * @param notice 公告数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:15
     */
    @Override
    public Integer insertNotice(SystemNotice notice) {
        String userName = SecurityUtils.getUserName();
        notice.setCreateBy(userName);
        notice.setUpdateBy(userName);
        return this.noticeMapper.insertNotice(notice);
    }

    /**
     * Description: 插入公告数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 19:15
     */
    @Override
    public Integer insertNotice(SystemNoticeRequest requestParam) {
        return this.insertNotice(BeanUtil.toBean(requestParam, SystemNotice.class));
    }

    /**
     * Description: 检查公告标题唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-23 19:17
     */
    private boolean checkNoticeTitleUnique(SystemNoticeRequest requestParam) {
        // 防止空指针 前端可能传noticeId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getNoticeId());
        Long noticeId = ObjectUtil.isNull(id) ? -1L : id;

        // 公告标题加载公告信息
        String noticeTitle = requestParam.getNoticeTitle();
        SystemNotice notice = this.selectNoticeByNoticeTitle(noticeTitle);

        // 是否唯一
        return ObjectUtil.isNotNull(notice) && !ObjectUtil.equals(noticeId, notice.getId());
    }

}
