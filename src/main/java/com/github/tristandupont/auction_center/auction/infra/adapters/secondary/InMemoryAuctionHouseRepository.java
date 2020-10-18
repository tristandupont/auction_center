package com.github.tristandupont.auction_center.auction.infra.adapters.secondary;

import com.github.tristandupont.auction_center.auction.domain.AuctionHouse;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionHouseRepository;
import com.github.tristandupont.auction_center.helper.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryAuctionHouseRepository implements AuctionHouseRepository {

    private final Map<String, AuctionHouseData> map = new HashMap<>();

    private final IdGenerator idGenerator;

    public InMemoryAuctionHouseRepository(final IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public AuctionHouse insert(final String name) {
        final String id = idGenerator.generateId();

        map.put(id, new AuctionHouseData(id, name));

        return new AuctionHouse(id, name);
    }

    @Override
    public Collection<AuctionHouse> findAll() {
        return map.values()
                .stream()
                .filter(auctionHouseData -> !auctionHouseData.deleted())
                .map(InMemoryAuctionHouseRepository::toAuctionHouse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(final String id) {
        final AuctionHouseData auctionHouseData = map.get(id);
        if ((null != auctionHouseData) && !auctionHouseData.deleted()) {
            auctionHouseData.markAsDeleted();
            return true;
        }

        return false;
    }

    @Override
    public boolean existsById(final String id) {
        final AuctionHouseData auctionHouseData = map.get(id);

        return (null != auctionHouseData) && !auctionHouseData.deleted();
    }

    private static AuctionHouse toAuctionHouse(final AuctionHouseData auctionHouseData) {
        return new AuctionHouse(
                auctionHouseData.id(),
                auctionHouseData.name()
        );
    }

}
