package edu.mrdrprof.app.repository;

import edu.mrdrprof.app.io.entity.Spouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 12:07 PM
 */
@Repository
public interface SpouseRepository extends JpaRepository<Spouse, Long> {
}
