package org.lc.admin.generator.service.impl;

import cn.hutool.core.util.StrUtil;
import org.lc.admin.common.pool.UserConstantsPool;
import org.lc.admin.common.utils.system.SecurityUtils;
import org.lc.admin.generator.entities.entity.GenTableColumn;
import org.lc.admin.generator.mapper.GenTableColumnMapper;
import org.lc.admin.generator.service.GenTableColumnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 生成器表字段service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 20:57
 */
@Service
public class GenTableColumnServiceImpl implements GenTableColumnService {

    public static final Logger log = LoggerFactory.getLogger(GenTableColumnServiceImpl.class);

    @Resource
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * Description:根据生成器表列字段插入生成器表列字段数据
     *
     * @param genTableColumn 生成器表列字段
     * @return {@link Integer } 记录数
     * @date 2022-10-13 16:49
     */
    @Override
    public Integer insertGenTableColumn(GenTableColumn genTableColumn) {
        // 设置创建修改者
        String userName = StrUtil.blankToDefault(SecurityUtils.getUserName(), UserConstantsPool.DEFAULT_AUTH_USER_NAME);
        genTableColumn.setCreateBy(userName);
        genTableColumn.setUpdateBy(userName);
        return genTableColumnMapper.insertGenTableColumn(genTableColumn);
    }


    /**
     * Description: 根据生成器表id查询生成器表列列表数据
     *
     * @param tableId 生成器表id
     * @return {@link List }<{@link GenTableColumn }> 生成器表列列表数据
     * @date 2022-10-13 17:45
     */
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
        return genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
    }

    /**
     * Description: 根据生成器表列数据更新生成器表列数据
     *
     * @param genTableColumn 生成器表列数据
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:30
     */
    @Override
    public Integer updateGenTableColumn(GenTableColumn genTableColumn) {
        return genTableColumnMapper.updateGenTableColumn(genTableColumn);
    }

    /**
     * Description: 根据生成器表主键列表删除生成器表列数据
     *
     * @param tableIds 生成器表主键列表
     * @return {@link Integer } 记录数
     * @date 2022-10-15 10:53
     */
    @Override
    public Integer deleteGenTableColumnByTableIds(List<Long> tableIds) {
        return genTableColumnMapper.deleteGenTableColumnByTableIds(tableIds);
    }

    /**
     * Description: 根据生成器表列主键列表删除生成器表列列表数据
     *
     * @param columnIds 生成器表列主键列表
     * @return {@link Integer } 记录数
     * @date 2022-10-15 13:03
     */
    @Override
    public Integer deleteGenTableColumnByIds(List<Long> columnIds) {
        return genTableColumnMapper.deleteGenTableColumnByIds(columnIds);
    }

    /**
     * Description: 根据生成器表主键删除生成器表列数据
     *
     * @param tableId 生成器表主键
     * @return {@link Integer } 记录数
     * @date 2022-10-15 17:18
     */
    @Override
    public Integer deleteGenTableColumnByTableId(Long tableId) {
        return genTableColumnMapper.deleteGenTableColumnByTableId(tableId);
    }

    /**
     * Description: 根据生成器表列列表数据插入生成器表列列表数据
     *
     * @param genTableColumns 生成器表列列表数据
     * @return {@link Integer } 记录数
     * @date 2022-10-15 17:24
     */
    @Override
    public Integer insertGenTableColumn(List<GenTableColumn> genTableColumns) {
        return genTableColumns.stream().map(this::insertGenTableColumn).reduce((num1, num2) -> num1 + num1).orElse(0);
    }

}
