package com.github.tristandupont.auction_center.auction.service;

import com.github.tristandupont.auction_center.auction.domain.Auction;
import com.github.tristandupont.auction_center.auction.domain.Bidding;
import com.github.tristandupont.auction_center.auction.domain.BiddingWinner;
import com.github.tristandupont.auction_center.auction.domain.CreateBidding;
import com.github.tristandupont.auction_center.auction.domain.port.primary.BiddingService;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionRepository;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.BiddingRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.github.tristandupont.auction_center.utils.TimeUtils.nowUtc;

@Service
public class BiddingServiceImpl implements BiddingService {

    private final BiddingServiceHelper biddingServiceHelper;
    private final AuctionRepository auctionRepository;
    private final BiddingRepository biddingRepository;

    public BiddingServiceImpl(final BiddingServiceHelper biddingServiceHelper,
                              final AuctionRepository auctionRepository,
                              final BiddingRepository biddingRepository) {
        this.biddingServiceHelper = biddingServiceHelper;
        this.auctionRepository = auctionRepository;
        this.biddingRepository = biddingRepository;
    }

    @Override
    public Bidding createBidding(final CreateBidding createBidding) {
        validateCreateBidding(createBidding);

        return biddingRepository.insert(createBidding);
    }

    private void validateCreateBidding(final CreateBidding createBidding) {
        final String auctionHouseId = createBidding.auctionHouseId();
        final String auctionId = createBidding.auctionId();
        auctionRepository.find(auctionHouseId, auctionId)
                .ifPresentOrElse(
                        auction -> biddingServiceHelper.validateCreateBidding(createBidding, auction),
                        () -> auctionNotFound(auctionHouseId, auctionId)
                );
    }

    @Override
    public Collection<Bidding> retrieveBiddings(final String auctionHouseId, final String auctionId) {
        return biddingRepository.findByAuctionHouseIdAndAuctionId(auctionHouseId, auctionId);
    }

    @Override
    public Optional<BiddingWinner> computeWinner(final String auctionHouseId, final String auctionId) {
        final Optional<Auction> auction = auctionRepository.find(auctionHouseId, auctionId);
        if (auction.isEmpty()) {
            auctionNotFound(auctionHouseId, auctionId);
        }

        return auction.flatMap(this::computeWinner);
    }

    public Optional<BiddingWinner> computeWinner(final Auction auction) {
        if (!isAuctionFinished(auction)) {
            throw new IllegalStateException("Cannot compute winner of non finished auction");
        }

        return biddingServiceHelper.findMaxBidding(auction)
                .map(BiddingServiceImpl::toBiddingWinner);
    }

    private static boolean isAuctionFinished(final Auction auction) {
        return nowUtc().isAfter(auction.endTime());
    }

    private static BiddingWinner toBiddingWinner(final Bidding bidding) {
        return new BiddingWinner(
                bidding.userName(),
                bidding.amount()
        );
    }

    private static void auctionNotFound(final String auctionHouseId, final String auctionId) {
        throw new NoSuchElementException("Invalid auction: " + auctionId + " for auction-house: " + auctionHouseId);
    }

}
