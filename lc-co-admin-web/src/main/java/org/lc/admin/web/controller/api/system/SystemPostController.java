package org.lc.admin.web.controller.api.system;

import org.lc.admin.common.annotation.Log;
import org.lc.admin.common.annotation.Pagination;
import org.lc.admin.common.base.controller.BaseController;
import org.lc.admin.common.base.entities.enums.StatusMsg;
import org.lc.admin.common.base.entities.response.ResultResponse;
import org.lc.admin.common.entities.enums.BusinessType;
import org.lc.admin.common.entities.page.TableData;
import org.lc.admin.common.exec.FileException;
import org.lc.admin.system.entities.request.SystemPostRequest;
import org.lc.admin.system.service.SystemPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 系统岗位controller控制器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 11:17
 */
@RequestMapping("/system/post")
@RestController
@Validated
public class SystemPostController extends BaseController {

    public static final String MODULE = "system_post";

    public static final Logger log = LoggerFactory.getLogger(SystemPostController.class);

    @Resource
    private SystemPostService postService;

    /**
     * Description: 查询岗位列表数据接口
     *
     * @param requestParam 请求参数
     * @return {@link TableData } 表格数据
     * @date 2022-09-23 11:28
     */
    @GetMapping("/list")
    @Pagination
    @PreAuthorize("@permissionService.hasPermission('system:post:list')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public TableData list(SystemPostRequest requestParam) {
        return getDataTable(this.postService.selectPostList(requestParam));
    }

    /**
     * Description: 查询岗位详情数据接口
     *
     * @param postId 岗位id
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 11:28
     */
    @GetMapping(value = "/{postId}")
    @PreAuthorize("@permissionService.hasPermission('system:post:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse post(@PathVariable("postId") Long postId) {
        return success(this.postService.selectPostById(postId));
    }

    /**
     * Description: 添加岗位数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 11:29
     */
    @PostMapping()
    @PreAuthorize("@permissionService.hasPermission('system:post:add')")
    @Log(module = MODULE, businessType = BusinessType.INSERT)
    public ResultResponse add(@Validated @RequestBody SystemPostRequest requestParam) {
        return toResponse(this.postService.addPost(requestParam));
    }

    /**
     * Description: 编辑岗位数据接口
     *
     * @param requestParam 请求参数
     * @return {@link ResultResponse }
     * @date 2022-09-23 11:29
     */
    @PutMapping()
    @PreAuthorize("@permissionService.hasPermission('system:post:adit')")
    @Log(module = MODULE, businessType = BusinessType.UPDATE)
    public ResultResponse edit(@Validated @RequestBody SystemPostRequest requestParam) {
        return toResponse(this.postService.editPost(requestParam));
    }

    /**
     * Description: 删除岗位列表数据接口
     *
     * @param postIds 岗位id列表
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 11:30
     */
    @DeleteMapping("/{postIds}")
    @PreAuthorize("@permissionService.hasPermission('system:post:remove')")
    @Log(module = MODULE, businessType = BusinessType.DELETE)
    public ResultResponse delete(@PathVariable("postIds") List<Long> postIds) {
        this.postService.deletePostByIds(postIds);
        return success();
    }

    /**
     * Description: 查询岗位选项选择列表数据接口
     *
     * @return {@link ResultResponse } 结果集响应
     * @date 2022-09-23 11:31
     */
    @GetMapping("/optionSelect")
    @PreAuthorize("@permissionService.hasPermission('system:post:query')")
    @Log(module = MODULE, businessType = BusinessType.SELECT)
    public ResultResponse optionSelect() {
        return success(this.postService.selectPostsAll());
    }

    /**
     * Description: 导出岗位列表数据接口
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-09-23 11:31
     */
    @PostMapping("/export")
    @PreAuthorize("@permissionService.hasPermission('system:post:export')")
    @Log(module = MODULE, businessType = BusinessType.EXPORT)
    public void export(SystemPostRequest requestParam, HttpServletResponse response) {
        try {
            // 根据请求参数导出岗位列表数据
            this.postService.export(requestParam, response);
        } catch (IOException e) {
            throw new FileException(StatusMsg.EXCEL_EXPORT_ERROR);
        }
    }

}
