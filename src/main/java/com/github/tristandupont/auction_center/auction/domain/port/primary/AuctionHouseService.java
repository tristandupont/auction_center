package com.github.tristandupont.auction_center.auction.domain.port.primary;

import com.github.tristandupont.auction_center.auction.domain.AuctionHouse;

import java.util.Collection;

public interface AuctionHouseService {

    AuctionHouse createAuctionHouse(final String name);

    Collection<AuctionHouse> retrieveAllAuctionHouses();

    void deleteAuctionHouse(final String auctionHouseId);

}
