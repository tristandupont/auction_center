package com.github.tristandupont.auction_center.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

class BiddingWinnerInfo {

    private final boolean hasWinner;
    private final String userName;
    private final long amount;

    BiddingWinnerInfo(final boolean hasWinner,
                      final String userName,
                      final long amount) {
        this.hasWinner = hasWinner;
        this.userName = userName;
        this.amount = amount;
    }

    @JsonProperty("hasWinner")
    public boolean hasWinner() {
        return hasWinner;
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
