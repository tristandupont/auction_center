package com.github.tristandupont.auction_center.auction.domain.port.primary;

import com.github.tristandupont.auction_center.auction.domain.Auction;
import com.github.tristandupont.auction_center.auction.domain.AuctionStatus;
import com.github.tristandupont.auction_center.auction.domain.CreateAuction;

import java.util.Collection;

public interface AuctionService {

    Auction createAuction(final CreateAuction createAuction);

    Collection<Auction> retrieveAuctions(final String auctionHouseId, final AuctionStatus auctionStatus);

    void deleteAuction(final String auctionHouseId, final String auctionId);

}
