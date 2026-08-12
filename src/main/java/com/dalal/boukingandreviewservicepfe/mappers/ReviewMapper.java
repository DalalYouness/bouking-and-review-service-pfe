package com.dalal.boukingandreviewservicepfe.mappers;

import com.dalal.boukingandreviewservicepfe.dtos.request.ReviewCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ClientReviewHistoryResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReviewResponse;
import com.dalal.boukingandreviewservicepfe.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "datePublication", ignore = true)
    @Mapping(target = "reservation", ignore = true) // غانربطوها بـ Entity Reservation فـ Service Layer
    Review toEntity(ReviewCreateRequest request);

    @Mapping(source = "datePublication", target = "createdAt")
    @Mapping(source = "reservation.idClient", target = "idClient")
    @Mapping(target = "clientName", ignore = true)
    @Mapping(target = "rating", expression = "java(review.getIsRecommended() != null && review.getIsRecommended() ? 5 : 1)")
    ReviewResponse toResponse(Review review);

    @Mapping(source = "datePublication", target = "createdAt")
    @Mapping(target = "providerId", ignore = true)
    @Mapping(target = "providerName", ignore = true)
    @Mapping(target = "serviceName", ignore = true)
    @Mapping(target = "rating", expression = "java(review.getIsRecommended() != null && review.getIsRecommended() ? 5 : 1)")
    ClientReviewHistoryResponse toClientHistoryResponse(Review review);
}
