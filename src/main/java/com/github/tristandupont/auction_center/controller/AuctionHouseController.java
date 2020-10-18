package com.github.tristandupont.auction_center.controller;

import com.github.tristandupont.auction_center.auction.domain.AuctionHouse;
import com.github.tristandupont.auction_center.auction.domain.port.primary.AuctionHouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuctionHouseController {

    private final AuctionHouseService auctionHouseService;

    public AuctionHouseController(final AuctionHouseService auctionHouseService) {
        this.auctionHouseService = auctionHouseService;
    }

    @PostMapping("/auctionHouses")
    public CreateAuctionHouseResponse createAuctionHouse(@RequestBody final CreateAuctionHouseRequest request) {
        final AuctionHouse auctionHouse = auctionHouseService.createAuctionHouse(request.name());

        return new CreateAuctionHouseResponse(auctionHouse.id());
    }

    @GetMapping("/auctionHouses")
    public List<AuctionHouseInfo> listAuctionHouses() {
        return auctionHouseService.retrieveAllAuctionHouses()
                .stream()
                .map(AuctionHouseController::toAuctionHouseInfo)
                .collect(Collectors.toList());
    }

    private static AuctionHouseInfo toAuctionHouseInfo(final AuctionHouse auctionHouse) {
        return new AuctionHouseInfo(
                auctionHouse.id(),
                auctionHouse.name()
        );
    }

    @DeleteMapping("/auctionHouses/{auctionHouseId}")
    public void deleteAuctionHouse(@PathVariable("auctionHouseId") final String auctionHouseId) {
        auctionHouseService.deleteAuctionHouse(auctionHouseId);
    }

}
