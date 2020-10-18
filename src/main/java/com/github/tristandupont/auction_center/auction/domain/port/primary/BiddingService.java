package com.github.tristandupont.auction_center.auction.domain.port.primary;

import com.github.tristandupont.auction_center.auction.domain.Bidding;
import com.github.tristandupont.auction_center.auction.domain.BiddingWinner;
import com.github.tristandupont.auction_center.auction.domain.CreateBidding;

import java.util.Collection;
import java.util.Optional;

public interface BiddingService {

    Bidding createBidding(final CreateBidding createBidding);

    Collection<Bidding> retrieveBiddings(final String auctionHouseId, final String auctionId);

    Optional<BiddingWinner> computeWinner(final String auctionHouseId, final String auctionId);

}
