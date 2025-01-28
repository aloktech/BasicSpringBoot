package com.imos.basics.repo;

import com.imos.basics.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface AddressRepo TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

  @Transactional
  @Query("delete from Address a where a.person.mailId = :mailId")
  @Modifying
  int deleteByPersonMailId(String mailId);
}
