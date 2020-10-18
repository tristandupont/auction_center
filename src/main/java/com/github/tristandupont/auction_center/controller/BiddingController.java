package com.github.tristandupont.auction_center.controller;

import com.github.tristandupont.auction_center.auction.domain.Bidding;
import com.github.tristandupont.auction_center.auction.domain.BiddingWinner;
import com.github.tristandupont.auction_center.auction.domain.CreateBidding;
import com.github.tristandupont.auction_center.auction.domain.port.primary.BiddingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BiddingController {

    private static final BiddingWinnerInfo NO_WINNER = new BiddingWinnerInfo(false, null, 0L);

    private final BiddingService biddingService;

    public BiddingController(final BiddingService biddingService) {
        this.biddingService = biddingService;
    }

    @PostMapping("/auctionHouses/{auctionHouseId}/auctions/{auctionId}/biddings")
    public void createBidding(@PathVariable("auctionHouseId") final String auctionHouseId,
                              @PathVariable("auctionId") final String auctionId,
                              @RequestBody final CreateBiddingRequest request) {
        final CreateBidding createBidding = new CreateBidding(
                auctionHouseId,
                auctionId,
                request.userName(),
                request.amount()
        );

        biddingService.createBidding(createBidding);
    }

    @GetMapping("/auctionHouses/{auctionHouseId}/auctions/{auctionId}/biddings")
    public List<BiddingInfo> listBiddings(@PathVariable("auctionHouseId") final String auctionHouseId,
                                          @PathVariable("auctionId") final String auctionId) {
        return biddingService.retrieveBiddings(auctionHouseId, auctionId)
                .stream()
                .map(BiddingController::toBiddingInfo)
                .collect(Collectors.toList());
    }

    private static BiddingInfo toBiddingInfo(final Bidding bidding) {
        return new BiddingInfo(
                bidding.userName(),
                bidding.amount()
        );
    }

    @GetMapping("/auctionHouses/{auctionHouseId}/auctions/{auctionId}/biddings/winner")
    public BiddingWinnerInfo getWinner(@PathVariable("auctionHouseId") final String auctionHouseId,
                                       @PathVariable("auctionId") final String auctionId) {
        return biddingService.computeWinner(auctionHouseId, auctionId)
                .map(BiddingController::toWinnerBiddingInfo)
                .orElse(NO_WINNER);
    }

    private static BiddingWinnerInfo toWinnerBiddingInfo(final BiddingWinner biddingWinner) {
        return new BiddingWinnerInfo(
                true,
                biddingWinner.userName(),
                biddingWinner.amount()
        );
    }

}
