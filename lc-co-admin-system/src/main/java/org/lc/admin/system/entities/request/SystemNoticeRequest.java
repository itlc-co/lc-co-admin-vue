package org.lc.admin.system.entities.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lc.admin.common.base.entities.request.BaseRequest;
import org.lc.admin.common.validated.annotation.Xss;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Description: 通知请求参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-21 19:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemNoticeRequest extends BaseRequest {

    /**
     * 公告标题
     */
    @NotBlank(message = "公告标题不能为空")
    @Size(max = 50, message = "公告标题不能超过50个字符")
    @Xss(message = "公告标题不能包含脚本字符")
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private Integer noticeType;

    /**
     * 公告id
     */
    private Long noticeId;

    /**
     * 公告内容
     */
    private String noticeContent;


}
