package com.github.tristandupont.auction_center.auction.service;

import com.github.tristandupont.auction_center.auction.domain.Auction;
import com.github.tristandupont.auction_center.auction.domain.Bidding;
import com.github.tristandupont.auction_center.auction.domain.CreateBidding;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.BiddingRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

import static com.github.tristandupont.auction_center.utils.TimeUtils.nowUtc;

@Component
class BiddingServiceHelper {

    private static final Comparator<Bidding> HIGHEST_BIDDING = Comparator.comparingLong(Bidding::amount);

    private final BiddingRepository biddingRepository;

    public BiddingServiceHelper(final BiddingRepository biddingRepository) {
        this.biddingRepository = biddingRepository;
    }

    Optional<Bidding> findMaxBidding(final Auction auction) {
        return biddingRepository.findByAuctionHouseIdAndAuctionId(auction.auctionHouseId(), auction.id())
                .stream()
                .max(HIGHEST_BIDDING);
    }

    void validateCreateBidding(final CreateBidding createBidding,
                               final Auction auction) {
        validateAuctionIsOpen(auction);

        findMaxBidding(auction).ifPresentOrElse(
                maxBidding -> validateOverbid(createBidding, maxBidding),
                () -> validateFirstBid(createBidding, auction)
        );
    }

    private static void validateAuctionIsOpen(final Auction auction) {
        final LocalDateTime now = nowUtc();
        if (now.isBefore(auction.startTime())) {
            throw new IllegalStateException("Cannot bid on an auction not yet started");
        }
        if (now.isAfter(auction.endTime())) {
            throw new IllegalStateException("Cannot bid on a finished auction");
        }
    }

    private static void validateOverbid(final CreateBidding createBidding, final Bidding maxBidding) {
        if (maxBidding.amount() >= createBidding.amount()) {
            throw new IllegalArgumentException("New bidding must be higher than previous one");
        }
    }

    private static void validateFirstBid(final CreateBidding createBidding, final Auction auction) {
        if (auction.startPrice() > createBidding.amount()) {
            throw new IllegalArgumentException("First bidding must be equal or higher to start-price");
        }
    }

}
