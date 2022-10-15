package org.lc.admin.system.service.impl;

import org.lc.admin.system.entities.entity.SystemUserPost;
import org.lc.admin.system.mapper.SystemUserPostMapper;
import org.lc.admin.system.service.SystemUserPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 系统用户岗位关联service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 19:33
 */
@Service
public class SystemUserPostServiceImpl implements SystemUserPostService {

    @Resource
    private SystemUserPostMapper userPostMapper;

    /**
     * Description: 根据用户id列表删除用户岗位关联数据
     *
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:49
     */
    @Override
    public Integer deleteUserPostsByUserIds(List<Long> userIds) {
        return this.userPostMapper.deleteUserPostsByUserIds(userIds);
    }

    /**
     * Description: 根据岗位id查询用户数量
     *
     * @param postId 岗位id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 22:55
     */
    @Override
    public Integer selectCntUserByPostId(Long postId) {
        return this.userPostMapper.selectCntUserByPostId(postId);
    }

    /**
     * Description: 根据用户id删除用户岗位关联数据
     *
     * @param userId 用户id
     * @return {@link Integer } 记录数
     * @date 2022-09-13 22:13
     */
    @Override
    public Integer deleteUserPostsByUserId(Long userId) {
        return this.userPostMapper.deleteUserPostsByUserId(userId);
    }

    /**
     * Description: 添加用户岗位关联列表数据
     *
     * @param userPosts 用户岗位关联列表数据
     * @return {@link Integer } 记录数
     * @date 2022-09-13 19:35
     */
    @Override
    public Integer batchUserPost(List<SystemUserPost> userPosts) {
        return userPostMapper.batchUserPost(userPosts);
    }
}
