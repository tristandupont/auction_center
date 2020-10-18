package com.github.tristandupont.auction_center.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class CreateAuctionHouseRequest {

    private final String name;

    @JsonCreator
    CreateAuctionHouseRequest(@JsonProperty("name") final String name) {
        this.name = name;
    }

    String name() {
        return name;
    }

}
