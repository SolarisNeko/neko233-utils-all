package com.neko233.event.delegate;

import com.neko233.mockData.MockDataDto;

public class TestEventListener implements EventListener<MockDataDto> {

    @Override
    public void handle(MockDataDto event) {
        System.out.println(event);
    }

}