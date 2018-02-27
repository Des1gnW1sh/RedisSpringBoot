package com.scnjwh.intellectreport.modules.excelreport.Controller;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-10-30.
 */
public class excelDemo {

    private static List<Student> getStudent() throws Exception
    {
        List list = new ArrayList();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        Student user1 = new Student(1, "张三", 16, df.parse("1997-03-12"));
        Student user2 = new Student(2, "李四", 17, df.parse("1996-08-12"));
        Student user3 = new Student(3, "王五", 26, df.parse("1985-11-12"));
        list.add(user1);
        list.add(user2);
        list.add(user3);

        return list;
    }

    public static void main(String[] args) throws Exception  {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("学生表一");
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyleTitle = wb.createCellStyle();

        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        cellStyleTitle.setFont(font);

        HSSFRow row = sheet.createRow(0);
        HSSFCell firstcell = row.createCell(0);
        row = sheet.createRow(1);

        CellRangeAddress cra = new CellRangeAddress(0,1,0,3);
        sheet.addMergedRegion(cra);

        firstcell.setCellValue("学生信息表");
        firstcell.setCellStyle(cellStyleTitle);
        row = sheet.createRow(2);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("学号");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(2);
        cell.setCellValue("年龄");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(3);
        cell.setCellValue("生日");
        cell.setCellStyle(cellStyle);

        List list = excelDemo.getStudent();
        for(int i = 0;i<list.size();i++){
            row = sheet.createRow(i+3);
            Student stu = (Student)list.get(i);
            row.createCell(0).setCellValue(stu.getId());
            row.createCell(1).setCellValue(stu.getName());
            row.createCell(2).setCellValue(stu.getAge());
            cell = row.createCell(3);
            cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(stu.getBirth()));
        }
        FileOutputStream fout = new FileOutputStream("D:/student.xls");
        wb.write(fout);
        fout.close();

    }
}
