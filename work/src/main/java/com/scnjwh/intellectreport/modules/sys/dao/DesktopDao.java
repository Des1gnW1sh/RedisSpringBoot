package com.scnjwh.intellectreport.modules.sys.dao;

import com.scnjwh.intellectreport.common.persistence.CrudDao;
import com.scnjwh.intellectreport.common.persistence.annotation.MyBatisDao;
import com.scnjwh.intellectreport.common.service.CrudService;

import java.util.List;
import java.util.Map;

/**
 * @author ZhouXin
 * @version 2018/2/7
 * @description 首页数据dao层
 * Created by ZhouXin on 2018-02-07 16:20
 */
@MyBatisDao
public interface DesktopDao {
    /**
     * 各部门本月填报数量
     * @return 数量
     */
    int getThisMonthCountByDepart();

    /**
     * 统计所有部门的填报数量
     * @return 数量
     */
    int getAllReportCountByDepart();

    /**
     * 统计当前时间点正在运行中的任务数量
     * @return 数量
     */
    int getRunningTaskCountByNow();

    /**
     * 根据结束时间点，统计近三天会结束的填报任务
     * @return 数量
     */
    int getWillOverTaskByOverDate();

    /**
     * 统计每月任务数量
     * @return
     */
    List<Map<String,String>> getEveryMonthTaskNum();

    /**
     * 统计收集任务和汇总任务的比例
     * @return
     */
    List<Map<String,String>> getTaskCountByTaskType();

    /**
     * 统计各部门任务填报数量
     * @return
     */
    List<Map<String,String>> getTaskCountByDepart();
}
