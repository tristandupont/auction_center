package com.github.tristandupont.auction_center.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

class CreateAuctionRequest {

    private final String name;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final long startPrice;

    @JsonCreator
    CreateAuctionRequest(@JsonProperty("name") final String name,
                         @JsonProperty("description") final String description,
                         @JsonProperty("startTime") final LocalDateTime startTime,
                         @JsonProperty("endTime") final LocalDateTime endTime,
                         @JsonProperty("startPrice") final long startPrice) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
    }

    String name() {
        return name;
    }

    String description() {
        return description;
    }

    LocalDateTime startTime() {
        return startTime;
    }

    LocalDateTime endTime() {
        return endTime;
    }

    long startPrice() {
        return startPrice;
    }

}
