package com.activiti.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.junit.Before;
import org.junit.Test;

public class Activititables {
	private RepositoryService repositoryService;
	
	private RuntimeService runtimeService;
	
	private TaskService taskService;
	
	@Before
	public void init() {
		ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
		repositoryService = defaultProcessEngine.getRepositoryService();
		runtimeService = defaultProcessEngine.getRuntimeService();
		taskService = defaultProcessEngine.getTaskService();
	}
	
	@Test
	public void deployment() { //发布流程图
		DeploymentBuilder createDeployment = repositoryService.createDeployment();
		DeploymentBuilder name = createDeployment.name("学生流程");
		createDeployment.addClasspathResource("studentjia.bpmn");
		createDeployment.deploy();
	}
	
	@Test
	public void entity() { //创建流程实例
		
		ProcessInstance startProcessInstanceByKey = runtimeService.startProcessInstanceByKey("myProcess");
		System.out.println("流程实例Id:"+startProcessInstanceByKey.getId());
	}
	
	@Test
	public void task() { //查询已有任务
		List<Task> list = taskService.createTaskQuery().processInstanceId("37501").list();
		for (Task task : list) {
			System.out.println(task.getId());
			System.out.println(task.getName());
			System.out.println(task.getCreateTime());
			System.out.println("-----------------------");
		}
	}
	
	@Test //创建流程变量
	public void bnumber() {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("number", 2);
		taskService.complete("37505", map);
	}
	
	@Test
	public void comit() {
		taskService.complete("42504");
		System.out.println("完成一步");
	}
}
