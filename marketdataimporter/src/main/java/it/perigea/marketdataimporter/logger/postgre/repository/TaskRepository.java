package it.perigea.marketdataimporter.logger.postgre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.perigea.marketdataimporter.logger.postgre.model.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, String>
{
	//troppo hardcoded?
	@Query(nativeQuery = true, value = "SELECT * FROM TASK WHERE TASK_STATE='ACTIVE'")
	public List<TaskEntity> getRunningTask(); 
}
