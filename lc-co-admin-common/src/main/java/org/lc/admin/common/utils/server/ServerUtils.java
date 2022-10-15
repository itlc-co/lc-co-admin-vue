package org.lc.admin.common.utils.server;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.OshiUtil;
import org.lc.admin.common.entities.server.Server;
import org.lc.admin.common.entities.server.entity.System;
import org.lc.admin.common.entities.server.entity.*;
import org.lc.admin.common.utils.AssertUtils;
import org.lc.admin.common.utils.ConvertUtils;
import org.lc.admin.common.utils.NumberUtils;
import org.lc.admin.common.utils.servlet.ServletUtils;
import oshi.hardware.GlobalMemory;

import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 服务器工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-10-02 15:20
 */
public class ServerUtils {

    /**
     * Description: 服务器信息数据
     *
     * @return {@link Server } 服务器信息数据
     * @date 2022-09-26 15:02
     */
    public static Server server() {
        return Server.builder()
                // cpu 信息
                .cpu(builderCpu())
                // jvm 信息
                .jvm(builderJvm())
                // memory 内存信息
                .memory(builderMemory())
                // os 操作系统信息
                .os(builderOs())
                // 项目系统信息
                .system(builderSystem())
                // os文件系统信息
                .systemFiles(builderSystemFiles())
                .build();
    }


    /**
     * Description: 构建cpu信息
     *
     * @return {@link Cpu } cpu信息数据
     * @date 2022-09-26 14:45
     */
    private static Cpu builderCpu() {
        return BeanUtil.toBean(OshiUtil.getCpuInfo(), Cpu.class);
    }

    /**
     * Description: 构建文件系统列表信息数据
     *
     * @return {@link List }<{@link SystemFile }> 文件系统列表信息数据
     * @date 2022-09-26 14:58
     */
    private static List<SystemFile> builderSystemFiles() {
        return OshiUtil.getOs().getFileSystem().getFileStores().stream().map((fileStore) -> {
            // 文件系统total总量，free空闲量，used使用量
            long free = fileStore.getUsableSpace();
            long total = fileStore.getTotalSpace();
            long used = total - free;
            return SystemFile.builder()
                    // 磁盘名称
                    .dirName(fileStore.getMount())
                    // 磁盘类型名称
                    .dirTypeName(fileStore.getType())
                    // 类型名称
                    .typeName(fileStore.getName())
                    // total总量
                    .total(ConvertUtils.convertSizeStr(total))
                    // free空闲量
                    .free(ConvertUtils.convertSizeStr(free))
                    // used使用量
                    .used(ConvertUtils.convertSizeStr(used))
                    // 使用率
                    .usage(NumberUtils.percentageScale2(used, total))
                    .build();
        }).collect(Collectors.toList());
    }


    /**
     * Description: 构建项目系统信息数据
     *
     * @return {@link System } 项目系统信息数据
     * @date 2022-09-26 14:51
     */
    private static System builderSystem() {
        return System.builder()
                // 工作目录
                .workDir(SystemUtil.getUserInfo().getCurrentDir())
                // 项目系统
                .computerIp(SystemUtil.getHostInfo().getAddress())
                // 项目系统名称
                .computerName(SystemUtil.getHostInfo().getName())
                .build();
    }

    /**
     * Description: 构建jvm数据
     *
     * @return {@link Jvm } jvm数据
     * @date 2022-09-26 15:11
     */
    private static Jvm builderJvm() {
        // jvm最大内存，total总内存，free空间内存，used使用内存
        long max = RuntimeUtil.getMaxMemory();
        long total = RuntimeUtil.getTotalMemory();
        long free = RuntimeUtil.getFreeMemory();
        long used = total - free;

        return Jvm.builder()
                // jvm名称
                .name(SystemUtil.getJavaRuntimeInfo().getName())
                // jvm版本
                .version(SystemUtil.getJavaRuntimeInfo().getVersion())
                // jvm home 目录
                .home(SystemUtil.getJavaRuntimeInfo().getHomeDir())
                // jvm memory total
                .total(ConvertUtils.convertMb(total))
                // jvm memory free
                .free(ConvertUtils.convertMb(free))
                // jvm memory used
                .used(ConvertUtils.convertMb(used))
                // jvm 内存使用率
                .usage(NumberUtils.percentageScale2(used, total))
                // 最大jvm内存
                .max(ConvertUtils.convertMb(max))
                // jvm 参数
                .inputArgs(ManagementFactory.getRuntimeMXBean().getInputArguments().toString())
                .build();
    }


    /**
     * Description: 构建内存信息数据
     *
     * @return {@link Memory } 内存信息数据
     * @date 2022-09-26 14:57
     */
    private static Memory builderMemory() {
        GlobalMemory memory = OshiUtil.getMemory();
        long total = memory.getTotal();
        long free = memory.getAvailable();
        long used = total - free;
        return Memory.builder()
                // 内存total总量
                .total(ConvertUtils.convertGb(total))
                // 内存used使用量
                .used(ConvertUtils.convertGb(used))
                // 内存free空闲量
                .free(ConvertUtils.convertGb(free))
                // 内存使用率
                .usage(NumberUtils.percentageScale2(used, total))
                .build();
    }


    /**
     * Description: 构建操作系统信息数据
     *
     * @return {@link Os } 系统信息数据
     * @date 2022-09-26 15:02
     */
    private static Os builderOs() {
        return Os.builder()
                // os操作系统名称
                .osName(SystemUtil.getOsInfo().getName())
                // os炒作系统架构
                .osArch(SystemUtil.getOsInfo().getArch())
                .build();
    }

    /**
     * Description: 服务器开始本地日期时间
     *
     * @return {@link LocalDateTime } 本地日期时间
     * @date 2022-09-26 15:07
     */
    public static LocalDateTime getServerStartLocalDateTime() {
        return LocalDateTimeUtil.of(getServerStartTime());
    }

    /**
     * Description: 获得服务器启动时间
     *
     * @return long 服务器启动时间毫秒数
     * @date 2022-09-26 15:07
     */
    public static long getServerStartTime() {
        return ManagementFactory.getRuntimeMXBean().getStartTime();
    }


    /**
     * Description: 获取完整的请求路径，包括：域名，端口，上下文访问路径
     *
     * @return {@link String } url路径
     * @date 2022-10-04 22:36
     */
    public static String url() {
        // 获取当前请求
        HttpServletRequest request = ServletUtils.getRequest();
        return url(request);
    }

    /**
     * Description: 得到访问路径
     *
     * @param request 请求
     * @return {@link String } 访问路径
     * @date 2022-10-04 22:36
     */
    public static String url(HttpServletRequest request) {
        AssertUtils.isNotNull(request);
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }


}
