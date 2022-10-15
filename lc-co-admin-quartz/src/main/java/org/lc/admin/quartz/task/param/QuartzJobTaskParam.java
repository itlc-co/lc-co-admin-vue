package org.lc.admin.quartz.task.param;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashMap;

/**
 * Description: 定时任务task参数
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-11 14:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class QuartzJobTaskParam {
    private String s;
    private Integer i;
    private Long l;
    private Character c;
    private Boolean b;
    private Double d;
}
