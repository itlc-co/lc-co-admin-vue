package org.lc.admin.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.lc.admin.common.pool.GenConstantsPool;
import org.lc.admin.common.pool.UserConstantsPool;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.generator.entities.entity.DataBaseTable;
import org.lc.admin.generator.entities.entity.DataBaseTableColumn;
import org.lc.admin.generator.entities.entity.GenTable;
import org.lc.admin.generator.entities.entity.GenTableColumn;
import org.lc.admin.generator.entities.request.GenTableRequest;
import org.lc.admin.generator.mapper.GenTableMapper;
import org.lc.admin.generator.service.DataBaseTableColumnService;
import org.lc.admin.generator.service.GenTableColumnService;
import org.lc.admin.generator.service.GenTableService;
import org.lc.admin.generator.utils.GeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Description: 生成器表service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 20:57
 */
@Service
public class GenTableServiceImpl implements GenTableService {

    public static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    @Resource
    private GenTableMapper genTableMapper;

    @Resource
    private DataBaseTableColumnService dataBaseTableColumnService;

    @Resource
    private GenTableColumnService genTableColumnService;


    /**
     * Description: 根据数据库表列表导入数据库表数据
     *
     * @param tableList 数据库表列表
     * @date 2022-10-12 22:59
     */
    @Override
    public void importTable(List<DataBaseTable> tableList) {
        for (DataBaseTable dataBaseTable : tableList) {
            // 根据数据库表初始化生成器表数据
            GenTable genTable = GeneratorUtils.initGenTable(dataBaseTable);
            // 根据生成器表插入生成器表数据
            Integer row = insertGenTable(genTable);
            if (row > 0) {
                // 生成器表数据插入成功则根据表名查询数据库表列字段列表数据
                List<DataBaseTableColumn> dataBaseTableColumns = dataBaseTableColumnService.selectDataBaseTableColumnList(genTable.getTableName());
                for (DataBaseTableColumn dataBaseTableColumn : dataBaseTableColumns) {
                    // 根据数据库表列字段初始化生成器表列字段数据
                    GenTableColumn genTableColumn = GeneratorUtils.initGenTableColumn(dataBaseTableColumn);
                    if (ObjectUtil.isNotNull(genTableColumn)) {
                        // 生成器表列不为null则设置生成表id
                        genTableColumn.setTableId(genTable.getTableId());
                    }
                    // 根据生成器表列字段插入生成器表列字段数据
                    genTableColumnService.insertGenTableColumn(genTableColumn);
                }
            }
        }
    }

    /**
     * Description: 根据请求参数查询生成器表列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link GenTable }> 生成器表列表数据
     * @date 2022-10-13 16:09
     */
    @Override
    public List<GenTable> selectGenTableList(GenTableRequest requestParam) {
        return genTableMapper.selectGenTableList(requestParam);
    }

    /**
     * Description: 根据生成器表插入生成器表数据
     *
     * @param genTable 生成器表
     * @return {@link Integer } 记录数
     * @date 2022-10-13 16:10
     */
    @Override
    public Integer insertGenTable(GenTable genTable) {
        // 设置修改创建者
        String userName = StrUtil.blankToDefault(SecurityUtils.getUserName(), UserConstantsPool.DEFAULT_AUTH_USER_NAME);
        genTable.setCreateBy(userName);
        genTable.setUpdateBy(userName);
        return genTableMapper.insertGenTable(genTable);
    }

    /**
     * Description: 根据生成器表id查询生成器表数据
     *
     * @param tableId 生成器表id
     * @return {@link GenTable } 生成器表数据
     * @date 2022-10-13 17:17
     */
    @Override
    public GenTable selectGenTableById(Long tableId) {
        GenTable genTable = genTableMapper.selectGenTableById(tableId);
        // 设置生成器表其他选项数据
        setTableByOptions(genTable);
        return genTable;
    }

    /**
     * Description: 查询所有生成器表列表数据
     *
     * @return {@link List }<{@link GenTable }> 生成器表列表数据
     * @date 2022-10-13 17:37
     */
    @Override
    public List<GenTable> selectGenTableAll() {
        return genTableMapper.selectGenTableAll();
    }

    /**
     * Description: 根据生成器表名查询生成器表数据
     *
     * @param tableName 生成器表名
     * @return {@link GenTable } 生成器表数据
     * @date 2022-10-13 21:20
     */
    @Override
    public GenTable selectGenTableByName(String tableName) {
        return genTableMapper.selectGenTableByName(tableName);
    }

    /**
     * Description: 根据请求参数编辑生成器表数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:20
     */
    @Override
    public Integer edit(GenTableRequest requestParam) {
        return updateGenTable(requestParam);
    }

    /**
     * Description: 根据请求参数更新生成器表数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:24
     */
    @Override
    public Integer updateGenTable(GenTableRequest requestParam) {
        // bean转换
        GenTable genTable = BeanUtil.toBean(requestParam, GenTable.class);
        // 设置其他选项
        genTable.setOptions(JSONUtil.toJsonStr(requestParam.getParams()));
        // 更新生成器表数据
        Integer row = updateGenTable(genTable);
        if (ObjectUtil.isNotNull(row) && row > 0) {
            for (GenTableColumn genTableColumn : genTable.getColumns()) {
                // 更新生成器表列数据
                genTableColumnService.updateGenTableColumn(genTableColumn);
            }
        }
        return row;
    }

    /**
     * Description: 根据生成器表数据更新生成器表数据
     *
     * @param genTable 生成器表数据
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:24
     */
    @Override
    public Integer updateGenTable(GenTable genTable) {
        return genTableMapper.updateGenTable(genTable);
    }

    /**
     * Description: 根据生成器表主键列表删除生成器表列表数据
     *
     * @param tableIds 生成器表主键
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:51
     */
    @Override
    public Integer deleteGenTableByIds(List<Long> tableIds) {
        return genTableMapper.deleteGenTableByIds(tableIds);
    }

    /**
     * Description: 根据生成器表其他选项设置生成器表数据
     *
     * @param genTable 生成器表
     * @date 2022-10-13 17:33
     */
    @SuppressWarnings(value = {"unchecked"})
    private void setTableByOptions(GenTable genTable) {
        Map<String, Object> map = JSONUtil.toBean(genTable.getOptions(), Map.class);
        if (MapUtil.isNotEmpty(map)) {

            // 其他选项数据
            String parentMenuId = StrUtil.toString(map.get(GenConstantsPool.PARENT_MENU_ID));
            String parentMenuName = StrUtil.toString(map.get(GenConstantsPool.PARENT_MENU_NAME));

            if (StrUtil.isNotBlank(parentMenuId)) {
                // 上级菜单ID字段
                genTable.setParentMenuId(parentMenuId);
            }
            if (StrUtil.isNotBlank(parentMenuName)) {
                // 上级菜单名称字段
                genTable.setParentMenuName(parentMenuName);
            }
        }
    }

}
