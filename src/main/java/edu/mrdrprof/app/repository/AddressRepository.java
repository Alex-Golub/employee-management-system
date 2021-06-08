package edu.mrdrprof.app.repository;

import edu.mrdrprof.app.io.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex Golub
 * @since 07-Jun-21, 12:10 PM
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
  Address findAddressByPublicId(String addressId);
  List<Address> findAddressesByEmployee_PublicId(String empId);
}
