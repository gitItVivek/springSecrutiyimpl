package com.springsecurtiyimpl.repo;

import com.springsecurtiyimpl.entity.Ticket;
import com.springsecurtiyimpl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByCreatedBy(User user);

}
