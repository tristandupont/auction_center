package com.github.tristandupont.auction_center.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

class AuctionInfo {

    private final String id;
    private final String name;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final long startPrice;

    AuctionInfo(final String id,
                final String name,
                final String description,
                final LocalDateTime startTime,
                final LocalDateTime endTime,
                final long startPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
    }

    @JsonProperty("id")
    String id() {
        return id;
    }

    @JsonProperty("name")
    String name() {
        return name;
    }

    @JsonProperty("description")
    String description() {
        return description;
    }

    @JsonProperty("startTime")
    LocalDateTime startTime() {
        return startTime;
    }

    @JsonProperty("endTime")
    LocalDateTime endTime() {
        return endTime;
    }

    @JsonProperty("startPrice")
    long startPrice() {
        return startPrice;
    }

}
