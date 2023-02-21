package com.jekfood.delivery.repository;

import com.jekfood.delivery.domain.Plates;
import com.jekfood.delivery.service.dto.PlatesDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Plates entity.
 */
@Repository
public interface PlatesRepository extends MongoRepository<Plates, String> {
    @Query("{}")
    Page<Plates> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Plates> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Plates> findOneWithEagerRelationships(String id);

    List<Plates> findAllByIdrestaurant(String idrestaurant);
}
