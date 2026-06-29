package com.test.EveryDayHelp.Repository;

import com.test.EveryDayHelp.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByEmail(String email);
    List<Booking> findByStatus(String status);
}