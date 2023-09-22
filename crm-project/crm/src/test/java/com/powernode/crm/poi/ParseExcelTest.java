package com.powernode.crm.poi;

import com.powernode.crm.commons.utils.HSSFUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;

/**
 * 使用apache-poi解析excel文件
 * @title:ParseExcelTest Author liu
 * @Date:2023/4/10 21:21
 * @Version 1.0
 */
public class ParseExcelTest {
    public static void main(String[] args) throws Exception{
        //1、根据指定的excel文件生成HSSFWorkbook对象，封装excel文件的所有信息
        FileInputStream is = new FileInputStream("D:\\webProject\\CRM\\crm-project\\crm\\src\\test\\java\\com\\powernode\\crm\\serverDir\\activityList.xls");
        HSSFWorkbook wb = new HSSFWorkbook(is);
        //2、根据wb获取HSSFSheet对象封装一页的所有信息
        HSSFSheet sheet = wb.getSheetAt(0);//根据页的下标和获取，从0开始

        HSSFRow row = null;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {//最后一行的下标
            //3、根据sheet获取HFFSRow对象，封装某一行的所有信息
            row = sheet.getRow(i);//根据行的下标和获取，从0开始

            HSSFCell cell = null;
            for (int j = 0; j < row.getLastCellNum(); j++) {//最后一列的下标+1
                //4、根据row获取HFFSCell对象，封装某一列的所有信息
                cell = row.getCell(j);

                //获取列中的数据【封装工具类】
                /*if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    System.out.print(cell.getStringCellValue() + " ");
                }else if (cell.getCellType() == HSSFCell.LAST_COLUMN_NUMBER) {
                    System.out.print(cell.getNumericCellValue() + " ");
                }else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue() + " ");
                }else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                    System.out.print(cell.getCellFormula() + " ");
                }else {
                    System.out.print("" + " ");
                }*/
                //获取列中的数据【封装工具类】
                System.out.print(HSSFUtils.getCellValueForStr(cell) + " ");
            }
            System.out.println();//每一行输出完换行
        }
    }


}