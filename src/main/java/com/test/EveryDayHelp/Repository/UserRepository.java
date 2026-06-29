package com.test.EveryDayHelp.Repository;

import com.test.EveryDayHelp.Entity.Admin;
import com.test.EveryDayHelp.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetails,String> {
    public UserDetails findByEmailAndPassword(String email, String password);
    void deleteByEmail(String email);
boolean existsByEmail(String email);
    UserDetails findByEmail(String email);
}
