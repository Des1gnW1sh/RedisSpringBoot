package com.scnjwh.intellectreport.common.timedtask;

import com.scnjwh.intellectreport.common.utils.DateUtils;
import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportCollectDao;
import com.scnjwh.intellectreport.modules.report.dao.ExcelConfigGlobalDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportCollect;
import com.scnjwh.intellectreport.modules.report.entity.ExcelConfigGlobal;
import com.scnjwh.intellectreport.modules.sys.entity.User;
import com.scnjwh.intellectreport.modules.taskstate.dao.TaskStateDao;
import com.scnjwh.intellectreport.modules.taskstate.entity.TaskState;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

/**
 * @author zhouxin
 * @version 2018-02-24
 * @date 17:44
 * @description 报表任务填报情况统计任务
 */
public class TaskStateCountTask extends QuartzJobBean {

    private static final Logger logger = Logger.getLogger(TaskStateCountTask.class);
    private static ExcelConfigGlobalDao excelConfigGlobalDao;
    private static ExcelBudgetReportCollectDao excelBudgetReportCollectDao;
    private static TaskStateDao taskStateDao;

    static {
        excelConfigGlobalDao = SpringContextHolder.getBean(ExcelConfigGlobalDao.class);
        excelBudgetReportCollectDao = SpringContextHolder.getBean(ExcelBudgetReportCollectDao.class);
        taskStateDao = SpringContextHolder.getBean(TaskStateDao.class);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //查询配置需统计的部门
        List<ExcelConfigGlobal> configs = excelConfigGlobalDao.getAllConfig();

        TaskState state = null;
        User user = null;
        if (configs.size() > 0) {
            //遍历部门，并查询各部门的填报任务
            for (ExcelConfigGlobal config : configs) {
                String userId = config.getUserId();
                String officeId = config.getOffice().getId();
                int isOpen = config.getIsOpen();
                int isDay = config.getIsDay();
                int time = config.getTime();
                if (isOpen == 0) {
                    continue;
                }
                long confMill = 0;
                if (isDay == 1) {
                    confMill = 24 * 60 * 60 * 1000 * time;
                } else {
                    confMill = 60 * 60 * 1000 * time;
                }
                StringBuilder taskIds = new StringBuilder();
                user = new User();
                user.setId(userId);
                state = taskStateDao.getByUserId(userId);
                String stateTaskIds = state != null ? state.getTaskIds() : null;
                //得到该部门的所有填报任务
                List<ExcelBudgetReportCollect> collects = excelBudgetReportCollectDao.getCollectByOffice(officeId);
                //进行中任务数量
                int runningCount = 0;
                //即将结束任务数量
                int aboutEndCount = 0;
                //超时任务数量
                int outTimeCount = 0;
                int len = collects.size();
                if (len <= 0) {
                    continue;
                }
                //判断任务是否变化
                boolean isChange = false;
                //遍历任务
                for (ExcelBudgetReportCollect collect : collects) {
                    String collectId = collect.getId();

                    ExcelBudgetReport report = collect.getExcelBudgetReport();
                    //任务规定完成时间
                    Date completeTime = report.getOverTimo();
                    //任务状态
                    String status = collect.getStatus();
                    //当前时间
                    Date now = new Date();
                    //比较完成时间与当前时间
                    int compare = completeTime.compareTo(now);
                    //获取任务完成时间距当前时间的毫秒时
                    long difference = Objects.requireNonNull(DateUtils.delDateSeconds(completeTime)).getTime() - Objects.requireNonNull(DateUtils.delDateSeconds(now)).getTime();
                    //若完成时间在当前时间之后，且任务未提交汇总，则进行中数量加1
                    if (compare > 0 && "0".equals(status)) {
                        taskIds.append(collectId).append(",");
                        //若任务变化
                        if (stateTaskIds != null && !stateTaskIds.contains(collectId) && !isChange) {
                            isChange = true;
                        }
                        //若完成时间距当前时间在配置时间内，则即将结束任务数加1
                        if(difference > 0 && difference <= confMill) {
                            aboutEndCount++;
                        }
                        runningCount++;
                    }

                    //若完成时间在当前时间之前，且任务未提交汇总，则超时数量加1
                    if (compare < 0 && "0".equals(status)) {
                        outTimeCount++;
                    }
                }

                if (state == null) {//若该用户不存在统计记录，则新建记录
                    //创建任务状态统计实体类
                    state = new TaskState();
                    state.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    state.setUser(user);
                    state.setTaskIds(taskIds.toString());
                    state.setRunningNum(runningCount);
                    state.setAboutEndNum(aboutEndCount);
                    state.setOvertimeNum(outTimeCount);
                    state.setIsCheck(0);
                    //保存数据
                    taskStateDao.insert(state);
                } else {//否则更新记录
                    state.setTaskIds(taskIds.toString());
                    state.setRunningNum(runningCount);
                    state.setAboutEndNum(aboutEndCount);
                    state.setOvertimeNum(outTimeCount);
                    if (isChange) {
                        state.setIsCheck(0);
                    }
                    //更新数据
                    taskStateDao.update(state);
                }
            }
        }
    }
}
