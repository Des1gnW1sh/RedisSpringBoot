package com.scnjwh.intellectreport.modules.excelreport.Controller;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-10-31.
 */
public class SourceAndPayOfRoom {
    /**
     * 获取最下层单元格模板内容
     * @return
     */
    public static List<Bdgsub> getBdgsub(){
        List<Bdgsub> list = new ArrayList<>();
        Bdgsub bdgsub = new Bdgsub("来源","指标","支付","结余","支付来源比","支付指标比");
        for(int i = 0;i<9;i++){
            list.add(bdgsub);
        }
        return list;
    }

    public static List<StockRoom> getRoom(){
        List<StockRoom> list = new ArrayList<>();
        String[] arr = new String[]{"全部股室合计","预算股","综合股","投资股","社保股","农业股","行政政法股","教科文股","其他股室"};
        for(String i:arr){
            StockRoom stockRoom = new StockRoom(i);
            list.add(stockRoom);
        }
        return list;
    }

    /**
     * 设置Excel样式
     * @param wb
     * @return
     */
    public static Map<String,HSSFCellStyle> getStyle(HSSFWorkbook wb){
        Map<String,HSSFCellStyle> map = new HashMap<>();
        HSSFCellStyle titleStyle = wb.createCellStyle();
        HSSFCellStyle style = wb.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        titleStyle.setFont(font);
        map.put("titlestyle",titleStyle);
        map.put("style",style);
        return map;
    }

    /**
     * 组装Excel模板
     * @return
     */
    public static HSSFWorkbook setExcel(){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("资金来源及支付情况表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        row = sheet.createRow(1);
        CellRangeAddress cra = new CellRangeAddress(0,1,0,55);
        sheet.addMergedRegion(cra);
        cell.setCellValue("资金来源及支付情况表(分归口股室和预算来源)");
        cell.setCellStyle(getStyle(wb).get("titlestyle"));
        //2
        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(1);
        cell.setCellValue("预算来源");
        cell.setCellStyle(getStyle(wb).get("style"));
        List<StockRoom> listRoom = getRoom();
        int numRoom = 2;
        for (int i = 0;i<listRoom.size();i++){
            cell = row.createCell(numRoom);
            cell.setCellValue(listRoom.get(i).getStockName());
            cell.setCellStyle(getStyle(wb).get("style"));
            CellRangeAddress cranum = new CellRangeAddress(2,2,numRoom,numRoom+5);
            sheet.addMergedRegion(cranum);
            numRoom+=6;
        }
        //3
        row = sheet.createRow(3);
        //遍历对象中的单个单元格
        List<Bdgsub> list = getBdgsub();
        int num = 2;
        for(int i = 0;i<list.size();i++){
            cell = row.createCell(num);
            cell.setCellValue(list.get(i).getImp());
            cell.setCellStyle(getStyle(wb).get("style"));
            num++;
            cell = row.createCell(num);
            cell.setCellValue(list.get(i).getBdgsub());
            cell.setCellStyle(getStyle(wb).get("style"));
            num++;
            cell = row.createCell(num);
            cell.setCellValue(list.get(i).getPay());
            cell.setCellStyle(getStyle(wb).get("style"));
            num++;
            cell = row.createCell(num);
            cell.setCellValue(list.get(i).getBalance());
            cell.setCellStyle(getStyle(wb).get("style"));
            num++;
            cell = row.createCell(num);
            cell.setCellValue(list.get(i).getPaysource());
            cell.setCellStyle(getStyle(wb).get("style"));
            num++;
            cell = row.createCell(num);
            cell.setCellValue(list.get(i).getPaymentratio());
            cell.setCellStyle(getStyle(wb).get("style"));
            num++;
        }
        CellRangeAddress cra1 = new CellRangeAddress(2,3,0,0);
        sheet.addMergedRegion(cra1);
        CellRangeAddress cra2 = new CellRangeAddress(2,3,1,1);
        sheet.addMergedRegion(cra2);
        sheet.createFreezePane(0,4,0,55);
        return  wb;
    }

    public static String getExcel(HSSFWorkbook wb){
        try {
            FileOutputStream out = new FileOutputStream("D:/SouceAndPayOfRoom.xls");
            wb.write(out);
            out.close();
            return "生成Excel成功!";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "生成失败!";
        } catch (IOException e) {
            e.printStackTrace();
            return "生成失败!";
        }
    }

    public static void main(String[] args) {
        HSSFWorkbook wb = new HSSFWorkbook();
        wb = setExcel();
        getExcel(wb);
    }

}
