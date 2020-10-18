package com.github.tristandupont.auction_center.auction.domain.port.secondary;

import com.github.tristandupont.auction_center.auction.domain.AuctionHouse;

import java.util.Collection;

public interface AuctionHouseRepository {

    AuctionHouse insert(final String name);

    Collection<AuctionHouse> findAll();

    boolean deleteById(final String id);

    boolean	existsById(final String id);

}
