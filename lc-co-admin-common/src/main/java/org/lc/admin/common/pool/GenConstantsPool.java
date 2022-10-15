package org.lc.admin.common.pool;

/**
 * Description: 生成器常量池
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-13 17:06
 */
public class GenConstantsPool {
    /**
     * java类型字符串类型
     */
    public static final String JAVA_TYPE_STRING = "String";
    /**
     * 默认java类型
     */
    public static final String DEFAULT_JAVA_TYPE = GenConstantsPool.JAVA_TYPE_STRING;
    /**
     * 查询类型=
     */
    public static final String QUERY_TYPE_EQUALS = "EQ";
    /**
     * 默认查询类型
     */
    public static final String DEFAULT_QUERY_TYPE = GenConstantsPool.QUERY_TYPE_EQUALS;

    /**
     * 列二进制类型
     */
    public static final String[] COLUMN_TYPE_BLOB = {"blob", "bit", "binary", "tinyblob", "longblob", "mediumblob"};

    /**
     * 数据库字符串类型
     */
    public static final String[] COLUMN_TYPE_STR = {"char", "varchar", "nvarchar", "varchar2"};

    /**
     * 数据库文本类型
     */
    public static final String[] COLUMN_TYPE_TEXT = {"tinytext", "text", "mediumtext", "longtext"};

    /**
     * 数据库时间类型
     */
    public static final String[] COLUMN_TYPE_DATE_TIME = {"datetime", "timestamp"};


    /**
     * 列类型日期类型
     */
    public static final String[] COLUMN_TYPE_DATE = {"date"};
    /**
     * 列类型时间类型
     */
    public static final String[] COLUMN_TYPE_TIME = {"time"};
    /**
     * 列类型所有日期时间类型
     */
    public static final String[] COLUMN_TYPE_ALL_TIME = {"datetime", "date", "time", "timestamp"};


    /**
     * 数据库数字类型
     */
    public static final String[] COLUMN_TYPE_NUMBER = {"tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bit", "bigint", "float", "double", "decimal"};
    /**
     * java类型浮点类型
     */
    public static final String JAVA_TYPE_BIG_DECIMAL = "BigDecimal";
    /**
     * java类型整型
     */
    public static final String JAVA_TYPE_INTEGER = "Integer";
    /**
     * java类型长整形
     */
    public static final String JAVA_TYPE_LONG = "Long";
    /**
     * java类型本地日期时间
     */
    public static final String JAVA_TYPE_LOCAL_DATE_TIME = "LocalDateTime";
    /**
     * java类型本地日期
     */
    public static final String JAVA_TYPE_LOCAL_DATE = "LocalDate";
    /**
     * java类型本地时间
     */
    public static final String JAVA_TYPE_LOCAL_TIME = "LocalTime";
    /**
     * java类型字节数组
     */
    public static final String JAVA_TYPE_BYTE_ARRAY = "byte[]";
    /**
     * html类型输入框
     */
    public static final String HTML_TYPE_INPUT = "input";
    /**
     * html类型文本域
     */
    public static final String HTML_TYPE_TEXTAREAT = "textarea";
    /**
     * html类型日期时间组件
     */
    public static final String HTML_TYPE_DATE_TIME = "datetime";
    /**
     * html类型单选框
     */
    public static final String HTML_TYPE_RADIO = "radio";
    /**
     * html类型选择框
     */
    public static final String HTML_TYPE_SELECT = "select";
    /**
     * html类型复选框
     */
    public static final CharSequence HTML_TYPE_CHECKBOX = "checkbox";
    /**
     * html类型图像上传
     */
    public static final String HTML_TYPE_IMAGE_UPLOAD = "imageUpload";
    /**
     * html类型文件上传
     */
    public static final String HTML_TYPE_FILE_UPLOAD = "fileUpload";
    /**
     * html类型编辑器
     */
    public static final String HTML_TYPE_EDITOR = "editor";
    /**
     * 必需字段
     */
    public static final Character REQUIRE = '1';
    /**
     * 查询类型模糊查询
     */
    public static final String QUERY_TYPE_LIKE = "LIKE";

    /**
     * 页面不需要编辑字段
     */
    public static final String[] COLUMN_NOT_EDIT = {"id", "create_by", "create_time", "del_flag", "update_by",
            "update_time"};

    /**
     * 页面不需要显示的列表字段
     */
    public static final String[] COLUMN_NOT_LIST = {"id", "create_by", "create_time", "del_flag", "update_by",
            "update_time"};

    /**
     * 页面不需要查询字段
     */
    public static final String[] COLUMN_NOT_QUERY = {"id", "create_by", "create_time", "del_flag", "update_by",
            "update_time", "remark"};

    /**
     * 树编码字段
     */
    public static final String TREE_CODE = "treeCode";
    /**
     * 树父编码字段
     */
    public static final String TREE_PARENT_CODE = "treeParentCode";
    /**
     * 树名称字段
     */
    public static final String TREE_NAME = "treeName";
    /**
     * 上级菜单ID字段
     */
    public static final String PARENT_MENU_ID = "parentMenuId";
    /**
     * 上级菜单名称字段
     */
    public static final String PARENT_MENU_NAME = "parentMenuName";
    public static final String TPL_SUB = "sub";
    public static final String TPL_CRUD = "crud";
    public static final String TPL_TREE = "tree";

    /** Entity基类字段 */
    public static final String[] BASE_ENTITY = { "createBy", "createTime", "updateBy", "updateTime", "remark" };

    /** Tree基类字段 */
    public static final String[] TREE_ENTITY = { "parentName", "parentId", "orderNum", "ancestors", "children" };

    public static final String DEFAULT_JDBC_TYPE = "VARCHAR";

}
