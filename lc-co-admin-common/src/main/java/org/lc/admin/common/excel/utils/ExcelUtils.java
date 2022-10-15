package org.lc.admin.common.excel.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.http.ContentType;
import org.apache.poi.ss.usermodel.Workbook;
import org.lc.admin.common.base.pool.CharacterSet;
import org.lc.admin.common.excel.style.ExcelExportStyle;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Description: excel工具类
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-15 13:12
 */
public class ExcelUtils {

    /**
     * 默认导入参数
     */
    private static final ImportParams DEFAULT_IMPORT_PARAMS = new ImportParams();

    static {
        // 初始化默认导入参数
        // 设置字段是否需要校验
        DEFAULT_IMPORT_PARAMS.setNeedVerify(true);
        // 设置sheetNum
        DEFAULT_IMPORT_PARAMS.setSheetNum(1);
        // 设置其实sheet索引
        DEFAULT_IMPORT_PARAMS.setStartSheetIndex(0);
    }

    /**
     * Description: 根据数据列表导出excel到响应中
     *
     * @param response  响应
     * @param list      数据列表
     * @param pojoClass 数据实体class对象
     * @param title     标题
     * @throws IOException io异常
     * @date 2022-10-07 18:01
     */
    public static <T> void exportExcel(HttpServletResponse response, List<T> list, Class<T> pojoClass, String title) throws IOException {
        exportExcel(response, list, pojoClass, title, title);
    }

    /**
     * Description: 根据数据列表导出excel到响应中
     *
     * @param response  响应
     * @param list      数据列表
     * @param pojoClass 数据实体class对象
     * @param title     标题
     * @param sheetName sheet的名称
     * @throws IOException io异常
     * @date 2022-10-07 18:03
     */
    public static <T> void exportExcel(HttpServletResponse response, List<T> list, Class<T> pojoClass, String title, String sheetName) throws IOException {
        // 创建excel工作簿
        Workbook workbook = createWorkbook(list, pojoClass, title, sheetName);
        // 导出excel
        exportExcel(response, workbook);
    }

    /**
     * Description: 导出excel工作簿到响应中
     *
     * @param response 响应
     * @param workbook 工作簿
     * @throws IOException io异常
     * @date 2022-10-07 18:04
     */
    private static void exportExcel(HttpServletResponse response, Workbook workbook) throws IOException {
        // 设置响应头contentType以及编码集
        response.setContentType(ContentType.build("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", StandardCharsets.UTF_8));
        response.setCharacterEncoding(CharacterSet.UTF_8);
        // 写出工作簿中的数据
        workbook.write(response.getOutputStream());
    }

    /**
     * Description: 创建工作簿
     *
     * @param list      数据列表
     * @param pojoClass 数据实体class对象
     * @param title     标题
     * @param sheetName sheet名称
     * @return {@link Workbook } 工作簿
     * @date 2022-10-07 18:05
     */
    private static <T> Workbook createWorkbook(List<T> list, Class<T> pojoClass, String title, String sheetName) {
        // 导出参数
        ExportParams params = new ExportParams(title, sheetName);
        // 设置导出excel样式类
        params.setStyle(ExcelExportStyle.class);
        return ExcelExportUtil.exportExcel(params, pojoClass, list);
    }

    /**
     * Description: 根据导出模板导出excel到响应中
     *
     * @param response    响应
     * @param map         数据map
     * @param templateUrl 模板url
     * @param sheetNum    sheet序号
     * @throws IOException io异常
     * @date 2022-10-07 18:05
     */
    public static void exportExcel(HttpServletResponse response, Map<String, Object> map, String templateUrl, Integer... sheetNum) throws IOException {
        // 创建工作簿
        Workbook workbook = createWorkbook(map, templateUrl, sheetNum);
        // 导出工作簿到响应中
        exportExcel(response, workbook);
    }

    /**
     * Description: 根据数据map导出模板创建工作簿
     *
     * @param map         地图
     * @param templateUrl 模板url
     * @param sheetNum    sheet序号
     * @return {@link Workbook } 工作簿
     * @date 2022-10-07 18:07
     */
    private static Workbook createWorkbook(Map<String, Object> map, String templateUrl, Integer... sheetNum) {
        // 创建导出模板参数
        TemplateExportParams templateExportParams = new TemplateExportParams(templateUrl, sheetNum);
        return ExcelExportUtil.exportExcel(templateExportParams, map);
    }

    /**
     * Description: 导入多数据
     *
     * @param inputStream  输入流
     * @param pojoClass    实体类class对象
     * @param importParams 导入参数
     * @return {@link ExcelImportResult }<{@link T }> 导入结果集
     * @throws Exception 异常
     * @date 2022-10-07 18:08
     */
    public static <T> ExcelImportResult<T> importMore(InputStream inputStream, Class<T> pojoClass, ImportParams importParams) throws Exception {
        return ExcelImportUtil.importExcelMore(inputStream, pojoClass, importParams);
    }

    /**
     * Description: 导入多数据
     *
     * @param inputStream 输入流
     * @param pojoClass   实体类class对象
     * @return {@link ExcelImportResult }<{@link T }> 导入结果集
     * @throws Exception 异常
     * @date 2022-10-07 18:08
     */
    public static <T> ExcelImportResult<T> importMore(InputStream inputStream, Class<T> pojoClass) throws Exception {
        return ExcelImportUtil.importExcelMore(inputStream, pojoClass, DEFAULT_IMPORT_PARAMS);
    }

    /**
     * Description: 导入多数据
     *
     * @param file      网络文件
     * @param pojoClass 实体类class对象
     * @return {@link ExcelImportResult }<{@link T }> 导入结果集
     * @throws Exception 异常
     * @date 2022-10-07 18:09
     */
    public static <T> ExcelImportResult<T> importMore(MultipartFile file, Class<T> pojoClass) throws Exception {
        return ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, DEFAULT_IMPORT_PARAMS);
    }


}
