package com.github.tristandupont.auction_center.auction.domain;

import java.time.LocalDateTime;

public class Auction {

    private final String id;
    private final boolean deleted;
    private final String auctionHouseId;
    private final String name;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final long startPrice;

    public Auction(final String id,
                   final boolean deleted,
                   final String auctionHouseId,
                   final String name,
                   final String description,
                   final LocalDateTime startTime,
                   final LocalDateTime endTime,
                   final long startPrice) {
        this.id = id;
        this.auctionHouseId = auctionHouseId;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
        this.deleted = deleted;
    }

    public String id() {
        return id;
    }

    public boolean deleted() {
        return deleted;
    }

    public String auctionHouseId() {
        return auctionHouseId;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public LocalDateTime startTime() {
        return startTime;
    }

    public LocalDateTime endTime() {
        return endTime;
    }

    public long startPrice() {
        return startPrice;
    }

}
