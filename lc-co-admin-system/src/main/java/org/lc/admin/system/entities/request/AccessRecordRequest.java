package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.lc.admin.common.base.entities.request.BaseRequest;
import org.lc.admin.common.base.exec.BaseException;
import org.lc.admin.common.entities.enums.AccessRecordStatus;

/**
 * Description: 访问记录请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-24 10:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AccessRecordRequest extends BaseRequest {

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录地点
     */
    private String accessLocation;

    /**
     * 消息
     */
    private String msg;

    public AccessRecordRequest(String userName, Integer status, String msg) {
        this.userName = userName;
        this.status = status;
        this.msg = msg;
    }

    public AccessRecordRequest(String userName, AccessRecordStatus recordStatus) {
        this.userName = userName;
        this.status = recordStatus.getCode();
        this.msg = recordStatus.getMsg();
    }

    public AccessRecordRequest(String userName, BaseException exception) {
        this.userName = userName;
        this.status = 1;
        this.msg = exception.getMessage();
    }

    public AccessRecordRequest(String userName, String message) {
        this.userName = userName;
        this.status = 1;
        this.msg = message;
    }

}
