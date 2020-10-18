package com.github.tristandupont.auction_center.auction.infra.adapters.secondary;

final class AuctionHouseData {

    private final String id;
    private final String name;

    private boolean deleted = false;

    AuctionHouseData(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    String id() {
        return id;
    }

    String name() {
        return name;
    }

    boolean deleted() {
        return deleted;
    }

    void markAsDeleted() {
        deleted = true;
    }

}
