package com.github.tristandupont.auction_center.auction.infra.adapters.secondary;

import com.github.tristandupont.auction_center.auction.domain.Auction;
import com.github.tristandupont.auction_center.auction.domain.CreateAuction;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionRepository;
import com.github.tristandupont.auction_center.helper.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryAuctionRepository implements AuctionRepository {

    private final Collection<AuctionData> auctions = new ArrayList<>();

    private final IdGenerator idGenerator;

    public InMemoryAuctionRepository(final IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Auction insert(final CreateAuction createAuction) {
        final AuctionData auctionData = new AuctionData(
                idGenerator.generateId(),
                createAuction.auctionHouseId(),
                createAuction.name(),
                createAuction.description(),
                createAuction.startTime(),
                createAuction.endTime(),
                createAuction.startPrice()
        );

        auctions.add(auctionData);

        return toAuction(auctionData);
    }

    @Override
    public Collection<Auction> findByAuctionHouseIdAnyStatus(final String auctionHouseId) {
        return auctions.stream()
                .filter(auctionData -> Objects.equals(auctionHouseId, auctionData.auctionHouseId()))
                .map(InMemoryAuctionRepository::toAuction)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(final String auctionHouseId, final String auctionId) {
        final Optional<AuctionData> auctionToDelete = auctions.stream()
                .filter(auctionData -> !auctionData.deleted())
                .filter(auctionData -> filterAuction(auctionData, auctionHouseId, auctionId))
                .findAny();

        auctionToDelete.ifPresent(AuctionData::markAsDeleted);

        return auctionToDelete.isPresent();
    }

    @Override
    public Optional<Auction> find(final String auctionHouseId, final String auctionId) {
        return auctions.stream()
                .filter(auctionData -> filterAuction(auctionData, auctionHouseId, auctionId))
                .findAny()
                .map(InMemoryAuctionRepository::toAuction);
    }

    @Override
    public void deleteByAuctionHouseId(final String auctionHouseId) {
        auctions.stream()
                .filter(auctionData -> !auctionData.deleted())
                .filter(auctionData -> Objects.equals(auctionHouseId, auctionData.auctionHouseId()))
                .forEach(AuctionData::markAsDeleted);
    }

    private boolean filterAuction(final AuctionData auctionData,
                                  final String auctionHouseId,
                                  final String auctionId) {
        return Objects.equals(auctionHouseId, auctionData.auctionHouseId())
                && Objects.equals(auctionId, auctionData.id());
    }

    private static Auction toAuction(final AuctionData auctionData) {
        return new Auction(
                auctionData.id(),
                auctionData.deleted(),
                auctionData.auctionHouseId(),
                auctionData.name(),
                auctionData.description(),
                auctionData.startTime(),
                auctionData.endTime(),
                auctionData.startPrice());
    }

}
