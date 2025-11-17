package com.springsecurtiyimpl.config;

import com.springsecurtiyimpl.entity.NetOpsMember;
import com.springsecurtiyimpl.entity.TicketCount;
import com.springsecurtiyimpl.entity.TicketCreatedEvent;
import com.springsecurtiyimpl.repo.TicketCountRepo;
import com.springsecurtiyimpl.service.TicketAissgnmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TicketAssignmentListener {



    @Autowired
    TicketAissgnmentService ticketAissgnmentService;

    @EventListener
    @Async
    public void onTicketCreated(TicketCreatedEvent event) {
        System.out.println("Event received: Ticket " + event.getTicket().getId() + " created");

        try {
            ticketAissgnmentService.assignTicketWithLeastCount(event.getTicket());
        } catch (Exception e) {
            System.out.println("Error assigning ticket:  " + e.getMessage());
        }
    }


}
