package it.perigea.marketdataimporter.logger.postgre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.perigea.marketdataimporter.logger.postgre.model.Request;

/**
 * A repository to store all the request to ours Services
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Integer>
{

}
