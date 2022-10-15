package org.lc.admin.generator.velocity;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.velocity.VelocityContext;
import org.lc.admin.common.base.pool.StrConstantsPool;
import org.lc.admin.common.pool.GenConstantsPool;
import org.lc.admin.generator.entities.entity.GenTable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: Velocity模板引擎工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-15 18:09
 */
public class VelocityUtils {

    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java" ;

    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper" ;

    /** 默认上级菜单，系统工具 */
    private static final Long DEFAULT_PARENT_MENU_ID = 3L;


    /**
     * Description: 准备Velocity上下文环境变量
     *
     * @param genTable 生成器表
     * @return {@link VelocityContext } Velocity上下文环境
     * @date 2022-10-15 18:08
     */
    public static VelocityContext prepareContext(GenTable genTable) {
        String moduleName = genTable.getModuleName();
        String businessName = genTable.getBusinessName();
        String packageName = genTable.getPackageName();
        String tplCategory = genTable.getTplCategory();
        String functionName = genTable.getFunctionName();
        String tableName = genTable.getTableName();
        String className = genTable.getClassName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory" , tplCategory);
        velocityContext.put("tableName" , tableName);
        velocityContext.put("functionName" , StrUtil.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName" , className);
        velocityContext.put("className" , StrUtil.lowerFirst(className));
        velocityContext.put("moduleName" , moduleName);
        velocityContext.put("BusinessName" , StrUtil.upperFirst(businessName));
        velocityContext.put("businessName" , businessName);
        velocityContext.put("packageName" , packageName);
        velocityContext.put("author" , genTable.getFunctionAuthor());
        velocityContext.put("dateTime" , LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm"));
        velocityContext.put("pkColumn" , genTable.getPkColumn());
        velocityContext.put("permissionPrefix" , getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns" , genTable.getColumns());
        velocityContext.put("table" , genTable);
        velocityContext.put("dicts" , getDicts(genTable));
        setMenuVelocityContext(velocityContext, genTable);
        return velocityContext;
    }

    /**
     * Description: 设置菜单Velocity引擎上下文环境变量
     *
     * @param context  上下文
     * @param genTable 生成器表
     * @date 2022-10-15 18:07
     */
    public static void setMenuVelocityContext(VelocityContext context, GenTable genTable) {
        // 其他选项
        String options = genTable.getOptions();
        JSONObject jsonObject = JSONUtil.parseObj(options);
        Long parentMenuId = getParentMenuId(jsonObject);
        context.put("parentMenuId" , parentMenuId);
    }

    /**
     * Description: 获取父菜单id
     *
     * @param jsonObject json对象
     * @return {@link String } 父菜单id
     * @date 2022-10-15 18:06
     */
    public static Long getParentMenuId(JSONObject jsonObject) {
        Long parentMenuId = DEFAULT_PARENT_MENU_ID;
        if ( ObjectUtil.isNotNull(jsonObject) && !jsonObject.isEmpty() && StrUtil.isNotBlank(jsonObject.getStr(GenConstantsPool.PARENT_MENU_ID,StrConstantsPool.EMPTY_STR))) {
            parentMenuId = Long.parseLong(jsonObject.getStr(GenConstantsPool.PARENT_MENU_ID));
        }
        return parentMenuId;
    }

    /**
     * Description: 获得权限前缀
     *
     * @param moduleName   模块名称
     * @param businessName 业务名称
     * @return {@link String } 权限前缀
     * @date 2022-10-15 18:06
     */
    private static String getPermissionPrefix(String moduleName, String businessName) {
        return StrUtil.format("{}:{}" , moduleName, businessName);
    }

    /**
     * Description: 获取字典
     *
     * @param genTable 生成器表
     * @return {@link String } 字典多个`,`分割
     * @date 2022-10-15 18:05
     */
    public static String getDicts(GenTable genTable) {
        return StrUtil.join(StrConstantsPool.COMMA_SEPARATOR,
                genTable.getColumns()
                        .stream()
                        .map((column) -> {
                            String tmp = StrConstantsPool.EMPTY_STR;
                            if (StrUtil.isNotEmpty(column.getDictType()) && StrUtil.equalsAnyIgnoreCase(column.getHtmlType(), GenConstantsPool.HTML_TYPE_SELECT, GenConstantsPool.HTML_TYPE_RADIO, GenConstantsPool.HTML_TYPE_CHECKBOX)) {
                                tmp = "'" + column.getDictType() + "'" ;
                            }
                            return tmp;
                        })
                        .filter(StrUtil::isNotBlank)
                        .collect(Collectors.toSet())
        );
    }


    /**
     * Description: 获取模板列表
     *
     * @param tplCategory 模板类型
     * @return {@link List }<{@link String }>  模板列表
     * @date 2022-10-15 18:05
     */
    public static List<String> getTemplateList(String tplCategory) {
        List<String> templates = ListUtil.list(false,
                "vm/java/entity.java.vm" ,
                "vm/java/entityRequest.java.vm" ,
                "vm/java/entityBo.java.vm" ,
                "vm/java/exception.java.vm" ,
                "vm/java/mapper.java.vm" ,
                "vm/java/service.java.vm" ,
                "vm/java/serviceImpl.java.vm" ,
                "vm/java/controller.java.vm" ,
                "vm/xml/mapper.xml.vm" ,
                "vm/sql/sql.vm" ,
                "vm/js/api.js.vm"
        );
        if (StrUtil.equals(GenConstantsPool.TPL_CRUD, tplCategory)) {
            templates.add("vm/vue/index.vue.vm");
        } else if (StrUtil.equals(GenConstantsPool.TPL_TREE, tplCategory)) {
            templates.add("vm/vue/index-tree.vue.vm");
        } else if (StrUtil.equals(GenConstantsPool.TPL_SUB, tplCategory)) {
            templates.add("vm/vue/index.vue.vm");
            templates.add("vm/java/sub-entity.java.vm");
        }
        return templates;
    }

    /**
     * Description: 获取文件名称
     *
     * @param template 模板
     * @param genTable 生成器表
     * @return {@link String }
     * @date 2022-10-15 18:05
     */
    public static String getFileName(String template, GenTable genTable) {
        // 文件名称
        String fileName = "" ;
        // 包路径
        String packageName = genTable.getPackageName();
        // 模块名
        String moduleName = genTable.getModuleName();
        // 大写类名
        String className = genTable.getClassName();
        // 业务名称
        String businessName = genTable.getBusinessName();

        String javaPath = PROJECT_PATH + "/" + StrUtil.replace(packageName, "." , "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;
        String vuePath = "vue" ;

        if (template.contains("entity.java.vm")) {
            fileName = StrUtil.format("{}/entities/entity/{}.java" , javaPath, className);
        } else if (template.contains("entityRequest.java.vm")) {
            fileName = StrUtil.format("{}/entities/request/{}Request.java" , javaPath, className);
        } else if (template.contains("entityBo.java.vm")) {
            fileName = StrUtil.format("{}/entities/bo/{}Bo.java" , javaPath, className);
        } else if (template.contains("exception.java.vm")) {
            fileName = StrUtil.format("{}/exec/{}Exception.java" , javaPath, className);
        } else if (template.contains("mapper.java.vm")) {
            fileName = StrUtil.format("{}/mapper/{}Mapper.java" , javaPath, className);
        } else if (template.contains("service.java.vm")) {
            fileName = StrUtil.format("{}/service/{}Service.java" , javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = StrUtil.format("{}/service/impl/{}ServiceImpl.java" , javaPath, className);
        } else if (template.contains("controller.java.vm")) {
            fileName = StrUtil.format("{}/controller/{}Controller.java" , javaPath, className);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = StrUtil.format("{}/{}Mapper.xml" , mybatisPath, className);
        } else if (template.contains("sql.vm")) {
            fileName = businessName + "Menu.sql" ;
        } else if (template.contains("api.js.vm")) {
            fileName = StrUtil.format("{}/api/{}/{}.js" , vuePath, moduleName, businessName);
        } else if (template.contains("index.vue.vm")) {
            fileName = StrUtil.format("{}/views/{}/{}/index.vue" , vuePath, moduleName, businessName);
        }
        return fileName;
    }
}
