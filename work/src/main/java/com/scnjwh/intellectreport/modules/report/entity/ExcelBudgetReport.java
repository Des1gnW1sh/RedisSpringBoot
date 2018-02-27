/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.scnjwh.intellectreport.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scnjwh.intellectreport.common.persistence.DataEntity;

/**
 * 网络数据报表预算Entity
 *
 * @author timo
 * @version 2017-06-06
 */
public class ExcelBudgetReport extends DataEntity<ExcelBudgetReport> {

    private static final long serialVersionUID = 1L;
    private String parentId; //上级ID
    private String name; // 名称
    private String officeLab; // 机构 id
    private String officeLabname; // 机构name
    private Date overTimo; //  完成时间
    private String issueFlag; // 下发标识 0 标识直接下发 1表示分级下发
    private String isOpenEmailWarn;//是否开启邮件提醒
    private Integer warnTime;//邮件提醒时间：距离完成时间6h,12h,24h
    private Integer taskType;//任务类型：0-收集任务、1-汇总任务
    //private Integer status;//任务状态：0-进行中、1-已结束
    private String progress;//任务进度，百分比
    private String informSchedule; // 短信流程通知
    private String informUrge; // 短信自动催报
    private String isOver; // 是否归集完成
    private Long urgeTime; // 自动催报间隔
    private String insideFormula; // 表内公式
    private String outsideFormula; // 表间公式
    private String forceFormula; // 强制公式审核
    private String excle; // 报表
    private String submit;//提交的数据
    private Date beginCreateDate; // 开始 创建时间
    private Date endCreateDate; // 结束 创建时间
    private Integer finishEmail;//是否已发送结束邮件
    private String userId;//用于查询条件
    private String status;
    private List<Office> offices;

    public ExcelBudgetReport() {
        super();
    }

    public ExcelBudgetReport(String id) {
        super(id);
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    @Length(min = 1, max = 255, message = "名称长度必须介于 1 和 255 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficeLab() {
        return officeLab;
    }

    public void setOfficeLab(String officeLab) {
        this.officeLab = officeLab;
    }

    public String getOfficeLabname() {
        return officeLabname;
    }

    public void setOfficeLabname(String officeLabname) {
        this.officeLabname = officeLabname;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "完成时间不能为空")
    public Date getOverTimo() {
        return overTimo;
    }

    public void setOverTimo(Date overTimo) {
        this.overTimo = overTimo;
    }

    @Length(min = 1, max = 1, message = "下发标识长度必须介于 1 和 1 之间")
    public String getIssueFlag() {
        return issueFlag;
    }

    public void setIssueFlag(String issueFlag) {
        this.issueFlag = issueFlag;
    }

    @Length(min = 1, max = 1, message = "短信流程通知长度必须介于 1 和 1 之间")
    public String getInformSchedule() {
        return informSchedule;
    }

    public void setInformSchedule(String informSchedule) {
        this.informSchedule = informSchedule;
    }

    @Length(min = 1, max = 1, message = "短信自动催报长度必须介于 1 和 1 之间")
    public String getInformUrge() {
        return informUrge;
    }

    public void setInformUrge(String informUrge) {
        this.informUrge = informUrge;
    }

    @Length(min = 1, max = 1, message = "是否归集完成长度必须介于 1 和 1 之间")
    public String getIsOver() {
        return isOver;
    }

    public void setIsOver(String isOver) {
        this.isOver = isOver;
    }

    public String getIsOpenEmailWarn() {
        return isOpenEmailWarn;
    }

    public void setIsOpenEmailWarn(String isOpenEmailWarn) {
        this.isOpenEmailWarn = isOpenEmailWarn;
    }

    public Integer getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(Integer warnTime) {
        this.warnTime = warnTime;
    }

    public Long getUrgeTime() {
        return urgeTime;
    }

    public void setUrgeTime(Long urgeTime) {
        this.urgeTime = urgeTime;
    }

    public String getInsideFormula() {
        return insideFormula;
    }

    public void setInsideFormula(String insideFormula) {
        this.insideFormula = insideFormula;
    }

    public String getOutsideFormula() {
        return outsideFormula;
    }

    public void setOutsideFormula(String outsideFormula) {
        this.outsideFormula = outsideFormula;
    }

    @Length(min = 1, max = 1, message = "强制公式审核长度必须介于 1 和 1 之间")
    public String getForceFormula() {
        return forceFormula;
    }

    public void setForceFormula(String forceFormula) {
        this.forceFormula = forceFormula;
    }

    @NotNull(message = "报表不能为空")
    public String getExcle() {
        return excle;
    }

    public void setExcle(String excle) {
        this.excle = excle;
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

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getFinishEmail() {
        return finishEmail;
    }

    public void setFinishEmail(Integer finishEmail) {
        this.finishEmail = finishEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}