package com.github.tristandupont.auction_center.auction.domain;

public class BiddingWinner {

    private final String userName;
    private final long amount;

    public BiddingWinner(final String userName, final long amount) {
        this.userName = userName;
        this.amount = amount;
    }

    public String userName() {
        return userName;
    }

    public long amount() {
        return amount;
    }

}
