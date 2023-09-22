package com.powernode.crm.poi;

/**
 * @title:CreatExcel Author liu
 * @Date:2023/4/9 2:56
 * @Version 1.0
 */

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 使用apache-poi生成excel文件
 */
public class CreatExcelTest {
    public static void main(String[] args) throws Exception{
        //创建HSSFWorkbook对象，生成excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //使用wb对象创建HSSFSheet对象，对应wb文件中的一页
        HSSFSheet sheet = wb.createSheet("学生列表");
        //使用sheet创建HSSFRow对象，对应sheet中的一行
        HSSFRow row = sheet.createRow(0);//从0开始，依次增加
        //使用row创建HSSFCell对象，对应row中的列
        HSSFCell cell = row.createCell(0);//从0开始，依次增加
        cell.setCellValue("学号");
        cell = row.createCell(1);//从0开始，依次增加
        cell.setCellValue("姓名");
        cell = row.createCell(2);//从0开始，依次增加
        cell.setCellValue("年龄");

        //生成HSSFCellStyle对象
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//居中

        //使用sheet创建10行row对象，对应sheet中的10行
        for (int i = 1; i <= 10; i++) {
            row = sheet.createRow(i);

            cell = row.createCell(0);//从0开始，依次增加
            cell.setCellValue(100+i);
            cell = row.createCell(1);//从0开始，依次增加
            cell.setCellValue("张三" + i);
            cell = row.createCell(2);//从0开始，依次增加
            cell.setCellStyle(cellStyle);
            cell.setCellValue(15 + i);
        }

        //调用工具函数，生成excel文件
        //文件输出流
        OutputStream os = new FileOutputStream("D:\\webProject\\CRM\\crm-project\\crm\\src\\test\\java\\com\\powernode\\crm\\serverDir\\studentList.xls");//目录必须手动创建好，文件如果不存在，poi插件会自动创建
        wb.write(os);
        //释放资源
        os.close();
        wb.close();
        System.out.println("===========excel执行成功============");
    }
}
