package org.lc.admin.common.base.entities.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lc.admin.common.base.entities.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 请求参数基类
 *
 * @author lc-co
 * @version 1.0
 * @date 2022-08-06  18:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseRequest extends BaseEntity {

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 请求参数
     */
    private Map<String, Object> params;

    public BaseRequest(Integer orderNum, Boolean delFlag, Integer status, Long id, String createBy, LocalDateTime createTime, String updateBy, LocalDateTime updateTime, String remark) {
        super(orderNum, delFlag, status, id, createBy, createTime, updateBy, updateTime, remark);
    }

    public BaseRequest() {
    }

    public BaseRequest(Integer orderNum, Boolean delFlag, Integer status, Long id, String createBy, LocalDateTime createTime, String updateBy, LocalDateTime updateTime, String remark, String searchValue, Map<String, Object> params) {
        super(orderNum, delFlag, status, id, createBy, createTime, updateBy, updateTime, remark);
        this.searchValue = searchValue;
        this.params = params;
    }

    public BaseRequest(String searchValue, Map<String, Object> params) {
        this.searchValue = searchValue;
        this.params = params;
    }


    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>(10);
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
