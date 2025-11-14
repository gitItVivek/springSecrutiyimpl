package com.springsecurtiyimpl.service;

import com.springsecurtiyimpl.entity.Ticket;
import com.springsecurtiyimpl.entity.User;
import com.springsecurtiyimpl.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;

    public Ticket createTicket(String title, String description, User currentUser) {
        Ticket ticket = Ticket.builder()
                .title(title)
                .createdBy(currentUser)
                .description(description)
                .status("OPEN")
                .build();
        return ticketRepository.save(ticket);
    }
    public List<Ticket> getMyTickets(User currentUser) {
        return ticketRepository.findByCreatedBy(currentUser);
    }
}
