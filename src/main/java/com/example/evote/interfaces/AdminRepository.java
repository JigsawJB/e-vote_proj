package com.example.evote.interfaces;

import com.example.evote.models.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUserName(String userName);
}
