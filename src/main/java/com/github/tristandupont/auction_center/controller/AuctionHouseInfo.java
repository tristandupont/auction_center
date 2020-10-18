package com.github.tristandupont.auction_center.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

class AuctionHouseInfo {

    private final String id;
    private final String name;

    AuctionHouseInfo(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("id")
    String id() {
        return id;
    }

    @JsonProperty("name")
    String name() {
        return name;
    }

}
