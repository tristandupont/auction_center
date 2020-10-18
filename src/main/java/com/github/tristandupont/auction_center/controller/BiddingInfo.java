package com.github.tristandupont.auction_center.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

class BiddingInfo {

    private final String userName;
    private final long amount;

    BiddingInfo(final String userName,
                final long amount) {
        this.userName = userName;
        this.amount = amount;
    }

    @JsonProperty("userName")
    String userName() {
        return userName;
    }

    @JsonProperty("amount")
    long amount() {
        return amount;
    }

}
