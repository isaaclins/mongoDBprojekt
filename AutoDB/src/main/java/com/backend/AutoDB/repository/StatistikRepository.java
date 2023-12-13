package com.backend.AutoDB.repository;

import com.backend.AutoDB.model.Statistik;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatistikRepository extends MongoRepository<Statistik, String> {
}
