package com.scnjwh.intellectreport.common.timedtask;

import com.scnjwh.intellectreport.common.utils.DateUtils;
import com.scnjwh.intellectreport.common.utils.MailUtil;
import com.scnjwh.intellectreport.common.utils.PropertiesUtil;
import com.scnjwh.intellectreport.common.utils.SpringContextHolder;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReport;
import com.scnjwh.intellectreport.modules.report.entity.ExcelBudgetReportCollect;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportCollectService;
import com.scnjwh.intellectreport.modules.report.service.ExcelBudgetReportService;
import com.scnjwh.intellectreport.modules.sys.entity.Office;
import com.scnjwh.intellectreport.modules.sys.entity.User;
import com.scnjwh.intellectreport.modules.sys.utils.DictUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 报表任务结束时给未填报部门通知
 * @author Administrator:zhouxin
 * @version 2018-02-01
 */
public class ReportEndNoticeTask extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(ReportEmailTask.class);
    private static ExcelBudgetReportService excelBudgetReportService;
    private static ExcelBudgetReportCollectService excelBudgetReportCollectService;
    private static final Properties prop = new PropertiesUtil("jeesite.properties").getProperties();

    static {
        excelBudgetReportService = SpringContextHolder.getBean(ExcelBudgetReportService.class);
        excelBudgetReportCollectService = SpringContextHolder.getBean(ExcelBudgetReportCollectService.class);
    }
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("=================================任务结束邮件提醒定时任务 start=============================");

        long start = System.currentTimeMillis();
        //查询已结束的任务中未上报的子任务
        //1.查询已结束的任务
        List<ExcelBudgetReport> reports = excelBudgetReportService.getFinishedTask();
        int reportSize = reports.size();
        if(reportSize > 0) {

            for (ExcelBudgetReport report : reports) {
                boolean hasChildTask = true;
                String reportId = report.getId();
                String reportName = report.getName();//任务名
                Date overDate = report.getOverTimo();//任务结束时间
                StringBuilder emails = new StringBuilder();
                //2.查询任务中未上报的子任务
                List<ExcelBudgetReportCollect> collects = excelBudgetReportCollectService.getNotUploadTask(reportId);
                int size = collects.size();
                //如果未查询到结果，则退出
                if (size <= 0) {
                    hasChildTask = false;
                } else {
                    //查询到结果，遍历
                    for (int i = 0; i < size; i++) {
                        ExcelBudgetReportCollect collect = collects.get(i);
                        Office office = collect.getOffice();//子任务所属部门
                        User primaryPerson = office.getPrimaryPerson();//部门主负责人
                        User deputyPerson = office.getDeputyPerson();//部门副负责人
                        String officeEmail = office.getEmail();//部门邮箱
                        String primaryEmail = primaryPerson != null ? primaryPerson.getEmail() : "";//主负责人邮箱
                        String deputyEmail = deputyPerson != null ? deputyPerson.getEmail() : "";//副负责人邮箱
                        //判断是否有合法邮箱地址，有则加入发送邮件
                        if (primaryEmail != null && !"".equals(primaryEmail.trim()) && MailUtil.checkEmail(primaryEmail)) {
                            emails.append(primaryEmail);
                        } else if (deputyEmail != null && !"".equals(deputyEmail.trim()) && MailUtil.checkEmail(deputyEmail)) {
                            emails.append(deputyEmail);
                        } else if (officeEmail != null && !"".equals(officeEmail.trim()) && MailUtil.checkEmail(officeEmail)) {
                            emails.append(officeEmail);
                        }
                        if (!emails.toString().trim().equals("") && i != size - 1) {
                            emails.append(",");
                        }
                    }
                }
                //如果邮箱地址不为空，则发送通知邮件
                boolean flag = true;
                if (!emails.toString().trim().equals("")) {
                    flag = sendEmail(emails.toString(), reportName, overDate);
                }
                //若发送邮件成功或该任务下午子任务则标识该任务为已发送邮件，避免重复发送，并结束任务
                if (flag || !hasChildTask) {
                    excelBudgetReportService.setFinishEmail(reportId);
                    excelBudgetReportService.updateIsOver(reportId);
                }
            }
        }
        long end = System.currentTimeMillis();
        logger.info("----------------------------耗时:" + ((end - start) / 1000) + "秒" + (Math.abs(start - end) % 1000) + "毫秒");
        logger.info("=================================任务结束邮件提醒定时任务  end =============================");
    }

    /**
     * 发送填报任务邮件
     *
     * @param receiver     邮件接收者
     * @param reportName    填报任务名
     * @param endTime       任务结束时间
     * @return 是否发送成功
     */
    private boolean sendEmail(String receiver, String reportName, Date endTime) {
        String title = "填报任务结束通知";
        String content = "您好，您的填报任务:“<span style='color:red'>" + reportName + "</span>”已于<span style='color:red'>" + DateUtils.formatDate(endTime,"yyyy年MM月dd日 HH时mm分ss秒") + "</span>结束，勿需再填报。";
        try {
            //发送邮件
            MailUtil.sendEmail(receiver, title, content);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
