package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.entity.SystemUserPost;

import java.util.List;

/**
 * Description: 系统用户岗位关联mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 19:34
 */
public interface SystemUserPostMapper {
    /**
     * Description: 添加用户岗位关联列表数据
     *
     * @param userPosts 用户岗位关联列表数据
     * @return {@link Integer } 记录数
     * @date 2022-09-13 19:35
     */
    Integer batchUserPost(@Param("userPosts") List<SystemUserPost> userPosts);

    /**
     * Description: 根据用户id删除用户岗位关联数据
     *
     * @param userId 用户id
     * @return {@link Integer } 记录数
     * @date 2022-09-13 22:13
     */
    Integer deleteUserPostsByUserId(@Param("userId") Long userId);

    /**
     * Description: 根据用户id列表删除用户岗位关联数据
     *
     * @param userIds 用户id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-14 09:51
     */
    Integer deleteUserPostsByUserIds(@Param("userIds") List<Long> userIds);

    /**
     * Description: 根据岗位id查询用户数量
     *
     * @param postId 岗位id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 22:55
     */
    Integer selectCntUserByPostId(@Param("postId") Long postId);
}
