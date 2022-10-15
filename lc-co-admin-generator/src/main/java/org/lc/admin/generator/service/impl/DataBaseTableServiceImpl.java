package org.lc.admin.generator.service.impl;

import org.lc.admin.generator.entities.entity.DataBaseTable;
import org.lc.admin.generator.entities.request.DataBaseTableRequest;
import org.lc.admin.generator.mapper.DataBaseTableMapper;
import org.lc.admin.generator.service.DataBaseTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 数据表service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-12 21:47
 */
@Service
public class DataBaseTableServiceImpl implements DataBaseTableService {

    public static final Logger log = LoggerFactory.getLogger(DataBaseTableServiceImpl.class);

    @Resource
    private DataBaseTableMapper dataBaseTableMapper;

    /**
     * Description: 根据请求参数查询数据库表列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link DataBaseTable }> 数据库表列表数据
     * @date 2022-10-12 21:53
     */
    @Override
    public List<DataBaseTable> selectDataBaseTableList(DataBaseTableRequest requestParam) {
        return dataBaseTableMapper.selectDataBaseTableList(requestParam);
    }

    /**
     * Description: 根据数据库表名列表查询数据库表列表数据
     *
     * @param tableNames 表名
     * @return {@link List }<{@link DataBaseTable }> 数据库表列表数据
     * @date 2022-10-12 22:41
     */
    @Override
    public List<DataBaseTable> selectDataBaseTableListByTableNames(List<String> tableNames) {
        return dataBaseTableMapper.selectDataBaseTableListByTableNames(tableNames);
    }
}
