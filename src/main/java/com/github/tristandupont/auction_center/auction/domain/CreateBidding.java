package com.github.tristandupont.auction_center.auction.domain;

public class CreateBidding {

    private final String auctionHouseId;
    private final String auctionId;
    private final String userName;
    private final long amount;

    public CreateBidding(final String auctionHouseId,
                         final String auctionId,
                         final String userName,
                         final long amount) {
        this.auctionHouseId = auctionHouseId;
        this.auctionId = auctionId;
        this.userName = userName;
        this.amount = amount;
    }

    public String auctionHouseId() {
        return auctionHouseId;
    }

    public String auctionId() {
        return auctionId;
    }

    public String userName() {
        return userName;
    }

    public long amount() {
        return amount;
    }

}
