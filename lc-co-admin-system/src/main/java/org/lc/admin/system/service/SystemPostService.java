package org.lc.admin.system.service;

import org.lc.admin.system.entities.bo.SystemPostBo;
import org.lc.admin.system.entities.entity.SystemPost;
import org.lc.admin.system.entities.request.SystemPostRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Description: 系统岗位service服务接口
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 09:15
 */
public interface SystemPostService {

    /**
     * Description: 查询所有岗位列表数据
     *
     * @return {@link List }<{@link SystemPost }> 所有岗位列表数据
     * @date 2022-09-23 17:21
     */
    List<SystemPost> selectPostsAll();

    /**
     * Description: 根据用户id查询岗位id列表数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link Long }> 岗位id列表数据
     * @date 2022-09-23 17:21
     */
    List<Long> selectPostIdsByUserId(Long userId);

    /**
     * Description: 查询岗位列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemPost }> 岗位列表数据
     * @date 2022-09-23 17:21
     */
    List<SystemPost> selectPostList(SystemPostRequest requestParam);

    /**
     * Description: 根据岗位id查询岗位数据
     *
     * @param postId 岗位id
     * @return {@link SystemPost } 岗位数据
     * @date 2022-09-23 17:22
     */
    SystemPost selectPostById(Long postId);

    /**
     * Description: 插入岗位数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 17:23
     */
    Integer insertPost(SystemPostRequest requestParam);

    /**
     * Description: 插入岗位数据
     *
     * @param post 岗位数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 17:23
     */
    Integer insertPost(SystemPost post);

    /**
     * Description: 根据岗位名称查询岗位数据
     *
     * @param postName 岗位名称
     * @return {@link SystemPost } 岗位数据
     * @date 2022-09-23 17:23
     */
    SystemPost selectPostByName(String postName);

    /**
     * Description: 根据岗位编码查询岗位数据
     *
     * @param postCode 岗位编码
     * @return {@link SystemPost } 岗位数据
     * @date 2022-09-23 17:24
     */
    SystemPost selectPostByCode(String postCode);

    /**
     * Description: 更新岗位数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 17:24
     */
    Integer updatePost(SystemPostRequest requestParam);

    /**
     * Description: 更新岗位数据
     *
     * @param post 岗位数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 17:25
     */
    Integer updatePost(SystemPost post);

    /**
     * Description: 根据岗位id删除岗位数据
     *
     * @param postId 岗位id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:13
     */
    Integer deletePostById(Long postId);

    /**
     * Description: 根据岗位id列表删除岗位数据
     *
     * @param postIds 岗位id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-23 17:25
     */
    Integer deletePostByIds(List<Long> postIds);

    /**
     * Description: 根据用户名称查询岗位名称集合数据
     *
     * @param userName 用户名
     * @return {@link Set }<{@link String }> 岗位名称集合数据
     * @date 2022-09-23 17:26
     */
    Set<String> selectPostNamesByUserName(String userName);

    /**
     * Description: 添加岗位数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 17:26
     */
    Integer addPost(SystemPostRequest requestParam);

    /**
     * Description: 编辑岗位数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 17:26
     */
    Integer editPost(SystemPostRequest requestParam);

    /**
     * Description: 根据请求参数导出岗位列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-10-07 17:50
     */
    void export(SystemPostRequest requestParam, HttpServletResponse response) throws IOException;

    /**
     * Description: 查询岗位Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemPostBo }> 岗位Bo列表数据
     * @date 2022-09-23 17:27
     */
    List<SystemPostBo> selectPostBoList(SystemPostRequest requestParam);
}
