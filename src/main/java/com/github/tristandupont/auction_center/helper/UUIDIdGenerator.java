package com.github.tristandupont.auction_center.helper;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDIdGenerator implements IdGenerator {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

}
