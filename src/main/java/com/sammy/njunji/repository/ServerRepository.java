package com.sammy.njunji.repository;

import com.sammy.njunji.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Server findByIpAddress (String ipAddress);

}
