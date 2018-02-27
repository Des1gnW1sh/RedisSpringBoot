package com.scnjwh.intellectreport.common.timedtask;

import com.scnjwh.intellectreport.common.utils.DateUtils;
import com.scnjwh.intellectreport.common.utils.MailUtil;
import com.scnjwh.intellectreport.common.utils.PropertiesUtil;
import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportCollectDao;
import com.scnjwh.intellectreport.modules.report.dao.ExcelBudgetReportDao;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportCollect;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportCollectService;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportService;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.entity.User;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.mail.MessagingException;
import java.util.*;


public class ReportEmailTask extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(ReportEmailTask.class);

    private static ExcelBudgetReportDao excelBudgetReportDao;

    private static ExcelBudgetReportCollectDao excelBudgetReportCollectDao;

    private static final Properties prop = new PropertiesUtil("jeesite.properties").getProperties();

    static {
        excelBudgetReportDao = SpringContextHolder.getBean(ExcelBudgetReportDao.class);
        excelBudgetReportCollectDao = SpringContextHolder.getBean(ExcelBudgetReportCollectDao.class);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("=================================邮件提醒定时任务 start=============================");
        long start = System.currentTimeMillis();//任务开始时间
        //获得填报任务中需要发送邮件提醒的任务
        List<ExcelBudgetReport> reports = excelBudgetReportDao.getNeedEmailReport();
        int reportSize = reports.size();
        if(reportSize > 0){
            for(ExcelBudgetReport report : reports){
                String reportId = report.getId();//任务id
                String reportName = report.getName();//任务名
                String issueFlag = report.getIssueFlag();//下发方式
                Integer warnTime = report.getWarnTime();//提醒距离结束时间
                Date overDate = report.getOverTimo();//任务结束时间
                //获得任务应提醒的时间
                long warnDateL = Objects.requireNonNull(DateUtils.delDateSeconds(overDate)).getTime() - warnTime * 60 * 60 * 1000;
                //当前时间
                long nowDateL = Objects.requireNonNull(DateUtils.delDateSeconds(new Date())).getTime();
                //如果计算的提醒时间不等于当前时间，则查询下一个任务是否需要发送邮件
                if(warnDateL != nowDateL){
                    continue;
                }
                //获取填报任务下的未填报子任务
                List<ExcelBudgetReportCollect> collects = excelBudgetReportCollectDao.getNotUploadTask(reportId);
                int collectSize = collects.size();
                StringBuilder receivers = new StringBuilder();
                if(collectSize > 0){
                    for( int i = 0; i < collectSize ;i ++){
                        ExcelBudgetReportCollect collect = collects.get(i);
                        Office office = collect.getOffice();//子任务所属部门
                        User primaryPerson = office.getPrimaryPerson();//部门主负责人
                        User deputyPerson = office.getDeputyPerson();//部门副负责人
                        String officeEmail = office.getEmail();//部门邮箱
                        String primaryEmail = primaryPerson != null ? primaryPerson.getEmail() : "";//主负责人邮箱
                        String deputyEmail = deputyPerson != null ? deputyPerson.getEmail() : "";//副负责人邮箱
                        //判断是否有合法邮箱地址，有则加入发送邮件
                        if (primaryEmail != null && !"".equals(primaryEmail.trim()) && MailUtil.checkEmail(primaryEmail)) {
                            receivers.append(primaryEmail);
                        } else if (deputyEmail != null && !"".equals(deputyEmail.trim()) && MailUtil.checkEmail(deputyEmail)) {
                            receivers.append(deputyEmail);
                        } else if (officeEmail != null && !"".equals(officeEmail.trim()) && MailUtil.checkEmail(officeEmail)) {
                            receivers.append(officeEmail);
                        }
                        if (!receivers.toString().trim().equals("") && i != collectSize - 1) {
                            receivers.append(",");
                        }
                    }
                }
                //格式化结束时间
                String endTime = DateUtils.formatDateTime(overDate);
                //计算任务距离结束的天、时、分、秒
                String remainingTime = DateUtils.getCountDown(overDate,true);
                //当前任务下有需要发送邮件的任务则发送邮件，否则遍历下一个任务
                if(!receivers.toString().trim().equals("")){
                    sendEmail(receivers.toString(),reportName,endTime,remainingTime,issueFlag);
                }
            }
        }

        long end = System.currentTimeMillis();
        logger.info("-------------------耗时: " + ((end - start) / 1000) + "秒" + (Math.abs(start - end) % 1000) + "毫秒");
        logger.info("=================================邮件提醒定时任务  end =============================");
    }

    /**
     * 发送填报任务邮件
     *
     * @param receivers     邮件接收者
     * @param reportName    填报任务名
     * @param endTime       任务结束时间
     * @param remainingTime 任务剩余时间
     * @param issueFlag 下发标识
     */
    private void sendEmail(String receivers, String reportName, String endTime, String remainingTime, String issueFlag) {
        String title = "填报任务";
        String content = "您的“<span style='color:red'>" + reportName + "</span>”填报任务，将于<span style='color:red'>" + endTime + "</span>时结束，离填报结束还有" + remainingTime + "，请抓紧时间填报或安排其他人填报。";
        if(issueFlag.equals("1")){
            content = "您或您下级部门的" + reportName + "填报任务，将于" + endTime + "时结束，离填报结束还有" + remainingTime + "，请抓紧时间填报或下发下级部门填报。";
        }

        try {
            //发送邮件
            MailUtil.sendEmail(receivers, title, content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
