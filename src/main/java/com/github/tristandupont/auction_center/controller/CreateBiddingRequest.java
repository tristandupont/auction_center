package com.github.tristandupont.auction_center.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class CreateBiddingRequest {

    private final String userName;
    private final long amount;

    @JsonCreator
    CreateBiddingRequest(@JsonProperty("userName") final String userName,
                         @JsonProperty("amount") final long amount) {
        this.userName = userName;
        this.amount = amount;
    }

    String userName() {
        return userName;
    }

    long amount() {
        return amount;
    }

}
