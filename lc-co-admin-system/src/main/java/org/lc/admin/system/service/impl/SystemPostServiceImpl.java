package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.common.exec.PostException;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.system.entities.bo.SystemPostBo;
import org.lc.admin.system.entities.entity.SystemPost;
import org.lc.admin.system.entities.request.SystemPostRequest;
import org.lc.admin.system.mapper.SystemPostMapper;
import org.lc.admin.system.service.SystemPostService;
import org.lc.admin.system.service.SystemUserPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Description: 系统岗位service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-13 10:22
 */
@Service
public class SystemPostServiceImpl implements SystemPostService {

    @Resource
    private SystemPostMapper postMapper;

    @Resource
    private SystemUserPostService userPostService;

    /**
     * Description: 根据用户id查询岗位id列表数据
     *
     * @param userId 用户id
     * @return {@link List }<{@link Long }> 岗位id列表数据
     * @date 2022-09-13 10:54
     */
    @Override
    public List<Long> selectPostIdsByUserId(Long userId) {
        return this.postMapper.selectPostIdsByUserId(userId);
    }

    /**
     * Description: 查询岗位列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemPost }> 岗位列表数据
     * @date 2022-09-23 18:46
     */
    @Override
    public List<SystemPost> selectPostList(SystemPostRequest requestParam) {
        return this.postMapper.selectPostList(requestParam);
    }

    /**
     * Description: 根据岗位id查询岗位数据
     *
     * @param postId 岗位id
     * @return {@link SystemPost } 岗位数据
     * @date 2022-09-23 18:47
     */
    @Override
    public SystemPost selectPostById(Long postId) {
        return this.postMapper.selectPostById(postId);
    }

    /**
     * Description: 插入岗位数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:47
     */
    @Override
    public Integer insertPost(SystemPostRequest requestParam) {
        return this.insertPost(BeanUtil.toBean(requestParam, SystemPost.class));
    }

    /**
     * Description: 检查岗位编码唯一性
     *
     * @param requestParam 请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-23 18:50
     */
    private boolean checkPostCodeUnique(SystemPostRequest requestParam) {
        // 防止空指针 前端可能传postId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getPostId(), requestParam.getId());
        Long postId = ObjectUtil.isNull(id) ? -1L : id;

        // 岗位名称加载岗位信息
        String postCode = requestParam.getPostCode();
        SystemPost post = this.selectPostByCode(postCode);

        // 是否唯一
        return ObjectUtil.isNotNull(post) && !ObjectUtil.equals(postId, post.getId());

    }

    /**
     * Description: 检查岗位名称唯一性
     *
     * @param requestParam 岗位请求参数
     * @return boolean true 不唯一 false 唯一
     * @date 2022-09-21 12:51
     */
    private boolean checkPostNameUnique(SystemPostRequest requestParam) {
        // 防止空指针 前端可能传postId或者id
        Long id = ObjectUtil.defaultIfNull(requestParam.getPostId(), requestParam.getId());
        Long postId = ObjectUtil.isNull(id) ? -1L : id;

        // 岗位名称加载岗位信息
        String postName = requestParam.getPostName();
        SystemPost post = this.selectPostByName(postName);

        // 是否唯一
        return ObjectUtil.isNotNull(post) && !ObjectUtil.equals(postId, post.getId());

    }

    /**
     * Description: 插入岗位数据
     *
     * @param post 岗位数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:47
     */
    @Override
    public Integer insertPost(SystemPost post) {
        // 设置创建修改者
        String userName = SecurityUtils.getUserName();
        post.setCreateBy(userName);
        post.setUpdateBy(userName);
        return this.postMapper.insertPost(post);
    }

    /**
     * Description: 根据岗位名称查询岗位数据
     *
     * @param postName 岗位名称
     * @return {@link SystemPost } 岗位数据
     * @date 2022-09-23 18:51
     */
    @Override
    public SystemPost selectPostByName(String postName) {
        return this.postMapper.selectPostByName(postName);
    }

    /**
     * Description: 根据岗位编码查询岗位数据
     *
     * @param postCode 岗位编码
     * @return {@link SystemPost } 岗位数据
     * @date 2022-09-23 18:51
     */
    @Override
    public SystemPost selectPostByCode(String postCode) {
        return this.postMapper.selectPostByCode(postCode);
    }

