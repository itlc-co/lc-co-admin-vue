package org.lc.admin.common.entities.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.lc.admin.common.entities.server.entity.System;
import org.lc.admin.common.entities.server.entity.*;

import java.util.List;

/**
 * Description: 服务器
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-26 15:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class Server {

    /**
     * 内存数据
     */
    private Memory memory;

    /**
     * cpu数据
     */
    private Cpu cpu;

    /**
     * jvm数据
     */
    private Jvm jvm;

    /**
     * 操作系统数据
     */
    private Os os;

    /**
     * 项目系统数据
     */
    private System system;

    /**
     * 文件系统列表
     */
    private List<SystemFile> systemFiles;


}
