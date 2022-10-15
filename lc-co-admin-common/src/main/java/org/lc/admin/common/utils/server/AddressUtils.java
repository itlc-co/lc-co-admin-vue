package org.lc.admin.common.utils.server;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Description: 地址工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-05 14:33
 */
public class AddressUtils {

    /**
     * 日志对象实例
     */
    public static final Logger log = LoggerFactory.getLogger(AddressUtils.class);


    /**
     * ip地址查询url
     */
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    /**
     * 未知物理地址
     */
    public static final String UNKNOWN = "XX XX";

    /**
     * Description: ip位置对应的物理地址
     *
     * @param ip ip地址（ipv4）
     * @return {@link String } 物理地址
     * @date 2022-09-05 14:36
     */
    public static String getLocationByIp(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            // url参数map
            HashMap<String, Object> paramMap = MapUtil.of("json", true);
            paramMap.put("ip", ip);
            // 返回json字符串结果集
            String jsonRes = HttpUtil.get(IP_URL, paramMap);

            if (StrUtil.isBlank(jsonRes)) {
                // 异常json
                log.error("ip为{}获取物理地理位置异常", ip);
                return UNKNOWN;
            }

            // 解析json获取省份，城市
            JSONObject jsonObject = JSONUtil.parseObj(jsonRes);
            String region = jsonObject.getStr("pro");
            String city = jsonObject.getStr("city");
            return StrUtil.format("{} {}", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return UNKNOWN;
    }
}
