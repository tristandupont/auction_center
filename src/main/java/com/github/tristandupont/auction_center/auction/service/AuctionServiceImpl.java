package com.github.tristandupont.auction_center.auction.service;

import com.github.tristandupont.auction_center.auction.domain.Auction;
import com.github.tristandupont.auction_center.auction.domain.AuctionStatus;
import com.github.tristandupont.auction_center.auction.domain.CreateAuction;
import com.github.tristandupont.auction_center.auction.domain.port.primary.AuctionService;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionHouseRepository;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.tristandupont.auction_center.utils.TimeUtils.nowUtc;

@Service
public class AuctionServiceImpl implements AuctionService {

    private final AuctionHouseRepository auctionHouseRepository;
    private final AuctionRepository auctionRepository;

    public AuctionServiceImpl(final AuctionHouseRepository auctionHouseRepository,
                              final AuctionRepository auctionRepository) {
        this.auctionHouseRepository = auctionHouseRepository;
        this.auctionRepository = auctionRepository;
    }

    @Override
    public Auction createAuction(final CreateAuction createAuction) {
        validateCreateAuction(createAuction);

        return auctionRepository.insert(createAuction);
    }

    private void validateCreateAuction(final CreateAuction createAuction) {
        final String auctionHouseId = createAuction.auctionHouseId();
        final boolean auctionHouseExists = auctionHouseRepository.existsById(auctionHouseId);
        if (!auctionHouseExists) {
            throw new NoSuchElementException("Invalid auction-house id: " + auctionHouseId);
        }

        if (!createAuction.endTime().isAfter(createAuction.startTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }

    @Override
    public Collection<Auction> retrieveAuctions(final String auctionHouseId,
                                                final AuctionStatus auctionStatus) {
        if (!auctionHouseRepository.existsById(auctionHouseId)) {
            throw new NoSuchElementException("Invalid auction-house id: " + auctionHouseId);
        }

        final Predicate<Auction> auctionFilter = filterByStatus(auctionStatus);
        return auctionRepository.findByAuctionHouseIdAnyStatus(auctionHouseId)
                .stream()
                .filter(auctionFilter)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAuction(final String auctionHouseId, final String auctionId) {
        final boolean deleted = auctionRepository.delete(auctionHouseId, auctionId);

        if (!deleted) {
            throw new NoSuchElementException(
                    String.format("Cannot find auction: %s from auction-house: %s", auctionId, auctionHouseId)
            );
        }
    }

    private static Predicate<Auction> filterByStatus(final AuctionStatus auctionStatus) {
        switch (auctionStatus) {
            case NOT_DELETED:
                return auction -> !auction.deleted();

            case NOT_STARTED:
                return auction -> nowUtc().isBefore(auction.startTime());

            case RUNNING: {
                final LocalDateTime now = nowUtc();

                return auction -> now.isAfter(auction.startTime())
                        && now.isBefore(auction.endTime());
            }

            case FINISHED:
                return auction -> nowUtc().isAfter(auction.endTime());

            case DELETED:
                return Auction::deleted;

            default:
                throw new IllegalArgumentException("Non managed AuctionStatus: " + auctionStatus);
        }
    }

}
