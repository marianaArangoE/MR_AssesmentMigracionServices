package com.nana.torneos;

import com.nana.torneos.gateways.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
@Component
@RequiredArgsConstructor
public class GetTeamsUseCase {

    private final TeamRepository teamRepository;

    public Flux<Teams> execute() {
        return teamRepository.findAll();
    }
}