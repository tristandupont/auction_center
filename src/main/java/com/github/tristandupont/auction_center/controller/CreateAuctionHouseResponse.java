package com.github.tristandupont.auction_center.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

class CreateAuctionHouseResponse {

    private final String auctionHouseId;

    CreateAuctionHouseResponse(final String auctionHouseId) {
        this.auctionHouseId = auctionHouseId;
    }

    @JsonProperty("auctionHouseId")
    String auctionHouseId() {
        return auctionHouseId;
    }

}
