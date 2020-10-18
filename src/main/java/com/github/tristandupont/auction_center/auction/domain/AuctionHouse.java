package com.github.tristandupont.auction_center.auction.domain;

public class AuctionHouse {

    private final String id;
    private final String name;

    public AuctionHouse(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

}
