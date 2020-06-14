package com.example.sweater.repo;

import com.example.sweater.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByActivationCode(String code);

}
