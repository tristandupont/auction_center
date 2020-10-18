package com.github.tristandupont.auction_center.auction.service;

import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionHouseRepository;
import com.github.tristandupont.auction_center.auction.domain.port.secondary.AuctionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionHouseServiceImplTest {

    @Mock
    private AuctionHouseRepository auctionHouseRepository;
    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    private AuctionHouseServiceImpl service;

    @Test
    void deleteAuctionHouse() {
        // Given
        when(auctionHouseRepository.deleteById(anyString()))
                .thenReturn(true);

        // When
        service.deleteAuctionHouse("id-123");

        // Then
        verify(auctionHouseRepository).deleteById("id-123");

        verify(auctionRepository).deleteByAuctionHouseId("id-123");
    }

    @Test
    void deleteAuctionHouseNotFound() {
        // Given
        when(auctionHouseRepository.deleteById(anyString()))
                .thenReturn(false);

        // When
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            service.deleteAuctionHouse("id-123");
        });

        // Then
        verify(auctionHouseRepository).deleteById("id-123");

        verify(auctionRepository, never()).deleteByAuctionHouseId(anyString());
    }

}
