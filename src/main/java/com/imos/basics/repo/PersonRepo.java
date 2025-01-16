package com.imos.basics.repo;

import com.imos.basics.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface PersonRepo TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Repository
public interface PersonRepo
    extends JpaRepository<Person, Long>, PagingAndSortingRepository<Person, Long> {

  Optional<Person> findByMailId(String mailId);

  @Query(
      value =
          """
                  select new Map(p.firstName as first_name, a.state as state)
                  from Person p
                  join Address a on p.id = a.person.id
                  where p.mailId = :mailId
                  """)
  List<Map<String, Object>> findAddressState(String mailId);
}
