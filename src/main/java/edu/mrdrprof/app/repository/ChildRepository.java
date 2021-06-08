package edu.mrdrprof.app.repository;

import edu.mrdrprof.app.io.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 12:08 PM
 */
@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
  Child findChildByPublicId(String childId);
  List<Child> findChildrenByEmployee_PublicId(String empId);
}
