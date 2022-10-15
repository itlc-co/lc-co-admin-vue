package org.lc.admin.system.entities.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * Description: 访问记录
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class AccessRecord extends BaseEntity {

    /**
     * 用户账号
     */
    private String userName;

    /**
     * ip地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String accessLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private LocalDateTime accessTime;


}
