package com.springsecurtiyimpl.repo;

import com.springsecurtiyimpl.entity.NetOpsMember;
import com.springsecurtiyimpl.entity.TicketCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TicketCountRepo extends JpaRepository<TicketCount,Long> {


    @Query(value = "select * from ticket_count t JOIN netOps_members n ON d.netOps_members_id = n.id where d.date =:date AND n.is_active=true ORDER BY d.count ASC LIMIT 1")
    TicketCount findLeastLoadedForDate(@Param("date") LocalDate date);

    Optional<Object> findByNetOpsMemberAndDate(NetOpsMember user, LocalDate today);
}
