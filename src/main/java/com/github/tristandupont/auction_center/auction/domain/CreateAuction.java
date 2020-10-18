package com.github.tristandupont.auction_center.auction.domain;

import java.time.LocalDateTime;

public class CreateAuction {

    private final String auctionHouseId;
    private final String name;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final long startPrice;

    public CreateAuction(final String auctionHouseId,
                         final String name,
                         final String description,
                         final LocalDateTime startTime,
                         final LocalDateTime endTime,
                         final long startPrice) {
        this.auctionHouseId = auctionHouseId;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
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
