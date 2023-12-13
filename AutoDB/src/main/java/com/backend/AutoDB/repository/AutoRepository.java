package com.backend.AutoDB.repository;

import com.backend.AutoDB.model.Auto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoRepository extends MongoRepository<Auto, String> {
}
