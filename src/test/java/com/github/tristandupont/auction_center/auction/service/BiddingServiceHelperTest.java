package com.github.tristandupont.auction_center.auction.service;

import com.github.tristandupont.auction_center.auction.domain.Auction;
import com.github.tristandupont.auction_center.auction.domain.Bidding;
import com.github.tristandupont.auction_center.auction.domain.CreateBidding;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.BiddingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BiddingServiceHelperTest {

    @Mock
    private BiddingRepository biddingRepository;

    @InjectMocks
    private BiddingServiceHelper helper;

    @Test
    void findMaxBidding() {
        // Given
        final Auction auction = mock(Auction.class);
        when(auction.auctionHouseId()).thenReturn("id-1");
        when(auction.id()).thenReturn("id-2");

        final List<Bidding> biddings = List.of(
                new Bidding("John", 100L),
                new Bidding("James", 150L),
                new Bidding("Mary", 50L)
        );

        when(biddingRepository.findByAuctionHouseIdAndAuctionId(anyString(), anyString()))
                .thenReturn(biddings);

        // When
        final Optional<Bidding> maxBidding = helper.findMaxBidding(auction);

        // Then
        assertTrue(maxBidding.isPresent());

        final Bidding bidding = maxBidding.get();
        assertEquals("James", bidding.userName());
        assertEquals(150L, bidding.amount());

        verify(biddingRepository).findByAuctionHouseIdAndAuctionId("id-1", "id-2");
    }

    @Test
    void findMaxBiddingNoBid() {
        // Given
        final Auction auction = mock(Auction.class);
        when(auction.auctionHouseId()).thenReturn("id-1");
        when(auction.id()).thenReturn("id-2");

        when(biddingRepository.findByAuctionHouseIdAndAuctionId(anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        // When
        final Optional<Bidding> maxBidding = helper.findMaxBidding(auction);

        // Then
        assertFalse(maxBidding.isPresent());

        verify(biddingRepository).findByAuctionHouseIdAndAuctionId("id-1", "id-2");
    }

    @Test
    void validateCreateBiddingAuctionNotStarted() {
        // Given
        final CreateBidding createBidding = mock(CreateBidding.class);

        final Auction auction = mock(Auction.class);
        when(auction.startTime()).thenReturn(LocalDateTime.now().plusDays(3L));

        // When
        Assertions.assertThrows(IllegalStateException.class, () -> {
            helper.validateCreateBidding(createBidding, auction);
        });
    }

    @Test
    void validateCreateBiddingAuctionFinished() {
        // Given
        final CreateBidding createBidding = mock(CreateBidding.class);

        final Auction auction = mock(Auction.class);
        when(auction.startTime()).thenReturn(LocalDateTime.now().minusDays(5L));
        when(auction.endTime()).thenReturn(LocalDateTime.now().minusDays(3L));

        // When
        Assertions.assertThrows(IllegalStateException.class, () -> {
            helper.validateCreateBidding(createBidding, auction);
        });
    }

    @Test
    void validateCreateBiddingFirstBidding() {
        // Given
        final CreateBidding createBidding = mock(CreateBidding.class);
        when(createBidding.amount()).thenReturn(75L);

        when(biddingRepository.findByAuctionHouseIdAndAuctionId(anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        final Auction auction = mock(Auction.class);
        when(auction.auctionHouseId()).thenReturn("id-1");
        when(auction.id()).thenReturn("id-2");
        when(auction.startTime()).thenReturn(LocalDateTime.now().minusDays(3L));
        when(auction.endTime()).thenReturn(LocalDateTime.now().plusDays(3L));
        when(auction.startPrice()).thenReturn(50L);

        // When
        helper.validateCreateBidding(createBidding, auction);
    }

    @Test
    void validateCreateBiddingFirstBiddingPriceTooLow() {
        // Given
        final CreateBidding createBidding = mock(CreateBidding.class);
        when(createBidding.amount()).thenReturn(40L);

        when(biddingRepository.findByAuctionHouseIdAndAuctionId(anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        final Auction auction = mock(Auction.class);
        when(auction.auctionHouseId()).thenReturn("id-1");
        when(auction.id()).thenReturn("id-2");
        when(auction.startTime()).thenReturn(LocalDateTime.now().minusDays(3L));
        when(auction.endTime()).thenReturn(LocalDateTime.now().plusDays(3L));
        when(auction.startPrice()).thenReturn(50L);

        // When
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            helper.validateCreateBidding(createBidding, auction);
        });
    }

    @Test
    void validateCreateBiddingSecondBidding() {
        // Given
        final CreateBidding createBidding = mock(CreateBidding.class);
        when(createBidding.amount()).thenReturn(150L);

        when(biddingRepository.findByAuctionHouseIdAndAuctionId(anyString(), anyString()))
                .thenReturn(List.of(new Bidding("John", 100L)));

        final Auction auction = mock(Auction.class);
        when(auction.auctionHouseId()).thenReturn("id-1");
        when(auction.id()).thenReturn("id-2");
        when(auction.startTime()).thenReturn(LocalDateTime.now().minusDays(3L));
        when(auction.endTime()).thenReturn(LocalDateTime.now().plusDays(3L));

        // When
        helper.validateCreateBidding(createBidding, auction);
    }

    @Test
    void validateCreateBiddingSecondBiddingPriceTooLow() {
        // Given
        final CreateBidding createBidding = mock(CreateBidding.class);
        when(createBidding.amount()).thenReturn(75L);

        when(biddingRepository.findByAuctionHouseIdAndAuctionId(anyString(), anyString()))
                .thenReturn(List.of(new Bidding("John", 100L)));

        final Auction auction = mock(Auction.class);
        when(auction.auctionHouseId()).thenReturn("id-1");
        when(auction.id()).thenReturn("id-2");
        when(auction.startTime()).thenReturn(LocalDateTime.now().minusDays(3L));
        when(auction.endTime()).thenReturn(LocalDateTime.now().plusDays(3L));

        // When
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            helper.validateCreateBidding(createBidding, auction);
        });
    }

}
