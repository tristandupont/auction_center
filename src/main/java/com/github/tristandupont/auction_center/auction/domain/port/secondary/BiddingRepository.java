package com.github.tristandupont.auction_center.auction.domain.port.secondary;

import com.github.tristandupont.auction_center.auction.domain.Bidding;
import com.github.tristandupont.auction_center.auction.domain.CreateBidding;

import java.util.Collection;

public interface BiddingRepository {

    Bidding insert(final CreateBidding createBidding);

    Collection<Bidding> findByAuctionHouseIdAndAuctionId(final String auctionHouseId, final String auctionId);

}
