package com.test.EveryDayHelp.Repository;

import com.test.EveryDayHelp.Entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ContactRepository extends JpaRepository<Contacts, Integer>{
    void deleteById(Integer contact_id);

}