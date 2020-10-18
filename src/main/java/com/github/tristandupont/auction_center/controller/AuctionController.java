package com.github.tristandupont.auction_center.controller;

import com.github.tristandupont.auction_center.auction.domain.Auction;
import com.github.tristandupont.auction_center.auction.domain.AuctionStatus;
import com.github.tristandupont.auction_center.auction.domain.CreateAuction;
import com.github.tristandupont.auction_center.auction.domain.port.primary.AuctionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(final AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/auctionHouses/{auctionHouseId}/auctions")
    public CreateAuctionResponse createAuction(@PathVariable("auctionHouseId") final String auctionHouseId,
                                               @RequestBody final CreateAuctionRequest request) {
        final Auction auction = auctionService.createAuction(toCreateAuction(auctionHouseId, request));

        return toCreateAuctionResponse(auction);
    }

    private static CreateAuction toCreateAuction(final String auctionHouseId,
                                                 final CreateAuctionRequest request) {
        return new CreateAuction(
                auctionHouseId,
                request.name(),
                request.description(),
                request.startTime(),
                request.endTime(),
                request.startPrice()
        );
    }

    private static CreateAuctionResponse toCreateAuctionResponse(final Auction auction) {
        return new CreateAuctionResponse(auction.id());
    }

    // TODO: List all auctions based on their status (i.e. not_started, running, terminated, deleted, ...)
    @GetMapping("/auctionHouses/{auctionHouseId}/auctions")
    public List<AuctionInfo> listAuctions(@PathVariable("auctionHouseId") final String auctionHouseId,
                                          @RequestParam(value = "status", defaultValue = "NOT_DELETED")
                                          final AuctionStatusInfo auctionStatusInfo) {
        final AuctionStatus auctionStatus = toAuctionStatus(auctionStatusInfo);

        return auctionService.retrieveAuctions(auctionHouseId, auctionStatus)
                .stream()
                .map(AuctionController::toAuctionInfo)
                .collect(Collectors.toList());
    }

    private static AuctionStatus toAuctionStatus(final AuctionStatusInfo auctionStatusInfo) {
        switch (auctionStatusInfo) {
            case NOT_DELETED:
                return AuctionStatus.NOT_DELETED;
            case NOT_STARTED:
                return AuctionStatus.NOT_STARTED;
            case RUNNING:
                return AuctionStatus.RUNNING;
            case FINISHED:
                return AuctionStatus.FINISHED;
            case DELETED:
                return AuctionStatus.DELETED;
            default:
                throw new IllegalArgumentException("Non managed AuctionStatusInfo: " + auctionStatusInfo);
        }
    }

    private static AuctionInfo toAuctionInfo(final Auction auction) {
        return new AuctionInfo(
                auction.id(),
                auction.name(),
                auction.description(),
                auction.startTime(),
                auction.endTime(),
                auction.startPrice()
        );
    }

    @DeleteMapping("/auctionHouses/{auctionHouseId}/auctions/{auctionId}")
    public void deleteAuction(@PathVariable("auctionHouseId") final String auctionHouseId,
                              @PathVariable("auctionId") final String auctionId) {
        auctionService.deleteAuction(auctionHouseId, auctionId);
    }

}
