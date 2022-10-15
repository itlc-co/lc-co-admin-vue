package org.lc.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.lc.admin.system.entities.bo.SystemPostBo;
import org.lc.admin.system.entities.entity.SystemPost;
import org.lc.admin.system.entities.request.SystemPostRequest;

import java.util.List;
import java.util.Set;

/**
 * Description: 系统岗位mapper接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 10:22
 */
public interface SystemPostMapper {

    /**
     * Description: 查询所有岗位列表
     *
     * @return {@link List }<{@link SystemPost }> 岗位列表
     * @date 2022-09-13 09:49
     */
    List<SystemPost> selectPostsAll();

    /**
     * Description: 根据用户id查询岗位id列表数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link Long }> 岗位id列表数据
     * @date 2022-09-13 10:54
     */
    List<Long> selectPostIdsByUserId(@Param("userId") Long userId);

    /**
     * Description: 查询岗位列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemPost }> 岗位列表数据
     * @date 2022-09-23 18:55
     */
    List<SystemPost> selectPostList(SystemPostRequest requestParam);

    /**
     * Description: 根据岗位id查询岗位数据
     *
     * @param postId 岗位id
     * @return {@link SystemPost } 岗位数据
     * @date 2022-09-23 18:55
     */
    SystemPost selectPostById(@Param("postId") Long postId);

    /**
     * Description: 插入岗位数据
     *
     * @param post 岗位数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:58
     */
    Integer insertPost(SystemPost post);

    /**
     * Description: 根据岗位名称查询岗位数据
     *
     * @param postName 岗位名称
     * @return {@link SystemPost } 岗位数据
     * @date 2022-09-23 18:59
     */
    SystemPost selectPostByName(@Param("postName") String postName);

    /**
     * Description: 根据岗位编码查询岗位数据
     *
     * @param postCode 岗位编码
     * @return {@link SystemPost } 岗位数据
     * @date 2022-09-23 18:59
     */
    SystemPost selectPostByCode(@Param("postCode") String postCode);

    /**
     * Description: 更新岗位数据
     *
     * @param post 岗位数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:59
     */
    Integer updatePost(SystemPost post);

    /**
     * Description: 根据岗位id列表删除岗位数据
     *
     * @param postIds 岗位id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:59
     */
    Integer deletePostByIds(@Param("postIds") List<Long> postIds);

    /**
     * Description: 根据用户名称查询岗位名称集合数据
     *
     * @param userName 用户名
     * @return {@link Set }<{@link String }> 岗位名称集合数据
     * @date 2022-09-23 19:00
     */
    Set<String> selectPostNamesByUserName(@Param("userName") String userName);

    /**
     * Description: 查询岗位Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemPostBo }> 岗位Bo列表数据
     * @date 2022-09-23 19:00
     */
    List<SystemPostBo> selectPostBoList(SystemPostRequest requestParam);

    /**
     * Description: 根据岗位id删除岗位数据
     *
     * @param postId 岗位id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:14
     */
    Integer deletePostById(@Param("postId") Long postId);
}
