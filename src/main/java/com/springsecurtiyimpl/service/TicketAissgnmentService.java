package com.springsecurtiyimpl.service;

import com.springsecurtiyimpl.entity.NetOpsMember;
import com.springsecurtiyimpl.entity.Ticket;
import com.springsecurtiyimpl.entity.TicketCount;
import com.springsecurtiyimpl.repo.NetOpsUserRepository;
import com.springsecurtiyimpl.repo.TicketCountRepo;
import com.springsecurtiyimpl.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TicketAissgnmentService {

    @Autowired
    TicketCountRepo ticketCountRepo;
    @Autowired
    TicketRepository ticketRepo;;

    @Transactional
    public NetOpsMember assignTicketWithLeastCount(Ticket ticket){
        LocalDate today = LocalDate.now();
        TicketCount rowWithLeastCount  = ticketCountRepo.findLeastLoadedForDate(today);
        NetOpsMember netOpsMember = rowWithLeastCount.getNetOpsMember();
        ticket.setAssignedAt(LocalDateTime.now());
        ticket.setAssignedTo(netOpsMember);
        ticket.setStatus("Assigned");
        ticketRepo.save(ticket);

        rowWithLeastCount.setCount(rowWithLeastCount.getCount()+1);
        ticketCountRepo.save(rowWithLeastCount);

        return netOpsMember;
    }
}
