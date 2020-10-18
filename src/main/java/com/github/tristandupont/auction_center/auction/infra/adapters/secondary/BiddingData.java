package com.github.tristandupont.auction_center.auction.infra.adapters.secondary;

class BiddingData {

    private final String userName;
    private final long amount;

    BiddingData(final String userName, final long amount) {
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
