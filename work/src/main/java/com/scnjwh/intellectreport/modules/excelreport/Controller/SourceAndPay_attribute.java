package com.scnjwh.intellectreport.modules.excelreport.Controller;

import com.scnjwh.intellectreport.common.web.BaseController;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-10-30.
 */
//@Controller
//@RequestMapping(value = "${adminPath}/excel/getSouceAndPayExcel")
public class SourceAndPay_attribute extends BaseController {

    /**
     * 获取最下层单元格模板内容
     * @return
     */
    public static List<Bdgsub> getBdgsub(){
        List<Bdgsub> list = new ArrayList<>();
        Bdgsub bdgsub = new Bdgsub("来源","指标","支付","结余","支付来源比","支付指标比");
        for(int i = 0;i<15;i++){
            list.add(bdgsub);
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
        HSSFSheet sheet = wb.createSheet("资金来源及支付情况表(分归口股室和资金性质)");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        row = sheet.createRow(1);
        CellRangeAddress cra = new CellRangeAddress(0,1,0,91);
        sheet.addMergedRegion(cra);
        cell.setCellValue("资金来源及支付情况表(分归口股室和资金性质)");
        cell.setCellStyle(getStyle(wb).get("titlestyle"));
        //2
        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(1);
        cell.setCellValue("归口股室");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(2);
        cell.setCellValue("全部资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(8);
        cell.setCellValue("政府综合预算");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(50);
        cell.setCellValue("其他资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        //3
        row = sheet.createRow(3);
        cell = row.createCell(8);
        cell.setCellValue("预算内");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(32);
        cell.setCellValue("财政专户管理的资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(38);
        cell.setCellValue("社保基金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(38);
        cell.setCellValue("其他专户资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(50);
        cell.setCellValue("福彩资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(56);
        cell.setCellValue("体彩资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(62);
        cell.setCellValue("代管资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(74);
        cell.setCellValue("自用资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(80);
        cell.setCellValue("往来资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(86);
        cell.setCellValue("其他");
        cell.setCellStyle(getStyle(wb).get("style"));
        //4
        row = sheet.createRow(4);
        cell = row.createCell(8);
        cell.setCellValue("一般预算");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(20);
        cell.setCellValue("基金预算");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(26);
        cell.setCellValue("国有资本经营预算");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(62);
        cell.setCellValue("代管中央资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(68);
        cell.setCellValue("代管单位资金");
        cell.setCellStyle(getStyle(wb).get("style"));
        //5
        row = sheet.createRow(5);
        cell = row.createCell(8);
        cell.setCellValue("经费拨款");
        cell.setCellStyle(getStyle(wb).get("style"));
        cell = row.createCell(14);
        cell.setCellValue("专项收入");
        cell.setCellStyle(getStyle(wb).get("style"));
        //6
        row = sheet.createRow(6);
        cell = row.createCell(1);
        cell.setCellValue("合计");
        cell.setCellStyle(getStyle(wb).get("style"));
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
        CellRangeAddress cra1 = new CellRangeAddress(2,5,0,0);
        sheet.addMergedRegion(cra1);
        CellRangeAddress cra2 = new CellRangeAddress(2,5,1,1);
        sheet.addMergedRegion(cra2);
        CellRangeAddress cra3 = new CellRangeAddress(2,5,2,7);
        sheet.addMergedRegion(cra3);
        CellRangeAddress cra4 = new CellRangeAddress(2,2,8,49);
        sheet.addMergedRegion(cra4);
        CellRangeAddress cra8 = new CellRangeAddress(3,3,8,31);
        sheet.addMergedRegion(cra8);
        CellRangeAddress cra5 = new CellRangeAddress(4,4,8,19);
        sheet.addMergedRegion(cra5);
        CellRangeAddress cra6 = new CellRangeAddress(5,5,8,13);
        sheet.addMergedRegion(cra6);
        CellRangeAddress cra7 = new CellRangeAddress(5,5,14,19);
        sheet.addMergedRegion(cra7);
        CellRangeAddress cra9 = new CellRangeAddress(4,5,20,25);
        sheet.addMergedRegion(cra9);
        CellRangeAddress cra10 = new CellRangeAddress(4,5,26,31);
        sheet.addMergedRegion(cra10);
        CellRangeAddress cra11 = new CellRangeAddress(3,5,32,37);
        sheet.addMergedRegion(cra11);
        CellRangeAddress cra12 = new CellRangeAddress(3,5,38,43);
        sheet.addMergedRegion(cra12);
        CellRangeAddress cra13 = new CellRangeAddress(3,5,44,49);
        sheet.addMergedRegion(cra13);
        CellRangeAddress cra14 = new CellRangeAddress(2,2,50,91);
        sheet.addMergedRegion(cra14);
        CellRangeAddress cra15 = new CellRangeAddress(3,5,50,55);
        sheet.addMergedRegion(cra15);
        CellRangeAddress cra16 = new CellRangeAddress(3,5,56,61);
        sheet.addMergedRegion(cra16);
        CellRangeAddress cra17 = new CellRangeAddress(3,3,62,73);
        sheet.addMergedRegion(cra17);
        CellRangeAddress cra18 = new CellRangeAddress(4,5,62,67);
        sheet.addMergedRegion(cra18);
        CellRangeAddress cra19 = new CellRangeAddress(4,5,68,73);
        sheet.addMergedRegion(cra19);
        CellRangeAddress cra20 = new CellRangeAddress(3,5,74,79);
        sheet.addMergedRegion(cra20);
        CellRangeAddress cra21 = new CellRangeAddress(3,5,80,85);
        sheet.addMergedRegion(cra21);
        CellRangeAddress cra22 = new CellRangeAddress(3,5,86,91);
        sheet.addMergedRegion(cra22);
        return  wb;
    }

    public static String getExcel(HSSFWorkbook wb){
        try {
            FileOutputStream out = new FileOutputStream("D://SourceAndPay_attribute.xls");
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

//    @RequiresPermissions("excel:getExcel")
//    @RequestMapping(value = "getSouceAndPay")
//    public String getExcel(RedirectAttributes redirectAttributes){
//        HSSFWorkbook wb = new HSSFWorkbook();
//        wb = setExcel();
//        getExcel(wb);
//        addMessage(redirectAttributes, "导出模板成功!");
//        return "";
//    }

}