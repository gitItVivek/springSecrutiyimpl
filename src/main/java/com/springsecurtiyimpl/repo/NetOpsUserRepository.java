package com.springsecurtiyimpl.repo;

import com.springsecurtiyimpl.entity.NetOpsMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetOpsUserRepository extends JpaRepository<NetOpsMember , Long> {
    public List<NetOpsMember> findByIsActiveTrue();
}
