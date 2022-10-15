package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;

/**
 * Description: 在线用户请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-27 09:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOnlineRequest extends BaseRequest {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * IP地址
     */
    private String ipaddr;

    /**
     * 位置
     */
    private String loginLocation;

}
