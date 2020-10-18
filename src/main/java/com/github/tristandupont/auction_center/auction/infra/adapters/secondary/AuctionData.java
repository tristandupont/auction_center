package com.github.tristandupont.auction_center.auction.infra.adapters.secondary;

import java.time.LocalDateTime;

class AuctionData {

    private final String id;
    private final String auctionHouseId;
    private final String name;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final long startPrice;

    private boolean deleted = false;

    AuctionData(final String id,
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
    }

    String id() {
        return id;
    }

    String auctionHouseId() {
        return auctionHouseId;
    }

    String name() {
        return name;
    }

    String description() {
        return description;
    }

    LocalDateTime startTime() {
        return startTime;
    }

    LocalDateTime endTime() {
        return endTime;
    }

    long startPrice() {
        return startPrice;
    }

    boolean deleted() {
        return deleted;
    }

    void markAsDeleted() {
        deleted = true;
    }

}
