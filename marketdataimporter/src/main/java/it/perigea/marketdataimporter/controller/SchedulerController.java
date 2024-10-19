package it.perigea.marketdataimporter.controller;


import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.perigea.marketdataimporter.logger.postgre.model.TaskEntity;
import it.perigea.marketdataimporter.logger.postgre.model.TaskState;
import it.perigea.marketdataimporter.logger.postgre.model.TaskType;
import it.perigea.marketdataimporter.service.SchedulerService;

/**
 * A Controller class for the schedulation REST API
 */
@CrossOrigin
@RestController
@RequestMapping("scheduler")
public class SchedulerController
{	
	private Logger logger = LoggerFactory.getLogger(SchedulerController.class);
	
	@Autowired
	private SchedulerService schedulerService; 
	
	/**
	 * A get request for retrieving all active schedules
	 * @return ResponseEntity - the HTTP response
	 * @throws Exception 
	 */
	@GetMapping
	public ResponseEntity<List<TaskEntity>> getAllTask() throws Exception
	{
		logger.info("SCHEDULER: getAllTask");
		List<TaskEntity> list = schedulerService.getAllTask();
		logger.info("getAllTask retried "+list.size()+" task");
		return ResponseEntity.ok(list);
	}
	/**
	 * A get request for retrieve a specific schedule from the id
	 * @param taskName - the name of the task to search
	 * @return ResponseEntity - the HTTP response
	 */
	@GetMapping("/{taskName}")
	public ResponseEntity<TaskEntity> getTask(@PathVariable String taskName)
	{
		logger.info("SCHEDULER: getTask with task name: "+taskName);
		return ResponseEntity.ok(schedulerService.getTask(taskName));
	}
	
	/**
	 * A post request for creating a default scheduled task
	 * @param taskName - a unique name for our task
	 * @return ResponseEntity - the HTTP response
	 */
	@PostMapping("/{taskName}")
	public ResponseEntity<String> createDefaultTask(@PathVariable String taskName)
	{
		logger.info("SCHEDULER: createDefaultTask, task name: "+taskName);
		TaskEntity defaultEntity = new TaskEntity();
		defaultEntity.setTaskName(taskName);
		defaultEntity.setSchedulingInterval("0 0 12 * * ?");
		defaultEntity.setType(TaskType.DAILY);
		defaultEntity.setState(TaskState.ACTIVE);
		schedulerService.addTask(defaultEntity);
		
		return ResponseEntity.ok("Successful");
	}
	
	/**
	 * A post request for creating a custom scheduled task
	 * @param task -
	 * @return ResponseEntity - the HTTP response
	 */
	@PostMapping("/custom")
	public ResponseEntity<String> createCustomTask(@RequestBody TaskEntity task)
	{
		logger.info("SCHEDULER: createCustomTask, task name: "+task.getTaskName());
		schedulerService.addTask(task);
		
		return ResponseEntity.ok("Successful");
	}
	
	/**
	 * A put request for modify a scheduled task 
	 * @param task - must have the same taskName of the task to modify 
	 * @return ResponseEntity - the HTTP response
	 */
	@PutMapping
	public ResponseEntity<String> modifyTask(@RequestBody TaskEntity task)
	{
		logger.info("SCHEDULER: modifyTask: "+task.getTaskName());
		schedulerService.modifyTask(task);
		
		return ResponseEntity.ok("Successful");
	}
	
	/**
	 * A delete request for eliminating a scheduled task
	 * note: the task will not be removed from ours DB,
	 * so the task can be restarted by modifying its state from ENDED to ACTIVE
	 * @param task - the name of the scheduled task to delete
	 * @return ResponseEntity - the HTTP response
	 */
	@DeleteMapping()
	public ResponseEntity<String> deleteTask(@RequestBody TaskEntity task)
	{
		logger.info("SCHEDULER: deleteTask, task name: "+task.getTaskName());
		schedulerService.removeTask(task);
		
		return ResponseEntity.ok("Successful");
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<String> invalidTaskExceptionHandler(IllegalArgumentException e)
	{
		logger.error("INVALID TASK EXCEPTION: "+e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(value = NoSuchElementException.class)
	public ResponseEntity<String> noTaskFound(NoSuchElementException e)
	{
		logger.error("NO SUCH ELEMENT EXCEPTION HANDLER: "+e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(Exception e)
	{
		logger.error("INTERNAL SERVER ERROR HANDLER: "+e.getMessage());
		return ResponseEntity.internalServerError().body(e.getMessage());
	}
}
