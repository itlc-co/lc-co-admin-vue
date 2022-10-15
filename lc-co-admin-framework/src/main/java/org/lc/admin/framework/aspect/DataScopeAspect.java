package org.lc.admin.framework.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.lc.admin.common.annotation.DataScope;
import org.lc.admin.common.base.entities.request.BaseRequest;
import org.lc.admin.common.entities.entity.AuthUser;
import org.lc.admin.common.entities.entity.SystemRole;
import org.lc.admin.common.entities.model.UserDetail;
import org.lc.admin.common.pool.DataScopeConstantsPool;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 数据范围aop切面处理
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-03 16:31
 */
@Aspect
@Component
@Order(1)
public class DataScopeAspect {

    public static final Logger log = LoggerFactory.getLogger(DataScopeAspect.class);

    /**
     * Description: 织入点前切
     *
     * @param joinPoint 织入点
     * @param dataScope 数据范围注解
     * @date 2022-10-03 16:48
     */
    @Before("@annotation(dataScope)")
    public void before(JoinPoint joinPoint, DataScope dataScope) {
        // 清空params.dataScope参数防止注入
        clearDataScope(joinPoint);
        // 处理数据范围
        handleDataScope(joinPoint, dataScope);
    }

    /**
     * Description: 处理数据范围
     *
     * @param joinPoint 织入点
     * @param dataScope 数据范围
     * @date 2022-10-03 16:43
     */
    private void handleDataScope(JoinPoint joinPoint, DataScope dataScope) {
        // 判断是否为admin用户
        UserDetail userDetail = SecurityUtils.getUserDetail();
        if (ObjectUtil.isNotEmpty(userDetail)) {
            AuthUser user = userDetail.getUser();
            if (ObjectUtil.isNotEmpty(user) && !user.isAdmin()) {
                // 非admin用户才需要进行数据过滤
                this.doDataScopeFilter(joinPoint, dataScope, user);
            }
        }
    }

    private void doDataScopeFilter(JoinPoint joinPoint, DataScope dataScope, AuthUser user) {
        // 数据范围注解参数
        String deptAlias = dataScope.deptAlias();
        String userAlias = dataScope.userAlias();

        // sql拼接时字符串
        StringBuilder sb = new StringBuilder();

        // 用于判断数据权限是否以及处理
        List<Integer> conditions = new ArrayList<>();

        // 用户角色列表
        List<SystemRole> roles = user.getRoles();

        for (SystemRole role : roles) {

            // 角色数据权限
            Integer dataScopeNum = role.getDataScope();

            if (DataScopeConstantsPool.DATA_SCOPE_CUSTOM.intValue() != dataScopeNum && conditions.contains(dataScopeNum)) {
                // 非自定义数据权限并且存在重复数据权限则跳过
                continue;
            }
            if (DataScopeConstantsPool.DATA_SCOPE_ALL.intValue() == dataScopeNum) {
                // 所有数据权限则无需过滤
                sb = new StringBuilder();
                break;
            } else if (DataScopeConstantsPool.DATA_SCOPE_CUSTOM.intValue() == dataScopeNum) {
                // 自定义部门数据权限
                sb.append(StrUtil.format(" OR {}.dept_id IN ( SELECT dept_id FROM system_role_dept WHERE role_id = {} ) ", deptAlias, role.getId()));
            } else if (DataScopeConstantsPool.DATA_SCOPE_DEPT.intValue() == dataScopeNum) {
                // 当前部门数据权限
                sb.append(StrUtil.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
            } else if (DataScopeConstantsPool.DATA_SCOPE_DEPT_AND_CHILD.intValue() == dataScopeNum) {
                // 当前部门以及子部门数据权限
                sb.append(StrUtil.format(" OR {}.dept_id IN ( SELECT dept_id FROM system_dept WHERE dept_id = {} OR FIND_IN_SET({},ancestors) ) "));
            } else if (DataScopeConstantsPool.DATA_SCOPE_SELF.intValue() == dataScopeNum) {
                // 仅本人数据权限
                sb.append(StrUtil.isNotBlank(userAlias) ? StrUtil.format(" OR {}.user_id = {} ", userAlias, user.getId()) : StrUtil.format(" OR {}.dept_id = 0", deptAlias));
            }
            conditions.add(dataScopeNum);
        }

        if (StrUtil.isNotBlank(sb)) {
            String sql = " AND ( " + StrUtil.subAfter(sb, "OR", false) + " ) ";
            // 获取切入点参数
            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {
                // 获取第一个参数
                Object arg = args[0];
                if (ObjectUtil.isNotEmpty(arg) && arg instanceof BaseRequest) {
                    // 参数不为空并且是BaseRequest的子类
                    BaseRequest requestParam = (BaseRequest) arg;
                    // 将请求参数中数据过滤key的value设置为sql字符串
                    requestParam.getParams().put(DataScopeConstantsPool.DATA_SCOPE, sql);
                }
            }
        }

    }

    /**
     * Description: 清空数据范围过滤参数防注入
     *
     * @param joinPoint 织入点
     * @date 2022-10-03 16:35
     */
    private void clearDataScope(JoinPoint joinPoint) {
        // 获取织入点的参数数组
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            // 获取第一个参数
            Object arg = args[0];
            if (ObjectUtil.isNotEmpty(arg) && arg instanceof BaseRequest) {
                // 参数不为空并且是BaseRequest的子类
                BaseRequest requestParam = (BaseRequest) arg;
                // 将请求参数中数据过滤key的value设置为空字符串
                requestParam.getParams().put(DataScopeConstantsPool.DATA_SCOPE, StrUtil.EMPTY);
            }
        }
    }


}
