package org.lc.admin.common.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 选择树
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreeSelect implements Serializable {

    private static final long serialVersionUID = 610201200311117781L;

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

}
