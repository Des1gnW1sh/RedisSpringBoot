package com.scnjwh.intellectreport.modules.report.utils;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel xml 工具类
 * @author Administrator:zhouxin
 * @version 2018-01-22
 */
@SuppressWarnings("unchecked")
public class ExcelUtil {
    /**
     * Excel column index begin 1
     *
     * @param colStr 字符列号
     * @return 数字列号
     */
    public static int excelColStrToNum(String colStr) {
        int num = 0;
        int result = 0;
        int length = colStr.length();
        for (int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int) (ch - 'A' + 1);
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    /**
     * Excel column index begin 1
     *
     * @param columnIndex 数字列号
     * @return 字符列号
     */
    public static String excelColIndexToStr(int columnIndex) {
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }

    /**
     * 去除xml文件中的转义字符：&lt;(<)、&gt;(>)、&quot;(")
     * @param xml
     * @return
     */
    public static String removeEscapeCharacter(String xml){
        if(xml.contains("&lt;")){
            xml = xml.replaceAll("&lt;","<");
        }
        if(xml.contains("&gt;")){
            xml = xml.replaceAll("&gt;",">");
        }
        if(xml.contains("&quot;")){
            xml = xml.replaceAll("&quot;","\"");
        }

        return xml;
    }

    /**
     * 添加xml文件中的转义字符：&lt;(<)、&gt;(>)、&quot;(")
     * @param xml
     * @return
     */
    public static String addEscapeCharacter(String xml){
        if(xml.contains("<")){
            xml = xml.replaceAll("<","&lt;");
        }
        if(xml.contains(">")){
            xml = xml.replaceAll(">","&gt;");
        }
        if(xml.contains("\"")){
            xml = xml.replaceAll("\"","&quot;");
        }

        return xml;
    }

    /**
     * 读取xml
     *
     * @param xml xml串
     * @return 文档类
     * @throws UnsupportedEncodingException 编码错误
     * @throws DocumentException 文档错误
     */
    private static Document getXMlDocument(String xml) throws UnsupportedEncodingException, DocumentException {
        xml = removeEscapeCharacter(xml);
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        return reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
    }

    /**
     * 拆分单元格名为行号，列名
     * @param cellName 单元格名
     * @return 拆分后包含列号和行号的集合
     */
    public static Map<String,String> splitCellName(String cellName){
        char[] chars = cellName.toCharArray();
        Map<String,String> cell = new HashMap<>();
        StringBuilder row = new StringBuilder();
        StringBuilder column = new StringBuilder();
        for (char c : chars) {
            if(Character.isDigit(c)){
                row.append(c);
            }else {
                column.append(c);
            }
        }
        String col = column.toString();
        cell.put("row",row.toString());
        cell.put("column",col);
        return cell;
    }

    /**
     * 给excel单元格添加别名
     * @param xml xml
     * @return 添加别名后
     */
    public static String setCellAlias(String xml){
        Document doc = null;
        String order = null;
        String alias = null;
        try {
            doc = getXMlDocument(xml);
            List<Node> tds = doc.selectNodes("/Report/WorkSheet/Table/TR/TD");
            for(int i = 0; i < tds.size(); i ++){
                Element tdEl = (Element) tds.get(i);
                order = tdEl.attributeValue("tabOrder");
                alias = tdEl.attributeValue("alias");
                if(alias != null){
                    tdEl.remove(tdEl.attribute("alias"));
                }
                if(order != null){
                    tdEl.addAttribute("alias",order + (i + 1));
                }
            }
            xml = doc.asXML();
            return addEscapeCharacter(xml);
        } catch (UnsupportedEncodingException | DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("解析XML文件错误");
        }
    }

    /**
     * 获取excel表内公式：不包括额外添加的文本框等
     * @param excel excel xml
     * @return map:key-单元格名，value-单元格的公式
     */
    public static Map<String,String> getExcelFormula(String excel){
        Map<String,String> formulas = new HashMap<>();
        Document doc = null;
        try {
            doc = getXMlDocument(excel);
            List<Node> trs = doc.selectNodes("/Report/WorkSheet/Table/TR");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = (Element) trs.get(i);
                List<Element> tds = tr.elements("TD");
                for (int j = 0; j < tds.size(); j++) {
                    Element td = tds.get(j);
                    String cellName = ExcelUtil.excelColIndexToStr(j+1) + (i+1);
                    String formula = td.attributeValue("formula");
                    if(formula != null){
                        formulas.put(cellName,formula);
                    }
                }
            }
        } catch (UnsupportedEncodingException | DocumentException e) {
            e.printStackTrace();
        }
        return formulas;
    }

