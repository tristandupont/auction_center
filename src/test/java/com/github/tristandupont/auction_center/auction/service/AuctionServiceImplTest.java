package com.github.tristandupont.auction_center.auction.service;

import com.github.tristandupont.auction_center.auction.domain.Auction;
import com.github.tristandupont.auction_center.auction.domain.CreateAuction;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionHouseRepository;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionServiceImplTest {

    @Mock
    private AuctionHouseRepository auctionHouseRepository;
    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    private AuctionServiceImpl service;

    @Test
    void createAuction() {
        // Given
        final CreateAuction createAuction = mock(CreateAuction.class);
        when(createAuction.auctionHouseId()).thenReturn("id-4");
        when(createAuction.startTime()).thenReturn(LocalDateTime.now().minusDays(1L));
        when(createAuction.endTime()).thenReturn(LocalDateTime.now().plusDays(1L));

        when(auctionHouseRepository.existsById(anyString())).thenReturn(true);

        final Auction auction = mock(Auction.class);
        when(auctionRepository.insert(any())).thenReturn(auction);

        // When
        final Auction result = service.createAuction(createAuction);

        // Then
        assertEquals(auction, result);

        verify(auctionHouseRepository).existsById("id-4");

        verify(auctionRepository).insert(createAuction);
    }

    @Test
    void createAuctionInvalidDates() {
        // Given
        final CreateAuction createAuction = mock(CreateAuction.class);
        when(createAuction.auctionHouseId()).thenReturn("id-4");
        when(createAuction.startTime()).thenReturn(LocalDateTime.now().plusDays(1L));
        when(createAuction.endTime()).thenReturn(LocalDateTime.now().minusDays(1L));

        when(auctionHouseRepository.existsById(anyString())).thenReturn(true);

        // When
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.createAuction(createAuction);
        });

        // Then
        verify(auctionHouseRepository).existsById("id-4");

        verify(auctionRepository, never()).insert(any());
    }

    @Test
    void createAuctionInvalidAuctionHouse() {
        // Given
        final CreateAuction createAuction = mock(CreateAuction.class);
        when(createAuction.auctionHouseId()).thenReturn("id-4");

        when(auctionHouseRepository.existsById(anyString())).thenReturn(false);

        // When
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            service.createAuction(createAuction);
        });

        // Then
        verify(auctionHouseRepository).existsById("id-4");

        verify(auctionRepository, never()).insert(any());
    }

}
