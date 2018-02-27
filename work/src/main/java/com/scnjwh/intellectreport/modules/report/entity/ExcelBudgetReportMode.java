package com.scnjwh.intellectreport.modules.report.entity;

import com.scnjwh.intellectreport.common.persistence.DataEntity;

import java.util.Date;

/**
 * Created by Administrator on 2018-2-5.
 */
public class ExcelBudgetReportMode extends DataEntity<ExcelBudgetReportMode> {
    private String name; //名称
    private String text; //说明
    private String mode; //下发模式
    private String office; //下发部门节点
    private String officeLabname; //下发部门节点名称
    private String finishtime; //完成时间
    private String isemail; //是否邮件提醒
    private String warntime; //邮件提醒时间
    private String tasktype; //任务类型
    private String istasksms; //是否短信流程提醒
    private String issms; //是否短信自动提醒
    private String urgetime; //催报时间间隔
    private String isformula; //是否强制公式审核
    private String xml; //模板内容
    private Date beginCreateDate;
    private Date endCreateDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getIsemail() {
        return isemail;
    }

    public void setIsemail(String isemail) {
        this.isemail = isemail;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public String getIstasksms() {
        return istasksms;
    }

    public void setIstasksms(String istasksms) {
        this.istasksms = istasksms;
    }

    public String getIssms() {
        return issms;
    }

    public void setIssms(String issms) {
        this.issms = issms;
    }

    public String getIsformula() {
        return isformula;
    }

    public void setIsformula(String isformula) {
        this.isformula = isformula;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getWarntime() {
        return warntime;
    }

    public void setWarntime(String warnTime) {
        this.warntime = warnTime;
    }

    public String getUrgetime() {
        return urgetime;
    }

    public void setUrgetime(String urgeTime) {
        this.urgetime = urgeTime;
    }

    public String getOfficeLabname() {
        return officeLabname;
    }

    public void setOfficeLabname(String officeLabname) {
        this.officeLabname = officeLabname;
    }
}