    /**
     * 解析xml并存为map
     * @param xml
     * @return
     */
    public static Map<String,Map<String,String>> parseXMLToMap(String xml) {
        Document document = null;

        Map<String,Map<String,String>> cell = new HashMap<>();
        Map<String,String> attrs = null;
        try {
            document = getXMlDocument(xml);
            List<Node> trs = document.selectNodes("/Report/WorkSheet/Table/TR");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = (Element) trs.get(i);
                List<Element> tds = tr.elements("TD");
                attrs = new HashMap<>();
                for (int j = 0; j < tds.size(); j++) {
                    Element td = tds.get(j);
                    String cellName = ExcelUtil.excelColIndexToStr(j+1) + (i+1);
                    String cellVal = td.getText();
                    attrs.put("value",cellVal);
                    List<Attribute> attributes = td.attributes();
                    for (Attribute attribute : attributes) {
                        String attrName = attribute.getName();
                        String attrVal = attribute.getValue();
                        attrs.put(attrName,attrVal);
                    }
                    cell.put(cellName,attrs);
                }
            }
            return cell;
        } catch (DocumentException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("解析xml文件出错");
        }
    }

    /**
     * 修改xml节点值
     * @param xml excel xml文件
     * @param cellVals 节点值
     * @return 修改xml后
     */
    public static String setXMLValue(String xml, Map<String,String> cellVals) {
        Document document = null;
        if (cellVals == null || cellVals.size() == 0) {
            return xml;
        }
        try {

            document = getXMlDocument(xml);
            List<Node> trs = document.selectNodes("/Report/WorkSheet/Table/TR");
            for (int i = 0; i < trs.size(); i++) {
                Element tr = (Element) trs.get(i);
                List<Element> tds = tr.elements("TD");
                for (int j = 0; j < tds.size(); j++) {
                    Element td = tds.get(j);
                    String tdName = ExcelUtil.excelColIndexToStr(j + 1) + (i + 1);
                    String tdVal = td.getText();
                    String cellVal = cellVals.get(tdName);
                    if(cellVal == null){
                        continue;
                    }
                    td.setText(cellVal);
                }
            }

            xml = document.asXML();
            return addEscapeCharacter(xml);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("解析xml文件出错");
        }
    }

    /**
     * 解析硕正报表的上报xml获取填报值
     * @param xml excel xml 文件
     * @return 解析后节点值的集合
     */
    public static Map<String, String> parseDataXML(String xml) {
        Map<String,String> values = new HashMap<String,String>();
        Document document = null;
        try {
            document = getXMlDocument(xml);
            List<Node> nodes = document.selectNodes("/WorkSheet/data");
            for(Node node : nodes){
                Element element = (Element) node;
                String cell = element.attributeValue("Cell");
                String data = element.getText();
                values.put(cell,data);
            }
            return values;
        } catch (UnsupportedEncodingException | DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("XML解析错误");
        }
    }

    /**
     * 为excel xml 节点赋值
     * @param xml excel xml 文件
     * @param values 需设置的值集合
     * @return 修改后的xml文件
     */
    public static String setDataXMLValue(String xml,Map<String,String> values){
        Document doc = null;
        if(values == null || values.size() == 0){
            return xml;
        }
        try {
            doc = getXMlDocument(xml);
            List<Node> nodes = doc.selectNodes("/WorkSheet/data");
            for (Node node : nodes) {
                Element element = (Element) node;
                String cell = element.attributeValue("Cell");
                String cellVal = values.get(cell);
                if(cellVal != null){
                    element.setText(cellVal);
                }
            }
            xml = doc.asXML();
            return addEscapeCharacter(xml);
        } catch (UnsupportedEncodingException | DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("XML文件解析错误");
        }
    }

    /**
     * 汇总多个个填报数据,并返回xml格式
     * @param xmls uploadRuntime模式下提交的数据xml
     * @return xml
     */
    public static String summaryExcels(String ... xmls){
        if(xmls == null || xmls.length == 0){
            return  null;
        }
        List<String> list = new ArrayList<>();
        for(String xml : xmls){
            if(xml != null && !"".equals(xml.trim())){
                list.add(xml);
            }
        }
        int len = list.size();
        if(len == 0){
            return null;
        }
        String tempXml = list.get(0);
        if(len == 1){
            return tempXml;
        }
        for(int i = 1; i < len; i++){
            String xml = list.get(i);
            tempXml = summaryExcelToXml(tempXml,xml);
        }
        return addEscapeCharacter(tempXml);
    }

    /**
     * 汇总两个填报数据
     * @param uploadXml uploadRuntime模式下提交的数据xml
     * @param uploadXml1 uploadRuntime模式下提交的数据xml
     * @return 计算后的数据集合
     */
    public static Map<String,String> summaryExcelToMap(String uploadXml,String uploadXml1){
        Map<String,String> uploadData = parseDataXML(uploadXml);
        Map<String,String> uploadDataOne = parseDataXML(uploadXml1);
        return summaryExcelToMap(uploadData,uploadDataOne);
    }

    /**
     * 汇总两个填报数据,并返回为xml
     * @param uploadXml uploadRuntime模式下提交的数据xml
     * @param uploadXml1 uploadRuntime模式下提交的数据xml
     * @return 计算后的xml
     */
    public static String summaryExcelToXml(String uploadXml,String uploadXml1){
        Map<String,String> uploadData = parseDataXML(uploadXml);
        Map<String,String> uploadDataOne = parseDataXML(uploadXml1);
        Map<String,String> sumData = summaryExcelToMap(uploadData,uploadDataOne);
        String sumXml = setDataXMLValue(uploadXml,sumData);
        return addEscapeCharacter(sumXml);
    }

    /**
     * 汇总两个填报数据
     * @param data uploadRuntime模式下提交的数据xml提取的数据集合
     * @param data1 uploadRuntime模式下提交的数据xml提取的数据集合
     * @return 计算后的数据集合
     */
    public static Map<String,String> summaryExcelToMap(Map<String,String> data,Map<String,String> data1){
        Map<String,String> sumData = new HashMap<>();
        for(Map.Entry<String,String> entry : data.entrySet()){
            String cell = entry.getKey();
            String val = entry.getValue();
            String cellValOne = data1.get(cell);
            if("".equals(val) || val == null){
                val = "0";
            }
            if("".equals(cellValOne) || cellValOne == null){
                cellValOne = "0";
            }
            if(StringUtil.isNumeric(val) && StringUtil.isNumeric(cellValOne)){
                BigDecimal sum = new BigDecimal(val).add(new BigDecimal(cellValOne));
                sumData.put(cell,sum.toString());
            }
        }
        return sumData;
    }

    /**
     * 汇总两个填报数据,并返回为xml
     * @param xml uploadRuntime模式下提交的数据xml
     * @param data uploadRuntime模式下提交的数据xml提取的数据集合
     * @param data1 uploadRuntime模式下提交的数据xml提取的数据集合
     * @return 计算后的xml
     */
    public static String summaryExcelToXml(String xml,Map<String,String> data,Map<String,String> data1){
        Map<String,String> sumData = new HashMap<>();
        for(Map.Entry<String,String> entry : data.entrySet()){
            String cell = entry.getKey();
            String val = entry.getValue();
            String cellValOne = data1.get(cell);
            if("".equals(val) || val == null){
                val = "0";
            }
            if("".equals(cellValOne) || cellValOne == null){
                cellValOne = "0";
            }
            if(StringUtil.isNumeric(val) && StringUtil.isNumeric(cellValOne)){
                BigDecimal sum = new BigDecimal(val).add(new BigDecimal(cellValOne));
                sumData.put(cell,sum.toString());
            }
        }
        String sumXml = setDataXMLValue(xml,sumData);
        return addEscapeCharacter(sumXml);
    }

    /**
     * 汇总多个excel数据
     * @param departNames 填报部门名
     * @param dataXMLs 部门填报数据
     * @return
     */
    public static Map<String,String> summary(List<String> departNames,List<String> dataXMLs){
        Map<String,String> finalResult = new HashMap<>();
        String sumXml = null;
        StringBuilder msg = new StringBuilder();
        int departSize = departNames.size();
        int size = dataXMLs.size();
        if(departSize != size){
            throw new RuntimeException("填报部门与填报数据表数量不一致！");
        }
        if(size == 0){
            sumXml = "";
        }else if (size == 1){
            sumXml = dataXMLs.get(0);
        }else{
            String tempXML = null;
            Map<String,String> result;
            for(int i = 0; i < size; i++){
                String departName = departNames.get(i);
                String xml = dataXMLs.get(i);
                result = summaryExcel(tempXML,xml,departName);
                String resultXml = result.get("sumXML");
                String errorMsg = result.get("errorMsg");
                if(errorMsg == null){
                    tempXML = resultXml;
                }else {
                    if(errorMsg.contains(departName)){
                        departNames.remove(departName);
                        dataXMLs.remove(xml);
                        size = dataXMLs.size();
                        i --;
                    }
                    msg.append(errorMsg).append("<br>");
                }
            }
            sumXml = tempXML;
        }
        finalResult.put("sumXML",setErrorCellToNull(sumXml));
        finalResult.put("error",msg.toString());
        return finalResult;
    }

    /**
     * 汇总两个excel数据
     * @param dataXMLOne 第一个excel填报数据
     * @param departName 第二个excel填报部门
     * @param dataXMLTwo 第二个excel填报数据
     * @return
     */
    public static Map<String,String> summaryExcel(String dataXMLOne,String dataXMLTwo,String departName){
        Map<String,String> result = new HashMap<>();
        Map<String,String> sumData = new HashMap<>();
        String sumXML;
        String error;
        if(dataXMLOne == null){
            sumXML = dataXMLTwo;
            result.put("sumXML",sumXML);
            result.put("errorMsg", null);
            return result;
        }
        Map<String,String> dataOne = parseDataXML(dataXMLOne);
        Map<String,String> dataTwo = parseDataXML(dataXMLTwo);
        StringBuilder errorMsgTwo = new StringBuilder(departName).append("：");
        for(Map.Entry<String,String> entry : dataOne.entrySet()){
            String cell = entry.getKey();
            String val = entry.getValue();
            String cellValOne = dataTwo.get(cell);
            if("".equals(val) || val == null){
                val = "0";
            }
            if("".equals(cellValOne) || cellValOne == null){
                cellValOne = "0";
            }

            if(!StringUtil.isNumeric(cellValOne)){
                errorMsgTwo.append("<br>&nbsp;&nbsp;").append(cell).append(":“").append(cellValOne).append("”&nbsp;<span style='color:red'>数据格式不正确</span>");
            }
            if(StringUtil.isNumeric(val) && StringUtil.isNumeric(cellValOne)){
                BigDecimal sum = new BigDecimal(val).add(new BigDecimal(cellValOne));
                sumData.put(cell,sum.toString());
            }else {
                sumData.put(cell,"error");
            }
        }
        sumXML = setDataXMLValue(dataXMLOne,sumData);
        boolean errorTwoIsNull = !errorMsgTwo.toString().equals(departName + "：");
        if(errorTwoIsNull){
            error = errorMsgTwo.toString() + "<br>";
        }else {
            error = null;
        }
        result.put("sumXML",sumXML);
        result.put("errorMsg",error);
        return result;
    }

    /**
     * 将标有error的单元格值设为null
     * @param dataXML excel data xml
     * @return
     */
    public static String setErrorCellToNull(String dataXML){
        Map<String,String> data = parseDataXML(dataXML);
        for(Map.Entry<String,String> entry : data.entrySet()){
            String cell = entry.getKey();
            String value = entry.getValue();
            if("error".equals(value)){
                data.put(cell,null);
            }
        }
        String result = setDataXMLValue(dataXML,data);
        return addEscapeCharacter(result);
    }

    public static void main(String[] args){
        boolean f = false;
        boolean ff = false;
        if(f && ff){
            System.out.println("fff");
        }else if(f && !ff){
            System.out.println("f");
        }else if(!f && ff){
            System.out.println("ff");
        }else{
            System.out.println("");
        }
    }
}
