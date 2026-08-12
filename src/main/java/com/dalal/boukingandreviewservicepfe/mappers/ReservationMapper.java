package com.dalal.boukingandreviewservicepfe.mappers;

import com.dalal.boukingandreviewservicepfe.dtos.request.ReservationCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReservationResponse;
import com.dalal.boukingandreviewservicepfe.entities.Reservation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
// @Mapper annotation pour mapstruct et componentModal c'est un attribut qui precise que cette classe doit annotaté
// par @Component pour que çe mapper puisse réagir comme un bean comme ça spring va l'intégrer dans le contexte donc on peut fait ID
public interface ReservationMapper {

    // 1. Soumettre une demande (Request DTO -> Entity)
    // on peut supprimer ces annotations mais la raison ce d'éviter des warning dans les log de compilation
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dureeReel", ignore = true)
    Reservation toEntity(ReservationCreateRequest request);

    // 2. Pour tous les retours (Entity -> Response DTO)
    ReservationResponse toResponse(Reservation entity);
}