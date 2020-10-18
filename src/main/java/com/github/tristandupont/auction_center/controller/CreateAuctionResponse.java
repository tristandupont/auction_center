package com.github.tristandupont.auction_center.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

class CreateAuctionResponse {

    private final String auctionId;

    CreateAuctionResponse(final String auctionId) {
        this.auctionId = auctionId;
    }

    @JsonProperty("auctionId")
    String auctionId() {
        return auctionId;
    }

}
