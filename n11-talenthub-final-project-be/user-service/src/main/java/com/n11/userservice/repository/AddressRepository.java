package com.n11.userservice.repository;

import com.n11.userservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mehmet Akif Tanisik
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAddressesByUserId(Long id);
}
