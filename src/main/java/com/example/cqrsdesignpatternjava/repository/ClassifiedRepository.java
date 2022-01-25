package com.example.cqrsdesignpatternjava.repository;

import com.example.cqrsdesignpatternjava.entity.Classified;
import org.springframework.data.repository.CrudRepository;

public interface ClassifiedRepository extends CrudRepository<Classified, Long> {
}
