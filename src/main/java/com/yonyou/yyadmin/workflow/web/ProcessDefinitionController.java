package com.yonyou.yyadmin.workflow.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yonyou.yyadmin.common.annotation.SystemLogAnnotation;
import com.yonyou.yyadmin.core.Result;
import com.yonyou.yyadmin.core.ResultGenerator;
import com.yonyou.yyadmin.workflow.entity.ProcessDefinitionDTO;
import com.yonyou.yyadmin.workflow.service.WorkFlowIdentifyService;
import com.yonyou.yyadmin.workflow.service.WorkflowDeployService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 业务流controller
 */

@RestController
@RequestMapping("/processDefinition")
public class ProcessDefinitionController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    //流程定义服务
    @Autowired
    private RepositoryService repositoryService;
    //流程运行时服务
    @Autowired
    private RuntimeService runtimeService;
    //流程任务服务
    @Autowired
    private TaskService taskService;
    @Autowired
    protected IdentityService identityService;
    //自定义流程服务
    @Autowired
    private WorkflowDeployService workflowProcessDefinitionService;

    @Autowired
    private WorkFlowIdentifyService workFlowIdentifyService;

    /**
     * 流程定义查询
     *
     * @return
     */
    @RequiresPermissions("processDefinition:list")
    @PostMapping("/list")
    public Result listProcessDefinition(Integer page, Integer size) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //此处分页有问题，仍然是每次查询所有的数据，只是与客户端通信做了分页处理，减少数据交互
        List<ProcessDefinition> list = processDefinitionQuery.list();
        ArrayList<ProcessDefinitionDTO> processDefinitionDTOS = new ArrayList<>();
        try {
            for (ProcessDefinition processDefinition : list) {
                ProcessDefinitionDTO processDefinitionDTO = new ProcessDefinitionDTO();
                BeanUtils.copyProperties(processDefinitionDTO, processDefinition);
                processDefinitionDTOS.add(processDefinitionDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("流程定义查询失败");
        }
        PageHelper.startPage(page, size);
        PageInfo pageInfo = new PageInfo(processDefinitionDTOS);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 激活流程
     */
    @SystemLogAnnotation("激活流程")
    @RequiresPermissions("processDefinition:active")
    @PostMapping("/active")
    public Result ActiviProcessDefinition(@RequestBody String processDefinitionId) {
        repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId);
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        return ResultGenerator.genSuccessResult(processDefinition);
    }

    /**
     * 挂起流程
     */
    @SystemLogAnnotation("挂起流程")
    @RequiresPermissions("processDefinition:suspend")
    @PostMapping("/suspend")
    public Result SuspendProcessDefinition(@RequestBody String processDefinitionId) {
        repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId);
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        return ResultGenerator.genSuccessResult(processDefinition);
    }

    /**
     * 部署流程
     */
    @SystemLogAnnotation("部署流程")
    @RequiresPermissions("processDefinition:deploy")
    @PostMapping("/deploy")
    public Result DeployedProcessDefinition(@RequestBody String id) {
        try {
            //删除流程实例
            repositoryService.deleteDeployment(id, true);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        String exportDir = "/tmp/prm/process";
        try {
            //部署流程
            workflowProcessDefinitionService.redeployAllBpmn(exportDir);
        } catch (Exception e) {
            return ResultGenerator.genFailResult("流程部署失败");
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 启动流程
     */
    @SystemLogAnnotation("启动流程")
    @RequiresPermissions("processDefinition:start")
    @PostMapping("/start")
    public Result startProcess(String processDefinitionKey, String businessType, String userID, String entityID) {

        //设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(userID);
        //2.构造 businessKey,流程关联业务
        String businessKey = businessType + "." + entityID;
        //3.流程变量
        HashMap<String, Object> variables = new HashMap<String, Object>();
        //4.启动流程实例,processDefinitionKey=process id
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 人员信息同步
     * @return
     */
    @SystemLogAnnotation("同步用户")
    @RequiresPermissions("processDefinition:synUserAndRole")
    @PostMapping("/synUserAndRole")
    public Result synAllUserAndRoleToActiviti() {
        //1.如果没有用户，同步用户和角色
        try {
            workFlowIdentifyService.synAllUserAndRoleToActiviti();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("工作流人员同步失败");
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 查看个人任务
     *
     * @param assignee
     * @return
     */
    @SystemLogAnnotation("查询个人任务")
    @RequiresPermissions("processDefinition:taskList")
    @PostMapping("/taskList")
    public Result findPersonelTaskList(@RequestBody String assignee) {
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 完成任务
     *
     * @param taskID
     * @return
     */
    @SystemLogAnnotation("完成任务")
    @RequiresPermissions("processDefinition:taskComplete")
    @PostMapping("/taskComplete")
    public Result completeTask(@RequestBody String taskID) {
        taskService.complete(taskID);
        return ResultGenerator.genSuccessResult();
    }
}



