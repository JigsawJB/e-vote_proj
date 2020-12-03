package com.example.evote.interfaces;

import com.example.evote.models.Election;
import org.springframework.data.repository.CrudRepository;

public interface ElectionRepository extends CrudRepository<Election, Long> {
    Election findByName(String name);
}
