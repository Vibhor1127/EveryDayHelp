package com.test.EveryDayHelp.Repository;

import com.test.EveryDayHelp.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AdminRepository extends JpaRepository<Admin, String>{
            public Admin findByEmailAndPassword(String email, String password);

}