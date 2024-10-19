package it.perigea.marketdataimporter.logger.postgre.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A class to store scheduled task
 */
@Entity
@Table(name = "task")
public class TaskEntity
{
	//identificare per nome-tipo-schedulazione
	@Id
	@Column(name = "TASK_NAME", length = 50)
	private String taskName;
	
	//max length "10-59 11-59 12-23 13-31 JAN-DEC SUN-SAT 2000-2999"
	@Column(name = "SCHEDULING_INTERVAL", nullable = false, length = 49)
	private String schedulingInterval;
	
	@Column(name = "TASK_TYPE", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private TaskType type;
	
	@Column(name = "TASK_STATE", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private TaskState state;
	
	@Column(name = "INITIALIZATION_TIME", columnDefinition = "TIMESTAMP")
	private LocalDateTime initializationTime;
	
	public TaskEntity()
	{
		initializationTime = LocalDateTime.now();
	}
	
	public String getTaskName()
	{
		return taskName;
	}
	
	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}
	
	public String getSchedulingInterval()
	{
		return schedulingInterval;
	}
	
	public void setSchedulingInterval(String schedulingInterval)
	{
		this.schedulingInterval = schedulingInterval;
	}
	
	public TaskState getState()
	{
		return state;
	}
	
	public void setState(TaskState state)
	{
		this.state = state;
	}

	public TaskType getType()
	{
		return type;
	}

	public void setType(TaskType type)
	{
		this.type = type;
	}

	public LocalDateTime getInitializationTime()
	{
		return initializationTime;
	}

	public void setInitializationTime(LocalDateTime initializationTime)
	{
		this.initializationTime = initializationTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(schedulingInterval, taskName, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskEntity other = (TaskEntity) obj;
		return Objects.equals(schedulingInterval, other.schedulingInterval) && Objects.equals(taskName, other.taskName)
				&& type == other.type;
	}
	
	
}
