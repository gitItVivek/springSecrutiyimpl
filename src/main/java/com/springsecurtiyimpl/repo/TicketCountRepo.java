package com.springsecurtiyimpl.repo;

import com.springsecurtiyimpl.entity.NetOpsMember;
import com.springsecurtiyimpl.entity.TicketCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TicketCountRepo extends JpaRepository<TicketCount,Long> {

    /*
    @Query(value = "select * from ticket_count t JOIN net_ops_members n ON t.net_ops_member_id = n.id where t.date =:date AND n.is_active=true ORDER BY t.count ASC LIMIT 1",nativeQuery = true)
    TicketCount findLeastLoadedForDate(@Param("date") LocalDate date);
    */

    @Query(value = "SELECT n.* FROM net_ops_members n LEFT JOIN ticket_count t ON t.net_ops_member_id = n.id AND t.date = :date WHERE n.is_active = true ORDER BY COALESCE(t.count, 0) ASC LIMIT 1",nativeQuery = true)
    NetOpsMember findLeastLoadedForDate(@Param("date") LocalDate date);


    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO ticket_count (net_ops_member_id, date, count)
    VALUES (:memberId, :date, 1)
    ON DUPLICATE KEY UPDATE count = count + 1
    """, nativeQuery = true)
    void incrementOrCreateCount(@Param("memberId") Long memberId, @Param("date") LocalDate date);



    Optional<Object> findByNetOpsMemberAndDate(NetOpsMember user, LocalDate today);
}
