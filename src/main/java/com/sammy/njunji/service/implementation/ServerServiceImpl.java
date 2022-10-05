package com.sammy.njunji.service.implementation;

import com.sammy.njunji.models.Server;
import com.sammy.njunji.repository.ServerRepository;
import com.sammy.njunji.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.sammy.njunji.enumeration.Status.SERVER_DOWN;
import static com.sammy.njunji.enumeration.Status.SERVER_UP;
import static java.lang.Boolean.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;
    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}",  server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress );
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000 ) ?  SERVER_UP : SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server bi ID: {}", id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}",  server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID: {}", id);
        serverRepository.deleteById(id);
        return TRUE ;
    }

    private String setServerImageUrl(){
        String[] imageNames = {"image0","image1","image2","image3","image4"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/Desktop" + imageNames[new Random().nextInt(6)]).toUriString();
    }
}