    /**
     * Description: 更新岗位数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:51
     */
    @Override
    public Integer updatePost(SystemPostRequest requestParam) {
        // bean实体转换并设置id
        SystemPost post = BeanUtil.toBean(requestParam, SystemPost.class);
        post.setId(ObjectUtil.defaultIfNull(requestParam.getId(), requestParam.getPostId()));
        return this.updatePost(post);
    }


    /**
     * Description: 更新岗位数据
     *
     * @param post 岗位数据
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:51
     */
    @Override
    public Integer updatePost(SystemPost post) {
        // 设置修改者
        String userName = SecurityUtils.getUserName();
        post.setUpdateBy(userName);
        return this.postMapper.updatePost(post);
    }

    /**
     * Description: 根据岗位id删除岗位数据
     *
     * @param postId 岗位id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:13
     */
    @Override
    public Integer deletePostById(Long postId) {
        // 判断当前岗位使用存在用户
        SystemPost post = this.selectPostById(postId);
        if (this.selectCntUserByPostId(postId) > 0) {
            throw new PostException(StatusMsg.POST_EXIST_USER.getCode(), StrUtil.format("{}" + StatusMsg.POST_EXIST_USER.getMsg(), post.getPostName()));
        }
        return this.postMapper.deletePostById(postId);
    }

    /**
     * Description: 根据岗位id列表删除岗位数据
     *
     * @param postIds 岗位id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:52
     */
    @Override
    public Integer deletePostByIds(List<Long> postIds) {
        postIds.forEach((postId) -> {
            // 判断当前岗位使用存在用户
            SystemPost post = this.selectPostById(postId);
            if (this.selectCntUserByPostId(postId) > 0) {
                throw new PostException(StatusMsg.POST_EXIST_USER.getCode(), StrUtil.format("{}" + StatusMsg.POST_EXIST_USER.getMsg(), post.getPostName()));
            }
        });
        return this.postMapper.deletePostByIds(postIds);
    }

    /**
     * Description: 根据用户名称查询岗位名称集合数据
     *
     * @param userName 用户名
     * @return {@link Set }<{@link String }> 岗位名称集合数据
     * @date 2022-09-23 18:52
     */
    @Override
    public Set<String> selectPostNamesByUserName(String userName) {
        return this.postMapper.selectPostNamesByUserName(userName);
    }

    /**
     * Description: 添加岗位数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:52
     */
    @Override
    public Integer addPost(SystemPostRequest requestParam) {
        // 检查岗位名称唯一性
        if (this.checkPostNameUnique(requestParam)) {
            throw new PostException(StatusMsg.POST_NAME_NOT_UNIQUE);
        }
        // 检查岗位编码唯一性
        if (this.checkPostCodeUnique(requestParam)) {
            throw new PostException(StatusMsg.POST_CODE_NOT_UNIQUE);
        }
        return this.insertPost(requestParam);
    }

    /**
     * Description: 根据请求参数导出岗位列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @throws IOException io异常
     * @date 2022-09-23 18:53
     */
    @Override
    public void export(SystemPostRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, this.selectPostBoList(requestParam), SystemPostBo.class, "岗位信息");
    }

    /**
     * Description: 查询岗位Bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link SystemPostBo }> 岗位Bo列表数据
     * @date 2022-09-23 18:53
     */
    @Override
    public List<SystemPostBo> selectPostBoList(SystemPostRequest requestParam) {
        return this.postMapper.selectPostBoList(requestParam);
    }

    /**
     * Description: 编辑岗位数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:54
     */
    @Override
    public Integer editPost(SystemPostRequest requestParam) {
        // 检查岗位名称唯一性
        if (this.checkPostNameUnique(requestParam)) {
            throw new PostException(StatusMsg.POST_NAME_NOT_UNIQUE);
        }
        // 检查岗位编码唯一性
        if (this.checkPostCodeUnique(requestParam)) {
            throw new PostException(StatusMsg.POST_CODE_NOT_UNIQUE);
        }
        return updatePost(requestParam);
    }

    /**
     * Description: 根据岗位id查询用户数量
     *
     * @param postId 岗位id
     * @return {@link Integer } 记录数
     * @date 2022-09-23 18:54
     */
    private Integer selectCntUserByPostId(Long postId) {
        return this.userPostService.selectCntUserByPostId(postId);
    }


    /**
     * Description: 查询所有岗位列表数据
     *
     * @return {@link List }<{@link SystemPost }> 所有岗位列表数据
     * @date 2022-09-13 09:52
     */
    @Override
    public List<SystemPost> selectPostsAll() {
        return this.postMapper.selectPostsAll();
    }
}
