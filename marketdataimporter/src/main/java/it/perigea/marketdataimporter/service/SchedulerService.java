package it.perigea.marketdataimporter.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import it.perigea.marketdataimporter.logger.postgre.model.TaskEntity;
import it.perigea.marketdataimporter.logger.postgre.model.TaskState;
import it.perigea.marketdataimporter.logger.postgre.model.TaskType;
import it.perigea.marketdataimporter.logger.postgre.repository.TaskRepository;

/**
 * A service for creating/removing/modifying scheduled download of data
 */
@Service
public class SchedulerService
{
	@Autowired
	private TaskRepository repository;
	
	@Autowired
	private ThreadPoolTaskScheduler scheduler;
	
	@Autowired
	private DataImporterService dataImporterService;
	
	private Map<TaskType, Runnable> mapper;
	
	private Map<TaskEntity, ScheduledFuture<?>> startedRunnableMap;
	
	@PostConstruct
	public void startup()
	{
		//TODO: da spostare da qualche parte 
		mapper = new HashMap<TaskType, Runnable>();
		mapper.put(TaskType.DAILY, () ->
		{
			try
			{
				dataImporterService.getDaily(LocalDate.now().plusDays(-1));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
		
		//fine
		startedRunnableMap = new HashMap<TaskEntity, ScheduledFuture<?>>();
		List<TaskEntity> listOfTasks = repository.getRunningTask();
		listOfTasks.forEach(task -> 
		{
			ScheduledFuture<?> futureResult = scheduler.schedule(mapper.get(task.getType()),
					new CronTrigger(task.getSchedulingInterval()));
			startedRunnableMap.put(task, futureResult);
		});
	}
	
	/**
	 * Get a list of Task from the database
	 * @return list - containing all task
	 */
	public List<TaskEntity> getAllTask()
	{
		return repository.findAll();
	}
	
	/**
	 * Get a Task from the database
	 * @param taskName - the name of the Task to find
	 * @return TaskEntity - the task found
	 * @exceptione NoSuchElementException - in case the task is not found
	 */
	public TaskEntity getTask(String taskName)
	{
		Optional<TaskEntity> optionalTask = repository.findById(taskName);
		if(optionalTask.isEmpty())		throw new NoSuchElementException("Task not found");
		return optionalTask.get();
	}
	
	/**
	 * Add the task to the schedule and save it to the database
	 * @param task - the task to schedule
	 * @throws IllegalArgumentException - in case some parameters are null or blank
	 */
	public void addTask(TaskEntity task)
	{
		if(null == task)
			throw new IllegalArgumentException("Task cannot be null");
		if(null == task.getTaskName())
			throw new IllegalArgumentException("Task name cannot be null");
		if(task.getTaskName().isBlank())
			throw new IllegalArgumentException("Task name cannot be blank");
		if(null == task.getSchedulingInterval())
			throw new IllegalArgumentException("Scheduling interval cannot be null");
		if(task.getSchedulingInterval().isBlank())
			throw new IllegalArgumentException("Scheduling interval cannot be blank");
		if(null == task.getType())
			throw new IllegalArgumentException("Task type cannot be null");
		if(null == task.getState())
			throw new IllegalArgumentException("Task state cannot be null");
		
		repository.save(task);
		if(task.getState() != TaskState.ACTIVE) 	return;
		ScheduledFuture<?> futureResult = scheduler.schedule(mapper.get(task.getType()),
				new CronTrigger(task.getSchedulingInterval()));
		startedRunnableMap.put(task, futureResult);
	}
	
	/**
	 * Remove the task from the schedule (it doesn't remove from db)
	 * @param task - the task that needs to be removed
	 * @throws IllegalArgumentException - in case some parameters are null or blank
	 */
	public void removeTask(TaskEntity task)
	{
		if(task == null) 				throw new IllegalArgumentException("Task cannot be null");
		if(task.getTaskName() == null)	throw new IllegalArgumentException("Task name cannot be null");
		if(task.getTaskName().isBlank())throw new IllegalArgumentException("Task name cannot be blank");
		
		Optional<TaskEntity> optionalTask = repository.findById(task.getTaskName());
		if(optionalTask.isEmpty())		throw new NoSuchElementException("Task not found");
		
		task = optionalTask.get();
		
		//verificare
		//scheduler.cancelRemainingTask(unmutableRunnableMap.get(task)); versione > 3.0 Spring
		if(task.getState().equals(TaskState.ACTIVE))
			startedRunnableMap.remove(task).cancel(false);
		
		task.setState(TaskState.ENDED);
		repository.save(task);
	}
	
	/**
	 * Modify the task from the schedule and the database
	 * @param task
	 */
	@Transactional
	public void modifyTask(TaskEntity task)
	{
		//elimino e creo nuovo
		removeTask(task);
		addTask(task);
	}
	
}
