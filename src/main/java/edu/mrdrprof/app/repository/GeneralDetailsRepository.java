package edu.mrdrprof.app.repository;

import edu.mrdrprof.app.io.entity.GeneralDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 12:06 PM
 */
@Repository
public interface GeneralDetailsRepository extends JpaRepository<GeneralDetails, Long> {
  GeneralDetails findGeneralDetailsByEmailAndSsn(String email, String ssn);
}
