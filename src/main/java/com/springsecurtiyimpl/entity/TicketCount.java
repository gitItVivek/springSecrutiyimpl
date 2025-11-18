package com.springsecurtiyimpl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ticket_count",uniqueConstraints = @UniqueConstraint(columnNames = {"net_ops_member_id", "date"}))
public class TicketCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer count;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "netOps_member_id")
    private NetOpsMember netOpsMember;

}
