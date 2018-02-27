/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">内江五环科技</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.report.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scnjwh.intellectreport.common.persistence.DataEntity;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 网络数据报表预算Entity
 *
 * @author timo
 * @version 2017-06-06
 */
public class ExcelConfigGlobal extends DataEntity<ExcelConfigGlobal> {

    private static final long serialVersionUID = 1L;
    private Integer isOpen;
    private Integer isDay;
    private Integer time;
    private String userId;
    private Office office;//用户部门

    public Integer getIsDay() {
        return isDay;
    }

    public void setIsDay(Integer isDay) {
        this.isDay = isDay;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}