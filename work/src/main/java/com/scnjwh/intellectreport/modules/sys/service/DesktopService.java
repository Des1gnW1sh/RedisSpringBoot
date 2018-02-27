package com.scnjwh.intellectreport.modules.sys.service;

import com.scnjwh.intellectreport.modules.sys.dao.DesktopDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ZhouXin
 * @version 2018/2/7
 * @description class description
 * Created by ZhouXin on 2018-02-07 16:18
 */
@Service
@Transactional(readOnly = true)
public class DesktopService {
    @Autowired
    private DesktopDao desktopDao;
    /**
     * 各部门本月填报数量
     * @return 数量
     */
    public int getThisMonthCountByDepart(){
        return desktopDao.getThisMonthCountByDepart();
    }

    /**
     * 统计所有部门的填报数量
     * @return 数量
     */
    public int getAllReportCountByDepart(){
        return desktopDao.getAllReportCountByDepart();
    }

    /**
     * 统计当前时间点正在运行中的任务数量
     * @return 数量
     */
    public int getRunningTaskCountByNow(){
        return desktopDao.getRunningTaskCountByNow();
    }

    /**
     * 根据结束时间点，统计近三天会结束的填报任务
     * @return 数量
     */
    public int getWillOverTaskByOverDate(){
        return desktopDao.getWillOverTaskByOverDate();
    }

    /**
     * 统计每月任务数量
     * @return
     */
    public List<Map<String,String>> getEveryMonthTaskNum(){
        return desktopDao.getEveryMonthTaskNum();
    }

    /**
     * 统计收集任务和汇总任务的比例
     * @return
     */
    public List<Map<String,String>> getTaskCountByTaskType(){
        return desktopDao.getTaskCountByTaskType();
    }

    /**
     * 统计各部门任务填报数量
     * @return
     */
    public List<Map<String,String>> getTaskCountByDepart(){
        return desktopDao.getTaskCountByDepart();
    }
}
