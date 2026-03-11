package com.nana.torneos;

import com.nana.torneos.gateways.TournamentRepository;
import com.nana.torneos.tournaments.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class CreateTournamentUseCase {

    private final TournamentRepository repository;

    public Mono<Tournament> execute(Tournament request) {

        LocalDate today = LocalDate.now();

        // 1️⃣ Fecha inicio no puede ser pasada
        if (request.getStartDate().isBefore(today)) {
            return Mono.error(new IllegalArgumentException(
                    "La fecha de inicio no puede ser menor a la fecha actual"));
        }

        // 2️⃣ Fecha inicio < fecha fin
        if (request.getStartDate().isAfter(request.getEndDate())) {
            return Mono.error(new IllegalArgumentException(
                    "La fecha de inicio no puede ser mayor a la fecha de fin"));
        }

        // 3️⃣ Equipos deben ser pares
        if (request.getTeamLimit() % 2 != 0) {
            return Mono.error(new IllegalArgumentException(
                    "La cantidad de equipos debe ser un número par"));
        }

        // 4️⃣ Views > 0
        if (request.getViewLimit() <= 0) {
            return Mono.error(new IllegalArgumentException(
                    "El límite de views debe ser mayor a cero"));
        }

        // 5️⃣ Regla especial: máximo 2 torneos gratis con 20 views
        if (request.getIsFree() && request.getViewLimit() == 20) {

            return repository.countFreeWith20ViewsByOrganizer(request.getOrganizerId())
                    .flatMap(count -> {
                        if (count >= 2) {
                            return Mono.error(new IllegalStateException(
                                    "No puedes crear más de 2 torneos gratuitos con 20 views."));
                        }
                        return continueCreation(request);
                    });
        }

        return continueCreation(request);
    }

    private Mono<Tournament> continueCreation(Tournament request) {

        return repository.existsByExactData(
                        request.getName(),
                        request.getStartDate(),
                        request.getEndDate(),
                        request.getVideoGameId(),
                        request.getOrganizerId()
                )
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalStateException(
                                "Ya existe un torneo con los mismos datos."));
                    }

                    Integer generatedId =
                            ThreadLocalRandom.current()
                                    .nextInt(100_000_000, 999_999_999);
                    Tournament toSave = Tournament.builder()
                            .id(generatedId)
                            .videoGameId(request.getVideoGameId())
                            .name(request.getName().trim())
                            .startDate(request.getStartDate())
                            .endDate(request.getEndDate())
                            .isFree(request.getIsFree())
                            .teamLimit(request.getTeamLimit())
                            .viewLimit(request.getViewLimit())
                            .platformId(request.getPlatformId())
                            .categoryId(request.getCategoryId())
                            .description(request.getDescription().trim())
                            .organizerId(request.getOrganizerId())
                            .build();

                    return repository.save(toSave);
                });
    }
}