package com.test.EveryDayHelp.Repository;

import com.test.EveryDayHelp.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    Feedback findByEmail(String email);
}