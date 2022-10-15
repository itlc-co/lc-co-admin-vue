package org.lc.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.lc.admin.common.excel.utils.ExcelUtils;
import org.lc.admin.common.utils.server.AddressUtils;
import org.lc.admin.common.utils.servlet.AsyncServletUtils;
import org.lc.admin.common.utils.server.IpUtils;
import org.lc.admin.system.entities.bo.AccessRecordBo;
import org.lc.admin.system.entities.entity.AccessRecord;
import org.lc.admin.system.entities.request.AccessRecordRequest;
import org.lc.admin.system.mapper.AccessRecordMapper;
import org.lc.admin.system.service.AccessRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Description: 访问记录service服务实现
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-23 22:39
 */
@Service
public class AccessRecordServiceImpl implements AccessRecordService {

    public static final Logger log = LoggerFactory.getLogger(AccessRecordServiceImpl.class);

    @Resource
    private AccessRecordMapper accessRecordMapper;


    /**
     * Description: 清空访问记录信息
     *
     * @return {@link Integer } 记录数
     * @date 2022-09-22 17:27
     */
    @Override
    public Integer cleanAccessRecord() {
        return this.accessRecordMapper.cleanAccessRecord();
    }

    /**
     * Description: 根据请求参数导出访问记录列表数据
     *
     * @param requestParam 请求参数
     * @param response     响应
     * @date 2022-10-11 17:41
     */
    @Override
    public void export(AccessRecordRequest requestParam, HttpServletResponse response) throws IOException {
        ExcelUtils.exportExcel(response, selectAccessRecordBoList(requestParam), AccessRecordBo.class, "访问记录信息");
    }

    /**
     * Description: 根据请求参数添加访问记录数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-27 23:57
     */
    @Override
    public Integer add(AccessRecordRequest requestParam) {
        return this.insertAccessRecord(requestParam);
    }


    /**
     * Description: 根据请求参数插入访问记录数据
     *
     * @param requestParam 请求参数
     * @return {@link Integer } 记录数
     * @date 2022-09-28 09:34
     */
    @Override
    public Integer insertAccessRecord(AccessRecordRequest requestParam) {
        // 获取当前请求
        HttpServletRequest request = AsyncServletUtils.getRequest();
        // 获取当前请求ip地址，地址，user-agent，os，browser
        String ipaddr = IpUtils.getIpAddr(request);
        String location = AddressUtils.getLocationByIp(ipaddr);
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        String os = userAgent.getOs().getName();
        String browser = StrUtil.format("{} {}", userAgent.getBrowser().getName(), userAgent.getVersion());

        // 转换bean: accessRecord请求参数转换为accessRecord实体并设置ip地址，地址等信息
        AccessRecord accessRecord = BeanUtil.toBean(requestParam, AccessRecord.class);
        accessRecord
                // IP地址
                .setIpaddr(ipaddr)
                // 浏览器
                .setBrowser(browser)
                // os操作系统
                .setOs(os)
                // 地址
                .setAccessLocation(location);

        return this.insertAccessRecord(accessRecord);
    }

    /**
     * Description: 根据访问记录插入访问记录数据
     *
     * @param accessRecord 访问记录
     * @return {@link Integer } 记录数
     * @date 2022-09-28 09:35
     */
    @Override
    public Integer insertAccessRecord(AccessRecord accessRecord) {
        log.info("记录用户登录信息:{}", accessRecord);
        return this.accessRecordMapper.insertAccessRecord(accessRecord);
    }

    /**
     * Description: 查询访问记录bo列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link AccessRecordBo }> 访问记录bo列表数据
     * @date 2022-09-23 22:42
     */
    @Override
    public List<AccessRecordBo> selectAccessRecordBoList(AccessRecordRequest requestParam) {
        return this.accessRecordMapper.selectAccessRecordBoList(requestParam);
    }

    /**
     * Description: 根据访问记录id删除访问记录数据
     *
     * @param accessId 访问id
     * @return {@link Integer } 记录数
     * @date 2022-10-06 22:41
     */
    @Override
    public Integer deleteAccessRecordById(Long accessId) {
        return this.accessRecordMapper.deleteAccessRecordById(accessId);
    }

    /**
     * Description: 根据访问记录id列表删除访问记录数据
     *
     * @param accessIds 访问id列表
     * @return {@link Integer } 记录数
     * @date 2022-09-22 17:21
     */
    @Override
    public Integer deleteAccessRecordByIds(List<Long> accessIds) {
        return this.accessRecordMapper.deleteAccessRecordByIds(accessIds);
    }

    /**
     * Description: 查询访问记录列表数据
     *
     * @param requestParam 请求参数
     * @return {@link List }<{@link AccessRecord }> 访问记录列表数据
     * @date 2022-09-22 16:52
     */
    @Override
    public List<AccessRecord> selectAccessRecordList(AccessRecordRequest requestParam) {
        return accessRecordMapper.selectAccessRecordList(requestParam);
    }


}
