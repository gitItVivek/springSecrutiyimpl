package com.springsecurtiyimpl.service;

import com.springsecurtiyimpl.entity.Ticket;
import com.springsecurtiyimpl.entity.TicketCreatedEvent;
import com.springsecurtiyimpl.entity.User;
import com.springsecurtiyimpl.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;


    public Ticket createTicket(String title, String description, User currentUser) {
        Ticket ticket = Ticket.builder()
                .title(title)
                .createdBy(currentUser)
                .description(description)
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .assignedTo(null)
                .assignedAt(null)
                .build();
        Ticket ticketRaised = ticketRepository.save(ticket);

        eventPublisher.publishEvent(new TicketCreatedEvent(this, ticket));
        return ticketRaised;
    }
    public List<Ticket> getMyTickets(User currentUser) {
        return ticketRepository.findByCreatedBy(currentUser);
    }
}
