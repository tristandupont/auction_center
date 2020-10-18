package com.github.tristandupont.auction_center.auction.domain.port.secondary;

import com.github.tristandupont.auction_center.auction.domain.Auction;
import com.github.tristandupont.auction_center.auction.domain.CreateAuction;

import java.util.Collection;
import java.util.Optional;

public interface AuctionRepository {

    Auction insert(final CreateAuction createAuction);

    Collection<Auction> findByAuctionHouseIdAnyStatus(final String auctionHouseId);

    boolean delete(final String auctionHouseId, final String auctionId);

    Optional<Auction> find(final String auctionHouseId, final String auctionId);

    void deleteByAuctionHouseId(final String auctionHouseId);

}
