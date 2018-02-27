package com.scnjwh.intellectreport.modules.excelreport.Controller;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BudgetController {
    public static void main(String[] args) {
        // 声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        //声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("预算执行四大板块支出表");
        //给单子名称一个长度
        sheet.setDefaultColumnWidth(15);
        // 生成一个样式
        HSSFCellStyle style = wb.createCellStyle();
        //创建第一行（也可以称为表头）
        HSSFRow row = sheet.createRow(0);
        //样式字体居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //给表头第一行一次创建单元格
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("预算执行四大板块支出表");
        cell.setCellStyle(style);
        CellRangeAddress ra =new CellRangeAddress(0,2,0,5);
        sheet.addMergedRegion(ra);

        HSSFRow row1 = sheet.createRow(3);
        cell = row1.createCell(0);
        cell.setCellValue("科目名称");
        cell.setCellStyle(style);
        cell = row1.createCell(1);
        cell.setCellValue("小计");
        cell.setCellStyle(style);
        cell = row1.createCell(2);
        cell.setCellValue("产业发展");
        cell.setCellStyle(style);
        cell = row1.createCell(3);
        cell.setCellValue("行政成本");
        cell.setCellStyle(style);
        cell = row1.createCell(4);
        cell.setCellValue("基础设施建设");
        cell.setCellStyle(style);
        cell = row1.createCell(5);
        cell.setCellValue("民生支出");
        cell.setCellStyle(style);

        //添加一些数据，这里先写死，大家可以换成自己的集合数据
//        List<Student> list = new ArrayList<Student>();
//        list.add(new Student(111, "张三", "男"));
//        list.add(new Student(111, "李四", "男"));
//        list.add(new Student(111, "王五", "女"));

        //向单元格里填充数据
//        for (short i = 0; i < list.size(); i++) {
//            row = sheet.createRow(i + 1);
//            row.createCell(0).setCellValue(list.get(i).getId());
//            row.createCell(1).setCellValue(list.get(i).getName());
//            row.createCell(2).setCellValue(list.get(i).getSex());
//        }

        try {
            //默认导出到D盘下
            FileOutputStream out = new FileOutputStream("D://预算执行四大板块支出表.xls");
            wb.write(out);
            out.close();
            JOptionPane.showMessageDialog(null, "导出成功!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "导出失败!");
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "导出失败!");
            e.printStackTrace();
        }
    }
}
