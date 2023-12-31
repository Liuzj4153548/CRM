package com.powernode.crm.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * 关于excel文件操作的工具类
 * @title:HSSFUtils Author liu
 * @Date:2023/4/11 11:57
 * @Version 1.0
 */
public class HSSFUtils {

    /**
     * 从指定的HSSFCell对象中获取列的值
     * @return
     */
    public static String getCellValueForStr(HSSFCell cell) {
        String ret = "";
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            ret = cell.getStringCellValue();
        }else if (cell.getCellType() == HSSFCell.LAST_COLUMN_NUMBER) {
            ret = Double.toString(cell.getNumericCellValue());

            //字符串拼接可以直接将double转换成字符串类型
            //ret = cell.getNumericCellValue() + "";

        }else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
            ret = Boolean.toString(cell.getBooleanCellValue());
        }else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
            ret = cell.getCellFormula();
        }else {
            ret = "";
        }
        return ret;
    }
}
