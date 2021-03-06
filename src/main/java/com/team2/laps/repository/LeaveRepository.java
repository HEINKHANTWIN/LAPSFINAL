package com.team2.laps.repository;

import java.util.List;

import com.team2.laps.model.Leave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, String> {
    @Query(value = "SELECT * FROM leaves WHERE YEAR(start_date) = YEAR(CURDATE()) AND YEAR(end_date) = YEAR(CURDATE()) AND user_id = :id ORDER BY start_date", nativeQuery = true)
    List<Leave> findCurrentYearLeaveByUserOrderByStartDate(@Param("id") String id);

    @Query(value = "SELECT leaves.* FROM leaves, users WHERE leaves.user_id = users.id AND start_date >= CURDATE() AND report_to = :id ORDER BY start_date", nativeQuery = true)
    List<Leave> findLeaveForApprovalBySubordinatesOrderByStartDate(@Param("id") String id);
}
