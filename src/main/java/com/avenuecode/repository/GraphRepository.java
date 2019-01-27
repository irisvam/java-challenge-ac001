package com.avenuecode.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.avenuecode.model.Graph;

/**
 * Interface Repository class to access the store data.
 */
@Repository
public interface GraphRepository extends CrudRepository<Graph, Integer> {

}
