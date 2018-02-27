/**
 * Copyright &copy; 2012-2016 <a href="https://www.scnjwh.com">财政综合管理</a> All rights reserved.
 */
package com.scnjwh.intellectreport.modules.act.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scnjwh.intellectreport.common.persistence.Page;
import com.scnjwh.intellectreport.common.utils.StringUtils;
import com.scnjwh.intellectreport.common.web.BaseController;
import com.scnjwh.intellectreport.modules.act.service.ActProcessService;
import com.scnjwh.intellectreport.modules.act.service.ProcessDiagramGenerator;

/**
 * 流程定义相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/process")
public class ActProcessController extends BaseController {

    @Autowired
    private ActProcessService actProcessService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RepositoryService repositoryService;

    /**
     * 流程定义列表
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = { "list", "" })
    public String processList(String category, HttpServletRequest request,
                              HttpServletResponse response, Model model) {
        /*
         * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
         */
        Page<Object[]> page = actProcessService.processList(new Page<Object[]>(request, response),category);
        model.addAttribute("page", page);
        model.addAttribute("category", category);
        return "modules/act/actProcessList";
    }

    /**
     * 运行中的实例列表
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "running")
    public String runningList(String procInsId, String procDefKey, HttpServletRequest request,
                              HttpServletResponse response, Model model) {
        Page<ProcessInstance> page = actProcessService.runningList(new Page<ProcessInstance>(
            request, response), procInsId, procDefKey);
        model.addAttribute("page", page);
        model.addAttribute("procInsId", procInsId);
        model.addAttribute("procDefKey", procDefKey);
        return "modules/act/actProcessRunningList";
    }

    /**
     * 读取资源，通过部署ID
     * @param processDefinitionId  流程定义ID
     * @param processInstanceId 流程实例ID
     * @param resourceType 资源类型(xml|image)
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "resource/read")
    public void resourceRead(String procDefId, String proInsId, String resType,
                             HttpServletResponse response) throws Exception {
        InputStream resourceAsStream = actProcessService.resourceRead(procDefId, proInsId, resType);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    
    /**
     * 获取流程图 并高亮当前
     * @param pProcessInstanceId 流程图id
     * @param response
     * @throws Exception
     * @Author : pengjunhao. create at 2017年5月12日 上午11:52:19
     */
    @RequestMapping(value = "getActivitiImage")
    public void getActivitiProccessImage(String pProcessInstanceId, HttpServletResponse response)
                                                                                                 throws Exception {
        logger.info("[开始]-获取流程图图像");
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery().processInstanceId(pProcessInstanceId)
                .singleResult();

            if (historicProcessInstance == null) {
                throw new Exception("获取流程实例ID[" + pProcessInstanceId + "]对应的历史流程实例失败！");
            } else {
                // 获取流程定义
                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                    .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());
                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService
                    .createHistoricActivityInstanceQuery().processInstanceId(pProcessInstanceId)
                    .orderByHistoricActivityInstanceId().asc().list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = new ArrayList<String>();
                int index = 1;
                logger.info("获取已经执行的节点ID");
                for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                    executedActivityIdList.add(activityInstance.getActivityId());
                    logger.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId()
                                + " : " + activityInstance.getActivityName());
                    index++;
                }

                // 获取流程图图像字符流
                InputStream imageStream = ProcessDiagramGenerator.generateDiagram(
                    processDefinition, "png", executedActivityIdList);

                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                imageStream.close();
            }
            logger.info("[完成]-获取流程图图像");
        } catch (Exception e) {
            logger.error("【异常】-获取流程图失败！" + e.getMessage());
            throw new Exception("获取流程图失败！" + e.getMessage());
        }
    }

    /**
     * 部署流程
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "/deploy", method = RequestMethod.GET)
    public String deploy(Model model) {
        return "modules/act/actProcessDeploy";
    }

    /**
     * 部署流程 - 保存
     * @param file
     * @return
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public String deploy(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir,
                         String category, MultipartFile file, RedirectAttributes redirectAttributes) {

        String fileName = file.getOriginalFilename();

        if (StringUtils.isBlank(fileName)) {
            redirectAttributes.addFlashAttribute("message", "请选择要部署的流程文件");
        } else {
            String message = actProcessService.deploy(exportDir, category, file);
            redirectAttributes.addFlashAttribute("message", message);
        }

        return "redirect:" + adminPath + "/act/process";
    }

    /**
     * 设置流程分类
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "updateCategory")
    public String updateCategory(String procDefId, String category,
                                 RedirectAttributes redirectAttributes) {
        actProcessService.updateCategory(procDefId, category);
        return "redirect:" + adminPath + "/act/process";
    }

    /**
     * 挂起、激活流程实例
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "update/{state}")
    public String updateState(@PathVariable("state") String state, String procDefId,
                              RedirectAttributes redirectAttributes) {
        String message = actProcessService.updateState(state, procDefId);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:" + adminPath + "/act/process";
    }

    /**
     * 将部署的流程转换为模型
     * @param procDefId
     * @param redirectAttributes
     * @return
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "convert/toModel")
    public String convertToModel(String procDefId, RedirectAttributes redirectAttributes)
                                                                                         throws UnsupportedEncodingException,
                                                                                         XMLStreamException {
        org.activiti.engine.repository.Model modelData = actProcessService
            .convertToModel(procDefId);
        redirectAttributes.addFlashAttribute("message", "转换模型成功，模型ID=" + modelData.getId());
        return "redirect:" + adminPath + "/act/model";
    }

    /**
     * 导出图片文件到硬盘
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "export/diagrams")
    @ResponseBody
    public List<String> exportDiagrams(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir)
                                                                                                              throws IOException {
        List<String> files = actProcessService.exportDiagrams(exportDir);
        ;
        return files;
    }

    /**
     * 删除部署的流程，级联删除流程实例
     * @param deploymentId 流程部署ID
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "delete")
    public String delete(String deploymentId,String procDefId,
                         RedirectAttributes redirectAttributes) {
        int conut = actProcessService.validateDelete(procDefId);
        if(conut>0){
            addMessage(redirectAttributes, "该流程被使用无法删除");
            return "redirect:" + adminPath + "/act/process";
        }
        actProcessService.deleteDeployment(deploymentId);
        return "redirect:" + adminPath + "/act/process";
    }

    /**
     * 删除流程实例
     * @param procInsId 流程实例ID
     * @param reason 删除原因
     */
    @RequiresPermissions("act:process:edit")
    @RequestMapping(value = "deleteProcIns")
    public String deleteProcIns(String procInsId, String reason,
                                RedirectAttributes redirectAttributes) {
        if (StringUtils.isBlank(reason)) {
            addMessage(redirectAttributes, "请填写删除原因");
        } else {
            actProcessService.deleteProcIns(procInsId, reason);
            addMessage(redirectAttributes, "删除流程实例成功，实例ID=" + procInsId);
        }
        return "redirect:" + adminPath + "/act/process/running/";
    }

}
