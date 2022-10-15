package org.lc.admin.common.excel.style;

import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

/**
 * Description: excel导出样式
 *
 * @author lc-co
 * @version 1.0.0
 * @date 2022-09-15 13:12
 */
public class ExcelExportStyle extends AbstractExcelExportStyler implements IExcelExportStyler {

    public ExcelExportStyle(Workbook workbook) {
        super.createStyles(workbook);
    }


    /**
     * Description: 获取(标题)样式
     *
     * @param headerColor 表头颜色
     * @return {@link CellStyle }
     * @date 2022-09-15 15:03
     */
    @Override
    public CellStyle getHeaderStyle(short headerColor) {
        return getCellStyle();
    }

    /**
     * Description: 字符串没有风格
     *
     * @param workbook 工作簿
     * @param isWarp   是否warp
     * @return {@link CellStyle } 单元格样式
     * @date 2022-09-15 14:57
     */
    @Override
    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        return getCellStyle(workbook, isWarp);
    }

    /**
     * Description: 获取单元格样式
     *
     * @param workbook 工作簿
     * @param isWarp   是否换行
     * @return {@link CellStyle } 单元格样式
     * @date 2022-09-18 22:49
     */
    private CellStyle getCellStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        // 字体样式
        font.setFontHeightInPoints((short) 12);
        font.setFontName("微软雅黑");
        style.setFont(font);

        // 边框、居中等样式
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // 字符串格式
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }
        return style;
    }

    /**
     * Description: 字符串septail样式
     *
     * @param workbook 工作簿
     * @param isWarp   是否warp
     * @return {@link CellStyle } 单元格样式
     * @date 2022-09-15 14:57
     */
    @Override
    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        return getCellStyle(workbook, isWarp);
    }

    /**
     * Description: 获取表头样式
     *
     * @param color 颜色
     * @return {@link CellStyle } 单元格样式
     * @date 2022-09-15 14:57
     */
    @Override
    public CellStyle getTitleStyle(short color) {
        return getCellStyle();
    }

    /**
     * Description: 获取单元格样式
     *
     * @return {@link CellStyle } 单元格样式
     * @date 2022-09-18 22:49
     */
    private CellStyle getCellStyle() {

        // 从工作簿中创建cell样式
        CellStyle cellStyle = workbook.createCellStyle();

        // 自定义字体样式
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.WHITE1.getIndex());
        font.setBold(true);
        font.setFontName("微软雅黑");
        cellStyle.setFont(font);

        // 自定义背景色 浅蓝
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 自定义对齐方式 center: 居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 自定义四个方向上的边框 thin 浅
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        // 自动换行
        cellStyle.setWrapText(true);
        return cellStyle;
    }
}
