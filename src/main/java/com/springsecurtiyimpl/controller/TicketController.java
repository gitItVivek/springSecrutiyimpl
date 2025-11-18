package com.springsecurtiyimpl.controller;

import com.springsecurtiyimpl.entity.Ticket;
import com.springsecurtiyimpl.entity.User;
import com.springsecurtiyimpl.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.stream.Location;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/flow")
public class TicketController {

    @Autowired
    TicketService ticketService;


    @PostMapping("/raiseTicket")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {

        User currentUser = getCurrentUser();
        Ticket TicketCreated = ticketService.createTicket(ticket.getTitle(),  ticket.getDescription(),currentUser);

        URI uri = URI.create("/flow/tickets/" + TicketCreated.getId());
        System.out.println("uri is:  "+ uri);
        return ResponseEntity.created(uri).body(TicketCreated);

    }

    private User getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Auth type: " + auth.getClass().getName());
        System.out.println("Principal type: " + auth.getPrincipal().getClass().getName());
        System.out.println("Authorities: " + auth.getAuthorities());

        return (User) auth.getPrincipal();
    }
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket>allTickets = ticketService.getMyTickets(getCurrentUser());
        return ResponseEntity.ok(allTickets);
    }


}
