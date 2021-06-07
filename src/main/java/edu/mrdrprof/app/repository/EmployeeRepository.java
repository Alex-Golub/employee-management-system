package edu.mrdrprof.app.repository;

import edu.mrdrprof.app.io.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Golub
 * @since 06-Jun-21, 9:26 PM
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Employee findEmployeeByPublicId(String publicId);
  void deleteByPublicId(String empId);
}
