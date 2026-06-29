package com.test.EveryDayHelp.Repository;

import com.test.EveryDayHelp.Entity.AdminNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<AdminNotice,Integer> {
        void deleteById(Integer notice_id);
}
