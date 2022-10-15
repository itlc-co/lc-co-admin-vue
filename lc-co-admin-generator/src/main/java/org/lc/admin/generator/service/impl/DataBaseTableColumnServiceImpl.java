package org.lc.admin.generator.service.impl;

import org.lc.admin.generator.entities.entity.DataBaseTableColumn;
import org.lc.admin.generator.mapper.DataBaseTableColumnMapper;
import org.lc.admin.generator.service.DataBaseTableColumnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 数据表列service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 21:47
 */
@Service
public class DataBaseTableColumnServiceImpl implements DataBaseTableColumnService {


    public static final Logger log = LoggerFactory.getLogger(DataBaseTableColumnServiceImpl.class);


    @Resource
    private DataBaseTableColumnMapper dataBaseTableColumnMapper;


    /**
     * Description: 根据数据库表名查询数据库表列字段列表
     *
     * @param tableName 数据库表名
     * @return {@link List }<{@link DataBaseTableColumn }> 数据库表列字段列表
     * @date 2022-10-12 22:13
     */
    @Override
    public List<DataBaseTableColumn> selectDataBaseTableColumnList(String tableName) {
        return dataBaseTableColumnMapper.selectDataBaseTableColumnList(tableName);
    }
}


