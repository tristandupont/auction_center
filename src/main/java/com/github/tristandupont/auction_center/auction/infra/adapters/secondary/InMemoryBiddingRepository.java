package com.github.tristandupont.auction_center.auction.infra.adapters.secondary;

import com.github.tristandupont.auction_center.auction.domain.Bidding;
import com.github.tristandupont.auction_center.auction.domain.CreateBidding;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.BiddingRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryBiddingRepository implements BiddingRepository {

    private final Map<String, Collection<BiddingData>> biddings = new HashMap<>();

    @Override
    public Bidding insert(final CreateBidding createBidding) {
        final BiddingData biddingData = new BiddingData(
                createBidding.userName(),
                createBidding.amount()
        );

        final String key = key(createBidding.auctionHouseId(), createBidding.auctionId());
        biddings.computeIfAbsent(key, unused -> new ArrayList<>())
                .add(biddingData);

        return toBidding(biddingData);
    }

    @Override
    public Collection<Bidding> findByAuctionHouseIdAndAuctionId(final String auctionHouseId,
                                                                final String auctionId) {
        final String key = key(auctionHouseId, auctionId);

        return biddings.getOrDefault(key, Collections.emptyList())
                .stream()
                .map(InMemoryBiddingRepository::toBidding)
                .collect(Collectors.toList());
    }

    private static Bidding toBidding(final BiddingData biddingData) {
        return new Bidding(
                biddingData.userName(),
                biddingData.amount()
        );
    }

    private static String key(final String auctionHouseId,
                              final String auctionId) {
        return auctionHouseId + ':' + auctionId;
    }

}
