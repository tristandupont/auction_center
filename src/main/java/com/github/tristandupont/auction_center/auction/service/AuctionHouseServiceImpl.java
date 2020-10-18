package com.github.tristandupont.auction_center.auction.service;

import com.github.tristandupont.auction_center.auction.domain.AuctionHouse;
import com.github.tristandupont.auction_center.auction.domain.port.primary.AuctionHouseService;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionHouseRepository;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class AuctionHouseServiceImpl implements AuctionHouseService {

    private final AuctionHouseRepository auctionHouseRepository;
    private final AuctionRepository auctionRepository;

    public AuctionHouseServiceImpl(final AuctionHouseRepository auctionHouseRepository,
                                   final AuctionRepository auctionRepository) {
        this.auctionHouseRepository = auctionHouseRepository;
        this.auctionRepository = auctionRepository;
    }

    @Override
    public AuctionHouse createAuctionHouse(final String name) {
        return auctionHouseRepository.insert(name);
    }

    @Override
    public Collection<AuctionHouse> retrieveAllAuctionHouses() {
        return auctionHouseRepository.findAll();
    }

    @Override
    public void deleteAuctionHouse(final String auctionHouseId) {
        final boolean deleted = auctionHouseRepository.deleteById(auctionHouseId);

        if (!deleted) {
            throw new NoSuchElementException("Invalid auction-house id: " + auctionHouseId);
        }

        auctionRepository.deleteByAuctionHouseId(auctionHouseId);
    }

}
